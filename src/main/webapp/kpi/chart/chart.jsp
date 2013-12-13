<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/inc/acl.inc" %>
<%@ include file="/inc/kpi.inc" %>
<%

    String product = ServletUtil.getStr(request, "p");
    if (product == null) product = "test";

    String s[] = request.getParameterValues("chart_select");
    String cols = "";
    for (int i = 0; i < s.length; i++) {
        cols = cols + s[i] + ",";
    }

    String chart_type = ServletUtil.getStr(request, "chart_type");

    String month = ServletUtil.getStr(request, "month");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="/inc/admin_meta.inc" %>
    <script type="text/javascript" src="http://static.im20.com.cn/global/js/AnyChart.js"></script>
    <script type="text/javascript" src="http://static.im20.com.cn/global/js/AnyChartHTML5.js"></script>
    <title>KPI</title>
</head>
<body class="main-body">

<div class="main-cont">
    <a onclick="history.back(-1)">&lt;&lt;返回</a>
    <div id="demo"></div>
</div>
</body>
</html>
<script type="text/javascript" language="javascript">
    AnyChart.renderingType = anychart.RenderingType.SVG_ONLY;
    var chart = new AnyChart();
    chart.width = '100%';
    chart.height = '90%';
    chart.setXMLFile('chart_xml.jsp?p=<%=product%>&c=<%=cols%>&t=<%=chart_type%>&m=<%=month%>');
    chart.write('demo');
</script>