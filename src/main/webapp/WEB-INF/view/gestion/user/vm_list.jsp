<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<script type="text/javascript" src="/js/kingsoft/vm_oper.js"></script>

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
    <%
		String tenantId = (String)request.getAttribute("tenantid");
		String userId = (String)request.getAttribute("userid");
 	%>
    <title>VM - VM列表</title>
   	<script language="javascript">
   		var j = jQuery.noConflict(true);
   		///////////   编辑vm开始  ///////////
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
		function editvm(){
			//判断虚拟机选中情况
			var vms = $("input[name='vm_list']");
			if(vms.length==0){
				alert("目前没有虚拟机可供操作，请先添加虚拟机");
				return;
			}
			var count=0;
			var vmids = "";
			for(var idx=0; idx<vms.length; idx++){
				if(vms[idx].checked == true){
					++count;
					vmids += vms[idx].id+",";
				}
			}
			if(count==0){
				alert("编辑前至少选择一个虚拟机进行操作");
				return;
			}
			vmids = vmids.substring(0, vmids.lastIndexOf(","));
			//编辑窗口
	   		$( "#editvm_form").dialog({
				autoOpen: false,
				postion: "center",
				modal: true,
				height:"250",
				width:"200",
				buttons: {
					"确认": function() {
						var val=$('input:radio[name="edit"]:checked').val();
						//删除
						if(val == "delete"){
							$.ajax({
								type: "POST",
								url : "/g/user/delete_vm/<%=tenantId %>/<%=userId %>",
								data : {"vmids":vmids},
								success : function(data) {
									alert("delete虚拟机成功，因机器情况各异，可能需要稍等片刻!");
									window.location.href="/g/user/vmlist/<%=tenantId %>/<%=userId %>";
								},
								error : function(XMLHttpRequest,textStatus,errorThrown) {
									alert("删除虚拟机失败!");
									alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
									alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
									alert("textStatus:"+textStatus);
								}
							});
						//其他操作
						}else{
							var action = "";
							if(val == "reboot"){
								action = "reboot";
							}/*if(val == "pause"){
								action = "pause";
							}if(val == "suspend"){
								action = "suspend";
							}if(val == "resume"){
								action = "resume";
							}*/
							else if(val == "stop"){
								action = "stop";
							}else if(val == "start"){
								action = "start";
							}
							$.ajax({
								type: "POST",
								url : "/g/user/edit_vm/"+action+"/<%=tenantId %>/<%=userId %>",
								data : {"vmids":vmids},
								success : function(data) {
									alert(action+"虚拟机成功，因机器情况各异，可能需要稍等片刻!");
									window.location.href="/g/user/vmlist/<%=tenantId %>/<%=userId %>";
								},
								error : function(XMLHttpRequest,textStatus,errorThrown) {
									alert("删除虚拟机失败!");
									alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
									alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
									alert("textStatus:"+textStatus);
								}
							});
						} 
						$(this).dialog( "close" );
					},
					"取消": function() {
						$(this).dialog( "close" );
					}
				},
				close : function() {
				}
			});
			$( "#editvm_form" ).dialog("open");
	   	}
	   	///////////   编辑vm结束  ///////////
	   	
	   	///////////   vm详情开始  ///////////
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
   		///////////   vm详情结束  ///////////
   		///////////   创建vm开始  ///////////
	   	function addvm(tenantid,userid){
	   		//获得镜像列表
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
						allFields = $( [] ).add( create_name ).add( create_imageRef ).add( create_max_count ).add( create_min_count ).add( create_security_groups ),
						tips = $( ".validateTips" );
						
						allFields.removeClass( "ui-state-error" );
						bValid = bValid && checknull( tips, create_name, "name");
						bValid = bValid && checknull( tips, create_imageRef, "imageRef");
						bValid = bValid && checknull( tips, create_max_count, "max_count");
						bValid = bValid && checknull( tips, create_min_count, "min_count");
						bValid = bValid && checknull( tips, create_security_groups, "security_groups");
						if(bValid){
							$.ajax({
								type: "POST",
								url : "/g/user/createvm/"+tenantid+"/"+userid,
								data : {name:$("#create_name").val(),imageRef:$("#create_imageRef").val(),flavorRef:$("#create_flavorRef").val(),max_count:$("#create_max_count").val(),min_count:$("#create_min_count").val(),security_groups:$("#create_security_groups").val()},
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

<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span><a href="/g/user/list/1">用户信息</a><span>&gt;</span>vm列表</div>
<div class="main-cont">
    <h3 class="title">vm列表
    </h3>

    <div class="set-area">
        <div><p class="tips-desc">vm列表，vm数量：<c:out value="${fn:length(vmlist)}"></c:out><span><img src="/img/refresh.jpg" height="100%" width="20px" style="margin-left:20px;" onclick="window.location.reload()"/></span><img onclick ="addvm('<%=tenantId %>','<%=userId %>')" src="/img/add.jpg" alt="新增虚拟机" height="100%" width="20px" style="float:right;margin-right:100px;"/></span><span><img onclick="editvm()" src="/img/edit.jpg" alt="编辑虚拟机" height="100%" width="20px" style="float:right;margin-right:20px;"/></span></p></div>
			        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
            	<th width="7%">
            		<div class="th-gap"><input name="vm_list_all" type="checkbox" onclick="selectAll(this.checked)"/></div>
            	</th>
                <th width="25%">
                    <div class="th-gap">虚拟机id</div>
                </th>
                <th width="10%">
                    <div class="th-gap">虚拟机状态</div>
                </th>	
                <th width="15%">
                    <div class="th-gap">instance_name</div>
                </th>
                <th width="10%">
                    <div class="th-gap">所属物理机</div>
                </th>
                <th width="22%">
                    <div class="th-gap">tenant_id</div>
                </th>
                 <th width="11%">
                    <div class="th-gap">详情</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="vm" items="${vmlist}" varStatus="status">
					<tr>
						<td><input type="checkbox" name="vm_list" id="${vm.id}" value="${vm.id}"/></td>
						<td>${vm.id} </td>
						<td>${vm.status} </td>
						<td>${vm.OS_EXT_SRV_ATTR_instance_name} </td>
						<td>${vm.OS_EXT_SRV_ATTR_host} </td>
						<td>${vm.tenant_id} </td>
						<td><button onclick="detail('${vm.id}')">详情</button></td>
						<div id="vm_dialog_${vm.id}" title="vm_${vm.id}详情" style="display:none">
							<p>status: ${vm.status}</p>
							<p>updated: ${vm.updated}</p>
							<p>OS-EXT-SRV-ATTR:host: ${vm.OS_EXT_SRV_ATTR_host}</p>
							<p>key_name: ${vm.key_name}</p>
							<p>OS-EXT-STS:task_state: ${vm.OS_EXT_STS_task_state}</p>
							<p>OS-EXT-STS:vm_state: ${vm.OS_EXT_STS_vm_state}</p>
							<p>OS-EXT-SRV-ATTR:instance_name: ${vm.OS_EXT_SRV_ATTR_instance_name}</p>
							<p>OS-EXT-SRV-ATTR:hypervisor_hostname: ${vm.OS_EXT_SRV_ATTR_hypervisor_hostname}</p>
							<p>id: ${vm.id}</p>
							<p>user_id: ${vm.user_id}</p>
							<p>name: ${vm.name}</p>
							<p>created: ${vm.created}</p>
							<p>tenant_id: ${vm.tenant_id}</p>
							<p>OS-DCF:diskConfig: ${vm.OS_DCF_diskConfig}</p>
							<p>accessIPv4: ${vm.accessIPv4}</p>
							<p>accessIPv6: ${vm.accessIPv6}</p>
							<p>progress: ${vm.progress}</p>
							<p>OS-EXT-STS:power_state: ${vm.OS_EXT_STS_power_state}</p>
							<p>config_drive: ${vm.config_drive}</p>
							<p>security_groups: </p>
							<c:forEach var="security_group" items="${vm.security_groups}" varStatus="status">
								<p>&nbsp;&nbsp;name: ${security_group.name}</p>
							</c:forEach>
							<p>flavor: </p>
							<p>&nbsp;&nbsp;id: ${vm.flavor.id}</p>
							<p>&nbsp;&nbsp;links: </p>
							<c:forEach var="link" items="${vm.flavor.links}" varStatus="status">
								<p>&nbsp;&nbsp;&nbsp;&nbsp;href: ${link.href}</p>
								<p>&nbsp;&nbsp;&nbsp;&nbsp;rel: ${link.rel}</p>
							</c:forEach>
						</div>
					</tr>
				</c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div id="editvm_form" title="请选择下列操作" style="display: none">
	<form>
		<div><input type="radio" name="edit" value="delete" id="edit_delete" style="float:left;"/>&nbsp;&nbsp;删除虚拟机</div>
		<div><input type="radio" name="edit" value="reboot" id="edit_reboot" style="float:left;"/>&nbsp;&nbsp;重启虚拟机</div>
		<!--<div><input type="radio" name="edit" value="pause" id="edit_pause" style="float:left;"/>&nbsp;&nbsp;暂停虚拟机</div>
		<div><input type="radio" name="edit" value="suspend" id="edit_suspend" style="float:left;"/>&nbsp;&nbsp;挂起虚拟机</div>
		<div><input type="radio" name="edit" value="resume" id="edit_resume" style="float:left;"/>&nbsp;&nbsp;恢复虚拟机</div>-->
		<div><input type="radio" name="edit" value="stop" id="edit_stop" style="float:left;"/>&nbsp;&nbsp;停止虚拟机</div>
		<div><input type="radio" name="edit" value="start" id="edit_start" style="float:left;"/>&nbsp;&nbsp;启动虚拟机</div>
	</form>
</div>
<div id="addvm_form" title="创建虚拟机" style="display: none">
	<p class="validateTips">All form fields are required.</p>
	<form>
		<fieldset>
			<label for="name">name</label>
			<input type="text" name="name" id="create_name" value="None" class="text ui-widget-content ui-corner-all" />
			<label for="imageRef">镜像</label>
			<select name="imageRef" id="create_imageRef" class="text ui-widget-content ui-corner-all" >
			<option value="">请选择镜像</option>
			</select>
			<label for="flavorRef">虚机类型</label>
			<select name="flavorRef" id="create_flavorRef" class="text ui-widget-content ui-corner-all" >
			<option value="">请选择虚机类型</option>
			</select>
			<label for="max_count">max_count</label>
			<input type="text" name="max_count" id="create_max_count" value="" class="text ui-widget-content ui-corner-all" />
			<label for="min_count">min_count</label>
			<input type="text" name="min_count" id="create_min_count" value="" class="text ui-widget-content ui-corner-all" />
			<label for="security_groups">security_groups</label>
			<select multiple="multiple" name="security_groups" id="create_security_groups" class="text ui-widget-content ui-corner-all" />
			<option value="default">default</option>
		</fieldset>
	</form>
</div>

</body>
</html>