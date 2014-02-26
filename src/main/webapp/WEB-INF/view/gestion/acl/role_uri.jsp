<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>

<head>
   	<%@include file="../inc/meta.jspf"%>
</head>
<body class="main-body">
<div class="path"><p>权限控制：权限对应管理<span>&gt;</span>权限对应列表</p></div>

<div class="main-cont">
    <h3 class="title">权限对应列表
    </h3>
    <div class="set-area">

        <table class="table" cellpadding="0" cellspacing="0" width="100%" border="0">
            <colgroup>
            </colgroup>
            <thead class="tb-tit-bg">
             <tr>
                <th width="20%">
                    <div class="th-gap">权限</div>
                </th>
                <th width="20%">
                    <div class="th-gap">uri</div>
                </th>
                <th width="20%">
                    <div class="th-gap">权限类型</div>
                </th>
            </tr>
            </thead>
            <tbody>
					<tr>
						<td>机器管理 </td>
						<td>/g/zonelist,/g/hostlistbyzone,/g/vmlist,/g/chart</td>
						<td>一级标签</td>
					</tr>
					<tr>
						<td>用户管理 </td>
						<td>/g/user</td>
						<td>一级标签</td>
					</tr>
					<tr>
						<td>KPI统计 </td>
						<td>kpi_pub_kvm,kpi_pub_op,kpi_pub_cash,kpi_user,kpi_ks3,kpi_pub_cash_rsc</td>
						<td>一级标签</td>
					</tr>
					<tr>
						<td>KPI公有云总体情况 </td>
						<td>kpi_pub_kvm</td>
						<td>二级标签</td>
					</tr>
					<tr>
						<td>KPI公有云操作情况 </td>
						<td>kpi_pub_op</td>
						<td>二级标签</td>
					</tr>
					<tr>
						<td>KPI公有云付费统计</td>
						<td>kpi_pub_cash</td>
						<td>二级标签</td>
					</tr>
					<tr>
						<td>KPI用户活跃数据 </td>
						<td>kpi_user</td>
						<td>二级标签</td>
					</tr>	
					<tr>
						<td>KPI:KS3付费统计</td>
						<td>kpi_ks3</td>
						<td>二级标签</td>
					</tr>
					<tr>
						<td>KPI公有云统计（关系客户） </td>
						<td>kpi_pub_cash_rsc</td>
						<td>二级标签</td>
					</tr>									
            </tbody>
        </table>
        <br/>
    </div>
</div>

</body>

</html>

