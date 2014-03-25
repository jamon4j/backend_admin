package com.ksyun.payment.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ICouponDao<CouponDto> extends IBaseDao<CouponDto, Long> {
	/**
	 * 根据条件查询代金券信息
	 * @param params
	 * @param sortString
	 * @return
	 */
	List<CouponDto> findAllByUser(Map<String, Object> params, String sortString);
	
	/**
	 * 返回用户优惠券账户总余额
	 * @param user_id
	 * @return
	 */
	BigDecimal getCouponBalance(long userId);
	
	/**
	 * 返回用户优惠券账户总余额
	 * @param user_id
	 * @return
	 */
	BigDecimal getCouponBalance(long userId, short productType);
	
	/**
	 * 更新代金券金额
	 * @param coupon_id
	 * @param balance
	 */
	void updateBalance(CouponDto coupon, double balance);
	
	/**
	 * 查询所有可用的代金券列表
	 * @param params
	 * @return
	 */
	List<CouponDto> getValidCoupon(long userId, short productType);
	
	/**
	 * 扣除代金券费用操作
	 * @param feeMaps <coupon_id, 需要扣除的金额>
	 */
	void updateBalance(Map<Long, BigDecimal> feeMaps);
	
	/**
	 * 查询代金券信息，（后台管理）
	 * @param params
	 * @return
	 */
	List<CouponDto> getAllCoupons(Map<String, Object> params);
	
	/**
	 * 根据代金券批号返回代金券列表
	 * @param params
	 * @return
	 */
	List<CouponDto> getAllCouponsByBatchNo(Map<String, Object> params);
	
}
