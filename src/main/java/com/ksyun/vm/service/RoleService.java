package com.ksyun.vm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ksyun.vm.dao.RoleDao;
import com.ksyun.vm.pojo.acl.RolePo;
import com.ksyun.vm.routedatasource.DataSourceInstances;

/**
 * User: zhangzongli
 * Date: 14-02-24
 * Time: 上午11:00
 * Func:
 */
@Service("roleService")
public class RoleService {
	
	@Resource
    private RoleDao<RolePo> roleDao;

    
    public List<RolePo> getAll() {
        return roleDao.findAll();
    }
    
    public RolePo getRole(int roleId)
    {
    	return roleDao.findById(roleId);
    }
    
    public void setSource() {
    	roleDao.setDataSource(DataSourceInstances.DS1);
    }
    
    public void addRole(String name, String roles, Byte roleType)
    {
    	RolePo dto = new RolePo();
    	dto.setRoleName(name);
    	dto.setRolePower(roles);
    	dto.setRoleType(roleType);
    	
    	roleDao.save(dto);
    }
    
    public void deleteRole(int roleId)
    {
    	roleDao.deleteById(roleId);
    }
    
    public void updateRole(RolePo dto)
    {
    	roleDao.update(dto);
    }
}
