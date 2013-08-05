package com.ksyun.vm.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 登陆、鉴权系统拦截器
 * User: Norton Chen(cjx)
 * Date: 2013-07-17
 */
public class HandleAuthenticationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String sname = request.getHeader("Host");
        String queryStr = request.getQueryString();
        request.setAttribute("uri", uri);
        boolean isDebug = false;

        System.out.println("-->sname=" + sname + " uri=" + uri);
        //先从Cookie中判断是否有用户信息
        String[] uinfo = null;//UserAuth.getUserInfo(request);

        if (sname.startsWith("local.") || sname.startsWith("test.")) { //如果在开发机，直接当做已登陆
            uinfo = new String[2];
            uinfo[0] = "1";
            uinfo[1] = "admin";
            isDebug = true;
        }

        if (uinfo == null || uinfo[0] == null || "0".equals(uinfo[0])) { //没登陆
            if (isDebug) System.out.println("HandleAuthenticationInterceptor: isLogin = false");
            request.setAttribute("isLogin", false);
            //判断拦截的URI前缀 ...
            if (uri.indexOf(".do") > 0) {
                response.sendRedirect("/user/login?to=" + URLEncoder.encode(uri, "utf-8"));
            } else if (uri.startsWith("/g/")) {
                response.getWriter().println("{\"s\":0,\"msg\":\"没有登录\"}");
                return false;
            }
            return true;
        } else {   //已登录
            int uid = 0;
            try {
                uid = Integer.parseInt(uinfo[0]);
            } catch (Exception e) {
                if (isDebug) System.out.println("HandleAuthenticationInterceptor: isLogin = false " + e.getMessage());
            }
            if (uid > 0) {
                request.setAttribute("isLogin", true);
                request.setAttribute("uid", uid);
                request.setAttribute("uname", uinfo[1]);
            } else {
                request.setAttribute("isLogin", false);
            }
            if (isDebug) System.out.println("HandleAuthenticationInterceptor: isLogin = " + uid);
        }
        return true;
    }
}
