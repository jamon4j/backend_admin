package com.ksyun.vm.interceptor;

import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆、鉴权系统拦截器
 * User: Norton Chen(cjx)
 * Date: 2013-07-17
 */
public class HandleAuthenticationInterceptor extends HandlerInterceptorAdapter {
    public static Map<String, Calendar> map = new HashMap<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String host = request.getRemoteHost();
        Cookie cookie = getCookieByName(request, InitConst.BACKEND);
        String type=Constants.getPropertyValue("sys.type");
        Integer timeout = Integer.valueOf(Constants.getPropertyValue("cookie.timeout"));
        if(uri.indexOf("/login")>-1||uri.indexOf("/html")>-1||uri.indexOf("/js")>-1||uri.indexOf("/img")>-1||host.equals("127.0.0.1")||type.equals("test")){
            return true;
        }
        if(cookie!=null){
            String backend = cookie.getValue();
            if(backend!=null){
                Calendar now = Calendar.getInstance();
                Calendar time = map.get(backend);
                if(time==null){
                    response.sendRedirect("/login");
                    return false;
                }
                if(now.getTimeInMillis()-time.getTimeInMillis()>timeout) {
                    map.remove(backend);
                    response.sendRedirect("/login");
                    return false;
                }
            }else{
                response.sendRedirect("/login");
                return false;
            }
        }else{
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }

    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }else{
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
