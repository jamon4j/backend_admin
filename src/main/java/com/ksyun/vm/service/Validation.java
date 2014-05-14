package com.ksyun.vm.service;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;

import java.util.Map;

/**
 * User: liuchuandong
 * Date: 14-2-24
 * Time: 下午1:54
 * Func:
 */
public interface Validation {

    public void setBaseAuth(BaseAuth baseAuth);
    public boolean isBlank(Map<String, String> keyValues);
    public boolean isHasAuth(String username, String id)throws ErrorCodeException, NoTokenException;
    public Map<String,Object> check(MethodType type, String username, Map<String, String> keyValues, CheckListener checkListener)throws ErrorCodeException, NoTokenException;
    public Map<String,Object> check(MethodType type, String username, Map<String, String> keyValues)throws ErrorCodeException, NoTokenException;
}
