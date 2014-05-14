package com.ksyun.vm.service.rds;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.Result;
import com.ksyun.vm.pojo.rds.Backup;
import com.ksyun.vm.pojo.rds.RDSInstance;
import com.ksyun.vm.pojo.rds.RDSInstanceRule;
import com.ksyun.vm.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 下午1:24
 * Func:
 */
@Service
public class RDSInstanceCheck extends Check implements Validation {

    @Resource(type = InstanceCheck.class)
    private CheckListener instanceCheck;
    @Resource
    private RdsService rdsService;

    @Override
    public Map<String, Object> check(MethodType type, String username, Map<String, String> keyValues,CheckListener checkListener) throws ErrorCodeException,NoTokenException {
        Map<String, Object> map = new HashMap<>();
        if (type.equals(MethodType.UPDATE) && checkListener.checkUpdate(keyValues)) {
            Result result = new Result();
            result.setData(new ArrayList());
            result.setStatus("0");
            result.setStatusText("[非法]操作失败!");
            map.put("isNotPass", true);
            map.put("result", result);
        } else {
            map = super.check(type, username, keyValues,null);
            if(map.size() > 0){
                if(Boolean.valueOf(map.get("isNotPass").toString())){
                    return map;
                }
            }
            RDSInstance instance = rdsService.getInstance(username,keyValues.get("id"));
            if(!(instance.getType().equals("MASTER")||instance.getType().equals("RR")||instance.getType().equals("SINGLE"))){
                Result result = new Result();
                result.setData(new ArrayList());
                result.setStatus("0");
                result.setStatusText("[非法]操作失败!");
                map.put("isNotPass", true);
                map.put("result", result);
            }else if(type.equals(MethodType.DELETE)){
                boolean isOwn = false;
                if(keyValues.containsKey("backup_id")){
                    List<Backup> list;
                    list = rdsService.getBackups(username,keyValues.get("id"));
                    for(Backup po:list){
                        if(po.getId().equals(keyValues.get("backup_id"))){
                            isOwn=true;
                        }
                    }
                }else if(keyValues.containsKey("rule_id")) {
                    List<RDSInstanceRule> rules = rdsService.getRules(username, keyValues.get("id"));
                    for(RDSInstanceRule rule:rules){
                        if(rule.getId().equals(keyValues.get("rule_id"))){
                            isOwn=true;
                        }
                    }
                }
                if (!isOwn){
                    Result result = new Result();
                    result.setData(new ArrayList());
                    result.setStatus("0");
                    result.setStatusText("[无权限]操作失败!");
                    map.put("isNotPass", true);
                    map.put("result", result);
                }
            }
        }
        return map;
    }

    public Map<String, Object> check(MethodType type, String username, Map<String, String> keyValues) throws ErrorCodeException,  NoTokenException {
        return check(type, username, keyValues,instanceCheck);
    }
}
