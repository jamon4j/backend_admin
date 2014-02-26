<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>

<head>
   	<%@include file="../inc/meta.jspf"%>
    <title>权限 - 角色列表</title>
   	<script>
   	var j = jQuery.noConflict(true);
    $(function(){
       $("li").hover(function(){
           $(this).addClass("ui-state-hover");
       },function(){
           $(this).removeClass("ui-state-hover");
       })
    });
    
   	function openwin(pageUrl) { 
     	window.open(pageUrl, "newwindow", "height=500, width=500, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=yes, status=no, channelmode=yes left=300 top=300");
	}
   	
   	function editRole(roleId,roleType,roleName,rolePower){
   		openwin("/g/acl/edit_role_input?roleId="+roleId + "&roleType="+roleType+ "&roleName="+roleName+ "&rolePower="+rolePower);
   	}
   	
   	function deleteRole(roleId){
	   	if (confirm("确认要删除？")) {
   			window.location.href="/g/acl/delete_role?roleId="+roleId;
	   	}
   	}
   	
 </script>
</head>
<body class="main-body">
<div class="path"><p>权限控制：角色管理<span>&gt;</span>角色列表</p></div>

<div class="main-cont">
    <h3 class="title">角色列表
    </h3>
    <div class="set-area">

        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="5%">
                    <div class="th-gap">id</div>
                </th>
                <th width="20%">
                    <div class="th-gap">角色名称</div>
                </th>
                <th width="30%">
                    <div class="th-gap">角色权限</div>
                </th>
                <th width="8%">
                    <div class="th-gap">操作</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="dto" items="${list}" varStatus="status">
					<tr>
						<td>${dto.id} </td>
						<td>${dto.roleName} </td>
						<td>${dto.rolePower}</td>
						<td><button onclick="editRole('${dto.id}','${dto.roleType}','${dto.roleName}','${dto.rolePower}')">编辑</button> <button onclick="deleteRole('${dto.id}')">删除</button></td>
					</tr>
				</c:forEach>
            </tbody>
        </table>
        <br/>
    </div>
</div>
<div id="adduser_form" title="请输入角色名称">
	<p class="validateTips">添加角色</p>
	<form action="/g/acl/add_role"  method="post" id="data_Form_add_role">
		<fieldset>
            <label for="name"> 名称:   </label>
            <input type="text" name="name" id="name"/>
            </br>
            <label for="roles"> 角色权限:   </label>
            <input type="text" name="roles" id="roles"/>
            </br>
            <label for="roleType">角色类型</label>
            <input type="text" name="roleType" id="roleType"/>
            </br>
            <input type="submit" value="添加"/>
		</fieldset>
	</form>
</div>
</body>
</html>