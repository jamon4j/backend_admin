<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<head>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<%@include file="../inc/meta.jspf"%>
<title>权限 - 用户列表</title>
<script>
	var j = jQuery.noConflict(true);
	$(function() {
		$("li").hover(function() {
			$(this).addClass("ui-state-hover");
		}, function() {
			$(this).removeClass("ui-state-hover");
		})

		$("#a_select_role").click(function() {
			$("#role_list").dialog({
				autoOpen : false,
				postion : "center",
				modal : true,
				height : "200",
				width : "460"
			});
			$("#role_list").dialog("open");
		});

		$("[name = chkItem]:checkbox").click(function() {
			var roles = $("#roles").val();
			if (this.checked) {
				if (roles == "") {
					$("#roles").val(roles + $(this).val())
				} else {
					$("#roles").val(roles + "," + $(this).val());
				}
			} else {
				var string = roles.split(",");
				var new_role = "";
				for (var int = 0; int < string.length; int++) {
					if (string[int] != $(this).val()) {
						new_role = new_role + string[int] + ",";
					}
				}
				$("#roles").val(new_role.substring(0, new_role.length - 1));
			}
		});
	});

	function openwin(pageUrl) {
		window
				.open(
						pageUrl,
						"newwindow",
						"height=500, width=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=yes, status=no, channelmode=yes left=300 top=300");
	}

	function editRole(userId) {
		openwin("/g/acl/user_role_input?userId=" + userId);
	}

	function deleteUser(userId) {
		if (confirm("确认要删除？")) {
			window.location.href = "/g/acl/delete_user?userId=" + userId;
		}
	}

	function checkIdsNotNull() {
		if ($("#roles").val() == "" || $("#roles").val() == null) {
			alert("请输入角色ID");
			return false;
		}
	}
</script>
</head>
<body class="main-body">
	<div class="path">
		<p>
			权限控制：用户管理<span>&gt;</span>用户列表
		</p>
	</div>
	<div class="main-cont">
		<h3 class="title">用户列表</h3>
		<div class="set-area">
			<table cellpadding="0" cellspacing="0" width="90%" border="0">
				<tr>
					<td style="margin-left: 10%"><span>
							<button onclick="" class="ui-state-default ui-corner-all">创建
								用户</button>
					</span></td>
				</tr>
			</table>
			<table class="table" cellpadding="0" cellspacing="0" width="100%"
				border="0">
				<colgroup>
				</colgroup>
				<thead class="tb-tit-bg">
					<tr>
						<th width="5%">
							<div class="th-gap">id</div>
						</th>
						<th width="20%">
							<div class="th-gap">email</div>
						</th>
						<th width="30%">
							<div class="th-gap">roles</div>
						</th>
						<th width="10%">
							<div class="th-gap">操作</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="dto" items="${list}" varStatus="status">
						<tr>
							<td>${dto.id}</td>
							<td>${dto.username}</td>
							<td>${dto.roles}</td>
							<td><button onclick="editRole('${dto.id}')">编辑</button>
								<button onclick="deleteUser('${dto.id}')">删除</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br />
		</div>
	</div>
	<div id="adduser_form" title="请输入用户名称"
		style="margin-left: 1.5%;margin-right: 2%">
		<p class="validateTips">添加用户</p>
		<form action="/g/acl/add_user" method="post" id="data_Form_add_user"
			onsubmit="return checkIdsNotNull();">
			<fieldset>
				<table cellpadding="0" cellspacing="0" width="100%" border="0">
					<tr>
						<td width="2%"><label for="email"> 邮箱: </label></td>
						<td><input type="text" name="email" id="email" /></td>
					</tr>
					<tr>
						<td><label for="password"> 密码: </label></td>
						<td><input type="text" name="password" id="password" /></td>
					</tr>
					<tr>
						<td><label for="roles"> 角色: </label></td>
						<td><input type="text" name="roles" id="roles"
							readonly="readonly" /><a href="#" id="a_select_role">选择</a></td>
					</tr>
				</table>
				<input type="submit" value="添加" />
			</fieldset>

		</form>
	</div>

	<div id="role_list" title="请选择角色" style="display: none;">
		<span style="font-size: small;"><font color="red">可选角色列表</font></span>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<c:forEach items="${role_list }" var="role" varStatus="status">
				<c:if test="${status.first==true || (status.count-1)%4==0}">
					<tr>
				</c:if>
				<td style="font-size: small;"><input type="checkbox"
					name="chkItem" value="${role.id }" />${role.roleName }</td>
				<c:if test="${status.count%4==0 ||status.last==true}">
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</div>

</body>
</html>