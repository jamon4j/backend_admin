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

System.out.println(fieldTarget);

int ret = 0;

if("today".equals(day)) day = KPI.today();
if("yesterday".equals(day)) day = KPI.yesterday();

System.out.println(fieldTarget);


if (op != null){
	if (op.equals("inc")) ret = KPI.submitInc(product, field, day, value);
	else if (op.equals("sum"))
	{
		if (year == null) year = KPI.getYear();
		
		if (fieldTarget == null) {
			ret = -1;
		}
		else ret = KPI.sumbyYear(product, field, fieldTarget, year, day,"update");
	}
	
	else if (op.equals("suminc"))
	{
		if (year == null) year = KPI.getYear();		
		if (fieldTarget == null) {
			ret = -1;
		}
		else ret = KPI.sumbyYear(product, field, fieldTarget, year, day,"inc");
	}
	
	else if (op.equals("list"))
	{
		String userId = ServletUtil.getString(request, "userId"); 
		int retTmp = 0;
		String[] fields = field.split(",");
		String[] values = value.split(",");
		int length = fields.length;
		for (int i=0; i<length;i++){
			System.out.println("field="+fields[i] + ",value="+values[i]);	
			retTmp = KPI.submit2(product, "day",day,"userId",userId,field,value,"");
			if (retTmp!=0) { ret = ret +1;}
		}
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
