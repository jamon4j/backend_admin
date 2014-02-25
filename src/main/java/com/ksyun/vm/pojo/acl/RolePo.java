package com.ksyun.vm.pojo.acl;

import com.ksyun.vm.pojo.BasePo;

public class RolePo extends BasePo{
    private Integer id;

    private Byte roleType;

    private String roleName;

    private String rolePower;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getRoleType() {
        return roleType;
    }

	public void setRoleType(Byte roleType) {
        this.roleType = roleType;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRolePower() {
        return rolePower;
    }

    public void setRolePower(String rolePower) {
        this.rolePower = rolePower == null ? null : rolePower.trim();
    }
    
    
    @Override
	public String toString() {
		return "RolePo [id=" + id + ", roleType=" + roleType + ", roleName="
				+ roleName + ", rolePower=" + rolePower + "]";
	}
    
}