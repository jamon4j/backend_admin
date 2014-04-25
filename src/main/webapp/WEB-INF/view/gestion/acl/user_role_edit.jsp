<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<head>
<%@include file="../inc/meta.jspf"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<script>
	$(function() {
		$("[name = chkItem]:checkbox").click(function() {
			var roles = $("#roles").val();
			if (this.checked) {
				$("#roles").val(roles + "," + $(this).val());
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
	function checkIdsNotNull() {
		if ($("#roles").val() == "" || $("#roles").val() == null) {
			alert("请输入角色ID");
			return false;
		}
	}
</script>
</head>

<body>

	<form action="/g/acl/edit_user_role" method="post"
		onsubmit="return checkIdsNotNull();">
		<span style="font-size: small;"><font color="red">可选角色列表</font></span>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<c:forEach items="${list }" var="role" varStatus="status">
				<c:if test="${status.first==true || (status.count-1)%4==0}">
					<tr>
				</c:if>
				<c:set var="isDoing" value="0" />
				<c:forEach items="${now_role_list }" var="now_role"
					varStatus="role_status">
					<c:if test="${now_role == role.id }">
						<c:set var="isDoing" value="1" />
						<td style="font-size: small;"><input type="checkbox"
							checked="checked" name="chkItem" value="${role.id }" />${role.roleName }</td>
					</c:if>
				</c:forEach>
				<c:if test="${isDoing!='1'}">
					<td style="font-size: small;"><input type="checkbox"
						name="chkItem" value="${role.id }" />${role.roleName }</td>
				</c:if>
				<c:if test="${status.count%4==0 ||status.last==true}">
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<table class="table" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<tr style="display: none;">
				<td bgcolor="#f2f2f2" colspan="2"><input type="hidden"
					name="userId" value=${userId } /></td>
			</tr>
			<tr>
				<td height="30" align="center" bgcolor="#f2f2f2" class="left_txt2">角色Ids：</td>
				<td bgcolor="#f2f2f2"><input type="text" name="roles"
					readonly="readonly" value="${now_role }" id="roles" /></td>
			</tr>

			<tr>
				<td width="20%" height="30" align="center" bgcolor="#f2f2f2"
					class="left_txt2" colspan="2"><input type="submit" value="确定" /></td>
			</tr>

		</table>
	</form>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td height="30" align="center" bgcolor="#f2f2f2" class="left_txt2"><a
				href="javascript:self.close()">关闭窗口</a></td>

		</tr>
	</table>

	<h5>说明： 1、角色Ids可以可以写多个，中间用","号隔开</h5>
</body>
</html>