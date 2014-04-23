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
	 * 查询执行id的VIP
	 * 
	 * @param userId
	 * @param tenantId
	 * @param vipId
	 * @return
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public VipPOJO getVip(String userId, String tenantId, String vipId)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		VipPOJO pojo = jsonService.poGet(InitConst.KVM_LBS_VIP, null, null,
				VipPOJO.class, vipId);
		return pojo;
	}

	/**
	 * 查询指定id的member
	 * 
	 * @param userId
	 * @param tenantId
	 * @param memberId
	 * @return
	 * @throws NoTokenException
	 * @throws ErrorCodeException
	 */
	public MemberPOJO getMember(String userId, String tenantId, String memberId)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		MemberPOJO pojo = jsonService.poGet(InitConst.KVM_LBS_MEMBER, null,
				null, MemberPOJO.class, memberId);
		return pojo;
	}

	/**
	 * 查询指定id的Health
	 * 
	 * @param userId
	 * @param tenantId
	 * @param healthId
	 * @return
	 * @throws NoTokenException
	 * @throws ErrorCodeException
	 */
	public HealthPOJO getHealth(String userId, String tenantId, String healthId)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		HealthPOJO pojo = jsonService.poGet(InitConst.KVM_LBS_HEALTH, null,
				null, HealthPOJO.class, healthId);
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

	/**
	 * 添加健康检查
	 * 
	 * @param userId
	 * @param tenantId
	 * @param delay
	 * @param max_retries
	 * @param type
	 * @param timeout
	 * @param rise
	 * @param fall
	 * @param http_method
	 * @param url_path
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void addHealth(String userId, String tenantId, String delay,
			String max_retries, String type, String timeout, String rise,
			String fall, String http_method, String url_path)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		Map<String, String> map = new HashMap<>();
		// 健康检查的类型氛围TCP和HTTP，根据不同的类型，创建的时候会传递不同的参数
		map.put("delay", delay);
		map.put("max_retries", max_retries);
		map.put("timeout", timeout);
		map.put("rise", rise);
		map.put("fall", fall);
		if ("HTTP".equals(type)) {
			map.put("http_method", http_method);
			map.put("url_path", url_path);
		}
		map.put("type", type);
		map.put("admin_state_up", "true");
		map.put("is_req_body", "true");
		String requestBody = JSONObject.toJSONString(map);
		jsonService.poPost(InitConst.KVM_LBS_HEALTH_ADD, null, null, null,
				requestBody);
	}

	/**
	 * 删除健康检查
	 * 
	 * @param userId
	 * @param tenantId
	 * @param health_monitor_id
	 * @throws NoTokenException
	 * @throws ErrorCodeException
	 */
	public void deleteHealth(String userId, String tenantId,
			String health_monitor_id) throws NoTokenException,
			ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		jsonService.poDelete(InitConst.KVM_LBS_HEALTH_DELETE, null, null,
				health_monitor_id);
	}

	/**
	 * 删除负载主机
	 * 
	 * @param userId
	 * @param tenantId
	 * @param member_id
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void deleteMember(String userId, String tenantId, String member_id)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		jsonService.poDelete(InitConst.KVM_LBS_MEMEBER_DELETE, null, null,
				member_id);
	}

	/**
	 * 删除规则 vip
	 * 
	 * @param userId
	 * @param tenantId
	 * @param vip_id
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void deleteVip(String userId, String tenantId, String vip_id)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		jsonService.poDelete(InitConst.KVM_LBS_VIP_DELETE, null, null, vip_id);
	}

	/**
	 * 删除Pool
	 * 
	 * @param userId
	 * @param tenantId
	 * @param pool_id
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void deletePool(String userId, String tenantId, String pool_id)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		jsonService
				.poDelete(InitConst.KVM_LBS_POOL_DELETE, null, null, pool_id);
	}

	/**
	 * 规则与健康检查绑定
	 * 
	 * @param userId
	 * @param tenantId
	 * @param vip_id
	 * @param health_monitor_id
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void vipBindHealth(String userId, String tenantId, String vip_id,
			String health_monitor_id) throws NoTokenException,
			ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		Map<String, Object> map = new HashMap<>();
		map.put("health_monitor_id", health_monitor_id);
		String requestBody = JSONObject.toJSONString(map);
		jsonService.poPost(InitConst.KVM_LBS_HEALTH_BIND, null, null, null,
				requestBody, vip_id);
	}

	/**
	 * 规则与健康检查解除绑定
	 * 
	 * @param userId
	 * @param tenantId
	 * @param vip_id
	 * @param health_monitor_id
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void vipUnBindHealth(String userId, String tenantId, String vip_id,
			String health_monitor_id) throws NoTokenException,
			ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		jsonService.poDelete(InitConst.KVM_LBS_HEALTH_UNBIND, null, null,
				vip_id, health_monitor_id);
	}

	/**
	 * 更新Health
	 * 
	 * @param userId
	 * @param tenantId
	 * @param healthId
	 * @param timeout
	 * @param delay
	 * @param fall
	 * @param rise
	 * @param max_retries
	 * @param admin_state_up
	 * @param type
	 * @param url_path
	 * @param http_method
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void updateHealth(String userId, String tenantId, String healthId,
			String timeout, String delay, String fall, String rise,
			String max_retries, String admin_state_up, String type,
			String url_path, String http_method) throws NoTokenException,
			ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		// 更新timeout
		Map<String, String> timeout_map = new HashMap<>();
		timeout_map.put("timeout", timeout);
		String string = JSONObject.toJSONString(timeout_map);
		jsonService.poPut(InitConst.KVM_LBS_HEALTH_UPDATE_TIMEOUT, null, null,
				null, string, healthId);
		// 更新delay
		Map<String, String> delay_map = new HashMap<>();
		delay_map.put("delay", delay);
		jsonService.poPut(InitConst.KVM_LBS_HEALTH_UPDATE_DELAY, null, null,
				null, JSONObject.toJSONString(delay_map), healthId);
		// 更新max_retries
		Map<String, String> max_retries_map = new HashMap<>();
		max_retries_map.put("max_retries", max_retries);
		jsonService.poPut(InitConst.KVM_LBS_HEALTH_UPDATE_MAX_RETRIES, null,
				null, null, JSONObject.toJSONString(max_retries_map), healthId);
		// 要判断Type，TCP的时候不去更新url_path和http_method
		if ("HTTP".equals(type)) {
			// 更新http_method
			Map<String, String> http_method_map = new HashMap<>();
			http_method_map.put("http_method", http_method);
			jsonService.poPut(InitConst.KVM_LBS_HEALTH_UPDATE_HTTP_METHOD,
					null, null, null, JSONObject.toJSONString(http_method_map),
					healthId);
			// 更新url_path
			Map<String, String> url_path_map = new HashMap<>();
			url_path_map.put("url_path", url_path);
			jsonService
					.poPut(InitConst.KVM_LBS_HEALTH_UPDATE_URL_PATH, null,
							null, null, JSONObject.toJSONString(url_path_map),
							healthId);
		}
		// 更新rise
		Map<String, String> rise_map = new HashMap<>();
		rise_map.put("rise", rise);
		jsonService.poPut(InitConst.KVM_LBS_HEALTH_UPDATE_RISE, null, null,
				null, JSONObject.toJSONString(rise_map), healthId);
		// 更新fall
		Map<String, String> fall_map = new HashMap<>();
		fall_map.put("fall", fall);
		jsonService.poPut(InitConst.KVM_LBS_HEALTH_FALL, null, null, null,
				JSONObject.toJSONString(fall_map), healthId);
		// 更新open
		Map<String, String> open_map = new HashMap<>();
		open_map.put("open", admin_state_up);
		jsonService.poPut(InitConst.KVM_LBS_HEALTH_OPEN, null, null, null,
				JSONObject.toJSONString(open_map), healthId);
	}

	/**
	 * 更新Member
	 * 
	 * @param userId
	 * @param tenantId
	 * @param memberId
	 * @param weight
	 * @param admin_state_up
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void updateMember(String userId, String tenantId, String memberId,
			String weight, String admin_state_up) throws NoTokenException,
			ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		Map<String, String> weight_map = new HashMap<>();
		weight_map.put("weight", weight);
		jsonService.poPut(InitConst.KVM_LBS_MEMBER_UPDATE_WEIGHT, null, null,
				null, JSONObject.toJSONString(weight_map), memberId);
		Map<String, String> open_map = new HashMap<>();
		open_map.put("open", admin_state_up);
		jsonService.poPut(InitConst.KVM_LBS_MEMBER_UPDATE_OPEN, null, null,
				null, JSONObject.toJSONString(open_map), memberId);
	}

	/**
	 * 更新VIP
	 * 
	 * @param userId
	 * @param tenantId
	 * @param vipId
	 * @param name
	 * @param admin_state_up
	 * @param connection_limit
	 * @param cookie_name
	 * @param cookie_type
	 * @param cookie_timeout
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void updateVip(String userId, String tenantId, String vipId,
			String name, String admin_state_up, String connection_limit,
			String cookie_name, String cookie_type, String cookie_timeout)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		// 更新name
		Map<String, String> name_map = new HashMap<>();
		name_map.put("name", name);
		jsonService.poPut(InitConst.KVM_LBS_VIP_UPDATE_NAME, null, null, null,
				JSONObject.toJSONString(name_map), vipId);
		// 更新OPEN
		Map<String, String> open_map = new HashMap<>();
		open_map.put("open", admin_state_up);
		jsonService.poPut(InitConst.KVM_LBS_VIP_UPDATE_OPEN, null, null, null,
				JSONObject.toJSONString(open_map), vipId);
		// 更新connection_limit
		Map<String, String> connection_limit_map = new HashMap<>();
		connection_limit_map.put("connection_limit", connection_limit);
		jsonService.poPut(InitConst.KVM_LBS_VIP_UPDATE_CONNECTION_LIMIT, null,
				null, null, JSONObject.toJSONString(connection_limit_map),
				vipId);
		// 更新session_persistence
		SessionPersistencePOJO pojo = new SessionPersistencePOJO();
		pojo.setCookie_name(cookie_name);
		pojo.setTimeout(cookie_timeout);
		pojo.setType(cookie_type);
		Map<String, Object> session_persistence_map = new HashMap<>();
		session_persistence_map.put("session_persistence", pojo);
		jsonService.poPut(InitConst.KVM_LBS_VIP_UPDATE_SESSION_PERSISTENCE,
				null, null, null,
				JSONObject.toJSONString(session_persistence_map), vipId);
	}

	/**
	 * 更新POOL
	 * 
	 * @param userId
	 * @param tenantId
	 * @param poolId
	 * @param name
	 * @param egress
	 * @param admin_state_up
	 * @throws ErrorCodeException
	 * @throws NoTokenException
	 */
	public void updatePool(String userId, String tenantId, String poolId,
			String name, String egress, String admin_state_up)
			throws NoTokenException, ErrorCodeException {
		jsonService.setId(userId);
		jsonService.setTenantId(tenantId);
		// 更新name
		Map<String, String> name_map = new HashMap<>();
		name_map.put("name", name);
		jsonService.poPut(InitConst.KVM_LBS_POOL_UPDATE_NAME, null, null, null,
				JSONObject.toJSONString(name_map), poolId);
		// 更新egress
		Map<String, String> egress_map = new HashMap<>();
		egress_map.put("egress", egress);
		jsonService.poPut(InitConst.KVM_LBS_POOL_UPDATE_EGRESS, null, null,
				null, JSONObject.toJSONString(egress_map), poolId);
		// 更新open
		Map<String, String> open_map = new HashMap<>();
		open_map.put("open", admin_state_up);
		jsonService.poPut(InitConst.KVM_LBS_POOL_UPDATE_OPEN, null, null, null,
				JSONObject.toJSONString(open_map), poolId);
	}
}
