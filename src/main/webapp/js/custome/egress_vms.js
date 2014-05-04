function a_update_egress(vm_id, old_egress, userId) {
	$("#new_brand_" + vm_id).val("");
	$("#dialog_update_egress_" + vm_id).dialog(
			{
				autoOpen : false,
				postion : "center",
				modal : true,
				height : "220",
				width : "390",
				buttons : {
					'更新' : function() {
						var new_egress = $("#new_brand_" + vm_id).val();
						var i_old_egress = parseInt(old_egress);
						if (new_egress != "") {
							if (new_egress > i_old_egress) {
								var confirm_html = "确认更新?\n\nvm_id:" + vm_id
										+ "\n\n旧带宽:" + old_egress + "M\n\n新带宽:"
										+ new_egress;
								if ($("#isNeedPay_" + vm_id).val() == "1") {
									confirm_html = confirm_html + "\n\n计费:是";
								} else {
									confirm_html = confirm_html + "\n\n计费:否";
								}
								confirm_html = confirm_html + "\n\n\n请仔细核对信息!"
								var jsonData = JSON.stringify({
									"userId" : userId,
									"productId" : vm_id,
									"net" : $("#new_brand_" + vm_id).val(),
									"isNeedPay" : $("#isNeedPay_" + vm_id)
											.val()
								});
								if (confirm(confirm_html)) {
									// 发送AJAX请求
									$.ajax({
										url : '/egress/update',
										cache : false,
										type : 'POST',
										contentType : 'application/json',
										data : jsonData,
										success : function(data) {
											if (data == "ok") {
												alert("虚拟机:" + vm_id
														+ " 带宽更新成功");
												window.location.reload();
											} else if (data == "fail") {
												alert("虚拟机:" + vm_id
														+ " 带宽更新失败");
												window.location.reload();
											} else {
												alert("虚拟机:" + vm_id
														+ " 带宽更新失败,错误代码:"
														+ data);
												window.location.reload();
											}
										},
										error : function(a, b, c) {
											alert("带宽升级失败:" + a + "|" + b + "|"
													+ c);
											window.location.reload();
										}
									});
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