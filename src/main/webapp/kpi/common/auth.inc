<%@ page import="com.ksyun.vm.kpi.common.*, com.ksyun.vm.utils.*,javax.servlet.http.Cookie,java.util.Calendar,com.ksyun.vm.interceptor.HandleAuthenticationInterceptor" %>  
<%
Cookie cookie = ServletUtil.getCookie(request, InitConst.BACKEND);
Integer timeout = Integer.valueOf(Constants.getPropertyValue("cookie.timeout"));

if(cookie!=null){
    String backend = cookie.getValue();
    if(backend!=null){
        Calendar now = Calendar.getInstance();
        Calendar time = HandleAuthenticationInterceptor.map.get(backend);  
        
        if(time==null){
            System.out.println("check if have time");
            response.sendRedirect("/login");
        }
     
        if(time==null){
            System.out.println("check if have time");
            response.sendRedirect("/login");
        }
        else if(now.getTimeInMillis()-time.getTimeInMillis()>timeout) {
        	System.out.println("check if time out");
            response.sendRedirect("/login");
        }
    }else{
        response.sendRedirect("/login");
    }
}else{
    System.out.println("no cookie");
    response.sendRedirect("/login");
}
%>
