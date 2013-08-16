package com.ksyun.vm.dto.admin;

import com.ksyun.vm.dto.common.CoreDto;

public class AuthenticateDto extends CoreDto{
	private String token;
	private AdminDto user;
	private AdminDto tenant;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public AdminDto getUser() {
		return user;
	}
	public void setUser(AdminDto user) {
		this.user = user;
	}
	public AdminDto getTenant() {
		return tenant;
	}
	public void setTenant(AdminDto tenant) {
		this.tenant = tenant;
	}
	
	
}
