package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.tools.net.ServletUtil;
import com.ksyun.vm.interceptor.HandleAuthenticationInterceptor;
import com.ksyun.vm.pojo.login.Msg;
import com.ksyun.vm.pojo.login.User;
import com.ksyun.vm.routedatasource.DataSourceInstances;
import com.ksyun.vm.service.DataSwitchService;
import com.ksyun.vm.service.LoginService;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.SHA1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Calendar;
import java.util.List;

/**
 * User: liuchuandong Date: 13-10-30 Time: 下午4:27 Func:
 */
@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private DataSwitchService dataSwitchService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, ModelAndView mav) {
		mav.setViewName("/gestion/login");
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mav) {
		String username = ServletUtil.getString(request, "username");
		String password = ServletUtil.getString(request, "password");
		String scloud = request.getParameter("scloud");
		// 设置数据源
		switch (scloud) {
		case "public":
			dataSwitchService.setDataSource(DataSourceInstances.DS1);
			break;
		case "private":
			dataSwitchService.setDataSource(DataSourceInstances.DS2);
			Constants.setPropertyValue(InitConst.HTTP_HOST, InitConst.HTTP_HOST_IP);
			break;
		}
		List<User> list = loginService.getUser(username, password);
		Msg msg = new Msg();
		if (list != null && !list.isEmpty()) {
			msg.setMsg("登陆成功");
			msg.setSuccess("true");
			msg.setUrl("/g/");
			// 设置Cookie
			Cookie nowCookie = null;// 当前Cookie
			Cookie allowCookie = null;// 已登录过的Cookie
			switch (scloud) {
			case InitConst.COOKIE_PUBLIC:
				request.getSession().setAttribute("type",
						InitConst.COOKIE_PUBLIC);
				nowCookie = new Cookie(InitConst.COOKIE_NOW_NAME, username
						+ InitConst.COOKIE_SPLIT + InitConst.COOKIE_PUBLIC);// 全局Cookie
				allowCookie = new Cookie(InitConst.COOKIE_PUBLIC_NAME,
						username + InitConst.COOKIE_SPLIT
								+ InitConst.COOKIE_PUBLIC);
				break;
			case InitConst.COOKIE_PRIVATE:
				request.getSession().setAttribute("type",
						InitConst.COOKIE_PRIVATE);
				nowCookie = new Cookie(InitConst.COOKIE_NOW_NAME, username
						+ InitConst.COOKIE_SPLIT + InitConst.COOKIE_PRIVATE);
				allowCookie = new Cookie(InitConst.COOKIE_PRIVATE_NAME,
						username + InitConst.COOKIE_SPLIT
								+ InitConst.COOKIE_PRIVATE);
				break;
			}
			allowCookie.setPath("/");
			nowCookie.setPath("/");
			String cookie = SHA1
					.getDigestOfString((username + "{" + password + "}")
							.getBytes());
			HandleAuthenticationInterceptor.map.put(cookie,
					Calendar.getInstance());
			HandleAuthenticationInterceptor.mapUserRoles.put(cookie, list
					.get(0).getRoles()); // 缓存用户的Roles
			msg.setCookie(cookie);
			response.addCookie(allowCookie);
			response.addCookie(nowCookie);
			return JSONObject.toJSONString(msg);
		} else {
			msg.setMsg("登陆失败");
			msg.setSuccess("false");
			msg.setUrl("/login");
			return JSONObject.toJSONString(msg);
		}
	}

	@RequestMapping(value = "/login_pvt", method = RequestMethod.POST)
	@ResponseBody
	public String login_pvt(HttpServletRequest request,
			HttpServletResponse response) {

		// 设置数据源
		dataSwitchService.setDataSource(DataSourceInstances.DS2);
		Constants.setPropertyValue(InitConst.HTTP_HOST, "192.168.16.23"); // 这里写上私有云的ip，正式部署部署公有云环境。需要确保公有云环境可以访问私有云数据库以及ip

		String username = ServletUtil.getString(request, "username");
		String password = ServletUtil.getString(request, "password");
		List<User> list = loginService.getUser(username, password);
		Msg msg = new Msg();
		if (list != null && !list.isEmpty()) {
			msg.setMsg("登陆成功");
			msg.setSuccess("true");
			msg.setUrl("/g/");
			String cookie = SHA1
					.getDigestOfString((username + "{" + password + "}")
							.getBytes());
			HandleAuthenticationInterceptor.map.put(cookie,
					Calendar.getInstance());
			HandleAuthenticationInterceptor.mapUserRoles.put(cookie, list
					.get(0).getRoles()); // 缓存用户的Roles
			msg.setCookie(cookie);
			return JSONObject.toJSONString(msg);
		} else {
			msg.setMsg("登陆失败");
			msg.setSuccess("false");
			msg.setUrl("/login");
			return JSONObject.toJSONString(msg);
		}
	}

}
