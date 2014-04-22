var protocol_html = "<input type=\"radio\" name=\"vip_protocol\" id=\"vip_protocol1\" checked=\"checked\" value=\"HTTP\">HTTP"
		+ " <input type=\"radio\" name=\"vip_protocol\" id=\"vip_protocol2\" value=\"TCP\">TCP";
var health_type_html = "<input type=\"radio\" name=\"health_type\" id=\"health_type1\" checked=\"checked\" value=\"HTTP\">HTTP"
	+ " <input type=\"radio\" name=\"health_type\" id=\"health_type2\" value=\"TCP\">TCP";
$(function() {
	$("#protocol_td").append(protocol_html);
	$("#health_type_td").append(health_type_html);
	$("#health_url_path_td").show();
	$("#http_method_td").show();
	$("#protocol_td :radio").change(function() {
		radio_protocol_change($(this).val());
	});
	$("#health_type_td :radio").change(function() {
		radio_health_change($(this).val());
	});
});
// Dialog方法区
function dialog_detail_pool(id) {
	$("#dialog_detail_pool_" + id).dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : "500",
		width : "700",
		show : {
			effect : "blind",
			duration : 200
		},
		hide : {
			effect : "explode",
			duration : 1000
		}
	});
	$("#dialog_detail_pool_" + id).dialog("open");
}

function dialog_detail_vip(id) {
	$("#dialog_detail_vip_" + id).dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : "500",
		width : "700",
		show : {
			effect : "blind",
			duration : 200
		},
		hide : {
			effect : "explode",
			duration : 1000
		}
	});
	$("#dialog_detail_vip_" + id).dialog("open");
}

function dialog_detail_member(id) {
	$("#dialog_detail_member_" + id).dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : "500",
		width : "700",
		show : {
			effect : "blind",
			duration : 200
		},
		hide : {
			effect : "explode",
			duration : 1000
		}
	});
	$("#dialog_detail_member_" + id).dialog("open");
}

function dialog_detail_health(id) {
	$("#dialog_detail_health_" + id).dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 500,
		width : 700,
		show : {
			effect : "blind",
			duration : 200
		},
		hide : {
			effect : "explode",
			duration : 1000
		}
	});
	$("#dialog_detail_health_" + id).dialog("open");
}

function dialog_add_pool(userId, tenantId) {
	$("#new_pool_name").val("");
	$("#new_pool_egress").val("");
	$("#dialog_add_pool").dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 220,
		width : 500,
		buttons : {
			'创建' : function() {
				$.ajax({
					url : "/g/lbs/pool/add/" + userId + "/" + tenantId,
					cache : false,
					data : {
						new_pool_name : $("#new_pool_name").val(),
						net_type : $('input[name="net_type"]:checked').val(),
						new_pool_egress : $("#new_pool_egress").val()
					},
					success : function(data) {
						alert("创建负载均衡:" + $("#new_pool_name").val() + "成功!");
						window.location.reload();
					},
					error : function(a, b, c) {
						alert("创建负载均衡失败！");
						window.location.reload();
					}
				});
			}
		}
	});
	$("#dialog_add_pool").dialog("open");
}

function dialog_add_vip(userId, tenantId) {
	$("#vip_name").val("");
	$("#vip_protocol_port").val("");
	$("#cookie_name").val("");
	$("#cookie_type").val("");
	$("#cookie_timeout").val("");
	$("#protocol_td").html("");
	$("#protocol_td").append(protocol_html);
	$("#protocol_td :radio").change(function() {
		radio_protocol_change($(this).val());
	});
	$("#dialog_add_vip").dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 410,
		width : 550,
		buttons : {
			'创建' : function() {
				$.ajax({
					url : "/g/lbs/vip/add/" + userId + "/" + tenantId,
					cache : false,
					data : {
						name : $("#vip_name").val(),
						protocol:$('input[name="vip_protocol"]:checked').val(),
						protocol_port:$("#vip_protocol_port").val(),
						lb_method:$('input[name="vip_lb_method"]:checked').val(),
						pool_id:$("#pool_id").val(),
						cookie_name:$("#cookie_name").val(),
						cookie_type:$("#cookie_type").val(),
						cookie_timeout:$("#cookie_timeout").val()
					},
					success : function(data) {
						if(data == "ok") {
							alert("规则:" + $("#vip_name").val()+" 创建成功");
							window.location.reload();
						} else {
							alert("规则创建失败，请检查输入内容");
							window.location.reload();
						}
					},
					error : function(a, b, c) {
						alert("规则创建失败!"+a +"|"+b+"|"+c);
						window.location.reload();
					}
				});
			}
		}
	});
	$("#dialog_add_vip").dialog("open");
}

