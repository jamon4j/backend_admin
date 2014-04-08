package com.ksyun.vm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ksyun.vm.dao.interfaces.IRoleDao;
import com.ksyun.vm.pojo.acl.RolePo;

/**
 * User: zhangzongli
 * Date: 14-02-24
 * Time: 上午11:00
 * Func:
 */
@Service
public class RoleService {
	
	@Resource
    private IRoleDao<RolePo> roleDao;

    
    public List<RolePo> getAll() {
        return roleDao.findAll();
    }
    
    public RolePo getRole(int roleId)
    {
    	return roleDao.findById(roleId);
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
