package com.ksyun.ic.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksyun.payment.common.AppConfig;
import com.ksyun.payment.common.PaymentCommonUtils;
import com.ksyun.payment.common.ProtocolOrderBean;
import com.ksyun.payment.dto.OrderDto;
import com.ksyun.payment.dto.UserDto;
import com.ksyun.payment.dto.UserInfoDto;
import com.ksyun.payment.service.ICashSerialService;
import com.ksyun.payment.service.IOrderItemService;
import com.ksyun.payment.service.IOrderService;
import com.ksyun.payment.service.ISequenceService;
import com.ksyun.tools.DateUtil;
import com.ksyun.tools.net.ServletUtil;
import com.ksyun.vm.utils.UserService;

/**
 * order 订单控制
 * @author ZhangYanchun
 * @date   2013-11-26
 */
@Controller
@RequestMapping("/admin")
public class OrderController extends BaseController {

	@Resource
	private IOrderService orderService;
    
	@Resource
	private IOrderItemService orderItemService;
	
	@Resource
	private AmqpTemplate amqpTemplate;
	
	@Resource
	private UserService userService; 
	
	@Resource
	private ISequenceService  sequenceService;
	
	@Resource
	private ICashSerialService cashSerialService;
	
	private String queueName = "control_kvm_q";
	
	//订单列表（后台功能模块）
	@RequestMapping("/order/order_index")
	public String order_index(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		short productType = (short)ServletUtil.getInt(req, "productType", 0);
		short orderType = (short)ServletUtil.getInt(req, "orderType", 0);
		short status = (short)ServletUtil.getInt(req, "status", 0);
		long userId = ServletUtil.getLong(req, "userId", 0);
		String stime = ServletUtil.getStr(req, "stime");
		String etime = ServletUtil.getStr(req, "etime");
		String orderId = ServletUtil.getStr(req, "orderId");
		int ss = ServletUtil.getInt(req, "ss", -1);
		int se = ServletUtil.getInt(req, "se", 0);
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(productType != 0) {
			params.put("productType", productType);
		}
		if(orderType != 0) {
			params.put("orderType", orderType);
		}
		if(userId != 0) {
			params.put("userId", userId);
		}
		if(status != 0) {
			params.put("status", status);
		}
		if(!"".equals(orderId)) {
			params.put("orderId", orderId);
		}
		Date subtime = DateUtil.changeyyyyMMddStrToDate(stime);
		Date esubtime = DateUtil.changeyyyyMMddStrToDate(etime);
		if(subtime != null) {
			params.put("stime", stime);
			params.put("startTime", DateUtil.getDateBeginTime(subtime));
			if(esubtime ==  null) {
				params.put("endTime", DateUtil.getDateEndTime(subtime));
			} else {
				params.put("endTime", DateUtil.getDateEndTime(esubtime));
			}
			
		}
		req.setAttribute("userId", userId);
		req.setAttribute("orderId", orderId);
		req.setAttribute("stime", stime);
		req.setAttribute("etime", etime);
		req.setAttribute("productType", productType);
		req.setAttribute("orderType", orderType);
		req.setAttribute("status", status);
		req.setAttribute("ss", ss);
		req.setAttribute("se", se);
		Map<String, Integer> sortMap = new LinkedHashMap<String, Integer>();
		
		if(ss != 0) sortMap.put("submit_time", ss);
		if(se != 0) sortMap.put("service_end_time", se);
		
		String sortString = PaymentCommonUtils.changeToSortStr(sortMap);
		req.setAttribute("pageList", orderService.getAllOrders(params, sortString));
		return "admin/order/order_index";
	}
	//订单详细配置
	@RequestMapping("/order/order_detail")
	public String order_detail(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String orderId = ServletUtil.getStr(req, "orderId");
		req.setAttribute("items", orderItemService.getAllOrderItemsNoCache(orderId));
		return "admin/order/order_detail";
	}
	//更新订单状态输入页面
	@RequestMapping("/order/order_change_status")
	public String order_change_status(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String orderId = ServletUtil.getStr(req, "orderId");
		req.setAttribute("orderId", orderId);
		return "admin/order/order_change_status";
	}
	
	//更改订单状态
	@RequestMapping("/order/order_change_status_do")
	public String order_change_status_do(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String orderId = ServletUtil.getStr(req, "orderId");
		String productId = ServletUtil.getStr(req, "productId");
		int ret = 0;
		if(!"".equals(productId)) {
			orderService.updateOrderProduct(orderId, productId);
			ret = 1;
		}
		req.setAttribute("ret", ret);
		return "admin/opt_rst";
	}
	
