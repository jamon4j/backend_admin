<%@ page import="com.ksyun.vm.kpi.common.*, com.ksyun.vm.utils.*,javax.servlet.http.Cookie,java.util.Calendar,com.ksyun.vm.interceptor.HandleAuthenticationInterceptor,
	com.ksyun.vm.service.RoleService,com.ksyun.vm.pojo.acl.RolePo" %>  

<%
Cookie cookie = ServletUtil.getCookie(request, InitConst.BACKEND);
Integer timeout = Integer.valueOf(Constants.getPropertyValue("cookie.timeout"));

if(cookie!=null){
    String backend = cookie.getValue();
    System.out.println(backend);
    if(backend!=null){
        Calendar now = Calendar.getInstance();
        Calendar time = HandleAuthenticationInterceptor.map.get(backend);  
        
        if(time==null){
            response.sendRedirect("/login");
            return;
        }
        else if(now.getTimeInMillis()-time.getTimeInMillis()>timeout) {
            response.sendRedirect("/login");
            return;
        }
        
        //这里权限判断根据params
    
        String roles = HandleAuthenticationInterceptor.mapUserRoles.get(backend);
        String product = request.getParameter("p");
        int flag = 0;
        
        if (roles != null) {
        	RoleService roleService = (RoleService)DaoFactory.getBean("roleService");
        	roleService.setSource();
        	String[] role_list = roles.split(",");
    		int length = role_list.length;
    		for(int i=0; i< length; i++){
	    		int roleId = Integer.parseInt(role_list[i]);
	    		//if (roleId == 1) {
	    		//	flag = 1;
	    		//	break;
	    		//}
	    		//普通员工--根据role_power判断权限。
	    		RolePo role = roleService.getRole(roleId);
	    		if (role != null){
	    			// admin角色的用户，没有指定URL，所以直接true返回
	    			if("admin".equals(role.getRoleName())) {
	    				flag = 1;
	    				break;
	    			}
	    			if (role.getRolePower().indexOf("kpi_"+product)>=0) {
	    				flag = 1;
	    				//只要有URI与当前匹配，就终止循环
	    				break;
	    			}
	    		}
    		}
    		if (flag == 0){
    		    response.sendRedirect("/test.jsp");
            	return;
    		}
        }
        
    }else{
        response.sendRedirect("/login");
        return;
    }
}else{
    response.sendRedirect("/login");
    return;
}
%>
