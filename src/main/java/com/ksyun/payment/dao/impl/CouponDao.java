package com.ksyun.payment.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.ICouponDao;
/**
 * 代金券DAO
 * @author ZhangYanchun
 * @param <CouponDto>
 * @date 2013-08-15
 */
@Repository
public class CouponDao<CouponDto> extends BaseDao<CouponDto, Long> implements ICouponDao<CouponDto> {

	@Override
	public List<CouponDto> findAllByUser(Map<String, Object> params, String sortString) {
		return super.find("findAllByUser", params, sortString);
	}

	@Override
	public BigDecimal getCouponBalance(long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("isActive", 1);
		params.put("expireTime", new Date());
		params.put("productType", 0);
		return sqlSession.selectOne(String.format("%s.getCouponBalance", nameSpace), params);
	}

	@Override
	public void updateBalance(CouponDto coupon, double balance) {
		update("updateCouponBalance", coupon);
	}

	@Override
	public BigDecimal getCouponBalance(long userId, short productType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("isActive", 1);
		params.put("expireTime", new Date());
		params.put("productType", productType);
		return sqlSession.selectOne(String.format("%s.getCouponBalance", nameSpace), params);
	}

	@Override
	public List<CouponDto> getValidCoupon(long userId, short productType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("isActive", 1);
		params.put("expireTime", new Date());
		params.put("productType", productType);
		return sqlSession.selectList(String.format("%s.getValidCoupon", nameSpace), params);
	}

	@Override
	public void updateBalance(Map<Long, BigDecimal> feeMaps) {
		Set<Long> couponIds = feeMaps.keySet();
		for(long couponId : couponIds) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("couponId", couponId);
			params.put("subMoney", feeMaps.get(couponId));
			sqlSession.update(String.format("%s.subCouponBalance", nameSpace), params);
			
		}
		
	}

	@Override
	public List<CouponDto> getAllCoupons(Map<String, Object> params) {
		return super.find("getAllCoupons", params, "");
	}

	@Override
	public List<CouponDto> getAllCouponsByBatchNo(Map<String, Object> params) {
		return sqlSession.selectList(String.format("%s.getAllCouponsByBatchNo", nameSpace), params);
	}

	
}
