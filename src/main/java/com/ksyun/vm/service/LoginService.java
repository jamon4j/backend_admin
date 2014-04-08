package com.ksyun.vm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksyun.vm.dao.LoginDao;
import com.ksyun.vm.pojo.login.User;

/**
 * User: liuchuandong Date: 13-10-31 Time: 上午11:00 Func:
 */
@Service
public class LoginService {
	@Autowired
	private LoginDao loginDao;

	public List<User> getUser(String username, String password) {
		return loginDao.getUser(username, password);
	}

	public List<User> getUserAll() {
		return loginDao.findAll();
	}

	public void addUser(String email, String passwd, String roles) {
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
}
