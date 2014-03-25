package com.ksyun.payment.dao;

import java.util.List;
import java.util.Map;


/**
 * 申请虚拟机使用DAO
 * @author ZhangYanchun
 * @date 2013-10-28 
 */
public interface IKvmApplicationDao<KvmApplicationDto> extends IBaseDao<KvmApplicationDto, Long> {
	
	/**
	 * 根据用户ID返回用户kvm申请信息
	 * @param userId
	 * @return
	 */
	KvmApplicationDto findByUser(long userId);
	
	/**
	 * 获取所有的使用申请（后台管理）
	 * @param params
	 * @return
	 */
	List<KvmApplicationDto> getAllApplications(Map<String, Object> params);
	/**
	 * 更新状态(后台)
	 * @param id
	 */
	void updateStatus(long id);
	
	/**
	 * 更新OS(后台)
	 * @param id
	 */
	void updateKvmOps(long id,String opsId);

}
