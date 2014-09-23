package com.ksyun.vm.service;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.memdb.ClusterPOJO;
import com.ksyun.vm.pojo.memdb.InstancePOJO;
import com.ksyun.vm.pojo.memdb.SecurityGroupPOJO;
import com.ksyun.vm.pojo.memdb.SeurityGroupRulePOJO;
import com.ksyun.vm.pojo.memdb.FlavorPOJO;
import com.ksyun.vm.utils.InitConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiYang14 on 2014/9/16.
 */

@Service
@Scope
public class MemdbService {

    @Autowired
    private JSONService jsonService;

    public List<ClusterPOJO> getClusterList(String userId, String tenantId, String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<ClusterPOJO> list = jsonService.getPoList(InitConst.MEMDB_CLUSTER_LIST, null, null, Region, ClusterPOJO.class);
        return list;
    }

    public void addCluster(String userId, String tenantId, String Region, String name, String ha_cluster, String protocol, int size) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, Object> map = new HashMap<>();
        map.put("size", size);
        map.put("name", name);
        map.put("protocol", protocol);
        map.put("ha_cluster", ha_cluster);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.MEMDB_CLUSTER_ADD, null, null, Region, null, requestBody);
    }

    public void clusterDelete(String userId, String tenantId, String Region, String cluster_id) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, Object> map = new HashMap<>();
        map.put("cluster_id", cluster_id);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poDelete(InitConst.MEMDB_CLUSTER_DELETE, null, null, Region, null, requestBody);

    }

    public void clusterResize(String userId, String tenantId, String Region, String cluster_id, int size) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, Object> map = new HashMap<>();
        map.put("cluster_id", cluster_id);
        map.put("size", size);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.MEMDB_CLUSTER_RESIZE, null, null, Region, null, requestBody);
    }

    public void clusterSetMaxmemoryPolicy(String userId, String tenantId, String Region, String cluster_id, String policy) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, Object> map = new HashMap<>();
        map.put("cluster_id", cluster_id);
        map.put("policy", policy);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.MEMDB_CLUSTER_MAXMEORYPOLICY, null, null, Region, null, requestBody);
    }

    public void clusterFlushdb(String userId, String tenantId, String Region, String cluster_id) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, Object> map = new HashMap<>();
        map.put("cluster_id", cluster_id);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.MEMDB_CLUSTER_FLUSHDB, null, null, Region, null, requestBody);
    }


    public List<InstancePOJO> getInstanceList(String userId, String tenantId, String Region,String cluster_id) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<InstancePOJO> list = jsonService.getPoList(InitConst.MEMDB_INSTANCE_LIST, null, null, Region, InstancePOJO.class,cluster_id);
        return list;
    }


    public void addInstance(String userId, String tenantId, String Region, String role,String flavor_id,String cluster_id) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, Object> map = new HashMap<>();
        map.put("role", role);
        map.put("flavor_id", flavor_id);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.MEMDB_INSTANCE_ADD, null, null, Region, null, requestBody,cluster_id);
    }

    public void failureoverInstance(String userId, String tenantId, String Region, String old_instance_id, String cluster_id) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, Object> map = new HashMap<>();
        map.put("old_instance_id", old_instance_id);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.MEMDB_INSTANCE_FAILUREOVER, null, null, Region, null, requestBody,cluster_id);
    }

    public List<SeurityGroupRulePOJO> clusterSecuritygroupRules(String userId, String tenantId, String Region, String cluster_id) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<SeurityGroupRulePOJO> list = jsonService.getPoList(InitConst.MEMDB_SECURITYGROUPRULE_LIST, null, null, Region, SeurityGroupRulePOJO.class,cluster_id);
        return list;
    }

    public  void  clusterSecuritygroupRuleAdd(String userId, String tenantId, String Region,String group_id,String protocol,String from_port,String to_port,String cdir) throws ErrorCodeException, NoTokenException
    {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, Object> map = new HashMap<>();
        map.put("group_id",group_id);
        map.put("protocol",protocol);
        map.put("from_port",from_port);
        map.put("to_port",to_port);
        map.put("cdir",cdir);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.MEMDB_SECURITYRULE_ADD, null, null, Region, null, requestBody);
    }


    public  void  clusterSecuritygroupRuleDelete(String userId, String tenantId, String Region,String security_group_rule_id) throws ErrorCodeException, NoTokenException
    {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, Object> map = new HashMap<>();
        map.put("security_group_rule_id",security_group_rule_id);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.MEMDB_SECURITYRULE_DELETE, null, null, Region, null, requestBody);
    }

    public  SecurityGroupPOJO clusterSecuritygroupid(String userId, String tenantId, String Region,String security_group_id)throws ErrorCodeException, NoTokenException
    {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, Object> map = new HashMap<>();

       return jsonService.poGet(InitConst.MEMDB_SECURITYGROUP_SINGLE, null, null, Region, SecurityGroupPOJO.class,security_group_id);

    }


    public FlavorPOJO flavor(String userId, String tenantId, String Region,String flavor_id)throws ErrorCodeException, NoTokenException
    {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        return jsonService.poGet(InitConst.MEMDB_FLAVOR_SINGLE, null, null, Region, FlavorPOJO.class,flavor_id);
    }


    public  List<FlavorPOJO> flavorList(String userId, String tenantId, String Region )throws ErrorCodeException, NoTokenException
    {

        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        return jsonService.getPoList(InitConst.MEMDB_FLAVOR_LIST, null, null, Region, FlavorPOJO.class);
    }


}
