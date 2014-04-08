package com.ksyun.vm.dao.acl;

import org.springframework.stereotype.Repository;

import com.ksyun.vm.pojo.acl.RolePo;
import com.ksyun.vm.dao.BaseDao;
import com.ksyun.vm.dao.interfaces.IRoleDao;


@Repository
public class RoleDao<RolePo> extends BaseDao<RolePo,Integer> implements IRoleDao<RolePo> {
	
    {
        nameSpace = "com.ksyun.vm.pojo.acl.RolePo";
    }

    /* (non-Javadoc)
	 * @see com.ksyun.vm.dao.acl.IRoleDao#test()
	 */
    @Override
	public void test()
    {
    	System.out.println("RoleDao.test");
    }
}
