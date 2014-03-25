package com.ksyun.payment.service;

import java.util.List;

import com.ksyun.payment.dto.KvmStockDto;


/**
 * kvm库存服务类
 * @author ZhangYanchun
 * @date   2013-12-05
 */
public interface IKvmStockService {
	
	/**
	 * 通过虚机类型获取库存信息
	 * @param kvmType
	 * @return
	 */
	KvmStockDto getByType(String kvmType);
	
	/**
	 * 获取所有的库存信息
	 * @return
	 */
	List<KvmStockDto> getAllStock();
	
	/**
	 * 更新整个库存操作
	 * @param kvmType
	 * @param amount
	 */
	void updateTotalAmount(String kvmType, int amount);

}
