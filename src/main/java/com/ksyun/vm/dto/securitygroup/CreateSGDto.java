package com.ksyun.vm.dto.securitygroup;

import com.ksyun.vm.dto.common.CoreDto;

public class CreateSGDto extends CoreDto{	
	private String name;
	private String desc;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
