package com.ksyun.vm.service;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.Result;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: liuchuandong
 * Date: 14-2-24
 * Time: 下午1:52
 * Func:
 */
@Service
public class Check implements Validation{

    Logger log = LoggerFactory.getLogger(Check.class);

    private BaseAuth baseAuth;

    public void setBaseAuth(BaseAuth baseAuth) {
        this.baseAuth = baseAuth;
    }

    @Override
    public boolean isBlank(Map<String,String> keyValues) {
        Iterator<Map.Entry<String,String>> it = keyValues.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if(StringUtils.isBlank(value)){
                log.info("[ "+key+" 空值]操作失败!");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isHasAuth(String username,String id) throws ErrorCodeException, NoTokenException {
        if(!baseAuth.isOwner(username,id)){
            log.info("[ "+username+" 没有 "+id+" 权限]操作失败!");
            return true;
        }
        return false;
    }

    @Override
    public Map<String,Object> check(MethodType type,String username,Map<String,String> keyValues,CheckListener checkListener) throws ErrorCodeException, NoTokenException {
        Map<String, Object> map = new HashMap<>();
        Result result;
        if (isBlank(keyValues)) {
            result = new Result();
            result.setData(new ArrayList());
            result.setStatus("0");
            result.setStatusText("[空值]操作失败!");
            map.put("isNotPass", true);
            map.put("result", result);
        } else if (keyValues.containsKey("id")&&isHasAuth(username, keyValues.get("id"))) {
            result = new Result();
            result.setData(new ArrayList());
            result.setStatus("0");
            result.setStatusText("[无权限]操作失败!");
            map.put("isNotPass", true);
            map.put("result", result);
        }
        return map;
    }
    @Override
    public Map<String,Object> check(MethodType type,String username,Map<String,String> keyValues) throws ErrorCodeException, NoTokenException {
        return check(type, username, keyValues, null);
    }
}
