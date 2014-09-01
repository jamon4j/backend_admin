package com.ksyun.vm.service;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.snapshot.SnapshotEBSPojo;
import com.ksyun.vm.pojo.snapshot.SnapshotVmPojo;
import com.ksyun.vm.utils.InitConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: liuchuandong
 * Date: 13-10-12
 * Time: 下午6:13
 * Func:
 */
@Service
public class SnapshotService {

    @Autowired
    private JSONService jsonService;

    public List<SnapshotVmPojo> getVmSnapshots(String userId,String tenantId) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<SnapshotVmPojo> list = jsonService.getPoList(InitConst.KVM_SNAPSHOT_VM_LIST,null,null,SnapshotVmPojo.class);
        return list;
    }

    public List<SnapshotVmPojo> getVmSnapshots(String userId,String tenantId,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<SnapshotVmPojo> list = jsonService.getPoList(InitConst.KVM_SNAPSHOT_VM_LIST,null,null,Region,SnapshotVmPojo.class);
        return list;
    }


    public List<SnapshotEBSPojo> getEBSSnapshots(String userId,String tenantId) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<SnapshotEBSPojo> list = jsonService.getPoList(InitConst.KVM_SNAPSHOT_EBS_LIST, null, null, SnapshotEBSPojo.class);
        return list;
    }

    public List<SnapshotEBSPojo> getEBSSnapshots(String userId,String tenantId,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<SnapshotEBSPojo> list = jsonService.getPoList(InitConst.KVM_SNAPSHOT_EBS_LIST, null, null, Region,SnapshotEBSPojo.class);
        return list;
    }



    public void addVMSnapshot(String userId,String tenantId,String vmId,String snanshotName) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("vm_id", vmId);
        map.put("name", snanshotName);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_SNAPSHOT_VM_CREATE,null,null,null,requestBody);
    }


    public void addVMSnapshot(String userId,String tenantId,String vmId,String snanshotName,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("vm_id", vmId);
        map.put("name", snanshotName);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_SNAPSHOT_VM_CREATE,null,null,Region,null,requestBody);
    }
    public void delVMSnapshot(String userId,String tenantId,String snapshotId) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        jsonService.poDelete(InitConst.KVM_SNAPSHOT_VM_DELETE, null, null, snapshotId);
    }


    public void delVMSnapshot(String userId,String tenantId,String snapshotId,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        jsonService.poDelete(InitConst.KVM_SNAPSHOT_VM_DELETE, null, null,Region, snapshotId);
    }

    public void addEBSSnapshot(String userId,String tenantId,String ebsId,String name,String desc) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("ebs_id", ebsId);
        map.put("name", name);
        map.put("description", desc);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_SNAPSHOT_EBS_CREATE,null,null,null,requestBody);
    }

    public void addEBSSnapshot(String userId,String tenantId,String ebsId,String name,String desc,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("ebs_id", ebsId);
        map.put("name", name);
        map.put("description", desc);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_SNAPSHOT_EBS_CREATE,null,null,Region,null,requestBody);
    }

    public void delEBSSnapshot(String userId,String tenantId,String snapshotId) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        jsonService.poDelete(InitConst.KVM_SNAPSHOT_EBS_DELETE, null, null, snapshotId);
    }

    public void delEBSSnapshot(String userId,String tenantId,String snapshotId,String Region) throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        jsonService.poDelete(InitConst.KVM_SNAPSHOT_EBS_DELETE, null, null,Region, snapshotId);
    }
}
