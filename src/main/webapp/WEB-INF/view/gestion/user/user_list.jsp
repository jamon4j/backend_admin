<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<style>
		label, input { display:block; }
		input.text { margin-bottom:12px; width:80%; padding: .4em; }
		select {margin-bottom:12px; width:80%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		div#users-contain { width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
</style>
<head>
   	<%@include file="../inc/meta.jspf"%>
    <title>用户 - 用户列表</title>
   	<script>
   	var j = jQuery.noConflict(true);
   	function ebs_snapshot_list(tenantId, userId){
   		window.location.href="/g/user/ebs_snapshot_list/"+tenantId+"/"+userId;
   	}
   	
   	function vm_list(tenantId, userId){
   		window.location.href="/g/user/vmlist/"+tenantId+"/"+userId;
   	}
   	
   	function security_groups(tenantId, userId){
   		window.location.href="/g/user/security_groups/"+tenantId+"/"+userId;
   	}
   	function checknull( tips, o, n ) {
		if ( o.val() == null || o.val() == "" ) {
			alert( n + " 不能为空!!!" );
			o.addClass( "ui-state-error" );
			updateTips( tips, n + " 不能为空!!!" );
			return false;
		} else {
			return true;
		}
	}
	function updateTips( tips, t ) {
		tips.text( t ).addClass( "ui-state-highlight" );
		setTimeout(function() {
			tips.removeClass( "ui-state-highlight", 1500 );
		}, 500 );
	}
   	function adduser(){
   		//创建虚拟机窗口
		   	$( "#adduser_form" ).dialog({
				autoOpen: false,
				height: 400,
				width: 400,
				modal: true,
				buttons: {
					"创建": function() {
						var bValid = true;
						var create_name = $( "#create_name" ),
						allFields = $( [] ).add( create_name ),
						tips = $( ".validateTips" );
						
						allFields.removeClass( "ui-state-error" );
						bValid = bValid && checknull( tips, create_name, "name");
						if(bValid){
							$.ajax({
								type: "POST",
								url : "/g/user/createuser",
								data : {name:$("#create_name").val()},
								success : function(data) {
									if(data.indexOf("token") != -1){
										alert("创建用户成功!");
										window.location.href="/g/user/list/1";
									}else{
										alert("创建用户失败");
									}
								},
								error : function(XMLHttpRequest,textStatus,errorThrown) {
									alert("创建用户失败!");
									alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
									alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
									alert("textStatus:"+textStatus);
								}
							});
						
						$("#adduser_form").dialog( "close" );
						}
					},
					"取消": function() {
						$("#adduser_form").dialog( "close" );
					}
				},
				close: function() {
				}
			});
	   		$( "#adduser_form" ).dialog("open");
   	}
   	
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：用户管理<span>&gt;</span>用户信息</p></div>

<div class="main-cont">
    <h3 class="title">用户信息
    </h3>

    <div class="set-area">
        <p class="tips-desc">目前仅提供查看功能，其他功能请稍后。<img onclick ="adduser()" src="/img/add.jpg" alt="新增用户" height="100%" width="20px" style="float:right;margin-right:100px;"/></p>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
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
                <th>
                    <div class="th-gap">email</div>
                </th>
                <th width="22%">
                    <div class="th-gap">tenant_id</div>
                </th>
                <th width="8%">
                    <div class="th-gap">安全组</div>
                </th>
                <th width="8%">
                    <div class="th-gap">EBS,镜像</div>
                </th>
                <th width="8%">
                    <div class="th-gap">虚拟机</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="dto" items="${page.data}" varStatus="status">
					<tr>
						<td>${dto.id} </td>
						<td>${dto.name} </td>
						<td>${dto.enabled}</td>
						<td>${dto.email}</td>
						<td>${dto.tenantId}</td>
						<td><button onclick="security_groups('${dto.tenantId}','${dto.id}')">查看</button></td>
						<td><button onclick="ebs_snapshot_list('${dto.tenantId}','${dto.id}')">查看</button></td>
						<td><button onclick="vm_list('${dto.tenantId}','${dto.id}')">查看</button></td>
					</tr>
				</c:forEach>
            </tbody>
        </table>
        <br/>
        <div style="float:right;">
        	<c:if test="${page.pageIndex-1>0}">
        		<span id="previus">
        			<a href="/g/user/list/${page.pageIndex-1}" style="font-size:4px;color:black;">上一页</a>
        		</span>
       		</c:if>
        	&nbsp;&nbsp;&nbsp;&nbsp;
        	<c:if test="${fn:length(page.data) == 10}">
        		<span id="next">
        			<a href="/g/user/list/${page.pageIndex+1}" style="font-size:4px;color:black;">下一页</a>
        		</span>
       		</c:if>
        </div>
    </div>
</div>
<div id="adduser_form" title="请输入用户名称" style="display: none">
	<p class="validateTips">所有字段均必填</p>
	<form>
		<fieldset>
			<label for="name">用户名(必填)</label>
			<input type="text" name="name" id="create_name" value="" class="text ui-widget-content ui-corner-all" />
		</fieldset>
	</form>
</div>
</body>
</html>