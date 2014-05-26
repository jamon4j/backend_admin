<%@ page import="java.util.*" %>
<%@page contentType="text/html;charset=utf-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/inc/acl.inc" %>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/js/frame/admin.css" media="screen"/>
<script type="text/javascript" src="/js/frame/jquery.min.js"></script>
<script type="text/javascript" src="/js/frame/admin.js"></script>
<script type="text/javascript" src="/js/frame/admin-all.js"></script>
<%@include file="/inc/admin_meta.inc"%>
</head>
<% try { %>
<body>
    <div class="main-cont">
        <h5 class="title">欢迎进入金山云RDS管理</h5>
        <div class="set-area">
            <div class="form-s1">
                <p class="tips">请从左部导航选择相应功能。</p>
            </div>

        </div>
    </div>
<%
} catch (Exception E) {
    out.println(E.getMessage());
}
%>
</body>
</html>
