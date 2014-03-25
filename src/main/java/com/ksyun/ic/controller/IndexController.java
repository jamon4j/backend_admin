package com.ksyun.ic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksyun.tools.net.ServletUtil;

/**
 * 登录首页及管理页面
 * @author ZhangYanchun
 * @date   2013-10-10
 */

@Controller
public class IndexController extends BaseController {
	
	//账单列表页面
	@RequestMapping("/admin/index")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return "admin/index";
	}
	
	@RequestMapping("/login_input")
	public String login_input(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String ret = ServletUtil.getStr(request, "ret");
		if(ret != null) request.setAttribute("ret", ret);
		return "login_input";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String name = ServletUtil.getStr(req, "name");
		String password = ServletUtil.getStr(req, "password");
		if("admin".equals(name) && "ksyun".equals(password)) {
			req.getSession().setAttribute("user", "admin");
			return "redirect:admin/index";
		}
		return "redirect:login_input?ret=0";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		request.getSession().removeAttribute("user");
		return "redirect:login_input?ret=1";
	}
	
	@RequestMapping("/admin/left")
	public String left(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return "admin/left";
	}
	
	@RequestMapping("/admin/right")
	public String right(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return "admin/right";
	}

	@RequestMapping("/admin/admin_top")
	public String admin_top(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return "admin/admin_top";
	}
	
}
