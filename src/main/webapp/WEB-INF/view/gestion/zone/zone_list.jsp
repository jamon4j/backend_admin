<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <th width="40%">
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
            </tr>
            </thead>
            <tbody>
			
            	<c:forEach var="dto" items="${zonelist}" varStatus="status">
					<tr>
						<td>${dto.id} </td>
						<td>${dto.name} </td>
						<td><c:if test="${dto.deleted=='false'}">是</c:if><c:if test="${dto.deleted=='true'}">否</c:if> </td>
						<td><button onclick="detail(${status.count})">详情</button></td>
						<td><button onclick="showhostlist(${dto.id})">查看物理机</button></td>
						<div id="zone_dialog_${status.count}" title="zone${status.count}详情" style="display:none">
							<p>id:${dto.id}</p>
							<p>name:${dto.name}</p>
							<p>isdeleted:${dto.deleted}</p>
							<p>availability_zone:${dto.availability_zone}</p>
							<p>created_at:${dto.created_at}</p>
							<p>updated_at:${dto.updated_at}</p>
							<p>deleted_at:${dto.deleted_at}</p>
						</div>
					</tr>
				</c:forEach>

            </tbody>
        </table>
    </div>
</div>

</body>
</html>