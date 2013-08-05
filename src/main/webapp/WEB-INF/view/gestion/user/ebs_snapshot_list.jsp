<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
   	<%@include file="../inc/meta.jspf"%>
    <title>用户 - EBS，快照列表</title>
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
<div class="path"><p>当前位置：用户管理<span>&gt;</span><a href="javascript:history.go(-1)">用户信息</a><span>&gt;</span>EBS，快照信息</p></div>

<div class="main-cont">
    <h3 class="title">EBS，快照信息
    </h3>

    <div class="set-area">
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
            </tr>
            </thead>
            <tbody>
			
            	<c:forEach var="ebs" items="${ebslist}" varStatus="status">
					<tr>
						<td>${ebs.display_name} </td>
						<td><button onclick="detail('${status.count}')">详情</button></td>
					</tr>
				</c:forEach>
					
            </tbody>
        </table>
        <br/>
        
        
        <p class="tips-desc">image列表，image数量：<c:out value="${fn:length(imagelist)}"></c:out></p>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="22%">
                    <div class="th-gap">name</div>
                </th>
               
                <th>
                    <div class="th-gap">详情</div>
                </th>
            </tr>
            </thead>
            <tbody>
			
            	<c:forEach var="image" items="${imagelist}" varStatus="status">
					<tr>
						<td>${image.name} </td>
						
						<td><button onclick="detail('${status.count}')">详情</button></td>
					</tr>
				</c:forEach>
					
            </tbody>
        </table>
        <br/>
   	 </div>
</div>

</body>
</html>