<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ksyun.vm.kpi.common.*" 
         contentType="text/html" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/inc/kpi.inc" %>
<%@ include file="chart/chart.inc" %>
    
<%
String product = ServletUtil.getString(request, "p");
System.out.println("pid=" + product);
if (product == null) product = "test";
Vector kpi = getKPI(product);

int cols = getColNum(product);
Calendar calendar = Calendar.getInstance();

String uri = request.getRequestURI();
String uri_prefix = uri.substring(0, uri.lastIndexOf("/"));

%>
<head>
	<title>KPI</title>
</head>
<body class="main-body">
<div class="path"><p>当前位置：<a href="<%=uri_prefix%>/index.jsp">KPI</a><span>&gt;</span><%=product%>
</p></div>

<div class="main-cont">
    <form method="post" action="chart/chart.jsp?p=<%=product%>" onsubmit="return btn_chart_onclick();">
        <%=getChart_list(product)%>
        <select name="chart_type">
            <option value="SplineArea">面积图</option>
            <option value="Spline">线形图</option>
            <option value="Bar">柱状图</option>
        </select>
        <select name="month">
            <option value="1">一个月</option>
            <option value="2">两个月</option>
            <option value="3">三个月</option>
        </select>
        <input type="submit" value="查看图表">
    </form>
    
    <br>
    
    <div class="set-area">
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <%=titleTD(product)%>
            </tr>
            </thead>
            <tbody>
            <%
                for (int i = 0; i < kpi.size(); i++) {
                    String[] one = (String[]) kpi.get(i);
            %>
            <%=doDay(one, cols)%>
            <%}%>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
<script>
    function btn_chart_onclick() {
        //检查chextbox是否被选中，如果没有选中不让提交
        var s = document.getElementsByName("chart_select");
        for (var i = 0; i < s.length; i++) {
            if (s[i].checked) {
                return true;
            }
        }
        alert("请选则要显示的图表数据！");
        return false;
    }

</script>
