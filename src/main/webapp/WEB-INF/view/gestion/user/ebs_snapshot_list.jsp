<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<html>
<head>
   	<%@include file="../inc/meta.jspf"%>
    <title>用户 - EBS，快照列表</title>
   	<script>
   	var j = jQuery.noConflict(true);
   	function showhostlist(zoneid){
   		window.location.href="/g/hostlistbyzone/"+zoneid;
   	}
   	function detail(id,type){
   		if(type=="systemImage"){
	   		$( "#systemImage_dialog_"+id ).dialog({
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
			$( "#systemImage_dialog_"+id ).dialog("open");
		}else if(type="ebs"){
			$( "#ebs_dialog_"+id ).dialog({
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
			$( "#ebs_dialog_"+id ).dialog("open");
		}
   	}
   	function addebs(){
   		$("#addebs_dialog").dialog({
			autoOpen: false,
			postion: "center",
			modal: true,
			height:"300",
			width:"600",
			show: {
				effect: "blind",
				duration: 200
				
			},
			hide: {
				effect: "explode",
				duration: 1000
			},
			buttons:{
				"创建":function(){
					var ebsname=$("#ebsname").val();
					var ebssize=$("#ebssize").val();
					var ebstype=$("#volume").val();
					var ebsdesc=$("#ebsdesc").val();
					if(!isnull(ebsname,ebssize,ebstype,ebsdesc)){
						return false;
					}else{
						$.ajax({
							url:"/g/user/createebs/${tenantId}/${userId}",
							type:"POST",
							data:{name:ebsname,size:ebssize,volume_type:ebstype,desc:ebsdesc},
							dataType:"text",
							success:function(data){
								if(data=="success"){
									location.reload();
								}
							}
						});
					}
					$("#addebs_dialog").dialog("close");
				},
				"取消":function(){
					$("#addebs_dialog").dialog("close");
				}
			}
		});
		$.ajax({
			url:"/g/user/volume_type/${tenantId}/${userId}",
			dataType:"json",
			success:function(data){
				$("#volume").empty();
				$.each(data,function(index,val){
					$("#volume").append("<option value='"+val.id+"'>"+val.name+"</option>");
				});
				$("#addebs_dialog").dialog("open");
			}
		});
   	}
   	function isnull(ebsname,ebssize,ebstype,ebsdesc){
   		if(ebsname==null||ebsname==""){
   			alert("name can't be null");
   			return false;
   		}
   		if(ebssize==null||ebssize==""){
   			alert("size can't be null");
   			return false;
   		}
   		if(ebstype==null||ebstype==""){
   			alert("type can't be null");
   			return false;
   		}
   		if(ebsdesc==null||ebsdesc==""){
   			alert("desc can't be null");
   			return false;
   		}
   		return true;
   	}
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：用户管理<span>&gt;</span><a href="javascript:history.go(-1)">用户信息</a><span>&gt;</span>EBS，快照信息</p></div>

<div class="main-cont">
    <h3 class="title">EBS，快照信息
    </h3>
    <div class="set-area">
    	<div><span><img onclick ="addebs()" src="/img/add.jpg" alt="新增EBS" height="100%" width="20px" style="float:right;margin-right:100px;"/></span></div>
        <p class="tips-desc">ebs列表，ebs数量：<c:out value="${fn:length(ebslist)}"></c:out></p>
        
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="22%">
                    <div class="th-gap">display_name</div>
                </th>
               
                <th>
                    <div class="th-gap">详情</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="ebs" items="${ebslist}" varStatus="status">
					<tr>
						<td>${ebs.display_name} </td>
						<td><button onclick="detail('${ebs.display_name}','ebs')">详情</button></td>
					</tr>
					<div id="ebs_dialog_${ebs.display_name}" style="display:none;">
						<fieldset>
							<legend>EBS</legend>
							<p>status:${ebs.status}</p>
							<p>display_name:${ebs.display_name}</p>
							<p>availability_zone:${ebs.availability_zone}</p>
							<p>bootable:${ebs.bootable}</p>
							<p>created_at:${ebs.created_at}</p>
							<p>tenant_id:${ebs.os_vol_tenant_attr_tenant_id}</p>
							<p>display_description:${ebs.display_description}</p>
							<p>host:${ebs.os_vol_host_attr_host}</p>
							<p>volume_type:${ebs.volume_type}</p>
							<p>snapshot_id:${ebs.snapshot_id}</p>
							<p>source_volid:${ebs.source_volid}</p>
							<p>id:${ebs.id}</p>
							<p>size:${ebs.size}</p>
							<p>attachments:</p>
							<c:forEach var="atta" items="${ebs.attachments}" varStatus="status">
								<p>${atta}</p>
							</c:forEach>
							<fieldset>
								<legend>METADATA</legend>
								<p>storage_location:${ebs.metadata.storage_location}</p>
							</fieldset>
						</fieldset>
					</div>
				</c:forEach>
            </tbody>
        </table>
        <br/>
        
        
        <p class="tips-desc">系统盘快照列表，系统盘快照数量：<c:out value="${fn:length(imagelist)}"></c:out></p>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="20%">
                    <div class="th-gap">name</div>
                </th>
               	<th width="25%">
                    <div class="th-gap">id</div>
                </th>
                <th width="10%">
                    <div class="th-gap">status</div>
                </th>
                <th width="10%">
                    <div class="th-gap">progress</div>
                </th>
               <th>
                    <div class="th-gap">详情</div>
                </th>
            </tr>
            </thead>
            <tbody>
			
            	<c:forEach var="systemImage" items="${systemImagelist}" varStatus="status">
					<tr>
						<td>${systemImage.name} </td>
						<td>${systemImage.id} </td>
						<td>${systemImage.status} </td>
						<td>${systemImage.progress} </td>
						<td><button onclick="detail('${systemImage.id}','systemImage')">详情</button></td>
					</tr>
					<div id="systemImage_dialog_${systemImage.id}" title="systemImage(${systemImage.id})详情" style="display:none">
						<fieldset>
							<legend>SYSTEMIMAGE</legend>
							<p>STATUS:${systemImage.status}</p>
							<p>UPDATED:${systemImage.updated}</p>
							<p>NAME:${systemImage.name}</p>
							<p>CREATED:${systemImage.created}</p>
							<p>MINDISK:${systemImage.minDisk}</p>
							<p>PROGRESS:${systemImage.progress}</p>
							<p>MINRAM:${systemImage.minRam}</p>
							<p>SYSTEMIMAGE_LINKS</p>
							<c:forEach var="link" items="${systemImage.links}" varStatus="status">
								<p>&nbsp;&nbsp;&nbsp;&nbsp;href: ${link.href}</p>
								<p>&nbsp;&nbsp;&nbsp;&nbsp;rel: ${link.rel}</p>
							</c:forEach>
							<fieldset>
								<legend>SERVER</legend>
								<p>SERVER_ID:${systemImage.server.id}</p>
								<p>SERVER_LINK:</p>
								<c:forEach var="link" items="${systemImage.server.links}" varStatus="status">
									<p>&nbsp;&nbsp;&nbsp;&nbsp;href: ${link.href}</p>
									<p>&nbsp;&nbsp;&nbsp;&nbsp;rel: ${link.rel}</p>
								</c:forEach>
							</fieldset>
							<fieldset>
							<legend>METADATA</legend>
								<p>INSTANCE_UUID:${systemImage.metadata.instance_uuid}</p>
								<p>IMAGE_LOCATION:${systemImage.metadata.image_location}</p>
								<p>IMAGE_STATE:${systemImage.metadata.image_state}</p>
								<p>USER_ID:${systemImage.metadata.user_id}</p>
								<p>IMAGE_TYPE:${systemImage.metadata.image_type}</p>
								<p>RAMDISK_ID:${systemImage.metadata.ramdisk_id}</p>
								<p>KERNEL_ID:${systemImage.metadata.kernel_id}</p>
								<p>USER_ID:${systemImage.metadata.user_id}</p>
								<p>OS_VERSION:${systemImage.metadata.os_version}</p>
								<p>STORAGE_LOCATE:${systemImage.metadata.storage_locate}</p>
								<p>BASE_IMAGE_REF:${systemImage.metadata.base_image_ref}</p>
								<p>OWNER_ID:${systemImage.metadata.owner_id}</p>
							</fieldset>
						</fieldset>
					</div>
				</c:forEach>
					
            </tbody>
        </table>
        <br/>
        
        
        <p class="tips-desc">数据盘快照列表，数据盘快照数量：<c:out value="${fn:length(ebslist)}"></c:out></p>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="22%">
                    <div class="th-gap">display_name</div>
                </th>
               
                <th>
                    <div class="th-gap">详情</div>
                </th>
            </tr>
            </thead>
            <tbody>
			
            	<c:forEach var="ebs" items="${ebslist}" varStatus="status">
					<tr>
						<td>${ebs.display_name} </td>
						<td><button onclick="detail('${status.count}')">详情</button></td>
					</tr>
				</c:forEach>
					
            </tbody>
        </table>
        <br/>
   	 </div>
</div>
 <div id="addebs_dialog" title="添加EBS" style="display:none;">
			<form id="ebs_form" action="/g/user/createebs/">
				<fieldset>
					<legend>添加EBS</legend>
					<p>name:<input type="text" value="" id="ebsname" name="ebsname"/></p>
					<p>size:<input type="text" value="" id="ebssize" name="ebssize"/></p>
					<p>volume:<select id="volume" name="ebsvolumetype"></select></p>
					<p>desc:<textarea rows="3" cols="50" id="ebsdesc" name="ebsdesc"></textarea></p>
					<p><input type="button" value="确定" onclick="addEBS();" /></p>
				</fieldset>
			</form>
		</div>
</body>
</html>