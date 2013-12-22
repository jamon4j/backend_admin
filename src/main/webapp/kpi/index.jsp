<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ksyun.vm.kpi.common.*" %>
<%@include file="/inc/acl.inc"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link type="text/css" rel="stylesheet" href="/js/frame/admin.css" media="screen"/>
<script type="text/javascript" src="/js/frame/jquery.min.js"></script>
<script type="text/javascript" src="/js/frame/admin.js"></script>
<script type="text/javascript" src="/js/frame/admin-all.js"></script>
<%@include file="/inc/admin_meta.inc"%>
<title>金山云KPI管理中心</title>
</head>
<body>
  <%@include file="/inc/admin_menu.inc"%>
            <div id="mainDiv" class="main-frame">
                <iframe src="home.jsp" id="mainframe" name="mainframe" width="100%" height="100%" frameborder="0" title="main frame content"></iframe>
            </div>
</body>

</html>
