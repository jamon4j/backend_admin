<%@ page import="com.ksyun.vm.kpi.common.*" %>>
<%
String product	= ServletUtil.getString(request, "product");
String field	= ServletUtil.getString(request, "field");
String day		= ServletUtil.getString(request, "day");
String value	= ServletUtil.getString(request, "value");
String src		= ServletUtil.getString(request, "src");
if(product == null || field == null || day == null || value == null) {
	out.println(-1);
	return;
}

if("today".equals(day)) day = KPI.today();
if("yesterday".equals(day)) day = KPI.yesterday();
int ret = KPI.submit(product, field, day, value, src);
System.err.println("KPI src=" + src + " product=" + product + " field=" + field + " day=" + day + " value=" + value);
%>
<%=ret%>
