<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
    <%@include file="../inc/meta.jspf"%>
    <title>VM - VM列表</title>
    <style type="text/css">
        #dialog_link{
            padding: .3em 1em .3em 20px;
            text-decoration: none;
            position: relative;
            margin: 5px;
            float: right;
        }
        #dialog_link span.ui-icon {
            margin: 0 5px 0 0;
            position: absolute;
            left: .2em;
            top: 50%;
            margin-top: -8px;
            zoom: 1;
        }
    </style>
   	<script language="javascript">
   	var j = jQuery.noConflict(true);

    var region=${Region};
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
    function igore_toright(){
        $("#igore_hosts").find("option:selected").appendTo($("#igore_hosts_sel"));
    }
    function igore_toleft(){
        $("#igore_hosts_sel").find("option:selected").appendTo($("#igore_hosts"));
    }
    function vm_toright(){
        $("#vms").find("option:selected").appendTo($("#vms_sel"));
    }
    function vm_toleft(){
        $("#vms_sel").find("option:selected").appendTo($("#vms"));
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
            url:"/g/vnc/"+instanceId+"/"+region,
            dataType:"json",
            success:function(data){
                window.open(data.url,instanceId,'height=600,width=800');
            }
        })
    }
    function coldmove(){
        $("#cold_move").dialog("open");
    }
    $(function(){
        $("#dialog_link").hover(function(){
            $(this).addClass("ui-state-hover");
        },function(){
            $(this).removeClass("ui-state-hover");
        });
        $("#cold_move").dialog({
            autoOpen: false,
            postion: "center",
            height:"500",
            width:"600",
            modal: true,
            buttons:{
                "确认":function(){
                    var igore_hosts_sel=$("#igore_hosts_sel").children();
                    var igore_hosts=$("#igore_hosts").children();
                    var vms=$("#vms_sel").children();
                    var target = $("#target_host").val();
                    var $vms="",$igores="";
                    if(target!="0"){
                        igore_hosts_sel==null;
                    }else{
                        if(igore_hosts.length==0||igore_hosts=="undefined"){
                            alert("请至少保留一台主机为弹性选择!");
                            return false;
                        }
                    }
                    if(vms==null||vms=="undefined"){
                        alert("请选择要迁移的虚机!");
                        return false;
                    }
                    $.each(vms,function(index,data){
                        $vms+=$(data).attr("value")+",";
                    })
                    $vms=$vms.substr(0,$vms.length-1);
                    $.each(igore_hosts_sel,function(index,data){
                        $igores+=$(data).attr("value")+",";
                    })
                    if($igores!=null&&$igores!="undefined"){
                        $igores=$igores.substr(0,$igores.length-1);
                    }else{
                        $igores="";
                    }
                    $.ajax({
                        url:'/g/coldmove',
                        dataType:"text",
                        data:{
                            vms:$vms,
                            igores:$igores,
                            target:target,
                            Region:region
                        },
                        type:'post',
                        success:function(data){
                            alert(data+"--请尝试刷新页面来确定是否迁移!");
                            location.reload();
                        }
                    })
                }
            }
        });
        $("#target_host").on("change",function(){
            if($(this).val()!="0"){
                $("#igore_hosts_div").hide();
            }else{
                $("#igore_hosts_div").show();
            }
        });
    })
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span><a href="/查询zoneg/zonelist">zone列表</a><span>&gt;</span><a href="javascript:history.go(-1)">host列表</a><span>&gt;</span>vm列表</p></div>
<div id="vncDialog" title="vnc" style="display:none">
</div>
<div id="cold_move" title="冷迁移" style="display: none">
    <div align="center">目标主机:<select id="target_host">
        <option value="0">弹性(自动)</option>
        <c:forEach var="host" items="${hosts}" varStatus="status">
            <c:if test="${host.hypervisor_hostname!=hostname}">
                <option value="${host.hypervisor_hostname}">${host.hypervisor_hostname}</option>
            </c:if>
        </c:forEach>
    </select></div>
    <div style="border: 1px solid black;margin: 5px;height: 43%;">
        <p>虚机列表<span style="color: orangered">(注:非ebs创建主机不显示在此)</span>:</p>
        <select id="vms" multiple="multiple" style="width: 43%;height: 88%;margin:0 5px;float: left;">
            <c:forEach var="vm" items="${validList}" varStatus="status">
                <option value="${vm.id}">${vm.name}</option>
            </c:forEach>
        </select>
        <div style="height: 50%;width: 9%;float: left;margin-top:40px;">
            <button id="vm_toright" style="float: left;" onclick="vm_toright()">>></button>
            <button id="vm_toleft" style="float: left;" onclick="vm_toleft()"><<</button>
        </div>
        <select id="vms_sel" multiple="multiple" style="width: 43%;height: 88%;float: left;">
        </select>
    </div>
    <div id="igore_hosts_div" style="border: 1px solid black;margin: 5px;height: 43%;">
        <p>忽略主机:</p>
        <select id="igore_hosts" multiple="multiple" style="width: 43%;height: 88%;float: left;margin:0 5px;">
        <c:forEach var="host" items="${hosts}" varStatus="status">
            <c:if test="${host.hypervisor_hostname!=hostname}">
                <option value="${host.hypervisor_hostname}">${host.hypervisor_hostname}</option>
            </c:if>
        </c:forEach>
        </select>
        <div style="height: 50%;width: 9%;float: left;margin-top:40px;">
            <button id="igore_toright" style="float: left;" onclick="igore_toright()">>></button>
            <button id="igore_toleft" style="float: left;" onclick="igore_toleft()"><<</button>
        </div>
        <select id="igore_hosts_sel" multiple="multiple" style="width: 43%;height: 88%;float: left;">
        </select>
    </div>
</div>
<div class="main-cont">
    <h3 class="title">vm列表
    </h3>
        <div class="set-area">
            vm列表，vm数量：<c:out value="${fn:length(vmlist)}"></c:out>
            <a href="#" id="dialog_link" onclick="coldmove();" class="ui-state-default ui-corner-all"><span class="ui-icon ui-icon-transfer-e-w"></span>冷迁移</a>
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
<script>
	$(function(){
		$("button").attr("class","ui-state-default ui-corner-all");
	});
</script>
</html>