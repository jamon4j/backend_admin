<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
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
   	<%@include file="../inc/meta.jspf"%>
   	<%
		String tenantId = (String)request.getAttribute("tenantid");
		String userId = (String)request.getAttribute("userid");
 	%>
 	<script type="text/javascript" src="/js/kingsoft/vm_oper.js"></script>
    <title>安全组 - 安全组列表</title>
   	<script>
   	var j = jQuery.noConflict(true);
   	//全选操作
	function selectAll(checked) {
		var sgs = $("input[name='sg_list']");
		if (sgs == 0) {
			return;
		}
		if (checked) {
			for ( var idx = 0; idx < sgs.length; idx++) {
				sgs[idx].checked = true;
			}
		} else {
			for ( var idx = 0; idx < sgs.length; idx++) {
				sgs[idx].checked = false;
			}
		}
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
  	function detail(id){
  		$( "#sg_dialog_"+id ).dialog({
			autoOpen: false,
			postion: "center",
			height:"500",
			width:"600",
			modal: true,
			show: {
				effect: "blind",
				duration: 200
				
			},
			hide: {
				effect: "explode",
				duration: 1000
			}
		});
		$( "#sg_dialog_"+id ).dialog("open");
   	}
   	function addsecuritygroup(tenantId, userId){
   		//创建虚拟机窗口
		   	$( "#addsecuritygroup_form" ).dialog({
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
								url : "/g/user/createsg/"+tenantId+"/"+userId,
								data : {name:$("#create_name").val(),desc:$("#create_desc").val()},
								success : function(data) {
									alert("创建安全组成功");
									window.location.href="/g/user/security_groups/"+tenantId+"/"+userId;
								},
								error : function(XMLHttpRequest,textStatus,errorThrown) {
									alert("创建安全组失败!");
									alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
									alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
									alert("textStatus:"+textStatus);
								}
							});
						
						$(this).dialog( "close" );
						}
					},
					"取消": function() {
						$(this).dialog( "close" );
					}
				},
				close: function() {
				}
			});
	   		$( "#addsecuritygroup_form" ).dialog("open");
   	}
   	
   	function delsecuritygroup(tenantId, userId){
   		//判断虚拟机选中情况
		var sgs = $("input[name='sg_list']");
		if(sgs.length==0){
			alert("目前没有安全组可供操作，请先添加安全组");
			return;
		}
		var count=0;
		var sgids = "";
		for(var idx=0; idx<sgs.length; idx++){
			if(sgs[idx].checked == true){
				++count;
				sgids += sgs[idx].id+",";
			}
		}
		if(count==0){
			alert("编辑前至少选择一个安全组进行操作");
			return;
		}
		sgids = sgids.substring(0, sgids.lastIndexOf(","));
		
		$.ajax({
			type: "POST",
			url : "/g/user/deletesgs/"+tenantId+"/"+userId,
			data : {sgids:sgids},
			success : function(data) {
				alert("删除安全组成功");
				window.location.href="/g/user/security_groups/"+tenantId+"/"+userId;
			},
			error : function(XMLHttpRequest,textStatus,errorThrown) {
				alert("删除安全组失败!");
				alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
				alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
				alert("textStatus:"+textStatus);
			}
		});
   	}
   	function rules(sgid,tenantId,userId){
   		window.location.href="/g/user/security_groups/rules/"+sgid+"/"+tenantId+"/"+userId;
   	}
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：安全组管理<span>&gt;</span>安全组列表</p></div>

<div class="main-cont">
    <h3 class="title">安全组列表
    </h3>

    <div class="set-area">
        <p class="tips-desc">安全组列表<span><img onclick ="addsecuritygroup('<%=tenantId %>','<%=userId %>')" src="/img/add.jpg" alt="新增安全组" height="100%" width="20px" style="float:right;margin-right:100px;"/></span><span><img onclick="delsecuritygroup('<%=tenantId %>','<%=userId %>')" src="/img/delete.jpg" alt="编辑虚拟机" height="100%" width="20px" style="float:right;margin-right:20px;"/></span></p>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
            	<th width="7%">
            		<div class="th-gap"><input name="vm_list_all" type="checkbox" onclick="selectAll(this.checked)"/></div>
            	</th>
                <th width="20%">
                    <div class="th-gap">id</div>
                </th>
                <th width="20%">
                    <div class="th-gap">name</div>
                </th>
                <th width="30%">
                    <div class="th-gap">tenantid</div>
                
                <th width="15%">
                    <div class="th-gap">详情</div>
                </th>
                <th width="15%">
                    <div class="th-gap">安全组规则</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="dto" items="${sglist}" varStatus="status">
					<tr>
						<td><input type="checkbox" name="sg_list" id="${dto.id}" value="${dto.id}"/></td>
						<td>${dto.id} </td>
						<td>${dto.name} </td>
						<td>${dto.tenantId} </td>
						<td><button onclick="detail(${dto.id})">详情</button></td>
						<td><button onclick="rules(${dto.id},'<%=tenantId %>','<%=userId %>')">查看</button></td>
						<div id="sg_dialog_${dto.id}" title="安全组${dto.id}详情" style="display:none">
							<p>id:${dto.id}</p>
							<p>name:${dto.name}</p>
							<p>description:${dto.description}</p>
							<c:forEach var="rule" items="${dto.rules}" varStatus="status">
								<p>rule${rule.id}</p>
								<p>&nbsp;&nbsp;from_port:${rule.fromPort}</p>
								<p>&nbsp;&nbsp;ip_protocol:${rule.ipProtocol}</p>
								<p>&nbsp;&nbsp;to_port:${rule.toPort}</p>
								<p>&nbsp;&nbsp;parent_group_id:${rule.parentGroupId}</p>
								<p>&nbsp;&nbsp;id:${rule.id}</p>
								<p>&nbsp;&nbsp;ip_range:${rule.ipRange}</p>
							</c:forEach>
						</div>
					</tr>
				</c:forEach>

            </tbody>
        </table>
    </div>
</div>
<div id="addsecuritygroup_form" title="新建安全组" style="display: none">
	<p class="validateTips">安全组名称必填</p>
	<form>
		<fieldset>
			<label for="name">安全组名称</label>
			<input type="text" name="name" id="create_name" value="" class="text ui-widget-content ui-corner-all" />
			<label for="desc">安全组描述</label>
			<input type="text" name="description" id="create_desc" value="" class="text ui-widget-content ui-corner-all" />
		</fieldset>
	</form>
</div>
</body>
</html>