	//作废订单确认页面
	@RequestMapping("/order/order_destroy_confirm")
	public String order_destroy_confirm(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String orderId = ServletUtil.getStr(req, "orderId");
		req.setAttribute("orderId", orderId);
		return "admin/order/order_destroy_confirm";
	}
	
	
	//作废订单确认
	@RequestMapping("/order/order_destroy")
	public String order_destroy(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String orderId = ServletUtil.getStr(req, "orderId");
		
		OrderDto order = orderService.findOrderById(orderId);
		if(order != null) {
			long userId = order.getUserId();
			UserInfoDto userInfo = userService.findUserInfo(userId);
			String email = userInfo.getEmail();
			short productType = order.getProductType();
			String productId = order.getProductId();
			String stime = DateUtil.getAllStrDate(new Date());
			String msg = "";
			if(productType == 1) {
				msg = AppConfig.DESTROY_KVM;
			}
			if(productType == 3) {
				msg = AppConfig.DESTROY_EBS;
			}
			String jmsMsg  =  String.format(msg, productId, userId, email, stime);
			amqpTemplate.convertAndSend(queueName, jmsMsg);
			logger.info(String.format("send to queue %s %s", queueName, jmsMsg));
			//更改数据库标志
			orderService.cancelOrder(orderId);
		}
		return "admin/opt_rst";
	}
		
	//作废订单确认页面
	@RequestMapping("/order/order_protocol_input")
	public String order_protocol_input(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String orderId = ServletUtil.getStr(req, "orderId");
		req.setAttribute("orderId", orderId);
		return "admin/order/order_protocol_input";
	}
	
	//作废订单确认页面(直接开通)
	@RequestMapping("/order/order_protocol_start_input")
	public String order_protocol_start_input(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String orderId = ServletUtil.getStr(req, "orderId");
		req.setAttribute("orderId", orderId);
		return "admin/order/order_protocol_start_input";
	}
	
