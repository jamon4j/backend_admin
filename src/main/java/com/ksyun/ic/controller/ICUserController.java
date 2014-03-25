package com.ksyun.ic.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksyun.payment.common.AppConfig;
import com.ksyun.payment.common.DocsvrHttpUtil;
import com.ksyun.payment.common.Encrypt;
import com.ksyun.tools.net.ServletUtil;
import com.ksyun.vm.utils.UserService;

/**
 * order   账户明细
 * @author ZhangYanchun
 * @date   2013-12-26
 */
@Controller
@RequestMapping("/admin")
public class ICUserController extends BaseController {

	@Resource
	private UserService userService;
	
	//用户信息列表查询页面
	@RequestMapping("/user/user_index")
	public String user_index(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		
		
		Long userId = ServletUtil.getLong(req, "userId", 0);
		String email = ServletUtil.getStr(req, "email");
		String mobile = ServletUtil.getStr(req, "mobile");
		
		
		req.setAttribute("userId", userId);
		req.setAttribute("email", email);
		req.setAttribute("mobile", mobile);
		
		req.setAttribute("pageList", userService.findUserInfoByEmailAndMobile(userId, email, mobile));
		return "admin/user/user_index";
	}
	
	
	//设置为关系客户输入
	@RequestMapping("/user/user_cr_input")
	public String user_cr_input(HttpServletRequest req, HttpServletResponse response, ModelMap model) {
		long userId = ServletUtil.getLong(req, "userId", 0);
		req.setAttribute("userId", userId);
		
		return "/admin/user/user_cr_input";
	}
	
	//设置用户为关系客户
	@RequestMapping("user/user_cr")
	public String user_cr(HttpServletRequest req, HttpServletResponse response, ModelMap model) {
		int ret = 0;
		
		long userId = ServletUtil.getLong(req, "userId", 0);
		int userType = ServletUtil.getInt(req, "userType", 0);
		
		String userFrom = ServletUtil.getStr(req, "userFrom");
		
		userService.updateUserType(userId, (short)userType, userFrom);
		
		req.setAttribute("ret", ret);
		return "/admin/opt_ret_rst";
	}
	
	//设置用户为关系客户
	@RequestMapping("user/user_sudo")
	public String user_sudo(HttpServletRequest req, HttpServletResponse response, ModelMap model) {
		
		return "/admin/user/user_sudo"; 
	}
	
	//设置用户为关系客户
	@RequestMapping("user/user_sudo_redirct")
	public String user_sudo_redirtct(HttpServletRequest req, HttpServletResponse response, ModelMap model) {
		
		String email = ServletUtil.getString(req, "email");
		String url = req.getParameter("url");
		
		DocsvrHttpUtil httpUtil = new DocsvrHttpUtil();
		String suToken = httpUtil.adminLogin();
		String token = httpUtil.adminGetUserAuth(suToken, email);
		
		if(token==null){
			req.setAttribute("ret", 1);
			return "/admin/opt_ret_rst";
		}
		
		token = Encrypt.encrypt(token, AppConfig.AES_KEY);
//		ServletUtil.deleteCookie(response, "kscdigest", ".ksyun.com");
//		ServletUtil.setCookie(response, "kscdigest", token, 7200);//7200与后端一致。时间短时为了保证安全
		
		model.put("url", URLEncode(url));
		model.put("token", URLEncode(token));
		return "/admin/user/user_sudo_window";
	}
	
	private String URLEncode(String str){
		
		String result=null;
		try {
			result = URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			//这里是100%不会抛异常的，因为"UTF-8"不是当参数传过来的，是咱们自己写死的
		}
		return result;
		
	}

}
