package com.ksyun.vm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.payment.common.AppConfig;
import com.ksyun.payment.common.PaymentCommonUtils;
import com.ksyun.payment.common.SmipleJsonService;
import com.ksyun.payment.dict.NetUpdateMessageDict;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.vm.ImagePojo;
import com.ksyun.vm.pojo.vm.VNC;
import com.ksyun.vm.pojo.vm.VmPojo;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.enumeration.EnumEditVm;

import javax.swing.plaf.synth.Region;

/**
 * User: liuchuandong Date: 13-10-14 Time: 下午1:37 Func:
 */
@Service
public class VmService {
	@Autowired
	private JSONService jsonService;

	public List<VmPojo> getVms(String userId, String tenantId)
			throws ErrorCodeException, NoTokenException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		List<VmPojo> list = jsonService.getPoList(InitConst.KVM_VM_LIST, null,
				null, VmPojo.class);
		return list;
	}


    public List<VmPojo> getVms(String userId, String tenantId,String Region)
            throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<VmPojo> list = jsonService.getPoList(InitConst.KVM_VM_LIST, null,
                null,Region, VmPojo.class);
        return list;
    }



	public List<VmPojo> getVmsAll() throws ErrorCodeException, NoTokenException {
		// jsonService.setId(userId);
		// jsonService.setTenantId(tenantId);
		List<VmPojo> list = jsonService.getPoList(InitConst.KVM_VM_LIST_ALL,
				InitConst.ADMIN, InitConst.PASSWORD, VmPojo.class);
		return list;
	}



    public List<VmPojo> getVmsAll(String Region) throws ErrorCodeException, NoTokenException {
        // jsonService.setId(userId);
        // jsonService.setTenantId(tenantId);
        List<VmPojo> list = jsonService.getPoList(InitConst.KVM_VM_LIST_ALL,
                InitConst.ADMIN, InitConst.PASSWORD,Region, VmPojo.class);
        return list;
    }


	/**
	 * 获取VNC信息
	 * 
	 * @param vmId
	 * @return
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public VNC getVNC(String vmId) throws ErrorCodeException, NoTokenException {
		Map<String, String> map = new HashMap<>();
		map.put("vm_id", vmId);
		String requestBody = JSONObject.toJSONString(map);
		VNC vnc = jsonService.poPost(InitConst.KVM_VNC, InitConst.ADMIN,
				InitConst.PASSWORD, VNC.class, requestBody);
		return vnc;
	}


    public VNC getVNC(String vmId,String Region) throws ErrorCodeException, NoTokenException {
        Map<String, String> map = new HashMap<>();
        map.put("vm_id", vmId);
        String requestBody = JSONObject.toJSONString(map);
        VNC vnc = jsonService.poPost(InitConst.KVM_VNC, InitConst.ADMIN,
                InitConst.PASSWORD,Region, VNC.class, requestBody);
        return vnc;
    }

	public List<ImagePojo> getImages(String userId, String tenantId)
			throws ErrorCodeException, NoTokenException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		List<ImagePojo> list = jsonService.getPoList(InitConst.KVM_IMAGE_LIST,
				null, null, ImagePojo.class);
		return list;
	}


    public List<ImagePojo> getImages(String userId, String tenantId,String Region)
            throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<ImagePojo> list = jsonService.getPoList(InitConst.KVM_IMAGE_LIST,
                null, null,Region, ImagePojo.class);
        return list;
    }


	public void editVm(String vmid, String action, String tenantId,
                        String userId, String rootpwd) throws ErrorCodeException,
            NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("vm_id", vmid);
        String requestBody = JSONObject.toJSONString(map);
        if (action.equals(EnumEditVm.restart.value())) {
            jsonService.poPost(InitConst.KVM_VM_RESTART, null, null, null,
                    requestBody);
        } else if (action.equals(EnumEditVm.changepwd.value())) {
            map.put("rootpwd", rootpwd);
            requestBody = JSONObject.toJSONString(map);
            jsonService.poPost(InitConst.KVM_VM_PWD, null, null, null,
                    requestBody);
        } else if (action.equals(EnumEditVm.terminal.value())) {
            jsonService.poDelete(InitConst.KVM_VM_DELETE, null, null, vmid);
        } else if (action.equals(EnumEditVm.stop.value())) {
            jsonService.poPost(InitConst.KVM_VM_STOP, null, null, null,
                    requestBody);
        } else if (action.equals(EnumEditVm.start.value())) {
            jsonService.poPost(InitConst.KVM_VM_START, null, null, null,
                    requestBody);
        }
    }

    public void editVm(String vmid, String action, String tenantId,
                       String userId, String rootpwd,String Region) throws ErrorCodeException,
            NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("vm_id", vmid);
        String requestBody = JSONObject.toJSONString(map);
        if (action.equals(EnumEditVm.restart.value())) {
            jsonService.poPost(InitConst.KVM_VM_RESTART, null, null,Region, null,
                    requestBody);
        } else if (action.equals(EnumEditVm.changepwd.value())) {
            map.put("rootpwd", rootpwd);
            requestBody = JSONObject.toJSONString(map);
            jsonService.poPost(InitConst.KVM_VM_PWD, null, null, Region,null,
                    requestBody);
        } else if (action.equals(EnumEditVm.terminal.value())) {
            jsonService.poDelete(InitConst.KVM_VM_DELETE, null, null,Region, vmid);
        } else if (action.equals(EnumEditVm.stop.value())) {
            jsonService.poPost(InitConst.KVM_VM_STOP, null, null,Region, null,
                    requestBody);
        } else if (action.equals(EnumEditVm.start.value())) {
            jsonService.poPost(InitConst.KVM_VM_START, null, null,Region, null,
                    requestBody);
        }
    }


	public void createVm(String tenantId, String userId, String name,
			String imageRef, String count, String securityGroups,
			String adminPass, String onEbs, String zone, String vcpu,
			String network, String rootDisk, String ram)
			throws ErrorCodeException, NoTokenException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		List<String> security_groups = new ArrayList<>();
		String[] groups = securityGroups.split(",");
		for (String group : groups) {
			security_groups.add(group);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("image", imageRef);
		map.put("instance_count", count);
		map.put("security_groups", security_groups);
		map.put("rootpwd", adminPass);
		map.put("memory", ram);
		map.put("vcpu", vcpu);
		map.put("root_disk", rootDisk);
		map.put("network_bandwidth", network);
		if (onEbs != null && !onEbs.equals("")) {
			map.put("boot_on_ebs", onEbs);
		}
		if (zone != null && !zone.equals("")) {
			map.put("zone", zone);
		}
		String requestBody = JSONObject.toJSONString(map);
		jsonService.poPost(InitConst.KVM_VM_CREATE, null, null, null,
				requestBody);
	}

    public void createVm(String tenantId, String userId, String name,
                         String imageRef, String count, String securityGroups,
                         String adminPass, String onEbs, String zone, String vcpu,
                         String network, String rootDisk, String ram,String Region)
            throws ErrorCodeException, NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        List<String> security_groups = new ArrayList<>();
        String[] groups = securityGroups.split(",");
        for (String group : groups) {
            security_groups.add(group);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("image", imageRef);
        map.put("instance_count", count);
        map.put("security_groups", security_groups);
        map.put("rootpwd", adminPass);
        map.put("memory", ram);
        map.put("vcpu", vcpu);
        map.put("root_disk", rootDisk);
        map.put("network_bandwidth", network);
        if(Region.equals("RegionOne")) {
            if (onEbs != null && !onEbs.equals("")) {
                map.put("boot_on_ebs", onEbs);
            }
        }
        if (zone != null && !zone.equals("")) {
            map.put("zone", zone);
        }
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_VM_CREATE, null, null, Region,null,
                requestBody);
    }


	public void reset(String tenantId, String userId, String vm_id,
			String password, String image_id) throws ErrorCodeException,
			NoTokenException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		Map<String, String> map = new HashMap<>();
		if (image_id != null && !image_id.equals("")) {
			map.put("image_id", image_id);
		}
		map.put("vm_id", vm_id);
		map.put("password", password);
		String requestBody = JSONObject.toJSONString(map);
		jsonService.poPost(InitConst.KVM_VM_RESET, null, null, null,
				requestBody);
	}

    public void reset(String tenantId, String userId, String vm_id,
                      String password, String image_id,String Region) throws ErrorCodeException,
            NoTokenException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        if (image_id != null && !image_id.equals("")) {
            map.put("image_id", image_id);
        }
        map.put("vm_id", vm_id);
        map.put("password", password);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_VM_RESET, null, null, null,
                requestBody);
    }



    /**
	 * 更新带宽
	 * 
	 * @param tenantId
	 * @param userId
	 * @param vm_id
	 * @param new_brand
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void updateBrand(String tenantId, String userId, String vm_id,
			String new_brand) throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		Map<String, String> map = new HashMap<>();
		map.put("vm_id", vm_id);
		map.put("egress", new_brand);
		String requestBody = JSONObject.toJSONString(map);
		jsonService.poPost(InitConst.KVM_BANDWIDTH, null, null, null,
				requestBody);
	}

    public void updateBrand(String tenantId, String userId, String vm_id,
                            String new_brand,String Region) throws NoTokenException, ErrorCodeException {
        jsonService.setId(userId);
        jsonService.setTenantId(tenantId);
        Map<String, String> map = new HashMap<>();
        map.put("vm_id", vm_id);
        map.put("egress", new_brand);
        String requestBody = JSONObject.toJSONString(map);
        jsonService.poPost(InitConst.KVM_BANDWIDTH, null, null, null,
                requestBody);
    }

	/**
	 * 带宽升级计费
	 * 
	 * @param userId
	 * @param vm_id
	 * @param new_brand
	 * @param isNeedPay
	 * @return
	 * @throws ErrorCodeException
	 */
	public NetUpdateMessageDict updateBrandAndPay(String userId, String vm_id,
			String new_brand, String isNeedPay) throws ErrorCodeException {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("productId", vm_id);
		map.put("net", new_brand);
		String signNew = PaymentCommonUtils.getSign(map, AppConfig.AES_KEY);
		map.put("isNeedPay", isNeedPay);
		map.put("sign", signNew);
		String requestBody = JSONObject.toJSONString(map);
		NetUpdateMessageDict msg = SmipleJsonService.post(
				InitConst.ICONSOLE_UPDATE_EGRESS, requestBody,
				NetUpdateMessageDict.class);
		return msg;
	}
}