function dialog_add_member(userId, tenantId) {
	$("#member_ip").val("");
	$("#member_port").val("");
	$("#member_weight").val("");
	$("#dialog_add_member").dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 290,
		width : 550,
		buttons : {
			'创建' : function() {
				$.ajax({
					url:"/g/lbs/member/add/" + userId + "/" + tenantId,
					cache : false,
					data : {
						address:$("#member_ip").val(),
						protocol_port:$("#member_port").val(),
						weight:$("#member_weight").val(),
						vip_id:$("#add_vip_id").val(),
						vm_id:$("#add_vm_id").val()
					},
					success : function(data) {
						if(data == "ok") {
							alert("负载主机创建成功");
							window.location.reload();
						} else {
							alert("负载主机创建失败，请检查内容");
							window.location.reload();
						}
					},
					error : function(a,b,c) {
						alert("负载主机创建失败! " + a + " " + b + " " + c);
						window.location.reload();
					}
				});
			}
		}
	});
	$("#dialog_add_member").dialog("open");
}

function dialog_add_health(userId, tenantId) {
	$("#health_delay").val("");
	$("#health_max_retries").val("");
	$("#health_timeout").val("");
	$("#health_rise").val("");
	$("#health_fall").val("");
	$("#health_url_path").val("");
	$("#health_type_td").html("");
	$("#health_type_td").append(health_type_html);
	$("#health_url_path_td").show();
	$("#http_method_td").show();
	$("#health_type_td :radio").change(function() {
		radio_health_change($(this).val());
	});
	$("#dialog_add_health").dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 360,
		width : 550,
		buttons : {
			'创建' : function() {
				$.ajax({
					url : "/g/lbs/health/add/" + userId + "/" + tenantId,
					cache : false,
					data :{
						delay:$("#health_delay").val(),
						max_retries:$("#health_max_retries").val(),
						type:$('input[name="health_type"]:checked').val(),
						timeout:$("#health_timeout").val(),
						rise:$("#health_rise").val(),
						fall:$("#health_fall").val(),
						url_path:$("#health_url_path").val(),
						http_method:"HEAD"
					},
					success : function(data) {
						if(data == "ok") {
							alert("健康检查创建成功");
							window.location.reload();
						} else {
							alert("健康检查创建失败，请检查内容");
							window.location.reload();
						}
					},
					error : function(a,b,c) {
						alert("健康检查创建失败! " + a + " " + b + " " + c);
						window.location.reload();
					}
				});
			}
		}
	});
	$("#dialog_add_health").dialog("open");
}

function dialog_delete_health(userId, tenantId, healthId) {
	$("#dialog_delete_health_" + healthId).dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 260,
		width : 500,
		buttons : {
			"确认":function() {
				$.ajax({
					url : "/g/lbs/health/del/" + userId + "/" + tenantId + "/" + healthId,
					cache : false,
					success : function(data) {
						if(data == "ok") {
							alert("健康检查:" + healthId + " 删除成功");
							window.location.reload();
						} else {
							alert("健康检查:" + healthId + " 删除失败");
							window.location.reload();
						}
					},
					error : function(a, b, c) {
						alert("健康检查删除失败!" + a + "|" + b + "|" + c);
						window.location.reload();
					}
				});
			}
		}
	});
	$("#dialog_delete_health_" + healthId).dialog("open");
}

function dialog_delete_member(userId, tenantId, memberId) {
	$("#dialog_delete_member_" + memberId).dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 260,
		width : 500,
		buttons : {
			"确认" : function() {
				$.ajax({
					url : "/g/lbs/member/del/" + userId + "/" + tenantId + "/" + memberId,
					cache : false,
					success : function(data) {
						if(data == "ok"){
							alert("负载主机:"+memberId+" 删除成功");
							window.location.reload();
						} else {
							alert("负载主机:"+memberId+" 删除失败");
							window.location.reload();
						}
					},
					error : function(a, b, c) {
						alert("负载主机删除失败!");
						window.location.reload();
					}
				});
			}
		}
	});
	$("#dialog_delete_member_" + memberId).dialog("open");
}

