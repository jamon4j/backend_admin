package com.ksyun.ic.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksyun.payment.dto.BillDto;
import com.ksyun.payment.service.IBillService;
import com.ksyun.tools.DateUtil;
import com.ksyun.tools.net.ServletUtil;

/**
 * order   账单列表
 * @author ZhangYanchun
 * @date   2013-12-04
 */
@Controller
@RequestMapping("/admin")
public class BillController extends BaseController {

	@Resource
	private IBillService billService;
	
	@RequestMapping("/bill/bill_cancel_input")
	public String bill_cancel_input(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		long billId = ServletUtil.getLong(req, "billId", 0);
	    req.setAttribute("bill", billService.getBillById(billId));
		return "admin/bill/bill_cancel_input";
	}
	
	@RequestMapping("/bill/bill_cancel")
	public String bill_cancel(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		long billId = ServletUtil.getLong(req, "billId", 0);
		BillDto bill = billService.getBillById(billId);
		short payStatus = bill.getPayStatus();
		if(payStatus == -1 || payStatus == 0) return "error/error";
		int ret = billService.cancelBill(bill);
		req.setAttribute("ret", ret);
		return "admin/opt_ret_rst";
	}
	
	
	//账单列表（后台功能模块）
	@RequestMapping("/bill/bill_index")
	public String bill_index(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		short payType = (short)ServletUtil.getInt(req, "payType", 0);
		long userId = ServletUtil.getLong(req, "userId", 0);
		String stime = ServletUtil.getStr(req, "stime");
		String etime = ServletUtil.getStr(req, "etime");
		String orderId = ServletUtil.getStr(req, "orderId");
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(payType != 0) {
			params.put("payType", payType);
		}
		if(userId != 0) {
			params.put("userId", userId);
		}
		
		if(!"".equals(orderId)) {
			String sorderId = (new StringBuilder("%")).append(orderId).append("%").toString();
			params.put("orderId", sorderId);
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
		
		req.setAttribute("payType", payType);
		req.setAttribute("userId", userId);
		req.setAttribute("orderId", orderId);
		req.setAttribute("stime", stime);
		req.setAttribute("etime", etime);
		
		req.setAttribute("pageList", billService.getAllKvmAndEbsBill(params));
		return "admin/bill/bill_index";
	}
	
	
}
