<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
   	<%@include file="../inc/meta.jspf"%>
    <title>安全组 - 安全组列表</title>
   	<script>
   	var j = jQuery.noConflict(true);
  	function detail(id){
  		$( "#sg_dialog_"+id ).dialog({
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
		$( "#sg_dialog_"+id ).dialog("open");
   	}
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：安全组管理<span>&gt;</span>安全组列表</p></div>

<div class="main-cont">
    <h3 class="title">安全组列表
    </h3>

    <div class="set-area">
        <p class="tips-desc">目前仅提供查看功能，其他功能请稍后。</p>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="20%">
                    <div class="th-gap">id</div>
                </th>
                <th width="20%">
                    <div class="th-gap">name</div>
                </th>
                <th width="40%">
                    <div class="th-gap">tenantid</div>
                </th>
                <th width="20%">
                    <div class="th-gap">详情</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="dto" items="${sglist}" varStatus="status">
					<tr>
						<td>${dto.id} </td>
						<td>${dto.name} </td>
						<td>${dto.tenantId} </td>
						<td><button onclick="detail(${dto.id})">详情</button></td>
						<div id="sg_dialog_${dto.id}" title="安全组${dto.id}详情" style="display:none">
							<p>id:${dto.id}</p>
							<p>name:${dto.name}</p>
							<p>description:${dto.description}</p>
							<p>tenant_id:${dto.tenantId}</p>
							<c:forEach var="rule" items="${dto.rules}" varStatus="status">
								<p>rule:${rule.id}</p>
								<p>&nbsp;&nbsp;from_port:${rule.from_port}</p>
								<p>&nbsp;&nbsp;ip_protocol:${rule.ip_protocol}</p>
								<p>&nbsp;&nbsp;to_port:${rule.to_port}</p>
								<p>&nbsp;&nbsp;parent_group_id:${rule.parent_group_id}</p>
								<p>&nbsp;&nbsp;id:${rule.from_port}</p>
							</c:forEach>
						</div>
					</tr>
				</c:forEach>

            </tbody>
        </table>
    </div>
</div>

</body>
</html>