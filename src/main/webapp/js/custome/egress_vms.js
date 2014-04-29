function a_update_egress(vm_id, old_egress) {
	$("#new_brand_" + vm_id).val("");
	$("#dialog_update_egress_" + vm_id).dialog(
			{
				autoOpen : false,
				postion : "center",
				modal : true,
				height : "195",
				width : "390",
				buttons : {
					'更新' : function() {
						var new_egress = $("#new_brand_" + vm_id).val();
						var i_old_egress = parseInt(old_egress);
						if (new_egress != "") {
							if (new_egress > i_old_egress) {
								var confirm_html = "确认更新?\n\nvm_id:" + vm_id
										+ "\n\n旧带宽:" + old_egress + "M\n\n新带宽:"
										+ new_egress + "M\n\n\n请仔细核对信息!";
								if (confirm(confirm_html)) {
									// 发送AJAX请求
								}
							} else if (new_egress <= i_old_egress) {
								alert("新的带宽值应大于当前带宽值");
							} else {
								alert("请输入正确的带宽值");
							}
						} else {
							alert("请输入新的带宽值!");
						}
					}
				}
			});
	$("#dialog_update_egress_" + vm_id).dialog("open");
}