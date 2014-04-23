<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@include file="../inc/meta.jspf"%>
<%@include file="../inc/jsCssIncludeHeader.jspf"%>
<link rel="stylesheet"
	href="/js/jquery/plugins/tablesorter/css/theme.bootstrap.css" />
<script type="text/javascript"
	src="/js/jquery/plugins/tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="/js/jquery/plugins/tablesorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="/js/custome/lbs_list.js"></script>
</head>

<body class="main-body">
	<div class="path">
		<p>
			当前位置：LBS管理<span>&gt;</span><a href="/g/lbs/user/list?t=1">LBS设置</a><span>&gt;</span><a
				href="/g/lbs/list/${user_id }/${tenant_id }/${pool_username}">用户LBS设置</a><span>&gt;</span><a
				href="/g/lbs/details/${user_id}/${tenant_id}/${ pool_username}/${pool_id }">pool:${pool_id }</a><span>&gt;</span>vip:${vip_id }
		</p>
	</div>
	<div class="main-cont">
		<h3 class="title">
			用户名：${pool_username} | userId：${user_id } | tenantId：${tenant_id }
			<button onclick="window.location.reload();"
				class="ui-state-default ui-corner-all">刷新</button>
		</h3>
		<%-- <form style="margin-left: 1.3%;display:inline-block;"
			action="/g/lbs/pool/${user_id}/${tenant_id}/${pool_username}">
			ID：<input type="text" name="pool_id" id="pool_id" value="" /> 类型：<select>
				<option>负载均衡</option>
				<option>规则</option>
				<option>负载主机</option>
				<option>健康检查</option>
			</select> <input type="submit" name="submit"
				class="ui-state-default ui-corner-all" value="查  找" />
		</form> --%>
		<div class="set-area">
			<div style="display: none;">
				<h3>负载均衡</h3>
				<table class="table" cellpadding="0" cellspacing="0" width="100%"
					id="table_pool" border="0">
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
							<th width="11%">
								<div class="th-gap">ip</div>
							</th>
							<th width="12%">
								<div class="th-gap">status</div>
							</th>
							<th width="12%">
								<div class="th-gap">admin_state_up</div>
							</th>
							<th width="12%">
								<div class="th-gap">create_time</div>
							</th>
							<th width="5%">
								<div class="th-gap">详情</div>
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
								<td>${dto.admin_state_up }</td>
								<td>${dto.create_time }</td>
								<td><button id="button_details_pool"
										onclick="button_details_pool('${dto.id }')"
										class="ui-state-default ui-corner-all">查看</button></td>
								<div id="dialog_delete_pool_${dto.id }"
									title="POOL_${dto.id }删除" style="display:none">
									<p>
										确定删除负载均衡:<font color="red">${dto.id }</font>?
									</p>
									<p>
										当前负载均衡关联的规则:<br />
										<c:forEach var="delete_pool_vip" items="${dto.vips}">
										${delete_pool_vip }<br />
										</c:forEach>
									</p>
									<hr />
									<p>
										<font color="blue">负载均衡删除后，与其关联的规则，负载主机都将删除，请小心操作</font>
									</p>
								</div>
								<div id="dialog_detail_pool_${dto.id }"
									title="POOL_${dto.id }详情" style="display:none">
									<p>status:${dto.status }</p>
									<p>
										vips:{
										<c:forEach var="pool_vips" items="${dto.vips}"
											varStatus="status">
											<a>${pool_vips}</a>,
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
			</div>
			<h3>规则</h3>
			<table class="table" cellpadding="0" cellspacing="0" width="100%"
				id="table_vip" border="0">
				<thead class="tb-tit-bg">
					<tr>
						<th>
							<div class="th-gap">id</div>
						</th>
						<th width="12%">
							<div class="th-gap">name</div>
						</th>
						<th width="10%">
							<div class="th-gap">ip</div>
						</th>
						<th width="8%">
							<div class="th-gap">status</div>
						</th>
						<th width="10%">
							<div class="th-gap">admin_state_up</div>
						</th>
						<th width="12%">
							<div class="th-gap">create_time</div>
						</th>
						<th width="7%">
							<div class="th-gap">详情</div>
						</th>
						<th width="10%">
							<div class="th-gap">健康检查</div>
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
							<td>${vip.admin_state_up }</td>
							<td>${vip.create_time }</td>
							<td><button onclick="button_details_vip('${vip.id }')"
									class="ui-state-default ui-corner-all">查看</button></td>
							<td><button
									onclick="dialog_vip_bind_health('${user_id }','${tenant_id }','${vip.id }')"
									class="ui-state-default ui-corner-all">绑定</button> |
								<button
									onclick="button_vip_unbind_health('${user_id }','${tenant_id }','${vip.id }')"
									class="ui-state-default ui-corner-all">解除</button></td>
							<td><button
									onclick="button_update_vip('${user_id }','${tenant_id }','${vip.id }','${vip.name }','${vip.admin_state_up }','${vip.connection_limit }','${vip.session_persistence.cookie_name}','${vip.session_persistence.type}','${vip.session_persistence.timeout}','${vip.protocol }')"
									class="ui-state-default ui-corner-all">修改</button></td>
							<div id="dialog_vip_delete_${vip.id }" title="VIP_${vip.id }删除"
								style="display:none">
								<p>
									确定删除规则:<font color="red">${vip.id }</font>?
								</p>
								<p>
									当前规则关联的健康检查:<br />
									<c:forEach var="delete_vip_health_monitors_status"
										items="${vip.health_monitors_status }">
										${delete_vip_health_monitors_status.monitor_id }<br />
									</c:forEach>
								</p>
								<p>
									当前规则关联的负载主机:<br />
									<c:forEach items="${vip.members }" var="delete_vip_members">
											${delete_vip_members }<br />
									</c:forEach>
								</p>
								<p>
									当前规则关联的负载均衡:<br /> ${vip.pool_id }<br />
								</p>
								<hr />
								<p>
									<font color="blue">规则删除后，健康检查也不再与该规则绑定，该规则下的负载主机将被删除，请小心操作</font>
								</p>
							</div>
							<div id="dialog_vip_unbind_health_${vip.id }"
								title="VIP_${vip.id }解除健康检查" style="display:none">
								<font color="red">请选择要解除的健康检查</font>
								<p>
									健康检查ID: <select id="unbind_health_monitor_id_${vip.id }">
										<c:forEach var="vip_health_monitors_status"
											items="${vip.health_monitors_status }">
											<option value="${vip_health_monitors_status.monitor_id }">${vip_health_monitors_status.monitor_id }</option>
										</c:forEach>
									</select>
								</p>
							</div>
							<div id="dialog_detail_vip_${vip.id }" title="VIP_${vip.id }详情"
								style="display:none">
								<p>status:${vip.status }</p>
								<p>lb_method:${vip.lb_method }</p>
								<p>protocol:${vip.protocol }</p>
								<p>description:${vip.description }</p>
								<p>subnet_id:${vip.subnet_id }</p>
								<p>deleted_time:${vip.deleted_time }</p>
								<p>admin_state_up:${vip.admin_state_up }</p>
								<p>connection_limit:${vip.connection_limit }</p>
								<p>pool_id:${vip.pool_id }</p>
								<p>session_persistence:{
									"cookie_name":${vip.session_persistence.cookie_name},
									"type":${vip.session_persistence.type},
									"timeout":${vip.session_persistence.timeout} }</p>
								<p>protocol_port:${vip.protocol_port }</p>
								<p>create_time:${vip.create_time }</p>
								<p>
									members:{
									<c:forEach items="${vip.members }" var="v_members">
											${v_members },
										</c:forEach>
									}
								</p>
								<p>address:${vip.address }</p>
								<p>port_id:${vip.port_id }</p>
								<p>status_description:${vip.status_description }</p>
								<p>
									health_monitors_status:{
									<c:forEach items="${vip.health_monitors_status }"
										var="monitors_status">
											${monitors_status.monitor_id },
										</c:forEach>
									}
								</p>
								<p>id:${vip.id }</p>
								<p>tenant_id:${vip.tenant_id }</p>
								<p>name:${vip.name }</p>
							</div>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br />
			<hr />
			<h3>负载主机</h3>
			<table cellpadding="0" cellspacing="0" width="90%" border="0">
				<tr>
					<td style="margin-left: 10%"><span>
							<button
								onclick="button_add_member('${user_id }','${tenant_id }')"
								class="ui-state-default ui-corner-all">创建 负载主机</button>
					</span></td>
				</tr>
			</table>
			<table class="table" cellpadding="0" cellspacing="0" width="100%"
				id="table_member" border="0">
				<colgroup>
				</colgroup>
				<thead class="tb-tit-bg">
					<tr>
						<th width="17%">
							<div class="th-gap">id</div>
						</th>
						<th width="17%">
							<div class="th-gap">vm_id</div>
						</th>
						<th width="12%">
							<div class="th-gap">ip</div>
						</th>
						<th width="12%">
							<div class="th-gap">status</div>
						</th>
						<th width="12%">
							<div class="th-gap">admin_state_up</div>
						</th>
						<th width="12%">
							<div class="th-gap">create_time</div>
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
					<c:forEach var="member" items="${member_list}" varStatus="status">
						<tr>
							<td>${member.id }</td>
							<td>${member.vm_id }</td>
							<td>${member.address }</td>
							<td>${member.status }</td>
							<td>${member.admin_state_up }</td>
							<td>${member.create_time }</td>
							<td><button onclick="button_details_member('${member.id }')"
									class="ui-state-default ui-corner-all">查看</button></td>
							<td><button
									onclick="button_update_member('${user_id }','${tenant_id }','${member.id }','${member.weight }','${member.admin_state_up }')"
									class="ui-state-default ui-corner-all">修改</button> |
								<button
									onclick="button_delete_member('${user_id }','${tenant_id }','${member.id }')"
									class="ui-state-default ui-corner-all">删除</button></td>
							<div id="dialog_delete_member_${member.id }"
								title="MEMBER_${member.id }删除" style="display:none">
								<p>
									确定删除负载主机:<font color="red">${member.id }</font>?
								</p>
								<p>
									当前负载主机所关联的规则:<br /> ${member.vip_id }
								</p>
								<hr>
								<p>
									<font color="blue">负载主机删除后，规则也不再与该主机绑定，请小心操作</font>
								</p>
							</div>
							<div id="dialog_detail_member_${member.id }"
								title="MEMBER_${member.id }详情" style="display:none">
								<p>status:${member.status }</p>
								<p>protocol_port:${member.protocol_port }</p>
								<p>vip_id:${member.vip_id }</p>
								<p>weight:${member.weight }</p>
								<p>admin_state_up:${member.admin_state_up }</p>
								<p>tenant_id:${member.tenant_id }</p>
								<p>create_time:${member.create_time }</p>
								<p>address:${member.address }</p>
								<p>deleted_time:${member.deleted_time }</p>
								<p>status_description:${member.status_description }</p>
								<p>vm_id:${member.vm_id }</p>
								<p>id:${member.id }</p>
							</div>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br />
			<hr />
			<h3>健康检查</h3>
			<table class="table" cellpadding="0" cellspacing="0" width="100%"
				id="table_health" border="0">
				<colgroup>
				</colgroup>
				<thead class="tb-tit-bg">
					<tr>
						<th>
							<div class="th-gap">id</div>
						</th>
						<th>
							<div class="th-gap">type</div>
						</th>
						<th width="12%">
							<div class="th-gap">admin_state_up</div>
						</th>
						<th width="12%">
							<div class="th-gap">create_time</div>
						</th>
						<th width="5%">
							<div class="th-gap">详情</div>
						</th>
						<th width="10%" style="display: none;">
							<div class="th-gap">操作</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="health" items="${health_list }">
						<tr>
							<td>${health.id }</td>
							<td>${health.type }</td>
							<td>${health.admin_state_up }</td>
							<td>${health.create_time }</td>
							<td><button onclick="button_details_health('${health.id }')"
									class="ui-state-default ui-corner-all">查看</button></td>
							<td style="display: none;"><button
									onclick="button_update_health('${user_id }','${tenant_id }','${health.id }','${health.timeout }','${health.delay }','${health.fall }','${health.rise }','${health.max_retries }','${health.admin_state_up }','${health.type }','${health.url_path }','${health.http_method }')"
									class="ui-state-default ui-corner-all">修改</button> |
								<button
									onclick="button_delete_health('${user_id }','${tenant_id }','${health.id }')"
									class="ui-state-default ui-corner-all">删除</button></td>
							<div id="dialog_delete_health_${health.id }"
								title="HEALTH_${health.id }删除" style="display:none">
								<p>
									确定删除健康检查:<font color="red">${health.id }</font>?
								</p>
								<p>
									当前健康检查所关联的规则列表:<br />
									<c:forEach var="health_vip" items="${health.vips }">
											${health_vip.vip_id }<br />
									</c:forEach>
								</p>
								<hr>
								<p>
									<font color="blue">健康检查删除后，规则也不再与该检查绑定，请小心操作</font>
								</p>
							</div>
							<div id="dialog_detail_health_${health.id }"
								title="HEALTH_${health.id }详情" style="display:none">
								<p>
									vips:{
									<c:forEach var="health_vip" items="${health.vips }">
											${health_vip.vip_id },
										</c:forEach>
									}
								</p>
								<p>admin_state_up:${health.admin_state_up }</p>
								<p>tenant_id:${health.tenant_id }</p>
								<p>rise:${health.rise }</p>
								<p>delay:${health.delay }</p>
								<p>max_retries:${health.max_retries }</p>
								<p>create_time:${health.create_time }</p>
								<p>http_method:${health.http_method }</p>
								<p>timeout:${health.timeout }</p>
								<p>fall:${health.fall }</p>
								<p>url_path:${health.url_path }</p>
								<p>type:${health.type }</p>
								<p>id:${health.id }</p>
							</div>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<!-- dialog弹出框 -->
	<div id="dialog_add_pool" title="创建负载均衡" style="display:none">
		<font color="red">以下内容均为必填项</font>
		<table class="table" cellpadding="0" cellspacing="0" width="60%"
			border="0">
			<tr>
				<td width="30%">名称(name):</td>
				<td><input id="new_pool_name"></td>
			</tr>
			<tr>
				<td>类型(network_type):</td>
				<td><input type="radio" name="net_type" id="net_type"
					value="public">公网 <input type="radio" name="net_type"
					id="net_type" value="private">内网</td>
			</tr>
			<tr>
				<td>带宽(egress):</td>
				<td><input id="new_pool_egress">M</td>
			</tr>
		</table>
	</div>

	<div id="dialog_add_vip" title="创建规则" style="display:none">
		<font color="red">以下内容均为必填项</font>
		<table class="table" cellpadding="0" cellspacing="0" width="60%"
			border="0">
			<tr>
				<td>名称(name):</td>
				<td><input id="vip_name"></td>
			</tr>
			<tr>
				<td width="32%">负载ID(pool_id):</td>
				<td><select id="pool_id" style="width: 70%">
						<c:forEach var="select_pool" items="${list}">
							<option value="${select_pool.id }">${select_pool.name }</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>监听协议(protocol):</td>
				<td id="protocol_td"></td>
			</tr>
			<tr>
				<td>监听端口(protocol_port):</td>
				<td><input id="vip_protocol_port"></td>
			</tr>
			<tr>
				<td>转发规则(lb_method):</td>
				<td><input type="radio" name="vip_lb_method"
					id="vip_lb_method1" checked="checked" value="ROUND_ROBIN">轮询
					<input type="radio" name="vip_lb_method" id="vip_lb_method2"
					value="LEAST_CONNECTIONS">最小连接数</td>
				</td>
			</tr>
			<tr>
				<td>session_persistence:</td>
				<td>
					<table class="table" cellpadding="0" cellspacing="0" width="60%"
						border="0">
						<tr>
							<td width="30%">cookie_name:</td>
							<td><input id="cookie_name" size="15"><font
								color="blue">#对于TCP无意义</font></td>
						</tr>
						<tr>
							<td>type:</td>
							<td><input id="cookie_type" size="15"><font
								color="blue">#对于TCP无意义</font></td>
						</tr>
						<tr>
							<td>timout:</td>
							<td><input id="cookie_timeout" size="10"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

	<div id="dialog_add_member" title="添加负载主机" style="display:none">
		<font color="red">以下内容均为必填项</font>
		<table class="table" cellpadding="0" cellspacing="0" width="60%"
			border="0">
			<tr>
				<td>虚拟机(vm_id):</td>
				<td><select id="add_vm_id" style="width: 70%">
						<c:forEach var="select_vm" items="${vm_list}">
							<option value="${select_vm.id }">${select_vm.name }</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>规则(vip_id)</td>
				<td><select id="add_vip_id" style="width: 70%">
						<c:forEach var="select_vip" items="${vip_list}">
							<option value="${select_vip.id }">${select_vip.name }</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>IP(address):</td>
				<td><input id="member_ip"></td>
			</tr>
			<tr>
				<td>端口(protocol_port):</td>
				<td><input id="member_port"></td>
			</tr>
			<tr>
				<td>权重(weight):</td>
				<td><input id="member_weight"></td>
			</tr>
		</table>
	</div>

	<div id="dialog_add_health" title="添加健康检查" style="display:none">
		<font color="red">以下内容均为必填项</font>
		<table class="table" cellpadding="0" cellspacing="0" width="60%"
			border="0">
			<tr>
				<td>响应超时时间(timeout):</td>
				<td><input id="health_timeout" size="10">ms</td>
			</tr>
			<tr>
				<td>健康检查时间(delay):</td>
				<td><input id="health_delay" size="10">s</td>
			</tr>
			<tr>
				<td>不健康阈值(fall):</td>
				<td><input id="health_fall" size="10"></td>
			</tr>
			<tr>
				<td>健康阈值(rise):</td>
				<td><input id="health_rise" size="10"></td>
			</tr>
			<tr>
				<td>max_retries:</td>
				<td><input id="health_max_retries" size="10"></td>
			</tr>
			<tr>
				<td>检查类型(type):</td>
				<td id="health_type_td"></td>
			</tr>
			<tr id="health_url_path_td">
				<td>HTTP请求连接(url_path):</td>
				<td><input id="health_url_path" size="10"></td>
			</tr>
			<tr id="http_method_td">
				<td>HTTP请求方式(http_method):</td>
				<td>HEAD</td>
			</tr>
		</table>
	</div>

	<div id="dialog_update_health" title="更新健康检查" style="display:none">
		<table class="table" cellpadding="0" cellspacing="0" width="60%"
			border="0">
			<tr>
				<td>ID:</td>
				<td><span id="span_update_id"></span> <input type="hidden"
					id="update_id"></td>
			</tr>
			<tr>
				<td>开关(open):</td>
				<td id="update_open_td"></td>
			</tr>
			<tr>
				<td>响应超时时间(timeout):</td>
				<td><input id="update_health_timeout" size="10">ms</td>
			</tr>
			<tr>
				<td>健康检查时间(delay):</td>
				<td><input id="update_health_delay" size="10">s</td>
			</tr>
			<tr>
				<td>不健康阈值(fall):</td>
				<td><input id="update_health_fall" size="10"></td>
			</tr>
			<tr>
				<td>健康阈值(rise):</td>
				<td><input id="update_health_rise" size="10"></td>
			</tr>
			<tr>
				<td>max_retries:</td>
				<td><input id="update_health_max_retries" size="10"></td>
			</tr>
			<tr>
				<td>检查类型(type):</td>
				<td id="update_health_type_td"><span
					id="update_health_type_span"></span> <input type="hidden"
					id="update_health_type"></td>
			</tr>
			<tr id="update_health_url_path_td">
				<td>HTTP请求连接(url_path):</td>
				<td><input id="update_health_url_path" size="10"></td>
			</tr>
			<tr id="update_http_method_td">
				<td>HTTP请求方式(http_method):</td>
				<td><input id="update_http_method" size="10"></td>
			</tr>
		</table>
	</div>

	<div id="dialog_vip_bind_health" title="绑定健康检查" style="display:none">
		<font color="red">请选择健康检查</font>
		<%-- <select id="bind_health_monitor_id">
						<c:forEach var="health" items="${health_all_list }">
							<option value="${health.id }">${health.id }</option>
						</c:forEach>
				</select> --%>
		<table class="table" cellpadding="0" cellspacing="0" width="60%"
			border="0">
			<thead>
				<th width="10%">选择</th>
				<th>详情</th>
			</thead>
			<tbody>
				<c:forEach var="health" items="${health_all_list }">
					<tr>
						<td><input type="radio" name="bind_health_monitor_id"
							id="bind_health_monitor_id_${health.id }" value="${health.id }">
						</td>
						<td>
							<p>
								id:<font color="red">${health.id }</font> | 开关(admin_state_up):<font
									color="red">${health.admin_state_up }</font> | 创建(create_time):<font
									color="red">${health.create_time }</font>
							</p>
							<p>
								类型(type):<font color="blue">${health.type }</font> | 健康阈值(rise):<font
									color="blue">${health.rise }</font> | 不健康阈值(fall):<font
									color="blue">${health.fall }</font> | 健康检查时间(delay):<font
									color="blue">${health.delay }</font> | max_retries:<font
									color="blue">${health.max_retries }</font>
							</p>
							<p>
								超时(timeout):<font color="blue">${health.timeout }</font> |
								http_method:<font color="blue">${health.http_method }</font> |
								url_path:<font color="blue">${health.url_path }</font>
							</p>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div id="dialog_update_member" title="修改负载主机" style="display:none">
		<table class="table" cellpadding="0" cellspacing="0" width="60%"
			border="0">
			<tr>
				<td width="20%">ID:</td>
				<td><span id="span_update_member_id"></span> <input
					type="hidden" id="update_member_id"></td>
			</tr>
			<tr>
				<td>开关(open):</td>
				<td id="td_update_member_open"></td>
			</tr>
			<tr>
				<td>权重(weight):</td>
				<td><input id="update_memeber_weight" size="10"></td>
			</tr>
		</table>
	</div>

	<div id="dialog_update_vip" title="修改规则" style="display:none">
		<table class="table" cellpadding="0" cellspacing="0" width="60%"
			border="0">
			<tr>
				<td>ID:</td>
				<td><span id="span_update_vip_id"></span> <input type="hidden"
					id="update_vip_id"></td>
			</tr>
			<tr>
				<td>监听协议(protocol):</td>
				<td><span id="span_update_vip_protocol"></span> <input
					type="hidden" id="update_vip_protocol"></td>
			</tr>
			<tr>
				<td>名称(name):</td>
				<td><input id="update_vip_name"></td>
			</tr>
			<tr>
				<td>开关(open):</td>
				<td id="td_update_vip_open"></td>
			</tr>
			<tr>
				<td>connection_limit:</td>
				<td><input id="update_vip_connection_limit" size="10">
				</td>
			</tr>
			<tr>
				<td>session_persistence:</td>
				<td>
					<table class="table" cellpadding="0" cellspacing="0" width="60%"
						border="0">
						<tr>
							<td width="30%">cookie_name:</td>
							<td><input id="update_vip_cookie_name" size="15"><font
								color="blue">#对于TCP无意义</font></td>
						</tr>
						<tr>
							<td>type:</td>
							<td><input id="update_vip_cookie_type" size="15"><font
								color="blue">#对于TCP无意义</font></td>
						</tr>
						<tr>
							<td>timout:</td>
							<td><input id="update_vip_cookie_timeout" size="10"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

	<div id="dialog_update_pool" title="修改负载均衡" style="display:none">
		<table class="table" cellpadding="0" cellspacing="0" width="60%"
			border="0">
			<tr>
				<td>ID:</td>
				<td><span id="span_update_pool_id"></span> <input type="hidden"
					id="update_pool_id"></td>
			</tr>
			<tr>
				<td>开关(open):</td>
				<td id="td_update_pool_open"></td>
			</tr>
			<tr>
				<td>名称(name):</td>
				<td><input id="update_pool_name"></td>
			</tr>
			<tr>
				<td>带宽(egress):</td>
				<td><input id="update_pool_egress"></td>
			</tr>
		</table>
	</div>
</body>
</html>
