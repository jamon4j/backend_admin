package com.ksyun.vm.pojo.login;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-10-30
 * Time: 下午4:28
 * Func:
 */
public class User extends BasePo{
    private Integer id;
    
    private String roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", roles=" + roles + ", username=" + username
				+ ", password=" + password + "]";
	}
    
}
