package com.ksyun.vm.dao;

import com.ksyun.vm.pojo.login.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<User> getUser(String username,String password) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        List<User> list = sqlSession.selectList(nameSpace + ".findUser", map);
        return list;
    }
}
