package com.ksyun.vm.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.pcloud.common.account.UserHelper;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.UserPo;
import com.ksyun.vm.pojo.user.UserPojo;
import com.ksyun.vm.service.JSONService;

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

	public UserPojo searchUser(String name, String email) throws ErrorCodeException, NoTokenException {
		UserPo userPo = null;
		String userId = null;
		if(!StringUtils.isEmpty(name) && name.equals(InitConst.ADMIN)){
			userPo = jsonService.getToken(name, InitConst.PASSWORD);
			userId = userPo.getUser_id();
		}else if(!StringUtils.isEmpty(name)){
			userPo = jsonService.getToken(name, name);
			userId = userPo.getUser_id();
		}
		
		if(!StringUtils.isEmpty(email)){
			String ADMINUSERNAME = "kpweb@kingsoft.com";
		    String ADMINPASSWORD = "WWcW59FqUFubBzmTe6BmOF1sHmaye";
		//	String ADMINUSERNAME = "test@kingsoft.com";
		//	String ADMINPASSWORD = "123456";
		    String token = UserHelper.login(ADMINUSERNAME, ADMINPASSWORD).get("token");
		    Map<String, String> map =  UserHelper.adminGetUserInfoByEmail(token, email);
		    userPo = jsonService.getToken(map.get("userId"), map.get("userId"));
		    userId = userPo.getUser_id();
		}
		
		UserPojo userPojo = jsonService.poGet(InitConst.KVM_USER, InitConst.ADMIN, InitConst.PASSWORD, UserPojo.class, userId);
		return userPojo;
	}
	
	public static void main(String[] args) {
	//	String ADMINUSERNAME = "kpweb@kingsoft.com";
	//    String ADMINPASSWORD = "WWcW59FqUFubBzmTe6BmOF1sHmaye";
		String ADMINUSERNAME = "test@kingsoft.com";
		String ADMINPASSWORD = "123456";
	    String token = UserHelper.login(ADMINUSERNAME, ADMINPASSWORD).get("token");
	    Map<String, String> map =  UserHelper.adminGetUserInfoByEmail(token, "liuyu1@kingsoft.com");
	    System.out.println(map);
	}
}
