<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
    <%@include file="../inc/meta.jspf"%>
    <title>Host - Host列表</title>
   	<script language="javascript">
   	var j = jQuery.noConflict(true)
   	//物理机详情
 	function detail(id){
		//物理机详情窗口
		$( "#host_detail_"+id).dialog({
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
		$( "#host_detail_"+id).dialog("open");
 	}
    $(function(){
        $(document).on('click', '.useClick', useOnClick)
    })
    var useOnClick = function(e){
        var $target = $(e.target),
            $tr =  $target.closest('tr'),
            tmpCount = $tr.data('count'),
            id = $tr.data('id'),
            name = $tr.find('td:nth-child(2)').text()
            $tr.data('count',tmpCount+1)
        show(id,name, $tr.data('count'))
    }
    function show(id,name, count){
        if(count < 2)
        $.ajax({
            url:'/g/stathost',
            dataType:'json',
            data:{hostname:name},
            type:'post',
            success:function(data){
                $.each(data,function(index,val){
                    var $tt='<tr>' +
                            '<td>'+val.resource.project+'</td>' +
                            '<td>'+val.resource.email+'</td>' +
                            '<td>'+val.resource.memory_mb+'MB</td>' +
                            '<td>'+val.resource.cpu+'核</td></tr>';
                    $( "#contain-"+id).find("table").find("tbody").append($tt);
                });
            }
        });
        $( "#contain-"+id ).toggle('500');
    }
 	//虚拟机列表
 	function showvmlist(hostname){
   		window.location.href="/g/vmlist/"+hostname;
   	}
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span><a href="/g/zonelist">zone列表</a><span>&gt;</span>host列表</p></div>

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
            	<th width="15%">
                    <div class="th-gap">hostid</div>
                </th>
                <th width="25%">
                    <div class="th-gap">host名称</div>
                </th>
                <th width="10%">
                    <div class="th-gap">详情</div>
                </th>	
                <th>
                    <div class="th-gap">查看该host下虚拟机</div>
                </th>
                <th>
                    <div class="th-gap">查看每个用户使用量</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="hostdto" items="${hostdtolist}" varStatus="status">
					<tr data-id="${hostdto.id}" data-count="0">
						<td>${hostdto.id} </td>
						<td>${hostdto.hypervisor_hostname}</td>
						<td><button onclick="detail('${hostdto.id}')">详情</button></td>
						<td><button onclick="showvmlist('${hostdto.hypervisor_hostname}')" >查看虚拟机</button></td>
                        <td><button class="useClick" >查看使用量</button></td>
						<div id="host_detail_${hostdto.id}" title="host${hostdto.id}详情" style="display:none">
							<p>vcpus_used:${hostdto.vcpus_used}</p>
							<p>hypervisor_type:${hostdto.hypervisor_type}</p>
							<p>local_gb_used:${hostdto.local_gb_used}</p>
							<p>hypervisor_hostname:${hostdto.hypervisor_hostname}</p>
							<p>memory_mb_used:${hostdto.memory_mb_used}</p>
							<p>memory_mb:${hostdto.memory_mb}</p>
							<p>vcpus:${hostdto.vcpus}</p>
							<p>running_vms:${hostdto.running_vms}</p>
							<p>free_disk_gb:${hostdto.free_disk_gb}</p>
							<p>hypervisor_version:${hostdto.hypervisor_version}</p>
							<p>disk_available_least:${hostdto.disk_available_least}</p>
							<p>local_gb:${hostdto.local_gb}</p>
							<p>free_ram_mb:${hostdto.free_ram_mb}</p>
							<p>id:${hostdto.id}</p>
							<%-- <p>cpu_info:</p>
							<p>vendor:&nbsp;&nbsp;${host.cpu_info.vendor}</p>
							<p>model:&nbsp;&nbsp;${host.cpu_info.model}</p>
							<p>arch:&nbsp;&nbsp;${host.cpu_info.arch}</p>
							<p>features:</p>
							<p>topology:${host.cpu_info.topology}</p>
							<p>cores:${host.cpu_info.topology.cores}</p>
							<p>threads:${host.cpu_info.topology.threads}</p>
							<p>sockets:${host.cpu_info.topology.sockets}</p> --%>
						</div>
					</tr>
                    <tr id="contain-${hostdto.id}" style="display: none;">
                        <td colspan="5">
                            <div>
                                <table class="table">
                                    <thead class="tb-tit-bg">
                                    <th>用户名</th>
                                    <th>邮箱</th>
                                    <th>内存使用量</th>
                                    <th>cpu使用量(可能超过物理机)</th>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </td>
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