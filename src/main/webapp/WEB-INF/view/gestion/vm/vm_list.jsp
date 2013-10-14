<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
    <%@include file="../inc/meta.jspf"%>
    <title>VM - VM列表</title>
   	<script language="javascript">
   	var j = jQuery.noConflict(true);
  	function detail(id){
  		$( "#vm_dialog_"+id ).dialog({
			autoOpen: false,
			postion: "center",
			height:"700",
			width:"900",
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
		$( "#vm_dialog_"+id ).dialog("open");
   	}
    function chart_load(id){
        window.location.href="/g/chart/load/"+id;
    }
    function chart_disk(id){
        window.location.href="/g/chart/disk/"+id;
    }
    function chart_network(id){
        window.location.href="/g/chart/network/"+id;
    }
    function chart_status(id){
        window.location.href="/g/chart/status/"+id;
    }
    function vnc(instanceId){
        $("#vncDialog").dialog({
            autoOpen: false,
            postion: "center",
            height:"450",
            width:"550",
            modal: true
        });
        $.ajax({
            url:"/g/vnc/"+instanceId,
            dataType:"json",
            success:function(data){
                window.open(data.url,instanceId,'height=600,width=800');
            }
        })
    }
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span><a href="/g/zonelist">zone列表</a><span>&gt;</span><a href="javascript:history.go(-1)">host列表</a><span>&gt;</span>vm列表</p></div>
<div id="vncDialog" title="vnc" style="display:none">
</div>
<div class="main-cont">
    <h3 class="title">vm列表
    </h3>
        <div class="set-area">
        	<p class="tips-desc">目前仅提供查看功能，其他功能请稍后。</p>
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
                <th width="25%">
                    <div class="th-gap">tenant_id</div>
                </th>
                 <th width="10%">
                    <div class="th-gap">详情</div>
                </th>
                <th width="12%">
                    <div class="th-gap">状态查看</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="vm" items="${vmlist}" varStatus="status">
					<tr>
						<td>${vm.id} </td>
						<td>${vm.status} </td>
						<td>${vm.name} </td>
						<td>${vm.host} </td>
                        <td>${vm.tenant_id} </td>
						<td><button onclick="detail('${status.count}')">详情</button>
                            <button onclick="vnc('${vm.id}')">登陆机器</button></td>
						<td><button onclick="chart_load('${vm.id}')">查看cpu内存</button>
                            <button onclick="chart_network('${vm.id}')">查看网络</button>
                            <button onclick="chart_disk('${vm.id}')">查看硬盘</button>
                            <button onclick="chart_status('${vm.id}')">查看状态</button></td>
						<div id="vm_dialog_${status.count}" title="vm_${status.count}详情" style="display:none">
							<p>status: ${vm.status}</p>
							<p>updated: ${vm.updated}</p>
							<p>host: ${vm.host}</p>
							<p>task_state: ${vm.task_state}</p>
							<p>vm_state: ${vm.vm_status}</p>
							<p>instance_name: ${vm.instance_name}</p>
							<p>hypervisor_hostname: ${vm.hypervisor_hostname}</p>
							<p>id: ${vm.id}</p>
                            <p>image_id:${vm.image_id}</p>
							<p>user_id: ${vm.user_id}</p>
							<p>name: ${vm.name}</p>
							<p>created: ${vm.created}</p>
							<p>tenant_id: ${vm.tenant_id}</p>
							<p>public_ip_address: </p>
							<c:forEach var="public_address" items="${vm.addresses.pubaddress}" varStatus="status">
								<p>&nbsp;&nbsp;version: ${public_address.version}</p>
								<p>&nbsp;&nbsp;address: ${public_address.addr}</p>
							</c:forEach>
							<p>private_ip_address: </p>
							<c:forEach var="private_address" items="${vm.addresses.priaddress}" varStatus="status">
								<p>&nbsp;&nbsp;version: ${private_address.version}</p>
								<p>&nbsp;&nbsp;address: ${private_address.addr}</p>
							</c:forEach>
							<p>progress: ${vm.progress}</p>
							<p>OS-EXT-STS:power_state: ${vm.power_state}</p>
							<p>security_groups: </p>
							<c:forEach var="security_group" items="${vm.security_groups}" varStatus="status">
								<p>&nbsp;&nbsp;name: ${security_group.name}</p>
							</c:forEach>
                            <p>&nbsp;&nbsp;instance_type:</p>
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;ram:${vm.instance_type.ram}</p>
                            <p>&nbsp;&nbsp;&nbsp;&nbsp;cpu:${vm.instance_type.cpu}</p>
							<p>&nbsp;&nbsp;metadata: </p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;storage_location: ${vm.metadata.storage_location}</p>
						</div>
					</tr>
				</c:forEach>
            </tbody>
        </table>
		</div>
</div>
</body>
</html>