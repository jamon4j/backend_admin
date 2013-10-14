package com.ksyun.vm.utils;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.user.UserPojo;
import com.ksyun.vm.service.JSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: liuchuandong
 * Date: 13-10-11
 * Time: 下午2:37
 * Func:
 */
@Service
public class UserService {

    @Autowired
    private JSONService jsonService;

    public List<UserPojo> getUsers() throws ErrorCodeException, NoTokenException {
        List<UserPojo> list = jsonService.getPoList(InitConst.KVM_USER_LIST,InitConst.ADMIN,InitConst.PASSWORD,UserPojo.class);
        return list;
    }

    public void addUser(String username,String email) throws ErrorCodeException, NoTokenException {
        Map<String,String> map = new HashMap();
        map.put("name",username);
        map.put("email",email);
        map.put("is_admin","0");
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poWithNoAuth(InitConst.KVM_USER_REGISTER, null,requestBody);
    }
}
