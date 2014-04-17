package com.ksyun.vm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.OpenStackResult;
import com.ksyun.vm.pojo.lbs.HealthPOJO;
import com.ksyun.vm.pojo.lbs.MemberPOJO;
import com.ksyun.vm.pojo.lbs.PoolPOJO;
import com.ksyun.vm.pojo.lbs.SessionPersistencePOJO;
import com.ksyun.vm.pojo.lbs.VipPOJO;
import com.ksyun.vm.utils.InitConst;

/**
 * LBS管理的Service
 * 
 * @author XueQi
 * 
 */
@Service
public class LBSService {
	@Autowired
	private JSONService jsonService;

	/**
	 * 获取用户所有的POOL 即负载均衡
	 * 
	 * @param userId
	 * @param tenantId
	 * @return
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public List<PoolPOJO> getPools(String userId, String tenantId)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		List<PoolPOJO> list = jsonService.getPoList(
				InitConst.KVM_LBS_POOL_LIST, null, null, PoolPOJO.class);
		return list;
	}

	/**
	 * 获取用户所有的VIP 即规则
	 * 
	 * @param userId
	 * @param tenantId
	 * @return
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public List<VipPOJO> getVips(String userId, String tenantId)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		List<VipPOJO> list = jsonService.getPoList(InitConst.KVM_LBS_VIP_LIST,
				null, null, VipPOJO.class);
		return list;
	}

	/**
	 * 获取所有用户的Member 已负载主机
	 * 
	 * @param userId
	 * @param tenantId
	 * @return
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public List<MemberPOJO> getMembers(String userId, String tenantId)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		List<MemberPOJO> list = jsonService.getPoList(
				InitConst.KVM_LBS_MEMBER_LIST, null, null, MemberPOJO.class);
		return list;
	}

	/**
	 * 获取用户所有的健康检查
	 * 
	 * @param userId
	 * @param tenantId
	 * @return
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public List<HealthPOJO> getHealths(String userId, String tenantId)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		List<HealthPOJO> list = jsonService.getPoList(
				InitConst.KVM_LBS_HEALTH_LIST, null, null, HealthPOJO.class);
		return list;
	}

	/**
	 * 查询指定id的pool
	 * 
	 * @param userId
	 * @param tenantId
	 * @param poolId
	 * @return
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public PoolPOJO getPool(String userId, String tenantId, String poolId)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		PoolPOJO pojo = jsonService.poGet(InitConst.KVM_LBS_POOL, null, null,
				PoolPOJO.class, poolId);
		return pojo;
	}

	/**
	 * 创建POOL
	 * 
	 * @param userId
	 * @param tenantId
	 * @param poolName
	 * @param type
	 * @param egress
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void addPool(String userId, String tenantId, String poolName,
			String type, String egress) throws NoTokenException,
			ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", poolName);
		map.put("egress", egress);
		map.put("network_type", type);
		map.put("is_req_body", "true");
		String requestBody = JSONObject.toJSONString(map);
		jsonService.poPost(InitConst.KVM_LBS_POOL_ADD, null, null, null,
				requestBody);
	}

	/**
	 * 创建VIP 规则
	 * 
	 * @param userId
	 * @param tenantId
	 * @param name
	 * @param protocol
	 * @param protocol_port
	 * @param lb_method
	 * @param pool_id
	 * @param session_persistence
	 * @throws NoTokenException
	 * @throws ErrorCodeException
	 */
	public void addVip(String userId, String tenantId, String name,
			String protocol, String protocol_port, String lb_method,
			String pool_id, SessionPersistencePOJO session_persistence)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("protocol", protocol);
		map.put("protocol_port", protocol_port);
		map.put("lb_method", lb_method);
		map.put("pool_id", pool_id);
		map.put("session_persistence", session_persistence);
		map.put("is_req_body", "true");
		String requestBody = JSONObject.toJSONString(map);
		jsonService.poPost(InitConst.KVM_LBS_VIP_ADD, null, null, null,
				requestBody);
	}

	/**
	 * 添加Member 负载主机
	 * 
	 * @param userId
	 * @param tenantId
	 * @param address
	 * @param protocol_port
	 * @param vip_id
	 * @param weight
	 * @param vm_id
	 * @throws NoTokenException
	 * @throws ErrorCodeException
	 */
	public void addMember(String userId, String tenantId, String address,
			String protocol_port, String vip_id, String weight, String vm_id)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		Map<String, String> map = new HashMap<>();
		map.put("address", address);
		map.put("protocol_port", protocol_port);
		map.put("vip_id", vip_id);
		map.put("weight", weight);
		map.put("vm_id", vm_id);
		map.put("is_req_body", "true");
		String requestBody = JSONObject.toJSONString(map);
		jsonService.poPost(InitConst.KVM_LBS_MEMBER_ADD, null, null, null,
				requestBody);
	}

}