function dialog_vip_bind_health(userId, tenantId, vipId) {
	$("#dialog_vip_bind_health").dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 180,
		width : 500,
		buttons : {
			"绑定" : function() {
				$.ajax({
					url : "/g/lbs/health/bind/" + userId + "/" + tenantId + "/" + vipId,
					cache : false ,
					data : {
						health_monitor_id : $("#bind_health_monitor_id").val()
					},
					success : function(data) {
						if(data == "ok") {
							alert("规则:"+vipId+" 绑定:"+$("#bind_health_monitor_id").val()+" 成功");
							window.location.reload();
						} else {
							alert("规则:"+vipId+" 绑定:"+$("#bind_health_monitor_id").val()+" 失败");
							window.location.reload();
						}
					},
					error : function(a ,b ,c) {
						alert("规则绑定检查失败!"+ a +"|" + b + "|" + c);
						window.location.reload();
					}
				});
			}
		}
	});
	$("#dialog_vip_bind_health").dialog("open");
}

function dialog_vip_unbind_health(userId, tenantId, vipId) {
	$("#dialog_vip_unbind_health_"+vipId).dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 180,
		width : 500,
		buttons : {
			"解绑" : function() {
				var health_monitor_id = $("#unbind_health_monitor_id_"+vipId).val();
				$.ajax({
					url : "/g/lbs/health/unbind/" + userId + "/" + tenantId + "/" + vipId + "/" + health_monitor_id,
					cache : false,
					success : function(data) {
						if(data == "ok") {
							alert("规则:"+vipId+" 解绑:"+health_monitor_id+" 成功");
							window.location.reload();
						} else {
							alert("规则:"+vipId+" 解绑:"+health_monitor_id+" 失败");
							window.location.reload();
						}
					},
					error : function(a, b, c) {
						alert("规则解绑检查失败!"+ a +"|" + b + "|" + c);
						window.location.reload();
					}
				});
			}
		}
	});
	$("#dialog_vip_unbind_health_"+vipId).dialog("open");
}

function dialog_vip_delete(userId, tenantId, vipId) {
	$("#dialog_vip_delete_" + vipId).dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 290,
		width : 550,
		buttons : {
			"删除" : function() {
				$.ajax({
					url : "/g/lbs/vip/del/" + userId + "/" + tenantId + "/" + vipId,
					cache : false,
					success : function(data) {
						if(data == "ok") {
							alert("规则:"+vipId+" 删除成功");
							window.location.reload();
						} else {
							alert("规则:"+vipId+" 删除失败");
							window.location.reload();
						}
					},
					error : function(a, b, c) {
						alert("规则删除失败!"+ a +"|" + b + "|" + c);
						window.location.reload();
					}
				});
			}
		}
	});
	$("#dialog_vip_delete_" + vipId).dialog("open");
}

function dialog_delete_pool(userId, tenantId, poolId) {
	$("#dialog_delete_pool_" + poolId).dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 290,
		width : 550,
		buttons : {
			"删除" : function() {
				$.ajax({
					url : "/g/lbs/pool/del/" + userId + "/" + tenantId + "/" + poolId,
					cache : false,
					success : function(data) {
						if(data == "ok") {
							alert("删除负载均衡:" + poolId+" 成功");
							window.location.reload();
						} else {
							alert("删除负载均衡:" + poolId+" 失败");
							window.location.reload();
						}
					},
					error : function(a, b, c) {
						alert("删除负载均衡失败！");
						window.location.reload();
					}
				});
			}
		}
	});
	$("#dialog_delete_pool_" + poolId).dialog("open");
}

