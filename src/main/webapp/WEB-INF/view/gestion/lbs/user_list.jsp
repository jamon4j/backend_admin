<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<!-- <style>
		label, input { display:block; }
		input.text { margin-bottom:12px; width:80%; padding: .4em; }
		select {margin-bottom:12px; width:80%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		div#users-contain { width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
        ul#icons {margin: 0; padding: 0;}
        ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
        ul#icons span.ui-icon {float: left; margin: 0 4px;}
</style> -->
<head>
<%@include file="../inc/meta.jspf"%>
<title>用户 - 用户列表</title>
</head>
<body class="main-body">
	<div class="path">
		<p>
			当前位置：LBS管理<span>&gt;</span>LBS设置
		</p>
	</div>

	<div class="main-cont">
		<h3 class="title">用户信息</h3>
		<form action="/g/lbs/user/search" method="post" id="data_Form"
			style="display:inline-block;">
			id：<input type="text" name="userid" value="" /> email：<input
				type="text" name="email" value="" /> name：<input type="text"
				name="name" value="" /> <input type="submit" name="submit"
				value="查  找" class="ui-state-default ui-corner-all" />
		</form>
		<form action="/g/lbs/user/list?t=1"
			style="margin-left: 5px;display: inline-block;">
			<input type="submit" name="submit" value="查看所有"
				class="ui-state-default ui-corner-all" />
		</form>
		<div class="set-area">

			<table class="table" cellpadding="0" cellspacing="0" width="100%"
				border="0">
				<colgroup>
				</colgroup>
				<thead class="tb-tit-bg">
					<tr>
						<th width="22%">
							<div class="th-gap">id</div>
						</th>
						<th width="10%">
							<div class="th-gap">name</div>
						</th>
						<th width="8%">
							<div class="th-gap">enabled</div>
						</th>
						<th width="10%">
							<div class="th-gap">email</div>
						</th>
						<th width="22%">
							<div class="th-gap">tenant_id</div>
						</th>
						<th width="8%">
							<div class="th-gap">LBS管理</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="dto" items="${list}" varStatus="status">
						<tr>
							<td>${dto.user_id}</td>
							<td>${dto.user_name}</td>
							<td>${dto.enabled}</td>
							<td>${dto.email}</td>
							<td>${dto.tenant_id}</td>
							<td><button class="ui-state-default ui-corner-all"
									onclick="in_pool('${dto.user_id}','${dto.tenant_id}','${dto.user_name}')">进入</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br />
			<%-- <div style="float:right;">
       		<span id="next">
       			<a href="/g/user/list/${page.data.}" style="font-size:4px;color:black;">下一页</a>
       		</span>
        </div> --%>
		</div>
	</div>
	<div id="adduser_form" title="请输入用户名称" style="display: none">
		<p class="validateTips">所有字段均必填</p>
		<form>
			<fieldset>
				<label for="create_name">用户名(必填)</label> <input type="text"
					name="name" id="create_name" value=""
					class="text ui-widget-content ui-corner-all" /> <label for="email">邮箱(必填)</label>
				<input type="text" name="email" id="email" value=""
					class="text ui-widget-content ui-corner-all" />
			</fieldset>
		</form>
	</div>
</body>
<script>
	function in_pool(userId, tenantId, username) {
		window.location.href = "/g/lbs/list/" + userId + "/" + tenantId
				+ "/" + username;
	}
</script>
</html>