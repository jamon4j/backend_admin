<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="inc/meta.jspf"%>
    <title>Gestion Home</title>
</head>
<body>
<div class="main-cont">
    <h3 class="title">欢迎进入 金山云-虚拟主机 管理后台</h3>

    <div class="set-area">
        <div class="form-s1">
            <p class="tips">请从顶部导航 和 左侧导航 选择相应功能。</p>

            <p class="tips">不同的后台页面，需要不同的权限级别。</p>
        </div>

        <div class="operate-cont">
            <div class="form-s2">
                <div class="item">登录信息：uid=${uid} uname=${uname} isLogin=${isLogin}
                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>