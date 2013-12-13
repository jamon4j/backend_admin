<%@ page import="java.util.*" %>
<%@page contentType="text/html;charset=utf-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/inc/acl.inc" %>
<%
%>
<html>
<head>
<%@include file="/inc/admin_meta.inc"%>
</head>
<% try { %>
<body>
    <div class="main-cont">
        <h3 class="title">欢迎进入金山云KPI后台</h3>
        <div class="set-area">
            <div class="form-s1">
                <p class="tips">请从顶部导航选择相应功能。</p>
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
