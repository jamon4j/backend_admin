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
    
   	function editRole(userId){
   		openwin("/g/acl/user_role_input?userId="+userId);
   	}
   	
   	function deleteUser(userId){
	   	 if (confirm("确认要删除？")) {
	   			window.location.href="/g/acl/delete_user?userId="+userId;
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
						<td>${dto.id} </td>
						<td>${dto.username} </td>
						<td>${dto.roles}</td>
						<td><button onclick="editRole('${dto.id}')">编辑</button> <button onclick="deleteUser('${dto.id}')">删除</button></td>
					</tr>
				</c:forEach>
            </tbody>
        </table>
        <br/>
    </div>
</div>
<div id="adduser_form" title="请输入用户名称">
	<p class="validateTips">所有字段均必填</p>
	<form action="/g/acl/add_user"  method="post" id="data_Form_add_user">
		<fieldset>
            <label for="email"> 邮箱:   </label>
            <input type="text" name="email" id="email"/>
            </br>
            <label for="password"> 密码:   </label>
            <input type="text" name="password" id="password"/>
            </br>
            <label for="roles">角色id</label>
            <input type="text" name="roles" id="roles"/>
            </br>
            <input type="submit" value="提交"/>
		</fieldset>
	</form>
</div>
</body>
</html>