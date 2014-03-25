package com.ksyun.ic.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksyun.payment.common.KvmBean;
import com.ksyun.payment.common.KvmHttpUtil;
import com.ksyun.payment.common.MsgModel;
import com.ksyun.payment.common.PaymentCommonUtils;
import com.ksyun.payment.common.RestapiRet;
import com.ksyun.payment.dto.KvmApplicationDto;
import com.ksyun.payment.dto.UserDto;
import com.ksyun.payment.service.IKvmApplicationService;
import com.ksyun.payment.service.IKvmPriceService;
import com.ksyun.payment.service.IOrderService;
import com.ksyun.payment.service.ISequenceService;
import com.ksyun.tools.DateUtil;
import com.ksyun.tools.net.ServletUtil;

/**
 * kvm 申请试用控制
 * @author ZhangYanchun
 * @date   2013-10-28
 */
@Controller
@RequestMapping("/admin")
public class KvmApplicationController extends BaseController {

	private static Logger logger = Logger.getLogger(KvmApplicationController.class);
	
	@Resource
	private IKvmApplicationService kvmApplicationService;
	
	@Resource
	private IOrderService orderService;

	@Resource
	private ISequenceService  sequenceService;
	
	@Resource
	private IKvmPriceService kvmPriceService;
	
	@Resource
	private   AmqpTemplate amqpTemplate;
	
	private final static String SMS_QUEUE_NAME = "sms_q";
	
	private final static String EMAIL_QUEUE_NAME = "mail_q";
	
	//试用申请页面
	@RequestMapping("/kvm/application_index")
	public String application_input(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String phone = ServletUtil.getStr(request, "phone");
		Map<String, Object> params = new HashMap<String, Object>();
		if(!"".equals(phone)){
			params.put("phone", phone);
		}
		request.setAttribute("phone", phone);
		request.setAttribute("pageList", kvmApplicationService.getAllApplications(params));
		return "admin/kvm/application_index";
	}
	
	
	@RequestMapping("/kvm/application_approval_input")
	public String approval_application_input(HttpServletRequest req, HttpServletResponse response){
		long id = ServletUtil.getLong(req, "id", 0);
		req.setAttribute("id", id);

		String phone = ServletUtil.getStr(req, "phone");
		Map<String, Object> params = new HashMap<String, Object>();
		if(!"".equals(phone)){
			params.put("phone", phone);
		}
		req.setAttribute("phone", phone);
		req.setAttribute("pageList", kvmApplicationService.getAllApplications(params));
		
		
		return "admin/kvm/application_approval_input";
	}
	
	//审核申请
	@RequestMapping("/kvm/application_approval")
	public String approval_application(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		
		long id = ServletUtil.getLong(req, "id", 0);
		int month = ServletUtil.getInt(req, "month", 0);
		int day = ServletUtil.getInt(req, "day", 0);
		
		KvmApplicationDto application = kvmApplicationService.findById(id);
		if(application != null && application.getStatus() == 0) {
			Date now = new Date();
			String orderId = PaymentCommonUtils.gerOrderId(sequenceService.incrNum(), now, (short)1);
			UserDto user = new UserDto();
			user.setUserId(application.getUserId());
			user.setName(application.getEmail());
			
			KvmBean bean = new KvmBean();
			bean.setCpu(application.getCpu());
			bean.setEbs(application.getEbs());
			bean.setMem(application.getMem());
			bean.setNet(application.getNet());
			bean.setOs(application.getOps());
			bean.setOrderType((short)1);
			bean.setProductType((short)1);
			bean.setMonth(month);
			bean.setDay(day);
			
			Map<String, KvmBean> orderItems = new HashMap<String, KvmBean>();
			orderItems.put(orderId, bean);
			RestapiRet priceRet = kvmPriceService.calcPrice(bean);
			int totalPrice = (Integer)(priceRet.getData().get(0).get("totalPrice"));
			int ebsPrice = (Integer)(priceRet.getData().get(0).get("ebsPrice"));
			bean.setPrice(totalPrice);
			bean.setRealPrice(0);
			bean.setEbsPrice(ebsPrice);
			orderService.payOrder(user, orderItems, new BigDecimal(totalPrice), BigDecimal.ZERO, "");
			//发送请求
			MsgModel msg = new MsgModel();
			msg.setBizType("applyNotify");
			msg.setMsgType("sms");
            msg.setAddress(application.getPhone());
            msg.setTimestamp(DateUtil.getAllStrDate(new Date()));
			Map<String, String> map = new HashMap<String, String>();
			map.put("userName", application.getUname());
			map.put("days", String.valueOf(day));
			msg.setDataMap(map);
			amqpTemplate.convertAndSend(SMS_QUEUE_NAME, msg.toString());
			logger.info(String.format("send to queue %s %s", SMS_QUEUE_NAME, msg));
			
			msg.setMsgType("mail");
			msg.setAddress(application.getEmail());
			amqpTemplate.convertAndSend(EMAIL_QUEUE_NAME, msg.toString());
			logger.info(String.format("send to queue %s %s", EMAIL_QUEUE_NAME, msg));
			
			kvmApplicationService.updateStatus(id);
		}
		return "admin/opt_rst";
	}
	
	
	@RequestMapping("/kvm/application_change_os_input")
	public String approval_change_os_input(HttpServletRequest req, HttpServletResponse response){
		long id = ServletUtil.getLong(req, "id", 0);
		req.setAttribute("id", id);
		
		KvmHttpUtil kvmHttpUtil = new KvmHttpUtil();
		Map<String,String> opsMap = kvmHttpUtil.getRawImageList();
		req.setAttribute("opsMap", opsMap);
		
		return "admin/kvm/application_change_os_input";
	}
	
	@RequestMapping("/kvm/application_change_os")
	public String approval_change_os(HttpServletRequest req, HttpServletResponse response){
		long id = ServletUtil.getLong(req, "id", 0);
		String opsId = ServletUtil.getString(req, "ops");

		if(id<=0||opsId==null||opsId.trim().length()==0){
			req.setAttribute("ret", 1);
			return "admin/opt_ret_rst";
		}
		
		kvmApplicationService.updateKvmOps(id, opsId);
		
		req.setAttribute("ret", 0);
		return "admin/opt_ret_rst";
		
	}
}