package com.ksyun.vm.service;

import com.ksyun.vm.dao.interfaces.ILoginDao;
import com.ksyun.vm.pojo.login.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-10-31
 * Time: 上午11:00
 * Func:
 */
@Service
public class LoginService {
    @Autowired
    private ILoginDao loginDao;

    public List<User> getUser(String username,String password) {
        return loginDao.getUser(username, password);
    }
}
