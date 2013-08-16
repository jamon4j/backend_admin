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
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span><a href="/g/zonelist">zone列表</a><span>&gt;</span><a href="javascript:history.go(-1)">host列表</a><span>&gt;</span>vm列表</p></div>

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
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="vm" items="${vmlist}" varStatus="status">
					<tr>
						<td>${vm.id} </td>
						<td>${vm.status} </td>
						<td>${vm.name} </td>
						<td>${vm.OS_EXT_SRV_ATTR_host} </td>
                        <td>${vm.tenant_id} </td>
						<td><button onclick="detail('${status.count}')">详情</button></td>
						<div id="vm_dialog_${status.count}" title="vm_${status.count}详情" style="display:none">
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
							<p>public_ip_address: </p>
							<c:forEach var="public_address" items="${vm.addresses.publicAddress}" varStatus="status">
								<p>&nbsp;&nbsp;version: ${public_address.version}</p>
								<p>&nbsp;&nbsp;address: ${public_address.addr}</p>
							</c:forEach>
							<p>private_ip_address: </p>
							<c:forEach var="private_address" items="${vm.addresses.privateAddress}" varStatus="status">
								<p>&nbsp;&nbsp;version: ${private_address.version}</p>
								<p>&nbsp;&nbsp;address: ${private_address.addr}</p>
							</c:forEach>
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
							<p>&nbsp;&nbsp;faults: </p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;message: ${vm.fault.message}</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;code: ${vm.fault.code}</p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;created: ${vm.fault.created}</p>
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