<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<head>
   	<%@include file="../inc/meta.jspf"%>
    <title>用户 - 用户列表</title>
   	<script>
   	var j = jQuery.noConflict(true);
   	function ebs_snapshot_list(tenantId, userId){
   		window.location.href="/g/user/ebs_snapshot_list/"+tenantId+"/"+userId;
   	}
   	
   	function vm_list(tenantId, userId){
   		window.location.href="/g/user/vmlist/"+tenantId+"/"+userId;
   	}
   	
 </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：用户管理<span>&gt;</span>用户信息</p></div>

<div class="main-cont">
    <h3 class="title">用户信息
    </h3>

    <div class="set-area">
        <p class="tips-desc">目前仅提供查看功能，其他功能请稍后。</p>
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="22%">
                    <div class="th-gap">id</div>
                </th>
                <th width="10%">
                    <div class="th-gap">name</div>
                </th>
                <th width="8%">
                    <div class="th-gap">enabled</div>
                </th>
                <th>
                    <div class="th-gap">email</div>
                </th>
                <th width="25%">
                    <div class="th-gap">tenant_id</div>
                </th>
                <th width="8%">
                    <div class="th-gap">EBS,镜像</div>
                </th>
                <th width="8%">
                    <div class="th-gap">虚拟机</div>
                </th>
            </tr>
            </thead>
            <tbody>
            	<c:forEach var="dto" items="${page.data}" varStatus="status">
					<tr>
						<td>${dto.id} </td>
						<td>${dto.name} </td>
						<td>${dto.enabled}</td>
						<td>${dto.email}</td>
						<td>${dto.tenantId}</td>
						<td><button onclick="ebs_snapshot_list('${dto.tenantId}','${dto.id}')">查看</button></td>
						<td><button onclick="vm_list('${dto.tenantId}','${dto.id}')">查看</button></td>
					</tr>
				</c:forEach>
            </tbody>
        </table>
        <br/>
        <div style="float:right;">
        	<c:if test="${page.pageIndex-1>0}">
        		<span id="previus">
        			<a href="/g/user/list/${page.pageIndex-1}" style="font-size:4px;color:black;">上一页</a>
        		</span>
       		</c:if>
        	&nbsp;&nbsp;&nbsp;&nbsp;
        	<c:if test="${fn:length(page.data) == 5}">
        		<span id="next">
        			<a href="/g/user/list/${page.pageIndex+1}" style="font-size:4px;color:black;">下一页</a>
        		</span>
       		</c:if>
        </div>
    </div>
</div>

</body>
</html>