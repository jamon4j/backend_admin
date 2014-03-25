package com.ksyun.payment.dao;

import java.util.List;
/**
 * 优惠券消费明细
 * @author ZhangYanchun
 * @param <CouponDetailDto>
 * @param <Long>
 */
public interface ICouponDetailDao<CouponDetailDto> extends IBaseDao<CouponDetailDto, Long> {
	/**
	 * 根据优惠券ID返回此优惠券所有消费明细
	 * @param couponId
	 * @return
	 */
	List<CouponDetailDto> findByCoupon(long couponId);
    
	
}
