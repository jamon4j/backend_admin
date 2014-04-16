<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../include/include.jsp"%>
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
			<!-- 添加iConsole标签 -->
			<li><a href="<%=webRootPath%>/iconsole_page/home.jsp">iConsole</a></li>
			<li><a href="<%=webRootPath%>/acl/role_list.jsp">ACL管理</a></li>
			<li><a href="<%=webRootPath%>/iconsole_page/lbs_home.jsp">LBS管理</a></li>
			<!--
        <li><a href="#">用户管理</a></li>
        <li><a href="#">虚拟机管理</a></li>
        <li><a href="#">EBS管理</a></li>
        <li><a href="#">镜像管理</a></li>
    -->
		</ul>
		<input type="hidden" id="changeType" value="${sessionScope.type}" />
		<p>
			<span><font color="red"><b>金山云 - 虚拟主机</b></font></span> <span
				class="line">|</span><span>欢迎回来：( ${sessionScope.username} )
			</span><a onclick="return confirm('确定退出？')"
				href="/logout?ref=%2fgestion%2findex">退出</a> <span class="line">|</span><span>当前：
				<c:if test="${sessionScope.type=='public'}">
					<b style="color:red;">公有云</b>
				</c:if> <c:if test="${sessionScope.type=='private'}">
					<b style="color:orange;">私有云</b>
				</c:if> <c:if test="${sessionScope.type=='test'}">
					<b style="color:green;">测试环境</b>
				</c:if> <!-- <b><A href="#" target="_self" id="change">切换</A></b> -->
				<button type="button" id="change" class="btn btn-primary btn-xs">切换</button>
			</span>
		</p>
	</div>

	<div id="side-menu">
		<div class="menu-group">
			<h2 class="first">机器管理</h2>
			<ul>
				<li><a href="/g/home?" router="home/0/0" target="mainframe">首页</a></li>
				<li><a href="/g/zonelist?" router="home/0/1" target="mainframe">查询zone</a></li>
				<!-- <li><a href="/g/allhostlist?" router="home/0/2" target="mainframe">查询物理机</a></li> -->
				<li>
					<!--<a href="/g/home?" router="home/0/3" target="mainframe">防火墙</a>-->
				</li>
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
				<li><a href="/g/user/search?name=&email=&userid="
					router="home/1/0" target="mainframe">用户信息</a></li>
				<!--
            <li><a href="/g/home?" router="home/1/1" target="mainframe">批量用户操作</a></li>
            <li><a href="/g/home?" router="home/1/2" target="mainframe">重置任务</a></li>
            <li><a href="/g/home?" router="home/1/3" target="mainframe">在线用户管理</a></li>
        -->
			</ul>
		</div>


		<div class="menu-group">
			<h2 class="first">KPI统计</h2>
			<ul>
				<li><a href="/kpi/home.jsp?t=1" router="home/2/0"
					target="mainframe">KPI首页</a></li>
				<li><a href="/kpi/kpi_list.jsp?p=pub_kvm" router="home/2/0"
					target="mainframe">公有云控制台总体情况</a></li>
				<li><a href="/kpi/kpi_list.jsp?p=pub_op" router="home/2/0"
					target="mainframe">公有云操作情况</a></li>
				<li><a href="/kpi/kpi_list.jsp?p=pub_cash" router="home/2/0"
					target="mainframe">公有云付费统计</a></li>
				<li><a href="/kpi/kpi_list.jsp?p=user" router="home/2/0"
					target="mainframe">用户活跃数据</a></li>
				<li><a href="/kpi/kpi_list.jsp?p=ks3" router="home/2/0"
					target="mainframe">KS3付费统计</a></li>
				<li><a href="/kpi/kpi_list.jsp?p=pub_cash_rsc"
					router="home/2/0" target="mainframe">公有云付费(关系客户)</a></li>
			</ul>
		</div>

		<!-- iConsole -->
		<div class="menu-group">
			<h2 class="first">iConsole</h2>
			<ul>
				<li><a href="/iconsole_page/home.jsp?t=1" router="home/3/0" id="order_li"
					target="mainframe">订单账单</a></li>
				<li id="order_div">
					<ul>
						<li><a href="/admin/order/order_index?p=user">&nbsp;&nbsp;|-订单列表</a></li>
						<li><a href="/admin/bill/bill_index?p=user">&nbsp;&nbsp;|-账单列表</a></li>
						<li><a href="/admin/coupon/index?p=user">&nbsp;&nbsp;|-代金券列表</a></li>
						<li><a href="/admin/account/cash_detail_index?p=user">&nbsp;&nbsp;|-现金账户明细</a></li>
					</ul>
				</li>
				<li><a href="/iconsole_page/home.jsp?t=1" id="config_li"
					router="home/3/1" target="mainframe">库存配置</a></li>
				<li id="config_div">
					<ul>
						<li><a href="/admin/kstock/kstock_index?p=user">&nbsp;&nbsp;|-库存列表</a></li>
						<li><a href="/admin/mt/mt_index?p=user">&nbsp;&nbsp;|-配置列表</a></li>
					</ul>
				</li>
				<li><a href="/iconsole_page/home.jsp?t=1" router="home/3/2" id="user_li"
					target="mainframe">用户与KVM</a></li>
				<li id="user_div">
					<ul>
						<li><a href="/admin/user/user_index?p=user">&nbsp;&nbsp;|-用户列表</a></li>
						<li><a href="/admin/user/user_sudo?p=user">&nbsp;&nbsp;|-超管切换</a></li>
						<li><a href="/admin/kvm/application_index?p=user">&nbsp;&nbsp;|-KVM试用申请</a></li>
					</ul>
				</li>
				<li><a href="/iconsole_page/home.jsp?t=1" router="home/3/3" id="brand_li"
					target="mainframe">带宽更新</a></li>
			</ul>
		</div>

		<div class="menu-group">
			<h2 class="first">ACL管理</h2>
			<ul>
				<li><a href="/g/acl/user_list?" router="home/4/0"
					target="mainframe">用户管理</a></li>
				<li><a href="/g/acl/role_list?" router="home/4/1"
					target="mainframe">角色管理</a></li>
				<li><a href="/g/acl/role_uri?" router="home/4/2"
					target="mainframe">权限uri对应表</a></li>
			</ul>
		</div>
		
		<div class="menu-group">
			<h2 class="first">LBS管理</h2>
			<ul>
				<li><a href="/g/lbs/user/list?t=1" router="home/5/1"
					target="mainframe">LBS设置</a></li>
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
		<iframe id="mainframe" name="mainframe" width="100%" height="100%"
			frameborder="0" title="main frame content"></iframe>
	</div>
	<script type='text/javascript'>
		//切换按钮AJAX
		$("#change").click(
				function() {
					var tempString;
					if ($("#changeType").val() == "public") {
						tempString = "确定从公有云切换到私有云？";
					} else if ($("#changeType").val() == "private") {
						tempString = "确定从私有云切换到公有云？";
					}
					if (confirm(tempString)) {
						$.ajax({
							type : "POST",
							url : "/change",
							dataType : "json",
							cache : false,
							success : function(data) {
								if (data.msg == "noNow") {
									alert("切换失败，请您重新操作或登陆");
									window.location.href = "/login";
								} else if (data.msg == "noPrivate") {
									alert("切换私有云失败，请您登陆私有云");
									window.location.href = "/login";
								} else if (data.msg == "noPublic") {
									alert("切换公有云失败，请您登陆公有云");
									window.location.href = "/login";
								} else {
									alert("切换成功");
									setCookie("backend", data.cookie,
											24 * 3600000, "/", "");
									window.location.href = "/g/";
								}
							},
							error : function(a, b, c) {
								alert("错误，请您重新操作或登陆&" + c);
								window.location.href = "/login";
							}
						});
					}
				});
		$("#order_div").hide();
		$("#config_div").hide();
		$("#user_div").hide();
		//带宽升级
		$("#brand_li").click(function(){
			$("#order_div").slideUp(800);
			$("#user_div").slideUp(800);
			$("#config_div").slideUp(800);
		});
		//订单账单div
		var order_click_flag = 0;
		$("#order_li").click(function() {
			if (order_click_flag == 0) {
				$("#order_div").slideDown(800);
				$("#user_div").slideUp(800);
				$("#config_div").slideUp(800);
				order_click_flag = 1;
				config_click_flag = 0;
				user_click_flag = 0;
			} else {
				$("#order_div").slideUp(800);
				order_click_flag = 0;
			}
		});
		//库存配置DIV
		var config_click_flag = 0;
		$("#config_li").click(function() {
			if (config_click_flag == 0) {
				$("#config_div").slideDown(800);
				$("#user_div").slideUp(800);
				$("#order_div").slideUp(800);
				config_click_flag = 1;
				order_click_flag = 0;
				user_click_flag = 0;
			} else {
				$("#config_div").slideUp(800);
				config_click_flag = 0;
			}
		});
		//用户与KVM DIV
		var user_click_flag = 0;
		$("#user_li").click(function() {
			if (user_click_flag == 0) {
				$("#user_div").slideDown(800);
				$("#order_div").slideUp(800);
				$("#config_div").slideUp(800);
				user_click_flag = 1;
				order_click_flag = 0
				config_click_flag = 0;
			} else {
				$("#user_div").slideUp(800);
				user_click_flag = 0;
			}
		});
		if (!window.Xwb)
			Xwb = {};
		Xwb.cfg = {
			basePath : '/gestion/'
		}
		Xwb.request.basePath = Xwb.cfg.basePath;

		//初始化 自适应窗口大小
		var autoSize = function() {
			var height = document.documentElement.clientHeight - 89;
			$('#side-menu').css('height', height + 'px');
			$('#mainDiv').css('height', height + 'px');
		}
		autoSize();
		$(window).resize(autoSize);

		function setCookie(name, value, time, path, domain, secure) {
			var exp = new Date();
			exp.setTime(exp.getTime() + time * 1);
			document.cookie = name
					+ "="
					+ escape(value)
					+ ((exp == null) ? "" : ("; expires=" + exp.toDateString()))
					+ ((path == null) ? "" : ("; path=" + path))
					+ ((domain == null) ? "" : ("; domain=" + domain))
					+ ((secure == true) ? "; secure" : "");
		}
	</script>
</body>

</html>
