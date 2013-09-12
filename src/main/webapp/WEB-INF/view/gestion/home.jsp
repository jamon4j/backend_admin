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
                <div align="center" class="item">登录信息：uid=${uid} uname=${uname} isLogin=${isLogin}
                </div>
                <div align="center" class="item" style="font-size: 22px;font-weight: bold;">
                    <c:if test="${type=='public'}">
                        您现在访问的是<b style="color:red;">公有云</b>,请小心使用!
                    </c:if>
                    <c:if test="${type=='private'}">
                        您现在访问的是<b style="color:orange;">私有云</b>,请小心使用!
                    </c:if>
                    <c:if test="${type=='test'}">
                        您现在访问的是<b style="color:green;">测试环境</b>,请玩命造吧!
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>