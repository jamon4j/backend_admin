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
		}else if(type=="ebs"){
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
		}else if(type=="snapshot"){
            $( "#snapshots_detail_"+id ).dialog({
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
            $( "#snapshots_detail_"+id ).dialog("open");
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
					var ebsdesc=$("#ebsdesc").val();
					if(!isnull(ebsname,ebssize,ebsdesc)){
						return false;
					}else{
						$.ajax({
							url:"/g/user/createebs/${tenantId}/${userId}",
							type:"POST",
							data:{name:ebsname,size:ebssize,desc:ebsdesc},
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
		$("#addebs_dialog").dialog("open");
   	}
   	function isnull(ebsname,ebssize,ebsdesc){
   		if(ebsname==null||ebsname==""){
   			alert("name can't be null");
   			return false;
   		}
   		if(ebssize==null||ebssize==""){
   			alert("size can't be null");
   			return false;
   		}
   		if(ebsdesc==null||ebsdesc==""){
   			alert("desc can't be null");
   			return false;
   		}
   		return true;
   	}
    function createsnapshot(ebsid,name,description,status){
        if(status=="in-use"){
            alert("使用中不能创建快照");
            return false;
        }
        $.ajax({
           url:'/g/user/createebsimage/${tenantId}/${userId}',
           data:{
               ebsid:ebsid,
               name:name,
               desc:description
           },
           dataType:'text',
           success:function(data){
               document.location.reload();
           }
        });
    }
    function deleteebs(ebsid,status){
        if(status=="in-use"){
            alert("使用中不能删除ebs");
            return false;
        }
        $.ajax({
            url:'/g/user/deleteebs/${tenantId}/${userId}',
            type:'POST',
            data:{
                ebsid:ebsid
            },
            dataType:'text',
            success:function(data){
                document.location.reload();
            }
        });
    }
    function deletesnapshot(ebsid,status){
        if(status=="in-use"){
            alert("使用中不能删除ebs快照");
            return false;
        }
        $.ajax({
            url:'/g/user/deleteebssnapshot/${tenantId}/${userId}',
            type:'POST',
            data:{
                ebssnapshotid:ebsid
            },
            dataType:'text',
            success:function(data){
                document.location.reload();
            }
        });
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
                <th>
                    <div class="th-gap">操作</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="ebs" items="${ebslist}" varStatus="status">
					<tr>
						<td>${ebs.name} </td>
						<td><button onclick="detail('${ebs.display_name}','ebs')">详情</button></td>
                        <td><button onclick="createsnapshot('${ebs.id}','${ebs.name}','${ebs.description}','${ebs.status}')">创建快照</button>
                            <button onclick="deleteebs('${ebs.id}','${ebs.status}')">删除ebs</button></td>
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
								<p>DEVICE:${atta.device}</p>
								<p>SERVER_ID:${atta.server_id}</p>
								<p>ID:${atta.id}</p>
								<p>VOLUME_ID:${atta.volume_id}</p>
							</c:forEach>
							<fieldset>
								<legend>METADATA</legend>
								<p>CONTENTS:${ebs.metadata.contents}</p>
							</fieldset>
						</fieldset>
					</div>
				</c:forEach>
            </tbody>
        </table>
        <br/>
        
        
        <p class="tips-desc">系统盘快照列表，系统盘快照数量：<c:out value="${fn:length(systemImagelist)}"></c:out></p>
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
                    <div class="th-gap">SIZE</div>
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
						<td><button onclick="detail('${systemImage.id}','systemImage')">详情</button></td>
					</tr>
					<div id="systemImage_dialog_${systemImage.id}" title="systemImage(${systemImage.id})详情" style="display:none">
						<fieldset>
							<legend>SYSTEMIMAGE</legend>
							<p>STATUS:${systemImage.status}</p>
							<p>NAME:${systemImage.name}</p>
							<p>DELETED:${systemImage.deleted}</p>
							<p>MINDISK:${systemImage.minDisk}</p>
							<p>PROGRESS:${systemImage.progress}</p>
							<p>MINRAM:${systemImage.minRam}</p>
							<p>ID:${systemImage.id}</p>
							
                                <%--<fieldset>
                                 <legend>PROPERTIES:</legend>
                                    <p>OS_VERSION:${systemImage.properties.os_version}</p>
                                    <p>STORAGE_LOCATE:${systemImage.properties.storage_locate}</p>
                                </fieldset> --%>
						</fieldset>
					</div>
				</c:forEach>
					
            </tbody>
        </table>
        <br/>
        
        
        <p class="tips-desc">数据盘快照列表，数据盘快照数量：<c:out value="${fn:length(snapshotlist)}"></c:out></p>
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
                <th>
                    <div class="th-gap">操作</div>
                </th>
            </tr>
            </thead>
            <tbody>
			
            	<c:forEach var="snapshot" items="${snapshotlist}" varStatus="status">
					<tr>
						<td>${snapshot.name} </td>
						<td><button onclick="detail('${snapshot.id}','snapshot')">详情</button></td>
                        <td><button onclick="deletesnapshot('${snapshot.id}','${snapshot.status}')">删除快照</button></td>
						<div id="snapshots_detail_${snapshot.id}" title="snapshot(${snapshot.id})详情" style="display:none">
						<fieldset>
							<legend>snapshot</legend>
							<p>STATUS:${snapshot.status}</p>
							<p>PROGRESS:${snapshot.progress}</p>
							<p>created_at:${snapshot.created_at}</p>
							<p>display_description:${snapshot.display_description}</p>
							<p>id:${snapshot.id}</p>
							<p>volume_id:${snapshot.volume_id}</p>
							<p>display_name:${snapshot.display_name}</p>
							<p>size:${snapshot.size}</p>
							<p>project_id:${snapshot.project_id}</p>
						</fieldset>
					</div>
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
					<p>desc:<textarea rows="3" cols="50" id="ebsdesc" name="ebsdesc"></textarea></p>
					<p><input type="button" value="确定" onclick="addEBS();" /></p>
				</fieldset>
			</form>
		</div>
</body>
</html>