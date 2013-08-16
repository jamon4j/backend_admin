package com.ksyun.vm.utils.enumeration;

public enum EnumEditVm {
	
	restart("restart"),
	changepwd("changepwd"),
	terminal("terminal"),
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
