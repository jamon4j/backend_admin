//全选操作
function selectAll(checked) {
	var vms = $("input[name='vm_list']");
	if (vms == 0) {
		return;
	}
	if (checked) {
		for ( var idx = 0; idx < vms.length; idx++) {
			vms[idx].checked = true;
		}
	} else {
		for ( var idx = 0; idx < vms.length; idx++) {
			vms[idx].checked = false;
		}
	}
}