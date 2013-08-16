package com.ksyun.vm.dto.admin;

import com.ksyun.vm.dto.common.CoreDto;

public class AdminDto extends CoreDto{
	private String name;
	private String id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
