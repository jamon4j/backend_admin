<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<html>
<head>
    <%@include file="../inc/meta.jspf"%>
    <title>用户 - VM的EBS列表</title>
    <script type="text/javascript">
        function unsetebs(attach_id,serverId){
            $.ajax({
                url:"/g/user/unbind/${tenantid}/${userid}",
                type:"POST",
                data:{
                    attach_id:attach_id,
                    volumeid:serverId
                },
                dataType:"text",
                success:function(data){
                    alert(data);
                    document.location.reload();
                },
                error:function(XMLHttpRequest,textStatus,errorThrown){
                	alert("XMLHttpRequest.status:"+XMLHttpRequest.status);
					alert("XMLHttpRequest.readyState:"+XMLHttpRequest.readyState);
					alert("textStatus:"+textStatus);
                }
            });
        }
    </script>
</head>
<body class="main-body">
<div class="path"><p>当前位置：机器管理<span>&gt;</span><a href="/g/user/list/1">用户信息</a><span>&gt;</span><a href="javascript:history.go(-1)">vm列表</a><span>&gt;</span>vm的ebs列表</p></div>
<div class="main-cont">
    <h3 class="title">vm的ebs列表
    </h3>

    <div class="set-area">
        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
            <tr>
                <th width="22%">
                    <div class="th-gap">id</div>
                </th>
                <th width="10%">
                    <div class="th-gap">device</div>
                </th>
                <th width="22%">
                    <div class="th-gap">serverId</div>
                </th>
                <th width="22%">
                    <div class="th-gap">操作</div>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="ebs" items="${vmebslist}" varStatus="status">
                <tr>
                    <td>${ebs.id }</td>
                    <td>${ebs.device }</td>
                    <td>${ebs.serverId }</td>
                    <td><button onclick="unsetebs('${ebs.id}','${ebs.serverId}');">解绑</button></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>