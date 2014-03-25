package com.ksyun.payment.dao;

import java.util.List;
import java.util.Map;

/**
 * 账单DAO接口
 * @author ZhangYanchun
 * @param <BillDto>
 * @date  2013-08-20
 */
public interface IBillDao<BillDto> extends IBaseDao<BillDto, Long>{
	/**
	 * 更新账单状态 0 未支付  1 支付完成
	 * @param baseBillId   账单ID
	 * @param payStatus    支付状态
	 * @param payType      支付方式
	 */
	void updateStatus(long baseBillId, short payStatus, short payType);
	/**
	 * 查询最近更新的一个账单信息
	 * @param userId
	 * @return
	 */
	BillDto findLatestBill(long userId);
	
	/**
	 * 查询账单列表
	 * @param params
	 * @param sortString
	 * @return
	 */
	List<BillDto> findAllBill(Map<String, Object> params, String sortString);
	
	/**
	 * 查询所有kvm和Ebs的账单（后台查询）
	 * @param params
	 * @param sortString
	 * @return
	 */
	List<BillDto> findAllKvmAndEbsBill(Map<String, Object> params, String sortString);

}
