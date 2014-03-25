package com.ksyun.payment.dao;

/**
 * kvm库存管理DAO
 * @author ZhangYanchun
 * @param <KvmStockDto>
 * @date 2013-12-05
 */
public interface IKvmStockDao<KvmStockDto> extends IBaseDao<KvmStockDto, String> {
	/**
	 * 根据kvm类型更新库存信息(amount - count 操作)
	 * @param kvmType
	 * @param count
	 */
	void updateAmount(String kvmType, int count);
	
	/**
	 * 更新整个库存操作
	 * @param kvmType
	 * @param amount
	 */
	void updateTotalAmount(String kvmType, int amount);
	
    
}
