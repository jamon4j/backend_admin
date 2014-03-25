package com.ksyun.payment.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 登陆、鉴权系统拦截器
 * User: Norton Chen(cjx)
 * Date: 2013-06-28
 */
public class HandleAuthenticationInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = Logger.getLogger(HandleAuthenticationInterceptor.class);
	
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler) throws Exception {
    	String uri = req.getRequestURI();
    	if(uri.indexOf("admin") != -1) {
    		Object user = req.getSession().getAttribute("user");
    		if(user == null) {
    			String strValue = "未登录，请先登录";
    	    	writeToResponse(response, strValue);
    	    	return false;
    		}
    	}
    	return true;
    	
    	/*String strValue = "<script language=\"javascript\" type=\"text/javascript\">window.location.href=login_input?ret=0;</script>";
    	writeToResponse(response, strValue);
    	return false;*/
    }
    
    /**
	 * 写入返回值
	 * @param response
	 * @param value
	 */
	private void writeToResponse(HttpServletResponse response, String value) {
		try {
			response.getWriter().write(value);
		} catch (IOException e) {
			logger.error(String.format("response write error: %s", value));
		}
	}
}
