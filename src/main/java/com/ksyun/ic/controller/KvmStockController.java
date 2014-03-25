package com.ksyun.ic.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksyun.payment.service.IKvmStockService;
import com.ksyun.tools.net.ServletUtil;

/**
 * order 订单控制
 * @author ZhangYanchun
 * @date   2013-11-26
 */
@Controller
@RequestMapping("/admin")
public class KvmStockController extends BaseController {

	@Resource
	private IKvmStockService kvmStockService;
 
	//订单列表（后台功能模块）
	@RequestMapping("/kstock/kstock_index")
	public String order_index(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		req.setAttribute("list", kvmStockService.getAllStock());
		return "admin/kstock/kstock_index";
	}
	
	//更新订单状态输入页面
	@RequestMapping("/kstock/kstock_update_amount")
	public String order_change_status(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String kvmType = ServletUtil.getStr(req, "kvmType");
		req.setAttribute("kvmType", kvmType);
		return "admin/kstock/kstock_update_amount";
	}
	
	//更改订单状态
	@RequestMapping("/kstock/kstock_update_amount_do")
	public String order_change_status_do(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String kvmType = ServletUtil.getStr(req, "kvmType");
		int amount = ServletUtil.getInt(req, "amount", 0);
		kvmStockService.updateTotalAmount(kvmType, amount);
		return "admin/opt_rst";
	}
	
	
}
