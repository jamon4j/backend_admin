package com.ksyun.vm.service;


import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;

/**
 * User: liuchuandong
 * Date: 13-12-9
 * Time: 下午7:16
 * Func:
 */
public interface BaseAuth {

    public boolean isOwner(String username, String id) throws ErrorCodeException, NoTokenException;
}
