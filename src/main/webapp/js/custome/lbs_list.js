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
	$("#dialog_detail_vip_"+id).dialog({
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
	$("#dialog_detail_vip_"+id).dialog("open");
}

//JS自定义方法
function button_details_pool(id) {
	dialog_detail_pool(id);
}

function button_details_vip(id) {
	dialog_detail_vip(id);
}
