<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<style>
		body { font-size: 62.5%; }
		label, input { display:block; }
		input.text { margin-bottom:12px; width:80%; padding: .4em; }
		select {margin-bottom:12px; width:80%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		div#users-contain { width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
	</style>
<head>
    <%@include file="../inc/meta.jspf"%>
    <title>VM - VM列表</title>
   	<script language="javascript">
		var j = jQuery.noConflict(true);
		var vmid=null;
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
	  		$( "#vm_dialog_"+id ).dialog({
				autoOpen: false,
				postion: "center",
				modal: true,
				height:"700",
				width:"900",
				show: {
					effect: "blind",
					duration: 200
					
				},
				hide: {
					effect: "explode",
					duration: 1000
				}
			});
			$( "#vm_dialog_"+id ).dialog("open");
	   	}
   	
	   	function addvm(tenantid,userid){
	   		$.ajax({
				url : "/g/user/image_public_id_list/"+tenantid+"/"+userid,
				dataType : 'text',
				success : function(data) {
					var imageJson = eval('('+data+')');
					for(i=0;i<imageJson.length;i++){
						$("#create_imageRef").append("<option value='"+imageJson[i].id+"'>"+imageJson[i].name+"</option>");
					}
				}
			});
			//获得虚拟机类型列表
			$.ajax({
				url : "/g/user/flavor_id_list/"+tenantid+"/"+userid,
				dataType : 'text',
				success : function(data) {
					var flavorJson = eval('('+data+')');
					for(i=0;i<flavorJson.length;i++){
						$("#create_flavorRef").append("<option value='"+flavorJson[i].id+"'>"+flavorJson[i].name+"</option>");
					}
				}
			});
			
			//创建虚拟机窗口
		   	$( "#addvm_form" ).dialog({
				autoOpen: false,
				height: 550,
				width: 450,
				modal: true,
				buttons: {
					"创建": function() {
						var bValid = true;
						var create_name = $( "#create_name" ),
						create_imageRef = $( "#create_imageRef" ),
						create_max_count = $( "#create_max_count" ),
						create_min_count = $( "#create_min_count" ),
						create_security_groups = $( "#create_security_groups" ),
						create_adminPass = $( "#create_adminPass" ),
						allFields = $( [] ).add( create_name ).add( create_imageRef ).add( create_max_count ).add( create_min_count ).add( create_security_groups ).add( create_adminPass ),
						tips = $( ".validateTips" );
						
						allFields.removeClass( "ui-state-error" );
						bValid = bValid && checknull( tips, create_name, "name");
						bValid = bValid && checknull( tips, create_imageRef, "imageRef");
						bValid = bValid && checknull( tips, create_max_count, "max_count");
						bValid = bValid && checknull( tips, create_min_count, "min_count");
						bValid = bValid && checknull( tips, create_security_groups, "security_groups");
						bValid = bValid && checknull( tips, create_adminPass, "adminPass");
						if(bValid){
							$.ajax({
								type: "POST",
								url : "/g/user/createvm/"+tenantid+"/"+userid,
								data : {name:$("#create_name").val(),
										imageRef:$("#create_imageRef").val(),
										flavorRef:$("#create_flavorRef").val(),
										max_count:$("#create_max_count").val(),
										min_count:$("#create_min_count").val(),
										security_groups:$("#create_security_groups").val(),
										adminPass:$("#create_adminPass").val()},
										success : function(data) {
									var jsonobj=eval('('+data+')');
									window.location.href="/g/user/vmlist/"+tenantid+"/"+userid+"/"+jsonobj.server.id;
								},
								error : function(XMLHttpRequest,textStatus,errorThrown) {
									alert("创建虚拟机失败!");
									alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
									alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
									alert("textStatus:"+textStatus);
								}
							});
						
						$("#addvm_form").dialog( "close" );
						}
					},
					"取消": function() {
						$("#addvm_form").dialog( "close" );
					}
				},
				close: function() {
					$("#create_imageRef").empty();
					$("#create_imageRef").append("<option value=''>请选择镜像</option>");
					$("#create_flavorRef").empty();
					$("#create_flavorRef").append("<option value=''>请选择虚机类型</option>");
				//	allFields.val( "" );
				}
			});
	   		$( "#addvm_form" ).dialog("open");
	   	}
	   	///////////   创建vm结束  ///////////
 	</script>
</head>
<%
	String tenantId = (String)request.getAttribute("tenantid");
	String userId = (String)request.getAttribute("usrid");
