package com.ksyun.vm.service;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.ebs.EBSPojo;
import com.ksyun.vm.pojo.ebs.VmEBSPojo;
import com.ksyun.vm.utils.InitConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: liuchuandong
 * Date: 13-10-12
 * Time: 下午5:28
 * Func:
 */
@Service
public class EBSService{

    @Autowired
    private JSONService jsonService;

    public List<EBSPojo> getEBS(String userId,String tenantId) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<EBSPojo> list = jsonService.getPoList(InitConst.KVM_EBS_USER_LIST, null, null, EBSPojo.class);
        return list;
    }

    public List<EBSPojo> getEBS(String userId,String tenantId,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<EBSPojo> list = jsonService.getPoList(InitConst.KVM_EBS_USER_LIST, null, null, Region,EBSPojo.class);
        return list;
    }
    public void addEBS(String userId,String tenantId,String name,String size,String desc) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("size",size);
        map.put("name", name);
        map.put("description", desc);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_EBS_CREATE, null, null, null, requestBody);
    }

    public void addEBS(String userId,String tenantId,String name,String size,String desc,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("size",size);
        map.put("name", name);
        map.put("description", desc);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_EBS_CREATE, null, null,Region, null, requestBody);
    }

    public void delEBS(String userId,String tenantId,String ebs_id) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        jsonService.poDelete(InitConst.KVM_EBS_DELETE, null, null, ebs_id);
    }

    public void delEBS(String userId,String tenantId,String ebs_id,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        jsonService.poDelete(InitConst.KVM_EBS_DELETE, null, null,Region,  ebs_id);
    }

    public List<VmEBSPojo> getVMEBS(String userId,String tenantId,String vmId) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<VmEBSPojo> list = jsonService.getPoList(InitConst.KVM_EBS_VM_LIST, null, null, VmEBSPojo.class, vmId);
        return list;
    }


    public List<VmEBSPojo> getVMEBS(String userId,String tenantId,String vmId,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<VmEBSPojo> list = jsonService.getPoList(InitConst.KVM_EBS_VM_LIST, null, null,Region, VmEBSPojo.class, vmId);
        return list;
    }

    public void attachEBS(String userId,String tenantId,String ebs_id,String vm_id,String device) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("ebs_id", ebs_id);
        map.put("vm_id",vm_id);
        map.put("device",device);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_EBS_ATTACH,null,null,null,requestBody);
    }


    public void attachEBS(String userId,String tenantId,String ebs_id,String vm_id,String device,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("ebs_id", ebs_id);
        map.put("vm_id",vm_id);
        map.put("device",device);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_EBS_ATTACH,null,null,Region,null,requestBody);
    }

    public void detachEBS(String userId,String tenantId,String ebs_id,String vm_id) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("ebs_id", ebs_id);
        map.put("vm_id",vm_id);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_EBS_DETACH, null, null, null, requestBody);
    }


    public void detachEBS(String userId,String tenantId,String ebs_id,String vm_id,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("ebs_id", ebs_id);
        map.put("vm_id",vm_id);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_EBS_DETACH, null, null,Region, null, requestBody);
    }

}
