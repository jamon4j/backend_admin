package com.ksyun.vm.dto.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.ksyun.vm.dto.common.CoreDto;

public class CreateUserDto extends CoreDto{
	
	private String name;
	private String isAdmin;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JSONField(name = "is_admin")	
	public String getIsAdmin() {
		return isAdmin;
	}
	@JSONField(name = "is_admin")	
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
