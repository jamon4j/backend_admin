package com.ksyun.vm.interceptor;

import com.ksyun.vm.pojo.acl.RolePo;
import com.ksyun.vm.service.RoleService;
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
    public static Map<String, String> mapUserRoles = new HashMap<>();

    private RoleService roleService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String host = request.getRemoteHost();
        Cookie cookie = getCookieByName(request, InitConst.BACKEND);
        String type=Constants.getPropertyValue("sys.type");
        Integer timeout = Integer.valueOf(Constants.getPropertyValue("cookie.timeout"));
        if(uri.indexOf("/login")>-1||uri.indexOf("/html")>-1||uri.indexOf("/js")>-1||uri.indexOf("/img")>-1|| uri.indexOf("/getvmsum")>-1 || host.equals("127.0.0.1")||type.equals("test")){
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
                    mapUserRoles.remove(backend);
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
        
        String cookieValue = cookie.getValue();
        String roles = mapUserRoles.get(cookieValue);
      
        if (uri.equals("/g/")) {
        	 return true;	
        }
        if (!isHavePower(roles,uri)) {
        	response.sendRedirect("/test.jsp");
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
    
    
    public boolean isHavePower(String roles, String uri)
    {    	
    	String[] role_power_list = null;
    	if (uri.indexOf("/g/home") == 0  || uri.endsWith("/g/")) return true;  //主页不判断权限
    	
    	if (roles == null) return true;
    	String[] role_list = roles.split(",");
    	String[] uri_list = uri.split("/");
    	int length = role_list.length;
    	for(int i=0; i< length; i++){
    		int roleId = Integer.parseInt(role_list[i]);
    		if (roleId == 1) return true;                 //admin
    		
    		//普通员工--根据role_power判断权限。
    		RolePo role = roleService.getRole(roleId);    	
    		if (role !=null){    
        		role_power_list = role.getRolePower().split(",");
        		for(int j=0; j<role_power_list.length; j++){
        			if (uri.indexOf(role_power_list[j])==0) return true;
        		}	
    		}
    	}
    	
    	return false;
    }
    
    

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
    
    
}
