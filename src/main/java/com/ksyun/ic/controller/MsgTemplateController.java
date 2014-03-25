package com.ksyun.ic.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksyun.payment.dto.MsgTemplateDto;
import com.ksyun.payment.service.IMsgTemplateService;
import com.ksyun.tools.net.ServletUtil;

/**
 * order 订单控制
 * @author ZhangYanchun
 * @date   2013-11-26
 */
@Controller
@RequestMapping("/admin")
public class MsgTemplateController extends BaseController {

	@Resource
	private IMsgTemplateService msgTemplateService;
 
	//信息模板列表
	@RequestMapping("/mt/mt_index")
	public String mt_index(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		req.setAttribute("list", msgTemplateService.getAllTemplates());
		return "admin/mt/mt_index";
	}
	
	//信息模板编辑
	@RequestMapping("/mt/mt_update_input")
	public String mt_update_input(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String bizType = ServletUtil.getStr(req, "bizType");
		String msgType = ServletUtil.getStr(req, "msgType");
		req.setAttribute("bizType", bizType);
		req.setAttribute("msgType", msgType);
		req.setAttribute("mt", msgTemplateService.getTemplate(bizType, msgType));
		return "admin/mt/mt_update_input";
	}
	//信息模板更新
	@RequestMapping("/mt/mt_update")
	public String mt_update(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String bizType = ServletUtil.getStr(req, "bizType");
		String msgType = ServletUtil.getStr(req, "msgType");
		String comment = ServletUtil.getStr(req, "comment");
		String template = ServletUtil.getStrNoFilter(req, "template");
		String obizType = ServletUtil.getStr(req, "obizType");
		String omsgType = ServletUtil.getStr(req, "omsgType");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("bizType", bizType);
		params.put("msgType", msgType);
		params.put("comment", comment);
		params.put("template", template);
		params.put("obizType", obizType);
		params.put("omsgType", omsgType);
		
		MsgTemplateDto msgTemplate = new MsgTemplateDto();
		msgTemplate.setBizType(bizType);
		msgTemplate.setMsgType(msgType);
		msgTemplate.setComment(comment);
		msgTemplate.setTemplate(template);
		
		msgTemplateService.updateTemplate(params);
		return "admin/opt_rst";
	}
	
	//信息模板新增
	@RequestMapping("/mt/mt_add_input")
	public String mt_add_input(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		return "admin/mt/mt_add_input";
	}
	
	//信息模板添加操作
	@RequestMapping("/mt/mt_add")
	public String mt_add(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String bizType = ServletUtil.getStr(req, "bizType");
		String msgType = ServletUtil.getStr(req, "msgType");
		String comment = ServletUtil.getStr(req, "comment");
		String template = ServletUtil.getStrNoFilter(req, "template");
		
		MsgTemplateDto msgTemplate = new MsgTemplateDto();
		msgTemplate.setBizType(bizType);
		msgTemplate.setMsgType(msgType);
		msgTemplate.setComment(comment);
		msgTemplate.setTemplate(template);
		
		msgTemplateService.saveTemplate(msgTemplate);
		return "admin/opt_rst";
	}
}
