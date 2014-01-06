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
            response.sendRedirect("/login");
        }
        if(now.getTimeInMillis()-time.getTimeInMillis()>timeout) {
            response.sendRedirect("/login");
        }
    }else{
        response.sendRedirect("/login");
    }
}else{
    response.sendRedirect("/login");
}
%>
