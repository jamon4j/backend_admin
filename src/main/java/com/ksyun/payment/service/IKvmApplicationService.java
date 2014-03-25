package com.ksyun.payment.service;

import java.util.List;
import java.util.Map;

import com.ksyun.payment.dto.KvmApplicationDto;

/**
 * 试用申请服务接口
 * @author ZhangYanchun
 * @date   2013-10-28
 */
public interface IKvmApplicationService {
	/**
	 * 保存申请表单
	 * @param application
	 */
	void saveApplication(KvmApplicationDto application);
	
	/**
	 * 根据用户ID获取kvm使用申请
	 * @param userId
	 * @return
	 */
	KvmApplicationDto getByUser(long userId);
	
	/**
	 * 获取所有试用信息(后台管理)
	 * @param params
	 * @return
	 */
	List<KvmApplicationDto> getAllApplications(Map<String, Object> params);
	
	/**
	 * 根据ID返回试用信息
	 * @param id
	 * @return
	 */
	KvmApplicationDto findById(long id);
	/**
	 * 更新状态（后台）
	 * @param id
	 */
	void updateStatus(long id);
	
	/**
	 * 更新OS（后台）
	 * @param id
	 */
	void updateKvmOps(long id,String opsId);
	

}
