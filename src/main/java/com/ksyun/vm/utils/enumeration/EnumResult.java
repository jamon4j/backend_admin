package com.ksyun.vm.utils.enumeration;

public enum EnumResult {
	
	successful(1),
	failed(0);
	
	private EnumResult(Integer type) {
		this.type = type;
	}
	
	private Integer type;

	public Integer value() {
		return type;
	}
}
