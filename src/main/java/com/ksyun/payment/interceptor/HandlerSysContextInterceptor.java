package com.ksyun.payment.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ksyun.payment.common.SysContext;
import com.ksyun.tools.net.ServletUtil;

/**
 * 获取页面分页信息，并放入到线程的ThreadLocal中
 * @author ZhangYanchun
 * @date   2013-08-12
 */
public class HandlerSysContextInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		int _page = ServletUtil.getInt(request, "page", 1);
		int _pageSize = ServletUtil.getInt(request, "pageSize", 15);
		
		SysContext.setPage(_page);
		SysContext.setPageSize(_pageSize);
		
		return true;
	}

}
