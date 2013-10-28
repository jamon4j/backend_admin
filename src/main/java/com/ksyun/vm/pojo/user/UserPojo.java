package com.ksyun.vm.pojo.user;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-10-11
 * Time: 下午2:35
 * Func:
 */
public class UserPojo extends BasePo {

    private String user_id;
    private String user_name;
    private String enabled;
    private String email;
    private String tenant_id;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

    
}
