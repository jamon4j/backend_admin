<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
   	<%@include file="../inc/meta.jspf"%>
    <title>ZONE - Zone列表</title>
   	<script>
   	var j = jQuery.noConflict(true);
  	function detail(id){
  		$( "#zone_dialog_"+id ).dialog({
			autoOpen: false,
			postion: "center",
			height:"300",
			width:"600",
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
		$( "#zone_dialog_"+id ).dialog("open");
   	}
    function show(id){
        $( "#contain-"+id ).toggle('500')
    }
    function showIp(id){
        $( "#ip-"+id ).toggle('500')
    }
   	function showhostlist(zoneid){
   		window.location.href="/g/hostlistbyzone/"+zoneid;
   	}
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span>zone列表</p></div>

<div class="main-cont">
    <h3 class="title">zone列表
    </h3>

    <div class="set-area">
        <p class="tips-desc">目前仅提供查看功能，其他功能请稍后。</p>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="20%">
                    <div class="th-gap">zone_id</div>
                </th>
                <th width="20%">
                    <div class="th-gap">zone名称</div>
                </th>
                <th width="10%">
                    <div class="th-gap">是否可用</div>
                </th>
                <th width="10%">
                    <div class="th-gap">详情</div>
                </th>
                <th>
                    <div class="th-gap">查看该zone下物理机</div>
                </th>
                <th>
                    <div class="th-gap">容量概览</div>
                </th>
                <th>
                    <div class="th-gap">容量详情</div>
                </th>
                <th>
                    <div class="th-gap">查看IP</div>
                </th>
            </tr>
            </thead>
            <tbody>
			
            	<c:forEach var="dto" items="${zonelist}" varStatus="status">
                    <c:set value="0" var="running_vms" />
                    <c:set value="0" var="vcpus" />
                    <c:set value="0" var="vcpus_used" />
                    <c:set value="0" var="local_gb" />
                    <c:set value="0" var="local_gb_used" />
                    <c:set value="0" var="free_disk_gb" />
                    <c:set value="0" var="memory_mb" />
                    <c:set value="0" var="memory_mb_used" />
                    <c:set value="0" var="free_ram_mb" />

                    <c:forEach items="${dto.statZone.host_usage}" var="husage" varStatus="status">
                        <tr>
                            <c:set value="${running_vms + husage.running_vms}" var="running_vms" />
                            <c:set value="${vcpus + husage.vcpus}" var="vcpus" />
                            <c:set value="${vcpus_used + husage.vcpus_used}" var="vcpus_used" />
                            <c:set value="${local_gb + husage.local_gb}" var="local_gb" />
                            <c:set value="${local_gb_used + husage.local_gb_used}" var="local_gb_used" />
                            <c:set value="${free_disk_gb + husage.free_disk_gb}" var="free_disk_gb" />
                            <c:set value="${memory_mb + husage.memory_mb}" var="memory_mb" />
                            <c:set value="${memory_mb_used + husage.memory_mb_used}" var="memory_mb_used" />
                            <c:set value="${free_ram_mb + husage.free_ram_mb}" var="free_ram_mb" />
                    </c:forEach>
					<tr>
						<td>${dto.id} </td>
						<td>${dto.name} </td>
						<td><c:if test="${dto.deleted=='false'}">是</c:if><c:if test="${dto.deleted=='true'}">否</c:if> </td>
						<td><button onclick="detail(${dto.id})">详情</button></td>
						<td><button onclick="showhostlist(${dto.id})">查看物理机</button></td>
						<td>
						    <fmt:parseNumber var="vcpus_used_scale_int" integerOnly="true" type="number" value="${vcpus_used / vcpus * 100}" />
						    <fmt:parseNumber var="local_gb_used_scale_int" integerOnly="true" type="number" value="${local_gb_used / local_gb * 100}" />
						    <fmt:parseNumber var="memory_mb_used_scale_int" integerOnly="true" type="number" value="${memory_mb_used / memory_mb * 100}" />
                            <p>已使用CPU：<em>${vcpus_used_scale_int}</em>&nbsp;%</p>
                            <p>已使用磁盘：<em>${local_gb_used_scale_int}</em>&nbsp;%</p>
                            <p>已使用内存：<em>${memory_mb_used_scale_int}</em>&nbsp;%</p>
                            <p>已运行虚机总数：<em>${running_vms}</em>&nbsp;个</p>
                            <p>物理机总数：<em><c:out value="${fn:length(dto.statZone.host_usage)}"></c:out>&nbsp;个</p>
						</td>
                        <td><button onclick="show(${dto.id})">查看容量详情</button></td>
                        <td><button onclick="showIp(${dto.id})">IP查看</button> </td>
						<div id="zone_dialog_${dto.id}" title="zone${dto.id}详情" style="display:none">
							<p>id:${dto.id}</p>
							<p>name:${dto.name}</p>
							<p>isdeleted:${dto.deleted}</p>
							<p>availability_zone:${dto.availability_zone}</p>
							<p>created_at:${dto.created_at}</p>
							<p>updated_at:${dto.updated_at}</p>
							<p>deleted_at:${dto.deleted_at}</p>
						</div>
					</tr>
                    <tr id="contain-${dto.id}" style="display: none;">
                        <td colspan="8">
                        <div>
                           <table class="table">
                           <thead class="tb-tit-bg" style="text-align:center">
                              <th>hostname</th>
                              <th>hypervisor类型</th>
                              <th>运行虚机个数</th>
                              <th>总cpu数；累计</th>
                              <th>使用的cpu数</th>
                              <th>总磁盘空间</th>
                              <th>已使用磁盘大小</th>
                              <th>剩余磁盘大小</th>
                              <th>总内存数</th>
                              <th>内存使用</th>
                              <th>剩余内存</th>
                              <th>
                                  <div class="th-gap">容量概览</div>
                              </th>
                              <th width="12%">cpu信息</th>
                          </thead>
                               <tr class="tb-tit-bg" style="text-align:center">
                                   <td>总和：[<span><em style="color:red;"><c:out value="${fn:length(dto.statZone.host_usage)}"></c:out></em></span>&nbsp个]</td>
                                   <td></td>
                                   <td>总和：[<span id="running_vms"><em style="color:red;">${running_vms}</em></span>&nbsp个]</td>
                                   <td>总和：[<span id="vcpus"><em style="color:red;">${vcpus}</em><em></span>&nbsp核]</td>
                                   <td>总和：[<span id="vcpus_used"><em style="color:red;">${vcpus_used}</em></span>&nbsp个]</td>
                                   <td>总和：[<span id="local_gb"><em style="color:red;">${local_gb}</em></span>&nbspGB]</td>
                                   <td>总和：[<span id="local_gb_used"><em style="color:red;">${local_gb_used}</em></span>&nbspGB]</td>
                                   <td>总和：[<span id="free_disk_gb"><em style="color:red;">${free_disk_gb}</em></span>&nbspGB]</td>
                                   <td>总和：[<span id="memory_mb"><em style="color:red;">${memory_mb}</em></span>&nbspMB]</td>
                                   <td>总和：[<span id="memory_mb_used"><em style="color:red;">${memory_mb_used}</em></span>&nbspMB]</td>
                                   <td>总和：[<span id="free_ram_mb"><em style="color:red;">${free_ram_mb}</em></span>&nbspMB]</td>
                                   <td width="12%"></td>
                               </tr>
                               <tbody>
                                    <c:forEach items="${dto.statZone.host_usage}" var="husage" varStatus="status">
                                        <tr>
                                            <td>${husage.hostname}</td>
                                            <td>${husage.hypervisor_type}</td>
                                            <td>${husage.running_vms}个</td>
                                            <td>${husage.vcpus}核</td>
                                            <td>${husage.vcpus_used}核</td>
                                            <td>${husage.local_gb}GB</td>
                                            <td>${husage.local_gb_used}GB</td>
                                            <td>${husage.free_disk_gb}GB</td>
                                            <td>${husage.memory_mb}MB</td>
                                            <td>${husage.memory_mb_used}MB</td>
                                            <td>${husage.free_ram_mb}MB</td>
                                            <td>
                                                <fmt:parseNumber var="vcpus_used_scale_int" integerOnly="true" type="number" value="${husage.vcpus_used / husage.vcpus * 100}" />
                                                <fmt:parseNumber var="local_gb_used_scale_int" integerOnly="true" type="number" value="${husage.local_gb_used / husage.local_gb * 100}" />
                                                <fmt:parseNumber var="memory_mb_used_scale_int" integerOnly="true" type="number" value="${husage.memory_mb_used / husage.memory_mb * 100}" />
                                                <p>已使用CPU：<em>${vcpus_used_scale_int}</em>&nbsp;%</p>
                                                <p>已使用磁盘：<em>${local_gb_used_scale_int}</em>&nbsp;%</p>
                                                <p>已使用内存：<em>${memory_mb_used_scale_int}</em>&nbsp;%</p>
                                            </td>
                                            <td>
                                                <p>厂商:${husage.cpu_infos.vendor}</p>
                                                <p>model:${husage.cpu_infos.model}</p>
                                                <p>架构:${husage.cpu_infos.arch}</p>
                                                <p>特性:${husage.cpu_infos.features}</p>
                                                <p>拓扑-cpu:${husage.cpu_infos.topology.cores}</p>
                                                <p>拓扑-线程:${husage.cpu_infos.topology.threads}</p>
                                                <p>拓扑-sockets:${husage.cpu_infos.topology.sockets}</p>
                                            </td>
                                        </tr>
                                    </c:forEach>
                               </tbody>
                           </table>
                        </div>
                        </td>
                    </tr>
                    <tr id="ip-${dto.id}" style="display: none;">
                        <td colspan="8">
                        <div>
                        <table class="table">
                        <thead class="tb-tit-bg">
                        <th>网络</th>
                        <th>总数</th>
                        <th>可用</th>
                        <th>已用</th>
                        </thead>
                        <tbody>
                            <c:forEach items="${dto.ipStat.wan}" var="wan" varStatus="status">
                            <tr>
                                <td>${wan.name}</td>
                                <td>${wan.ipnum}</td>
                                <td>${wan.availability_ipnum}</td>
                                <td>${wan.used_ipnum}</td>
                            </tr>
                            </c:forEach>
                            <c:forEach items="${dto.ipStat.lan}" var="lan" varStatus="status">
                            <tr>
                                <td>${lan.name}</td>
                                <td>${lan.ipnum}</td>
                                <td>${lan.availability_ipnum}</td>
                                <td>${lan.used_ipnum}</td>
                            </tr>
                            </c:forEach>
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

</body>
<script>
	$(function(){
		$("button").attr("class","ui-state-default ui-corner-all");
	});
</script>
</html>