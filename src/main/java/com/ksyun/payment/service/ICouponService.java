package com.ksyun.payment.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ksyun.payment.dto.CouponDetailDto;
import com.ksyun.payment.dto.CouponDto;

public interface ICouponService {
	/**
	 * 保存优惠券信息
	 * @param couponDto
	 */
	void saveCoupon(CouponDto couponDto);
	
	/**
	 * 查询用户优惠券列表
	 * @param user_id
	 * @return
	 */
	List<CouponDto> findAllCoupon(long userId);
	
	/**
	 * 获取用户优惠券余额
	 * @param user_id
	 * @return
	 */
	BigDecimal getCouponBalance(long userId);
	
	/**
	 * 根据用户ID和产品类型，查询某个用户适合此产品的代金券余额
	 * @param userId
	 * @param productType
	 * @return
	 */
	BigDecimal getCouponBalance(long userId, short productType);
	
	/**
	 * 根据优惠券ID查询此优惠券所有明细
	 * @param couponId
	 * @return
	 */
	List<CouponDetailDto> findAllCounponDetail(long couponId);
	
	/**
	 * 查询所有代金券信息 （后台）
	 * @param params
	 * @return
	 */
	List<CouponDto> getAllCoupons(Map<String, Object> params);

	/**
	 * 批量创建代金券
	 * @param coupon
	 * @param num
	 */
	void saveCoupon(CouponDto coupon, int num);
	
	/**
	 * 根据批号获取
	 * @param batchNo
	 * @return
	 */
	List<CouponDto> getAllCouponsByBatchNo(String batchNo);
	

}
