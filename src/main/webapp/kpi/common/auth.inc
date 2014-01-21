<%@ page import="com.ksyun.vm.kpi.common.*, com.ksyun.vm.utils.*,javax.servlet.http.Cookie,java.util.Calendar,com.ksyun.vm.interceptor.HandleAuthenticationInterceptor" %>  
<%
Cookie cookie = ServletUtil.getCookie(request, InitConst.BACKEND);
Integer timeout = Integer.valueOf(Constants.getPropertyValue("cookie.timeout"));

System.out.println(cookie);

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
    }else{
        response.sendRedirect("/login");
        return;
    }
}else{
    response.sendRedirect("/login");
    return;
}
%>
