package com.ksyun.ic.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksyun.tools.net.ServletUtil;
import com.ksyun.vm.interceptor.HandleAuthenticationInterceptor;
import com.ksyun.vm.utils.InitConst;

/**
 * 登录首页及管理页面
 * 
 * @author ZhangYanchun
 * @date 2013-10-10
 */

@Controller
public class IndexController extends BaseController {

	// 账单列表页面
	@RequestMapping("/admin/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "admin/index";
	}

	@RequestMapping("/login_input")
	public String login_input(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String ret = ServletUtil.getStr(request, "ret");
		if (ret != null)
			request.setAttribute("ret", ret);
		return "login_input";
	}

	/*
	 * @RequestMapping("/login") public String login(HttpServletRequest req,
	 * HttpServletResponse response, ModelMap model){ String name =
	 * ServletUtil.getStr(req, "name"); String password =
	 * ServletUtil.getStr(req, "password"); if("admin".equals(name) &&
	 * "ksyun".equals(password)) { req.getSession().setAttribute("user",
	 * "admin"); return "redirect:admin/index"; } return
	 * "redirect:login_input?ret=0"; }
	 */

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().removeAttribute("user");
		// 移除Cookie
		Cookie now_cookie = HandleAuthenticationInterceptor.getCookieByName(
				request, InitConst.COOKIE_NOW_NAME);
		Cookie public_cookie = HandleAuthenticationInterceptor.getCookieByName(
				request, InitConst.COOKIE_PUBLIC_NAME);
		Cookie private_cookie = HandleAuthenticationInterceptor
				.getCookieByName(request, InitConst.COOKIE_PRIVATE_NAME);
		if (now_cookie != null) {
			now_cookie.setValue(null);
			now_cookie.setPath("/");
			response.addCookie(now_cookie);
		}
		if (public_cookie != null) {
			public_cookie.setValue(null);
			public_cookie.setPath("/");
			response.addCookie(public_cookie);
		}
		if (private_cookie != null) {
			private_cookie.setValue(null);
			private_cookie.setPath("/");
			response.addCookie(private_cookie);
		}
		return "redirect:/login";
	}

	@RequestMapping("/admin/left")
	public String left(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "admin/left";
	}

	@RequestMapping("/admin/right")
	public String right(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "admin/right";
	}

	@RequestMapping("/admin/admin_top")
	public String admin_top(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "admin/admin_top";
	}

}
