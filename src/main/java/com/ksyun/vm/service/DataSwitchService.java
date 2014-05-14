package com.ksyun.vm.service;

import javax.annotation.Resource;

import com.ksyun.vm.dao.rds.RDSValidationDao;
import org.springframework.stereotype.Service;

import com.ksyun.vm.dao.LoginDao;
import com.ksyun.vm.dao.MonitorVmDiskDao;
import com.ksyun.vm.dao.MonitorVmLoadDao;
import com.ksyun.vm.dao.MonitorVmNetworkDao;
import com.ksyun.vm.dao.MonitorVmStatusFlowDao;
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
	@Resource
	private MonitorVmLoadDao monitorVmLoadDao;
	@Resource
	private MonitorVmNetworkDao monitorVmNetworkDao;
	@Resource
	private MonitorVmStatusFlowDao monitorVmStatusFlowDao;
    @Resource
    private RDSValidationDao rdsValidationDao;

	public void setDataSource(String dataSource) {
		// 由于是Spring的注入默认是单例，对应DAO的baseDAO设置完数据源以后，所有DAO操作的数据源都改变了
		roleDao.setDataSource(dataSource);
		loginDao.setDataSource(dataSource);
		monitorVmDiskDao.setDataSource(dataSource);
		monitorVmLoadDao.setDataSource(dataSource);
		monitorVmNetworkDao.setDataSource(dataSource);
		monitorVmStatusFlowDao.setDataSource(dataSource);
        rdsValidationDao.setDataSource(dataSource);
	}
}
