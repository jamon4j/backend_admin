package com.ksyun.vm.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ksyun.vm.dao.LoginDao;
import com.ksyun.vm.dao.MonitorVmDiskDao;
import com.ksyun.vm.dao.RoleDao;
import com.ksyun.vm.pojo.acl.RolePo;


@Service
public class DataSwitchService {
	
	@Resource
    private RoleDao<RolePo> roleDao;
	
    @Resource
    private LoginDao loginDao;
    
    @Resource 
    private MonitorVmDiskDao monitorVmDiskDao;
    
    
    
    
    public void setDataSource(String dataSource)
    {
    	roleDao.setDataSource(dataSource);
    	loginDao.setDataSource(dataSource);
    }
}
