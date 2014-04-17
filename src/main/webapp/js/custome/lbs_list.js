var protocol_html = "<input type=\"radio\" name=\"vip_protocol\" id=\"vip_protocol1\" checked=\"checked\" value=\"HTTP\">HTTP"
		+ " <input type=\"radio\" name=\"vip_protocol\" id=\"vip_protocol2\" value=\"TCP\">TCP";
var health_type_html = "<input type=\"radio\" name=\"health_type\" id=\"health_type1\" checked=\"checked\" value=\"HTTP\">HTTP"
	+ " <input type=\"radio\" name=\"health_type\" id=\"health_type2\" value=\"TCP\">TCP";
$(function() {
	$("#protocol_td").append(protocol_html);
	$("#health_type_td").append(health_type_html);
	$("#protocol_td :radio").change(function() {
		radio_protocol_change($(this).val());
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
						
					}
				});
			}
		}
	});
	$("#dialog_add_member").dialog("open");
}

function dialog_add_health(userId, tenantId) {
	$("#health_type_td").html("");
	$("#health_type_td").append(health_type_html);
	$("#dialog_add_health").dialog({
		autoOpen : false,
		postion : "center",
		modal : true,
		height : 360,
		width : 550,
		buttons : {
			'创建' : function() {
				
			}
		}
	});
	$("#dialog_add_health").dialog("open");
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

function radio_protocol_change(value) {
	if (value == "TCP") {
		$("#cookie_type").val("APP_COOKIE");
		$("#cookie_name").val("MyAppCookie");
	} else {
		$("#cookie_type").val("");
		$("#cookie_name").val("");
	}
}
