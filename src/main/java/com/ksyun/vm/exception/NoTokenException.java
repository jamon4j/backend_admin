package com.ksyun.vm.exception;

/**
 * User: liuchuandong
 * Date: 13-9-5
 * Time: 下午5:35
 * Func: 没有授权异常
 */
public class NoTokenException extends Exception {

    public NoTokenException(String username,String password) {
        super("Can't get UserToken by username:"+username+" password:"+password);
    }
}
