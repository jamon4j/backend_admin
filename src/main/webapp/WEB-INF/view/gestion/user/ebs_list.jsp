<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<html>
<head>
   	<%@include file="../inc/meta.jspf"%>
    <title>用户 - 用户列表</title>
    <style type="text/css">
		label, input { display:block; }
		input.text { margin-bottom:12px; width:80%; padding: .4em; }
		select {margin-bottom:12px; width:80%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		div#users-contain { width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
	</style>
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
                    <div class="th-gap">volumeId</div>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="ebs" items="${vmebslist}" varStatus="status">
            	<tr>
            		<td>${ebs.id }</td>
            		<td>${ebs.device }</td>
            		<td>${ebs.serverId }</td>
            		<td>${ebs.volumeId }</td>
            	</tr>
            </c:forEach>
            </tbody>
            </table>
</div>
</div>
</body>
</html>