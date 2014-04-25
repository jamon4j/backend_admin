package com.ksyun.vm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * User: liuchuandong
 * Date: 13-10-30
 * Time: 下午7:38
 * Func:
 */
@Repository
public class LoginDao<User> extends BaseDao<User,Integer>{
    {
        setNameSpace("com.ksyun.vm.pojo.login.User");
    }
    /* (non-Javadoc)
	 * @see com.ksyun.vm.dao.ILoginDao#getUser(java.lang.String, java.lang.String)
	 */
	public List<User> getUser(String username,String password) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        List<User> list = sqlSession.selectList(nameSpace + ".findUser", map);
        return list;
    }
	
	public User queryUserById(Integer userId) {
		Map<String, Integer> map = new HashMap<>();
		map.put("id", userId);
		User user = sqlSession.selectOne(nameSpace + ".selectByPrimaryKey",map);
		return user;
	}
}
