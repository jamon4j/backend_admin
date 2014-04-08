package com.ksyun.vm.dao.acl;

import org.springframework.stereotype.Repository;
import com.ksyun.vm.pojo.acl.RolePo;
import com.ksyun.vm.dao.BaseDao;


@Repository
public class RoleDao<RolePo> extends BaseDao<RolePo,Integer> {
	
    {
        nameSpace = "com.ksyun.vm.pojo.acl.RolePo";
    }

    public void test()
    {
    	System.out.println("RoleDao.test");
    }
}
