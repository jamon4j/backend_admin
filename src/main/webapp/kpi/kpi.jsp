<%@ page import="com.ksyun.vm.kpi.common.*" %>
<%
String product	= ServletUtil.getString(request, "product");
String field	= ServletUtil.getString(request, "field");
String day		= ServletUtil.getString(request, "day");
String value	= ServletUtil.getString(request, "value");
String src		= ServletUtil.getString(request, "src");
String op		= ServletUtil.getString(request, "op");
if(product == null || field == null || day == null || value == null) {
	out.println(-1);
	return;
}

int ret = 0;

if("today".equals(day)) day = KPI.today();
if("yesterday".equals(day)) day = KPI.yesterday();

if (op != null){
	if (op.equals("inc")) ret = KPI.submitInc(product, field, day, value);
	else ret = KPI.submit(product, field, day, value);
}
else{
	ret = KPI.submit(product, field, day, value);	
}

System.err.println("KPI src=" + src + " product=" + product + " field=" + field + " day=" + day + " value=" + value);
%>
<%=ret%>