function dialog_update_health(userId, tenantId, healthId,timeout,delay,fall,rise,max_retries,admin_state_up,type,url_path,http_method) {
	var id_html = "<font color='red'>"+healthId+"</font>";
	var type_html = "<font color='blue'>"+type+"</font>";
	$("#span_update_id").html(id_html);
	$("#update_health_type_span").html(type_html);
	if(admin_state_up == "true") {
		var open_html = "<input type=\"radio\" name=\"update_admin_state_up\" id=\"update_admin_state_up1\" checked=\"checked\" value=\"true\">开"
			+ " <input type=\"radio\" name=\"update_admin_state_up\" id=\"update_admin_state_up2\" value=\"false\">关";
		$("#update_open_td").html(open_html);
	} else {
		var open_html = "<input type=\"radio\" name=\"update_admin_state_up\" id=\"update_admin_state_up1\" value=\"true\">开"
			+ " <input type=\"radio\" name=\"update_admin_state_up\" id=\"update_admin_state_up2\" checked=\"checked\" value=\"false\">关";
		$("#update_open_td").html(open_html);
	}
	if(type == "TCP") {
		$("#update_health_url_path_td").hide();
		$("#update_http_method_td").hide();
	} else {
		$("#update_health_url_path_td").show();
		$("#update_http_method_td").show();
	}
	$("#update_health_timeout").val(timeout);
	$("#update_health_delay").val(delay);
	$("#update_health_fall").val(fall);
	$("#update_health_rise").val(rise);
	$("#update_health_max_retries").val(max_retries);
	$("#update_health_type").val(type);
	$("#update_health_url_path").val(url_path);
	$("#update_http_method").val(http_method);
	$("#dialog_update_health").dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 390,
		width : 550,
		buttons : {
			'修改' : function() {
				$.ajax({
					url :"/g/lbs/health/update/" + userId + "/" + tenantId + "/" + healthId,
					cache : false,
					data :{
						timeout : $("#update_health_timeout").val(),
						delay : $("#update_health_delay").val(),
						fall : $("#update_health_fall").val(),
						rise : $("#update_health_rise").val(),
						max_retries : $("#update_health_max_retries").val(),
						admin_state_up : $('input[name="update_admin_state_up"]:checked').val(),
						type : $("#update_health_type").val(),
						url_path : $("#update_health_url_path").val(),
						http_method : $("#update_http_method").val()
					},
					success : function(data) {
						if(data == "ok") {
							alert("健康检查:" + healthId + " 更新成功");
							window.location.reload();
						} else {
							alert("健康检查:" + healthId + " 更新失败");
							window.location.reload();
						}
					},
					error : function(a,b,c) {
						alert("健康检查更新失败!" + a + "|" + b + "|" + c);
						window.location.reload();
					}
				});
			}
		}
	});
	$("#dialog_update_health").dialog("open");
}

// JS自定义方法
function button_details_pool(id) {
	dialog_detail_pool(id);
}

function button_details_vip(id) {
	dialog_detail_vip(id);
}

function button_details_member(id) {
	dialog_detail_member(id);
}

function button_details_health(id) {
	dialog_detail_health(id)
}

function button_add_pool(userId, tenantId) {
	dialog_add_pool(userId, tenantId);
}

function button_add_vip(userId, tenantId) {
	dialog_add_vip(userId, tenantId)
}

function button_add_member(userId, tenantId) {
	dialog_add_member(userId, tenantId);
}

function button_add_health(userId, tenantId) {
	dialog_add_health(userId, tenantId);
}

function button_delete_health(userId, tenantId, healthId) {
	dialog_delete_health(userId, tenantId, healthId);
}

function button_delete_member(userId, tenantId, memberId){
	dialog_delete_member(userId, tenantId, memberId);
}

function button_vip_bind_health(userId, tenantId, vipId) {
	dialog_vip_bind_health(userId, tenantId, vipId);
}

function button_vip_unbind_health(userId, tenantId, vipId) {
	dialog_vip_unbind_health(userId, tenantId, vipId);
}

function button_vip_delete(userId, tenantId, vipId) {
	dialog_vip_delete(userId, tenantId, vipId);
}

function button_delete_pool(userId, tenantId, poolId) {
	dialog_delete_pool(userId, tenantId, poolId);
}

function button_update_health(userId, tenantId,healthId,timeout,delay,fall,rise,max_retries,admin_state_up,type,url_path,http_method) {
	$("#span_update_id").html("");
	$("#update_health_type_span").html("");
	$("#update_open_td").html("");
	$("#update_health_timeout").val("");
	$("#update_health_delay").val("");
	$("#update_health_fall").val("");
	$("#update_health_rise").val("");
	$("#update_health_max_retries").val("");
	$("#update_health_url_path").val("");
	$("#update_http_method").val("");
	dialog_update_health(userId, tenantId,healthId,timeout,delay,fall,rise,max_retries,admin_state_up,type,url_path,http_method);
}

function radio_protocol_change(value) {
	if (value == "TCP") {
		$("#cookie_type").val("APP_COOKIE");
		$("#cookie_name").val("MyAppCookie");
	} else {
		$("#cookie_type").val("");
		$("#cookie_name").val("");
	}
}

function radio_health_change(value) {
	if (value == "HTTP") {
		$("#health_url_path_td").show();
		$("#http_method_td").show();
	} else {
		$("#health_url_path_td").hide();
		$("#http_method_td").hide();
	}
}