%>
<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span><a href="/g/user/list/1">用户信息</a><span>&gt;</span>vm列表</div>
<div class="main-cont">
    <h3 class="title">vm列表
    </h3>

    <div class="set-area">
        <div><p class="tips-desc">新创建的vm<span><img src="/img/refresh.jpg" height="100%" width="20px" style="margin-left:20px;" onclick="window.location.reload()"/></span><!--<img onclick ="addvm('<%=tenantId %>','<%=userId %>')" src="/img/add.jpg" alt="新增虚拟机" height="100%" width="20px" style="float:right;margin-right:100px;"/>--></p></div>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="25%">
                    <div class="th-gap">虚拟机id</div>
                </th>
                <th width="10%">
                    <div class="th-gap">虚拟机状态</div>
                </th>	
                <th width="15%">
                    <div class="th-gap">name</div>
                </th>
                <th width="15%">
                    <div class="th-gap">所属物理机</div>
                </th>
                <th width="22%">
                    <div class="th-gap">tenant_id</div>
                </th>
                 <th>
                    <div class="th-gap">详情</div>
                </th>
            </tr>
            </thead>
            <tbody>
					<tr>
						<td>${dto.id} </td>
						<td>${dto.status} </td>
						<td>${dto.name} </td>
						<td>${dto.OS_EXT_SRV_ATTR_host} </td>
						<td>${dto.tenant_id} </td>
						<td><button onclick="detail('${status.count}')">详情</button></td>
						<div id="vm_dialog_${status.count}" title="vm_${status.count}详情" style="display:none">
							<p>status: ${dto.status}</p>
							<p>updated: ${vm.updated}</p>
							<p>OS-EXT-SRV-ATTR:host: ${dto.OS_EXT_SRV_ATTR_host}</p>
							<p>key_name: ${dto.key_name}</p>
							<p>OS-EXT-STS:task_state: ${dto.OS_EXT_STS_task_state}</p>
							<p>OS-EXT-STS:vm_state: ${dto.OS_EXT_STS_vm_state}</p>
							<p>OS-EXT-SRV-ATTR:instance_name: ${dto.OS_EXT_SRV_ATTR_instance_name}</p>
							<p>OS-EXT-SRV-ATTR:hypervisor_hostname: ${dto.OS_EXT_SRV_ATTR_hypervisor_hostname}</p>
							<p>id: ${dto.id}</p>
							<p>user_id: ${dto.user_id}</p>
							<p>name: ${dto.name}</p>
							<p>created: ${dto.created}</p>
							<p>tenant_id: ${dto.tenant_id}</p>
							<p>OS-DCF:diskConfig: ${dto.OS_DCF_diskConfig}</p>
							<p>public_ip_address: </p>
							<c:forEach var="public_address" items="${dto.addresses.publicAddress}" varStatus="status">
								<p>&nbsp;&nbsp;version: ${public_address.version}</p>
								<p>&nbsp;&nbsp;address: ${public_address.addr}</p>
							</c:forEach>
							<p>private_ip_address: </p>
							<c:forEach var="private_address" items="${dto.addresses.privateAddress}" varStatus="status">
								<p>&nbsp;&nbsp;version: ${private_address.version}</p>
								<p>&nbsp;&nbsp;address: ${private_address.addr}</p>
							</c:forEach>
							<p>progress: ${dto.progress}</p>
							<p>OS-EXT-STS:power_state: ${dto.OS_EXT_STS_power_state}</p>
							<p>config_drive: ${dto.config_drive}</p>
							<p>security_groups: </p>
							<c:forEach var="security_group" items="${dto.security_groups}" varStatus="status">
								<p>&nbsp;&nbsp;name: ${security_group.name}</p>
							</c:forEach>
							<p>flavor: </p>
							<p>&nbsp;&nbsp;id: ${dto.flavor.id}</p>
							<p>&nbsp;&nbsp;links: </p>
							<c:forEach var="link" items="${dto.flavor.links}" varStatus="status">
								<p>&nbsp;&nbsp;&nbsp;&nbsp;href: ${link.href}</p>
								<p>&nbsp;&nbsp;&nbsp;&nbsp;rel: ${link.rel}</p>
							</c:forEach>
							<p>&nbsp;&nbsp;faults: </p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;message: ${vm.fault.message}</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;code: ${vm.fault.code}</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;created: ${vm.fault.created}</p>
							<p>&nbsp;&nbsp;metadata: </p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;storage_location: ${vm.metadata.storage_location}</p>
						</div>
					</tr>
            </tbody>
        </table>
    </div>
</div>
<div id="addvm_form" title="创建虚拟机" style="display: none">
	<p class="validateTips">All form fields are required.</p>
	<form>
		<fieldset>
			<label for="name">name(必填)</label>
			<input type="text" name="name" id="create_name" value="None" class="text ui-widget-content ui-corner-all" />
			<label for="imageRef">镜像(必选)</label>
			<select name="imageRef" id="create_imageRef" class="text ui-widget-content ui-corner-all" >
			<option value="">请选择镜像</option>
			</select>
			<label for="flavorRef">虚机类型(必选)</label>
			<select name="flavorRef" id="create_flavorRef" class="text ui-widget-content ui-corner-all" >
			<option value="">请选择虚机类型</option>
			</select>
			<label for="max_count">max_count(必填)</label>
			<input type="text" name="max_count" id="create_max_count" value="" class="text ui-widget-content ui-corner-all" />
			<label for="min_count">min_count(必填)</label>
			<input type="text" name="min_count" id="create_min_count" value="" class="text ui-widget-content ui-corner-all" />
			<label for="adminPass">初始密码(必填)</label>
			<input type="text" name="adminPass" id="create_adminPass" value="" class="text ui-widget-content ui-corner-all" />
			<label for="security_groups">security_groups(必选)</label>
			<select multiple="multiple" name="security_groups" id="create_security_groups" class="text ui-widget-content ui-corner-all" />
			<option value="default">default</option>
			
		</fieldset>
	</form>
</div>
</body>
</html>