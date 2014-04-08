package com.ksyun.vm.dao;

import org.springframework.stereotype.Repository;


@Repository
public class RoleDao<RolePo> extends BaseDao<RolePo,Integer> {
	
    {
        nameSpace = "com.ksyun.vm.pojo.acl.RolePo";
    }

    /* (non-Javadoc)
	 * @see com.ksyun.vm.dao.acl.IRoleDao#test()
	 */
	public void test()
    {
    	System.out.println("RoleDao.test");
    }
}
