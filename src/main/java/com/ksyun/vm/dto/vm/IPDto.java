package com.ksyun.vm.dto.vm;

import com.ksyun.vm.dto.common.CoreDto;

public class IPDto extends CoreDto{
	private String version;
	private String addr;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
}
