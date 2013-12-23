<%@ page import="com.ksyun.vm.kpi.common.*,java.util.Date,java.text.SimpleDateFormat,java.text.DateFormat;" %>
<%
String product	= ServletUtil.getString(request, "product");
String field	= ServletUtil.getString(request, "field");
String day		= ServletUtil.getString(request, "day");
String value	= ServletUtil.getString(request, "value");
String src		= ServletUtil.getString(request, "src");
String op		= ServletUtil.getString(request, "op");
String year     = ServletUtil.getString(request, "year");
String fieldTarget = ServletUtil.getString(request, "fieldTarget");

if(product == null || field == null || day == null || value == null) {
	out.println(-1);
	return;
}

int ret = 0;

if("today".equals(day)) day = KPI.today();
if("yesterday".equals(day)) day = KPI.yesterday();


if (op != null){
	if (op.equals("inc")) ret = KPI.submitInc(product, field, day, value);
	else if (op.equals("sum"))
	{
		if (year == null) year = KPI.getYear();
		
		if (fieldTarget == null) {
			ret = -1;
		}
		else ret = KPI.sumbyYear(product, field, fieldTarget, year, day);
	}
	else ret = KPI.submit(product, field, day, value);
}
else{
	ret = KPI.submit(product, field, day, value);	
}



DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date date = new Date();
System.err.println(f.format(date) + " : KPI src=" + src + " product=" + product + " field=" + field + " day=" + day + " value=" + value);///加个时间
%>
<%=ret%>
