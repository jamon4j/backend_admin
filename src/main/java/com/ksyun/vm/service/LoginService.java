package com.ksyun.vm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksyun.vm.dao.LoginDao;
import com.ksyun.vm.pojo.login.User;
import com.ksyun.vm.utils.MD5Utils;

/**
 * User: liuchuandong Date: 13-10-31 Time: 上午11:00 Func:
 */
@Service
public class LoginService {
	@Autowired
	private LoginDao loginDao;

	public List<User> getUser(String username, String password) {
		//password = MD5Utils.MD5Encoding(password);
		return loginDao.getUser(username, password);
	}

	public List<User> getUserAll() {
		return loginDao.findAll();
	}

	public void addUser(String email, String passwd, String roles) {
		//passwd = MD5Utils.MD5Encoding(passwd);
		User dto = new User();
		dto.setPassword(passwd);
		dto.setRoles(roles);
		dto.setUsername(email);
		loginDao.save(dto);
	}

	public void deleteUser(int userId) {
		loginDao.deleteById(userId);
	}

	public void updateUserRoles(int userId, String roles) {
		User dto = new User();
		dto.setId(userId);
		dto.setRoles(roles);
		loginDao.updateIfNecessary(dto);
	}
	
	public User getUserById(Integer userId) {
		User user = (User) loginDao.queryUserById(userId);
		return user;
	}
}