	//协议订单页面
	@RequestMapping("/order/order_protocol")
	public String order_protocol(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		int ret = 0;
		String retStr = "admin/opt_ret_rst";
		//获取消息内容
		long userId = ServletUtil.getLong(req, "userId", 0);
		String kvmId = ServletUtil.getStr(req, "kvmId");
		int cpu = ServletUtil.getInt(req, "cpu", 0);
		double mem = ServletUtil.getDouble(req, "mem", 0);
		int net = ServletUtil.getInt(req, "net", 0);
		String skvmEndTime = ServletUtil.getStr(req, "kvmEndTime");
		BigDecimal kvmPrice = ServletUtil.getDecimal(req, "kvmPrice");
		
		String ebsId = ServletUtil.getStr(req, "ebsId");
		int ebs = ServletUtil.getInt(req, "ebs", 0);
		String sebsEndTime = ServletUtil.getStr(req, "ebsEndTime");
		BigDecimal ebsPrice = ServletUtil.getDecimal(req, "ebsPrice");
    
		UserDto user = userService.getUserAccount(userId);
		if(user == null) {
			req.setAttribute("ret", -1001);
			return retStr;
		}
		
		BigDecimal totalCost = kvmPrice.add(ebsPrice);
		
		BigDecimal balance = user.getBalance();
		if(balance.compareTo(totalCost) < 0) {
			req.setAttribute("ret", -1002);
			return retStr;
		}
		
		Date now = new Date();
		ProtocolOrderBean pob = new ProtocolOrderBean();
		pob.setUserId(userId);
		pob.setStatus((short)3);
		String kvmOrderId = "";
		if(!"".equals(kvmId)) {
			pob.setCpu(cpu);
			pob.setMem(mem);
			pob.setNet(net);
			pob.setKvmId(kvmId);
			pob.setKvmPrice(kvmPrice);
			Date kvmServiceEndTime = DateUtil.getDateEndTime(DateUtil.changeyyyyMMddStrToDate(skvmEndTime));
			pob.setKvmEndTime(kvmServiceEndTime);
			kvmOrderId = PaymentCommonUtils.gerOrderId(sequenceService.incrNum(), now, (short)1);
		}
		
		String ebsOrderId = "";
		if(!"".equals(ebsId)) {
			pob.setEbs(ebs);
			pob.setEbsId(ebsId);
			pob.setEbsPrice(ebsPrice);
			pob.setEbsEndTime(DateUtil.getDateEndTime(DateUtil.changeyyyyMMddStrToDate(sebsEndTime)));
			ebsOrderId = PaymentCommonUtils.gerOrderId(sequenceService.incrNum(), now, (short)3);
		}
		
		String cashSerial = "";
		if(totalCost.compareTo(BigDecimal.ZERO) > 0) {
			cashSerial = (new StringBuilder(cashSerialService.incrSerialNum()).append("b")).toString();
		}
		
		orderService.saveProtocolOrder(user, pob, kvmOrderId, ebsOrderId, cashSerial);
		
		req.setAttribute("ret", ret);
	    return retStr;
	     
		
		
	}
	
	
	//协议订单页面
	@RequestMapping("/order/order_protocol_start")
	public String order_protocol_start(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		int ret = 0;
		String retStr = "admin/opt_ret_rst";
		//获取消息内容
		long userId = ServletUtil.getLong(req, "userId", 0);
		String skvm = ServletUtil.getStr(req, "skvm");
		int cpu = ServletUtil.getInt(req, "cpu", 0);
		double mem = ServletUtil.getDouble(req, "mem", 0);
		int net = ServletUtil.getInt(req, "net", 0);
		String image = ServletUtil.getStr(req, "image");
		String skvmEndTime = ServletUtil.getStr(req, "kvmEndTime");
		BigDecimal kvmPrice = ServletUtil.getDecimal(req, "kvmPrice");
		
		String sebs = ServletUtil.getStr(req, "sebs");
		int ebs = ServletUtil.getInt(req, "ebs", 0);
		String sebsEndTime = ServletUtil.getStr(req, "ebsEndTime");
		BigDecimal ebsPrice = ServletUtil.getDecimal(req, "ebsPrice");
    
		UserDto user = userService.getUserAccount(userId);
		if(user == null) {
			req.setAttribute("ret", -1001);
			return retStr;
		}
		
		BigDecimal totalCost = kvmPrice.add(ebsPrice);
		
		BigDecimal balance = user.getBalance();
		if(balance.compareTo(totalCost) < 0) {
			req.setAttribute("ret", -1002);
			return retStr;
		}
		
		Date now = new Date();
		ProtocolOrderBean pob = new ProtocolOrderBean();
		pob.setUserId(userId);
		pob.setStatus((short)2);
		String kvmOrderId = "";
		if("yes".equals(skvm)) {
			pob.setCpu(cpu);
			pob.setMem(mem);
			pob.setNet(net);
			pob.setKvmId("");
			pob.setKvmPrice(kvmPrice);
			Date kvmServiceEndTime = DateUtil.getDateEndTime(DateUtil.changeyyyyMMddStrToDate(skvmEndTime));
			pob.setKvmEndTime(kvmServiceEndTime);
			kvmOrderId = PaymentCommonUtils.gerOrderId(sequenceService.incrNum(), now, (short)1);
		}
		
		String ebsOrderId = "";
		if("yes".equals(sebs)) {
			pob.setEbs(ebs);
			pob.setEbsId("");
			pob.setEbsPrice(ebsPrice);
			pob.setEbsEndTime(DateUtil.getDateEndTime(DateUtil.changeyyyyMMddStrToDate(sebsEndTime)));
			ebsOrderId = PaymentCommonUtils.gerOrderId(sequenceService.incrNum(), now, (short)3);
		}
		
		String cashSerial = "";
		if(totalCost.compareTo(BigDecimal.ZERO) > 0) {
			cashSerial = (new StringBuilder(cashSerialService.incrSerialNum()).append("b")).toString();
		}
		
		if(!"yes".equals(skvm) && !"yes".equals(sebs)) {
			req.setAttribute("ret", -1003);
			return retStr;
		}
		
		orderService.saveProtocolOrder(user, pob, kvmOrderId, ebsOrderId, cashSerial);
		UserInfoDto uinfo = userService.findUserInfo(userId);
		//发送开通消息至jms队列
		String jmsMsg = String.format(AppConfig.START_KVM, kvmOrderId, DateUtil.getAllStrDate(pob.getKvmEndTime()), image, new Double(pob.getMem() * 1024).intValue(), pob.getCpu(), pob.getNet(), ebsOrderId, pob.getEbs(), DateUtil.getAllStrDate(pob.getEbsEndTime()), userId, uinfo.getEmail(), DateUtil.getAllStrDate(now));
		amqpTemplate.convertAndSend(queueName, jmsMsg);
		logger.info(String.format("send to start kvm queue %s", jmsMsg));
		req.setAttribute("ret", ret);
	    return retStr;
	}
	
	
	//延长订单有效期（仅适用试用订单）
	@RequestMapping("/order/order_extend_input")
	public String order_extend_input(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String orderId = ServletUtil.getStr(req, "orderId");
		req.setAttribute("orderId", orderId);
		return "admin/order/order_extend_input";
	}
	
	//延长订单有效期（仅适用试用订单）
	@RequestMapping("/order/order_extend")
	public String order_extend(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String orderId = ServletUtil.getStr(req, "orderId");
	    int day = ServletUtil.getInt(req, "day", 0);
	    if(day != 0) {
	    	orderService.updateApplicationOrderServiceEndTime(orderId, day);
	    }
		req.setAttribute("ret", 0);
		return "admin/opt_ret_rst";
	}
	
}
