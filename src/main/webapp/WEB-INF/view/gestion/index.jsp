<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="inc/meta.jspf"%>
    <title>金山云 - 虚拟主机管理中心</title>
</head>
<body>
<div id="header">
    <h1>金山云虚拟主机管理中心</h1>
    <ul>
        <li><a href="<%=webRootPath%>/zonelist">机器管理</a></li>
        <%-- <li><a href="<%=webRootPath%>/sglist">安全组管理</a></li> --%>
        <li><a href="<%=webRootPath%>/userlist">用户管理</a></li>
        <li><a href="<%=webRootPath%>/kpi/home.jsp">KPI统计</a></li>
        <li><a href="<%=webRootPath%>/acl/role_list.jsp">ACL管理</a></li><!--
        <li><a href="#">用户管理</a></li>
        <li><a href="#">虚拟机管理</a></li>
        <li><a href="#">EBS管理</a></li>
        <li><a href="#">镜像管理</a></li>
    --></ul>
    <p>
        <span><font color="red"><b>金山云 - 虚拟主机</b></font></span>
        <span class="line">|</span>
        <span>欢迎回来：${uname} ( ${uid} ) </span>
        <span class="line">|</span>
        <a href="/gestion/logout?ref=%2fgestion%2findex">退出</a>
    </p>
</div>

<div id="side-menu">
    <div class="menu-group">
        <h2 class="first">机器管理</h2>
        <ul>
            <li><a href="/g/home?" router="home/0/0" target="mainframe">首页</a></li>
            <li><a href="/g/zonelist?" router="home/0/1" target="mainframe">查询zone</a></li>
            <!-- <li><a href="/g/allhostlist?" router="home/0/2" target="mainframe">查询物理机</a></li> -->
            <li><!--<a href="/g/home?" router="home/0/3" target="mainframe">防火墙</a>--></li>
        </ul>
    </div>
   <!--  <div class="menu-group">
        <h2 class="first">安全组管理</h2>
        <ul>
            <li><a href="/g/sglist?" router="home/1/0" target="mainframe">安全组信息</a></li>
            <li><a href="/g/sgrulelist?" router="home/1/1" target="mainframe">安全组规则信息</a></li>
            <li><a href="/g/home?" router="home/1/1" target="mainframe">批量用户操作</a></li>
            <li><a href="/g/home?" router="home/1/2" target="mainframe">重置任务</a></li>
            <li><a href="/g/home?" router="home/1/3" target="mainframe">在线用户管理</a></li>
       </ul>
    </div> -->
    <div class="menu-group">
        <h2 class="first">用户管理</h2>
        <ul>
            <li><a href="/g/user/search?name=&email=&userid=" router="home/1/0" target="mainframe">用户信息</a></li><!--
            <li><a href="/g/home?" router="home/1/1" target="mainframe">批量用户操作</a></li>
            <li><a href="/g/home?" router="home/1/2" target="mainframe">重置任务</a></li>
            <li><a href="/g/home?" router="home/1/3" target="mainframe">在线用户管理</a></li>
        --></ul>
    </div>


    <div class="menu-group">
        <h2 class="first">KPI统计</h2>
        <ul>
            <li><a href="/kpi/home.jsp?t=1" router="home/2/0" target="mainframe">KPI首页</a></li>
            <li><a href="/kpi/kpi_list.jsp?p=pub_kvm" router="home/2/0" target="mainframe">公有云控制台总体情况</a></li>
            <li><a href="/kpi/kpi_list.jsp?p=pub_op" router="home/2/0" target="mainframe">公有云操作情况</a></li>
            <li><a href="/kpi/kpi_list.jsp?p=pub_cash" router="home/2/0" target="mainframe">公有云付费统计</a></li>
            <li><a href="/kpi/kpi_list.jsp?p=user" router="home/2/0" target="mainframe">用户活跃数据</a></li>
            <li><a href="/kpi/kpi_list.jsp?p=ks3" router="home/2/0" target="mainframe">KS3付费统计</a></li>
            <li><a href="/kpi/kpi_list.jsp?p=pub_cash_rsc" router="home/2/0" target="mainframe">公有云付费(关系客户)</a></li>
        </ul>
    </div>
    
    <div class="menu-group">
        <h2 class="first">ACL管理</h2>
        <ul>
            <li><a href="/g/acl/user_list?" router="home/3/0" target="mainframe">用户管理</a></li>
            <li><a href="/g/acl/role_list?" router="home/3/1" target="mainframe">角色管理</a></li>
        </ul>
    </div>


    <div class="menu-group">
        <h2 class="first">用户管理</h2>
        <ul>
            <li><a href="/g/home?" router="home/2/0" target="mainframe">KPI-用户</a></li>
            <li><a href="/g/home?" router="home/2/1" target="mainframe">KPI-战斗</a></li>
            <li><a href="/g/home?" router="home/2/2" target="mainframe">KPI-UV留存率</a></li>
            <li>--------------</li>
        </ul>
    </div>
    <div class="menu-group">
        <h2 class="first">虚拟机管理</h2>
        <ul>
            <li><a href="/g/home?" router="home/3/0" target="mainframe">用户信息</a></li>
        </ul>
    </div>
    <div class="menu-group">
        <h2 class="first">EBS管理</h2>
        <ul>
            <li><a href="/g/home?" router="home/4/0" target="mainframe">激活用户查询</a></li>
            <li><a href="/g/home?" router="home/4/1" target="mainframe">修改激活码</a></li>
        </ul>
    </div>
    <div class="menu-group">
        <h2 class="first">镜像管理</h2>
        <ul>
            <li><a href="/g/home?" router="home/5/0" target="mainframe">HOME</a></li>
            <li><a href="/g/home?" router="home/5/1" target="mainframe">home2</a></li>
        </ul>
    </div>
</div>


<div id="mainDiv" class="main-frame">
    <iframe id="mainframe" name="mainframe" width="100%" height="100%" frameborder="0" title="main frame content"></iframe>
</div>
<script type='text/javascript'>
    if (!window.Xwb)Xwb = {};
    Xwb.cfg = {
        basePath:'/gestion/'
    }
    Xwb.request.basePath = Xwb.cfg.basePath;

    //初始化 自适应窗口大小
    var autoSize = function () {
        var height = document.documentElement.clientHeight - 89;
        $('#side-menu').css('height', height + 'px');
        $('#mainDiv').css('height', height + 'px');
    }
    autoSize();
    $(window).resize(autoSize);

</script>
</body>

</html>
