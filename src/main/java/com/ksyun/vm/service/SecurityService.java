package com.ksyun.vm.service;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.security.Rule;
import com.ksyun.vm.pojo.security.SecurityPojo;
import com.ksyun.vm.utils.InitConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: liuchuandong
 * Date: 13-10-11
 * Time: 下午3:47
 * Func:
 */
@Service
public class SecurityService {

    @Autowired
    private JSONService jsonService;

    public List<SecurityPojo> getSecurity(String id,String tenant_id) throws ErrorCodeException, NoTokenException {
        jsonService.setId(id);
        jsonService.setTenantId(tenant_id);
        List<SecurityPojo> list = jsonService.getPoList(InitConst.KVM_SECURITY_GROUP_LIST, null, null, SecurityPojo.class);
        return list;
    }

    public void addSecurity(String id,String tenantId,String name,String desc) throws ErrorCodeException, NoTokenException {
        Map<String,String> map = new HashMap<>();
        map.put("name", name);
        map.put("desc", desc);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.setId(id);
        jsonService.setTenantId(tenantId);
        jsonService.poPost(InitConst.KVM_SECURITY_GROUP_CRATE,null,null,null,requestBody);
    }

    public void delSecurity(String id,String tenantId,String groupIds) throws ErrorCodeException, NoTokenException {
        String[] ids = groupIds.split(",");
        jsonService.setId(id);
        jsonService.setTenantId(tenantId);
        for(String gid:ids){
            jsonService.poDelete(InitConst.KVM_SECURITY_GROUP_DELETE,null,null,gid);
        }
    }

    public SecurityPojo getRules(String id,String tenantId,String groupId) throws ErrorCodeException, NoTokenException {
        jsonService.setId(id);
        jsonService.setTenantId(tenantId);
        SecurityPojo pojo = jsonService.poGet(InitConst.KVM_SECURITY_GROUP, null, null, SecurityPojo.class, groupId);
        return pojo;
    }

    public void addRule(String userId,String tenantId,String sgid,String protocal,String fromPort,String toPort,String cidr) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("group_id", sgid);
        map.put("ip_protocol", protocal);
        map.put("from_port", fromPort);
        map.put("to_port", toPort);
        map.put("cidr", cidr);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_SECURITY_GROUP_RULE_CREATE, null, null, null, requestBody);
    }
    public void delRule(String userId,String tenantId,String ruleId) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        jsonService.poDelete(InitConst.KVM_SECURITY_GROUP_RULE_DELETE, null, null, ruleId);
    }
}
