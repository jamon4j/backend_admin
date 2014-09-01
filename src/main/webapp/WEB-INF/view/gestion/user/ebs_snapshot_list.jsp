<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<html>
<head>
   	<%@include file="../inc/meta.jspf"%>
    <title>用户 - EBS，快照列表</title>
    <style type="text/css">
        ul#icons {margin: 0; padding: 0;}
        ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
        ul#icons span.ui-icon {float: left; margin: 0 4px;}
    </style>
   	<script>
    var region="${region}";
   	var j = jQuery.noConflict(true);
    $(function(){
        $("li").hover(function(){
            $(this).addClass("ui-state-hover");
        },function(){
            $(this).removeClass("ui-state-hover");
        })
    });
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
							url:"/g/user/createebs/${tenantId}/${userId}"+"/"+region,
							type:"POST",
							data:{name:ebsname,size:ebssize,desc:ebsdesc},
							dataType:"text",
							success:function(data){
								if(data=="true"){
									location.reload();
								}else{
                                    alert("创建失败");
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
           url:'/g/user/createebsimage/${tenantId}/${userId}'+"/"+region,
           data:{
               ebsid:ebsid,
               name:name,
               desc:description
           },
           dataType:'text',
           success:function(data){
               if(data=="false"){
                    alert("创建失败");
               }else{
                   alert("创建成功");
                   document.location.reload();
               }
           }
        });
    }
    function deleteebs(ebsid,status){
        if(status=="in-use"||status=="creating"){
            alert("删除ebs的状态必须为available!");
            return false;
        }
        $.ajax({
            url:'/g/user/deleteebs/${tenantId}/${userId}'+"/"+region,
            type:'POST',
            data:{
                ebsid:ebsid
            },
            dataType:'text',
            success:function(data){
                if(data=="true"){
                    alert("删除成功");
                    document.location.reload();
                }else{
                    alert("删除失败");
                }
            }
        });
    }
    function deletesnapshot(ebsid,status){
        if(status=="in-use"){
            alert("使用中不能删除ebs快照");
            return false;
        }
        $.ajax({
            url:'/g/user/deleteebssnapshot/${tenantId}/${userId}'+"/"+region,
            type:'POST',
            data:{
                ebssnapshotid:ebsid
            },
            dataType:'text',
            success:function(data){
                if(data=="true"){
                    alert("删除成功");
                    document.location.reload();
                }else{
                    alert("删除失败");
                }
            }
        });
    }
    function delSysImg(snapshotId){
        $.ajax({
            url:'/g/user/deletesnapshot/${tenantId}/${userId}'+"/"+region,
            type:'POST',
            data:{
                snapshotid:snapshotId
            },
            dataType:'text',
            success:function(data){
                if(data=="true"){
                    alert("删除成功");
                    document.location.reload();
                }else{
                    alert("删除失败");
                }
            }
        });
    }
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：用户管理<span>&gt;</span><a href="javascript:history.go(-1)">用户信息</a><span>&gt;</span>EBS，快照信息</p></div>

<div class="main-cont">
    <h3 class="title">EBS，快照信息<!--(${regionname})-->
    </h3>
    <div class="set-area">ebs列表，ebs数量：<c:out value="${fn:length(ebslist)}"></c:out>
        <ul id="icons" class="ui-widget ui-helper-clearfix" style="float: right;">
            <li class="ui-state-default ui-corner-all" onclick="addebs();">
                <span class="ui-icon ui-icon-circle-plus"></span>
            </li>
        </ul>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="22%">
                    <div class="th-gap">display_name</div>
                </th>
                <th>
                    <div class="th-gap">status</div>
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
                        <td>${ebs.status}</td>
						<td><button onclick="detail('${ebs.name}','ebs')">详情</button></td>
                        <td><button onclick="createsnapshot('${ebs.id}','${ebs.name}','${ebs.description}','${ebs.status}')">创建快照</button>
                            <button onclick="deleteebs('${ebs.id}','${ebs.status}')">删除ebs</button></td>
					</tr>
					<div id="ebs_dialog_${ebs.name}" style="display:none;">
						<fieldset>
							<legend>EBS</legend>
							<p>status:${ebs.status}</p>
							<p>name:${ebs.name}</p>
							<p>description:${ebs.description}</p>
							<p>created_at:${ebs.created_at}</p>
							<p>snapshot_id:${ebs.snapshot_id}</p>
							<p>id:${ebs.id}</p>
							<p>size:${ebs.size}</p>
                            <fieldset>
							<legend>attachments:</legend>
                                <c:forEach var="atta" items="${ebs.attachments}" varStatus="status">
                                    <p>DEVICE:${atta.device}</p>
                                    <p>SERVER_ID:${atta.server_id}</p>
                                    <p>ID:${atta.id}</p>
                                    <p>VOLUME_ID:${atta.volume_id}</p>
                                </c:forEach>
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
                <th>
                    <div class="th-gap">操作</div>
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
                        <td><button onclick="delSysImg('${systemImage.id}')">删除快照</button></td>
					</tr>
					<div id="systemImage_dialog_${systemImage.id}" title="systemImage(${systemImage.id})详情" style="display:none">
						<fieldset>
							<legend>SYSTEMIMAGE</legend>
							<p>STATUS:${systemImage.status}</p>
							<p>NAME:${systemImage.name}</p>
							<p>CREATED:${systemImage.created}</p>
							<p>UPDATED:${systemImage.updated}</p>
							<p>MINDISK:${systemImage.minDisk}</p>
							<p>PROGRESS:${systemImage.progress}</p>
							<p>MINRAM:${systemImage.minRam}</p>
							<p>ID:${systemImage.id}</p>
							<p>VM_ID:${systemImage.vm_id}</p>
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
							<p>description:${snapshot.description}</p>
							<p>id:${snapshot.id}</p>
							<p>display_name:${snapshot.name}</p>
							<p>size:${snapshot.size}</p>
							<p>ebs_id:${snapshot.ebs_id}</p>
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
                    <label for="ebsname">name:</label>
					<input type="text" value="" id="ebsname" name="ebsname"/>
                    <label for="ebssize">size:</label>
					<input type="text" value="" id="ebssize" name="ebssize"/>
                    <br/>
                    <br/>
                    <label for="ebsdesc">desc:</label>
					<textarea rows="3" cols="50" id="ebsdesc" name="ebsdesc"></textarea>
				</fieldset>
			</form>
		</div>
</body>
<script>
	$(function(){
		$("button").attr("class","ui-state-default ui-corner-all");
	});
</script>
</html>