package com.ksyun.payment.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.ICouponDetailDao;

/**
 * 实现类
 * @author ZhangYanchun
 * @param <CouponDetailDto>
 * @param <Long>
 */
@Repository
public class CouponDetailDao<CouponDetailDto> extends BaseDao<CouponDetailDto, Long> implements ICouponDetailDao<CouponDetailDto> {

	@Override
	public List<CouponDetailDto> findByCoupon(long couponId) {
		return sqlSession.selectList(String.format("%s.findByCoupon", nameSpace), couponId);
	}

}
