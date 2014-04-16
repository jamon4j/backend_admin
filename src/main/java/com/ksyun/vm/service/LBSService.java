package com.ksyun.vm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.lbs.PoolPOJO;
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

}
