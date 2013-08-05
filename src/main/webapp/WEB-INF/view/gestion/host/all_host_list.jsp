<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
    <%@include file="../inc/meta.jspf"%>
    <title>Host - Host列表</title>
   	<script language="javascript">
   	var j = jQuery.noConflict(true);
   	//物理机详情
 	function detail(id){
 		$.ajax({
			url : "/g/hostdetail/"+id,
			dataType : 'text',
			success : function(data) {
				var jsonobj=eval('('+data+')');
				$("#vcpus_used").html("vcpus_used: "+jsonobj.hypervisor.vcpus_used);
				$("#hypervisor_type").html("hypervisor_type: "+jsonobj.hypervisor.hypervisor_type);
				$("#local_gb_used").html("local_gb_used: "+jsonobj.hypervisor.local_gb_used);
				$("#hypervisor_hostname").html("hypervisor_hostname: "+jsonobj.hypervisor.hypervisor_hostname);
				$("#memory_mb_used").html("memory_mb_used: "+jsonobj.hypervisor.memory_mb_used);
				$("#memory_mb").html("memory_mb: "+jsonobj.hypervisor.memory_mb);
				$("#current_workload").html("current_workload: "+jsonobj.hypervisor.current_workload);
				$("#vcpus").html("vcpus: "+jsonobj.hypervisor.vcpus);
				$("#running_vms").html("running_vms: "+jsonobj.hypervisor.running_vms);
				$("#free_disk_gb").html("free_disk_gb: "+jsonobj.hypervisor.free_disk_gb);
				$("#hypervisor_version").html("hypervisor_version: "+jsonobj.hypervisor.hypervisor_version);
				$("#disk_available_least").html("disk_available_least: "+jsonobj.hypervisor.disk_available_least);
				$("#local_gb").html("local_gb: "+jsonobj.hypervisor.local_gb);
				$("#free_ram_mb").html("free_ram_mb: "+jsonobj.hypervisor.free_ram_mb);
				$("#id").html("id: "+jsonobj.hypervisor.id);
				var cpuobj=eval('('+jsonobj.hypervisor.cpu_info+')');
				$("#cpu_info").html("cpu_info: ");
				$("#vendor").html("&nbsp;&nbsp;vendor: "+cpuobj.vendor);
				$("#model").html("&nbsp;&nbsp;model: "+cpuobj.model);
				$("#arch").html("&nbsp;&nbsp;arch: "+cpuobj.arch);
				$("#features").html("&nbsp;&nbsp;features: "+cpuobj.features);
				var topologyobj=eval(cpuobj.topology);
				$("#topology").html("topology: ");
				$("#cores").html("&nbsp;&nbsp;cores: "+topologyobj.cores);
				$("#threads").html("&nbsp;&nbsp;threads: "+topologyobj.threads);
				$("#sockets").html("&nbsp;&nbsp;sockets: "+topologyobj.sockets);
			},
			complete: function(msg) {
				$( "#host_detail" ).dialog("open");
			}
		});
		//物理机详情窗口
		$( "#host_detail").dialog({
			autoOpen: false,
			postion: "center",
			height:"550",
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
 	}
 	//虚拟机列表
 	function showvmlist(hostname){
   		window.location.href="/g/vmlist/"+hostname+"/0";
   	}
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span>host列表</p></div>

<div class="main-cont">
    <h3 class="title">host列表
    </h3>

    <div class="set-area">
        <p class="tips-desc">目前仅提供查看功能，其他功能请稍后。</p>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="40%">
                    <div class="th-gap">host名称</div>
                </th>
                <th width="10%">
                    <div class="th-gap">详情</div>
                </th>	
                <th>
                    <div class="th-gap">查看该host下虚拟机</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="hostItem" items="${hostMap}" varStatus="status">
					<tr>
						<td>${hostItem.key} </td>
						<td><button onclick="detail('${hostItem.value.id}')">详情</button></td>
						<td><button onclick="showvmlist('${hostItem.value.hypervisor_hostname}')" >查看虚拟机</button></td>
					</tr>
				</c:forEach>

            </tbody>
        </table>
    </div>
</div>
<div id="host_detail"  style="display:none">
	<p id="vcpus_used"></p>
	<p id="hypervisor_type"></p>
	<p id="local_gb_used"></p>
	<p id="hypervisor_hostname"></p>
	<p id="memory_mb_used"></p>
	<p id="memory_mb"></p>
	<p id="current_workload"></p>
	<p id="vcpus"></p>
	<p id="running_vms"></p>
	<p id="free_disk_gb"></p>
	<p id="hypervisor_version"></p>
	<p id="disk_available_least"></p>
	<p id="local_gb"></p>
	<p id="free_ram_mb"></p>
	<p id="id"></p>
	<p id="cpu_info"></p>
	<p id="vendor"></p>
	<p id="model"></p>
	<p id="arch"></p>
	<p id="features"></p>
	<p id="topology"></p>
	<p id="cores"></p>
	<p id="threads"></p>
	<p id="sockets"></p>
</div>
</body>
</html>