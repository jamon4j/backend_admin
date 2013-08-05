package com.ksyun.vm.utils.enumeration;

public enum EnumEditVm {
	
	reboot("reboot"),
	pause("pause"),
	suspend("suspend"),
	resume("resume"),
	stop("stop"),
	start("start");
	
	
	private EnumEditVm(String action) {
		this.action = action;
	}
	
	private String action;

	public String value() {
		return action;
	}
}
