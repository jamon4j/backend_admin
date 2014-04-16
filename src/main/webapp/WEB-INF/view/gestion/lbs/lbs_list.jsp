<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@include file="../inc/meta.jspf"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<script type="text/javascript" src="/js/custome/lbs_list.js"></script>
</head>

<body class="main-body">
	<div class="path">
		<p>
			当前位置：LBS管理<span>&gt;</span><a href="/g/lbs/user/list?t=1">LBS设置</a><span>&gt;</span>用户LBS设置
		</p>
	</div>
	<div class="main-cont">
		<h3 class="title">用户名：${pool_username} | userId：${user_id } |
			tenantId：${tenant_id }</h3>
		<%-- <table cellpadding="0" cellspacing="0" width="100%" border="0">
			<tr>
				<td>
					<form style="margin-left: 2%" action="/g/lbs/pool/${user_id}/${tenant_id}/${pool_username}">
						id：<input type="text" name="pool_id" id="pool_id" value="" /> <input
							type="submit" name="submit"
							class="ui-state-default ui-corner-all" value="查  找" />
					</form>
				</td>
			</tr>
		</table> --%>
		<div class="set-area">
			<h3>负载均衡</h3>
			<table cellpadding="0" cellspacing="0" width="90%" border="0">
				<tr>
					<td style="margin-left: 10%"><span><button
								onclick="window.location.reload();"
								class="ui-state-default ui-corner-all">刷新</button>
							<button class="ui-state-default ui-corner-all">创建 负载均衡</button></span></td>
				</tr>
			</table>
			<table class="table" cellpadding="0" cellspacing="0" width="100%"
				border="0">
				<colgroup>
				</colgroup>
				<thead class="tb-tit-bg">
					<tr>
						<th>
							<div class="th-gap">id</div>
						</th>
						<th>
							<div class="th-gap">name</div>
						</th>
						<th>
							<div class="th-gap">ip</div>
						</th>
						<th width="12%">
							<div class="th-gap">status</div>
						</th>
						<th width="5%">
							<div class="th-gap">详情</div>
						</th>
						<th width="10%">
							<div class="th-gap">操作</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="dto" items="${list}" varStatus="status">
						<tr>
							<td>${dto.id }</td>
							<td>${dto.name }</td>
							<td>${dto.ip_address }</td>
							<td>${dto.status }</td>
							<td><button id="button_details_pool"
									onclick="button_details_pool('${dto.id }')"
									class="ui-state-default ui-corner-all">查看</button></td>
							<td><button class="ui-state-default ui-corner-all">修改</button>|
								<button class="ui-state-default ui-corner-all">删除</button></td>
							<div id="dialog_detail_pool_${dto.id }" title="POOL_${dto.id }详情"
								style="display:none">
								<p>status:${dto.status }</p>
								<p>
									vips:{
									<c:forEach var="pool_vips" items="${dto.vips}"
										varStatus="status">
									 	${pool_vips},
									 </c:forEach>
									}
								</p>
								<p>name:${dto.name }</p>
								<p>admin_state_up:${dto.admin_state_up }</p>
								<p>subnet_id:${dto.subnet_id }</p>
								<p>provider:${dto.provider }</p>
								<p>egress:${dto.egress }</p>
								<p>create_time:${dto.create_time }</p>
								<p>tenant_id:${dto.tenant_id }</p>
								<p>status_description:${dto.status_description }</p>
								<p>ip_address:${dto.ip_address }</p>
								<p>id:${dto.id }</p>
								<p>description:${dto.description }</p>
							</div>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br />
			<hr />
			<h3>规则</h3>
			<table cellpadding="0" cellspacing="0" width="90%" border="0">
				<tr>
					<td style="margin-left: 10%"><span>
							<button class="ui-state-default ui-corner-all">创建 规则</button></span></td>
				</tr>
			</table>
			<table class="table" cellpadding="0" cellspacing="0" width="100%"
				border="0">
				<colgroup>
				</colgroup>
				<thead class="tb-tit-bg">
					<tr>
						<th>
							<div class="th-gap">id</div>
						</th>
						<th>
							<div class="th-gap">name</div>
						</th>
						<th>
							<div class="th-gap">ip</div>
						</th>
						<th width="12%">
							<div class="th-gap">status</div>
						</th>
						<th width="5%">
							<div class="th-gap">详情</div>
						</th>
						<th width="10%">
							<div class="th-gap">操作</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="vip" items="${vip_list}" varStatus="status">
						<tr>
							<td>${vip.id }</td>
							<td>${vip.name }</td>
							<td>${vip.address }</td>
							<td>${vip.status }</td>
							<td><button onclick="button_details_vip('${vip.id }')"
									class="ui-state-default ui-corner-all">查看</button></td>
							<td><button class="ui-state-default ui-corner-all">修改</button>|
								<button class="ui-state-default ui-corner-all">删除</button></td>
							<div id="dialog_detail_vip_${vip.id }" title="VIP_${vip.id }详情"
								style="display:none">
								<p>status:${vip.status }</p>
								<p>lb_method:${vip.lb_method }</p>
								<p>protocol:${vip.protocol }</p>
								<p>description:${vip.description }</p>
								<p>health_monitors:${vip.health_monitors }</p>
								<p>subnet_id:${vip.subnet_id }</p>
								<p>deleted_time:${vip.deleted_time }</p>
								<p>admin_state_up:${vip.admin_state_up }</p>
								<p>connection_limit:${vip.connection_limit }</p>
								<p>pool_id:${vip.pool_id }</p>
								<p>session_persistence:{
										"cookie_name":${vip.session_persistence.cookie_name},
										"type":${vip.session_persistence.type},
										"timeout":${vip.session_persistence.timeout}
									}</p>
								<p>protocol_port:${vip.protocol_port }</p>
								<p>create_time:${vip.create_time }</p>
								<p>members:{
										<c:forEach items="${vip.members }" var="v_members">
											${v_members },
										</c:forEach>
									}</p>
								<p>address:${vip.address }</p>
								<p>port_id:${vip.port_id }</p>
								<p>status_description:${vip.status_description }</p>
								<p>health_monitors_status:{
										<c:forEach items="${vip.health_monitors_status }" var="monitors_status">
											${monitors_status },
										</c:forEach>
									}</p>
								<p>id:${vip.id }</p>
								<p>tenant_id:${vip.tenant_id }</p>
								<p>name:${vip.name }</p>
							</div>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
