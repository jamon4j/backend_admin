package com.ksyun.vm.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.interceptor.HandleAuthenticationInterceptor;
import com.ksyun.vm.pojo.login.Msg;
import com.ksyun.vm.routedatasource.DataSourceInstances;
import com.ksyun.vm.service.DataSwitchService;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;

/**
 * 公有云 私有云切换Controller
 * 
 * @author XueQi
 * 
 */
@Controller
public class ExchangeController {
	@Autowired
	private DataSwitchService dataSwitchService;

	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public String exchange(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取当前Cookie的状态
		Cookie nowCookie = HandleAuthenticationInterceptor.getCookieByName(
				request, InitConst.COOKIE_NOW_NAME);
		Msg msg = new Msg();
		msg.setSuccess("true");
		if (nowCookie == null || "".equals(nowCookie.getValue())
				|| nowCookie.getValue() == null) {
			msg.setMsg("noNow");
		} else {// 当前云类型存在
			Cookie new_nowCookie = null;
			String[] splitCookie = nowCookie.getValue().split(
					InitConst.COOKIE_SPLIT);
			switch (splitCookie[splitCookie.length - 1]) {
			// 取得当前云类型
			case InitConst.COOKIE_PUBLIC:// 公有云
				/**
				 * 当前类型为公有云，切换到私有云，要判断私有云的Cookie是否存在，不存在，登陆；存在，切换
				 */
				Cookie privateCookie = HandleAuthenticationInterceptor
						.getCookieByName(request, InitConst.COOKIE_PRIVATE_NAME);
				if (privateCookie == null
						|| "".equals(privateCookie.getValue())
						|| privateCookie.getValue() == null) {// 私有云的Cookie不存在，登陆
					msg.setMsg("noPrivate");
					return JSONObject.toJSONString(msg);
				} else {
					dataSwitchService.setDataSource(DataSourceInstances.DS2);
					Constants.setPropertyValue(InitConst.HTTP_HOST, Constants
							.getPropertyValue(InitConst.HTTP_HOST_PRIVATE));
					Constants.setPropertyValue(InitConst.HTTP_PORT, Constants
							.getPropertyValue(InitConst.HTTP_PORT_PRIVATE));
					request.getSession().setAttribute("type",
							InitConst.COOKIE_PRIVATE);
					// 设置私有云的用户名Session
					String[] splitPrivate = privateCookie.getValue().split(
							InitConst.COOKIE_SPLIT);
					String privateUsername = splitPrivate[0];
					request.getSession().setAttribute("username",
							privateUsername);
					// 设置新的nowCookie
					new_nowCookie = new Cookie(InitConst.COOKIE_NOW_NAME,
							"exchange" + InitConst.COOKIE_SPLIT
									+ InitConst.COOKIE_PRIVATE);
				}
				break;
			case InitConst.COOKIE_PRIVATE:// 私有云
				Cookie publicCookie = HandleAuthenticationInterceptor
						.getCookieByName(request, InitConst.COOKIE_PUBLIC_NAME);
				if (publicCookie == null || "".equals(publicCookie.getValue())
						|| publicCookie.getValue() == null) {// 共有云的Cookie不存在，登陆
					msg.setMsg("noPublic");
					return JSONObject.toJSONString(msg);
				} else {
					dataSwitchService.setDataSource(DataSourceInstances.DS1);
					Constants.setPropertyValue(InitConst.HTTP_HOST, Constants
							.getPropertyValue(InitConst.HTTP_HOST_PUBLIC));
					Constants.setPropertyValue(InitConst.HTTP_PORT, Constants
							.getPropertyValue(InitConst.HTTP_PORT_PUBLIC));
					request.getSession().setAttribute("type",
							InitConst.COOKIE_PUBLIC);
					// 设置公有云的用户名Session
					String[] splitPublic = publicCookie.getValue().split(
							InitConst.COOKIE_SPLIT);
					String publicUsername = splitPublic[0];
					request.getSession().setAttribute("username",
							publicUsername);
					new_nowCookie = new Cookie(InitConst.COOKIE_NOW_NAME,
							"exchange" + InitConst.COOKIE_SPLIT
									+ InitConst.COOKIE_PUBLIC);
				}
				break;
			}
			// 切换成功，返回JSON的数据
			msg.setMsg("change");
			// 设置新的全局Cookie的作用域
			new_nowCookie.setPath("/");
			response.addCookie(new_nowCookie);
		}
		return JSONObject.toJSONString(msg);
	}
}
