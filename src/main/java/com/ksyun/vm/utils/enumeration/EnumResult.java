package com.ksyun.vm.utils.enumeration;

public enum EnumResult {
	
	successful("successful"),
	failed("failed");
	
	private EnumResult(String type) {
		this.type = type;
	}
	
	private String type;

	public String value() {
		return type;
	}
}
