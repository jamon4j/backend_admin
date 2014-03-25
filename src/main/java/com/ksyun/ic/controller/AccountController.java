package com.ksyun.ic.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksyun.payment.dto.UserAccountDetailDto;
import com.ksyun.payment.dto.UserDto;
import com.ksyun.payment.service.ICashSerialService;
import com.ksyun.payment.service.IUserService;
import com.ksyun.tools.DateUtil;
import com.ksyun.tools.net.ServletUtil;
import com.ksyun.vm.utils.UserService;

/**
 * order   账户明细
 * @author ZhangYanchun
 * @date   2013-12-26
 */
@Controller
@RequestMapping("/admin")
public class AccountController extends BaseController {

	@Resource
	private UserService userService;
	
	@Resource
	private ICashSerialService cashSerialService;
	//现金收支明细
	@RequestMapping("/account/cash_detail_index")
	public String cash_detail_index(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		
		short dealType = (short)ServletUtil.getInt(req, "dealType", 0);
		
		long userId = ServletUtil.getLong(req, "userId", 0);
		String stime = ServletUtil.getStr(req, "stime");
		String etime = ServletUtil.getStr(req, "etime");
		String email = ServletUtil.getStr(req, "email");
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(dealType != 0) {
			params.put("dealType", dealType);
		}
		
		if(userId != 0) {
			params.put("userId", userId);
		}
		if(!"".equals(email)) {
			params.put("email", email);
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
		req.setAttribute("email", email);
		req.setAttribute("stime", stime);
		req.setAttribute("etime", etime);
		req.setAttribute("dealType", dealType);
		req.setAttribute("pageList", userService.findAllDetail(params));
		return "admin/account/cash_detail_index";
	}
	
	//后台储值操作
	@RequestMapping("/account/account_recharge_input")
	public String account_recharge_input(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		
		return "admin/account/account_recharge_input";
		
	}
	//后台提现操作
	@RequestMapping("/account/account_draw_input")
	public String account_draw_input(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		return "admin/account/account_draw_input";
		
	}
	

	//后台储值作废操作
	@RequestMapping("/account/account_deduct_input")
	public String account_deduct_input(HttpServletRequest req, HttpServletResponse response, ModelMap model){	
		return "admin/account/account_deduct_input";
		
	}
	
	
    //储值操作
	@RequestMapping("/account/account_recharge")
	public String account_recharge(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		
		int ret = 0;
		long userId = ServletUtil.getLong(req, "userId", 0);
		UserDto user = userService.getUserAccount(userId);
		if(user == null) {
			ret = -1001;
		} else {
			BigDecimal money = ServletUtil.getDecimal(req, "money");
			if(money.compareTo(BigDecimal.ZERO) == 0) {
				ret = -1002;
			} else  {
				StringBuilder sb = new StringBuilder(cashSerialService.incrSerialNum()).append("b");
				String payId = ServletUtil.getStr(req, "payId");
				userService.saveBackRechargeMoney(userId, payId, sb.toString(), money);
			}
			
		}
		
	    
		req.setAttribute("ret", ret);
		return "admin/opt_ret_rst";
	}
	
	//储值操作
	@RequestMapping("/account/account_draw")
	public String account_draw(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		
		int ret = 0;
		long userId = ServletUtil.getLong(req, "userId", 0);
		UserDto user = userService.getUserAccount(userId);
		if(user == null) {
			ret = -1001;
		} else {
			
			BigDecimal money = ServletUtil.getDecimal(req, "money");
			if(money.compareTo(BigDecimal.ZERO) == 0) {
				ret = -1002;
			} else  {
				BigDecimal balance = user.getBalance();
				if(money.compareTo(balance) > 0) {
					ret = -1003;
				} else {
					StringBuilder sb = new StringBuilder(cashSerialService.incrSerialNum()).append("b");
					String payId = ServletUtil.getStr(req, "payId");
					userService.saveBackDrawMoney(userId, payId, sb.toString(), money);
				}
				
			}
			
		}
		
	    
		req.setAttribute("ret", ret);
		return "admin/opt_ret_rst";
	}
	
    //后台储值作废操作
	@RequestMapping("/account/account_deduct")
	public String account_deduct(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		
		int ret = 0;
		String caSerial = ServletUtil.getStr(req, "caSerial");
		long userId = ServletUtil.getLong(req, "userId", 0);
		
		
		UserAccountDetailDto accountDetail = userService.findDetail(caSerial);
		UserDto user = userService.getUserAccount(userId);
		
		//判空
		if (accountDetail == null || user == null) {
			ret = -1001;			
		} else {			
			if (userId != accountDetail.getUserId()) {  //判断userId是否相等
				ret = -1001;					
			} else {
				BigDecimal money = accountDetail.getIncomeMoney();
				short dealType = accountDetail.getDealType();
				
				if ( (dealType != 1) || !caSerial.endsWith("b")) {  //判断充值类型，只有后台充值才能作废
					ret = -1001;
					
				} else if(money.compareTo(BigDecimal.ZERO) == 0) {
					ret = -1002;
					
				} else  {
					StringBuilder sb = new StringBuilder(cashSerialService.incrSerialNum()).append("b");
					userService.saveBackDeductMoney(userId, caSerial, sb.toString(), money);
				}				
			}
			
		}
		
	    
		req.setAttribute("ret", ret);
		return "admin/opt_ret_rst";
	}
		
}
