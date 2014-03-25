package com.ksyun.payment.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ksyun.payment.dao.ICouponDao;
import com.ksyun.payment.dao.ICouponDetailDao;
import com.ksyun.payment.dto.CouponDetailDto;
import com.ksyun.payment.dto.CouponDto;
import com.ksyun.payment.service.ICouponService;

@Service
public class CouponService implements ICouponService {
	
	@Resource
	private ICouponDao<CouponDto> couponDao;
	
	@Resource
	private ICouponDetailDao<CouponDetailDto> couponDetailDao;

	@Override
	public void saveCoupon(CouponDto couponDto) {
		couponDao.save(couponDto);
		CouponDetailDto cdetail = new CouponDetailDto();
		cdetail.setCouponId(couponDto.getCouponId());
		cdetail.setUserId(couponDto.getUserId());
		cdetail.setDealStatus((short)1);
		cdetail.setDealType((short)1);
		cdetail.setIncomeMoney(couponDto.getSumMoney());
		cdetail.setPayMoney(BigDecimal.ZERO);
		cdetail.setBaseBillId(0);
		cdetail.setDealTime(new Date());
		couponDetailDao.save(cdetail);
	}

	@Override
	public List<CouponDetailDto> findAllCounponDetail(long couponId) {
		return couponDetailDao.findByCoupon(couponId);
	}
	
	@Override
	public List<CouponDto> findAllCoupon(long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String sortString = "coupon_id.desc";//如果你想排序的话逗号分隔可以排序多列

		List<CouponDto> list = couponDao.findAllByUser(params, sortString);
		
		//获得结果集条总数
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageList<CouponDto> pageList = (PageList)list;
		return pageList;
	}

	@Override
	public BigDecimal getCouponBalance(long userId) {
		return couponDao.getCouponBalance(userId);
	}

	@Override
	public BigDecimal getCouponBalance(long userId, short productType) {
		return couponDao.getCouponBalance(userId, productType);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CouponDto> getAllCoupons(Map<String, Object> params) {
		List<CouponDto> list = couponDao.getAllCoupons(params);
		return (PageList)list;
		
	}

	@Override
	public void saveCoupon(CouponDto coupon, int num) {
		for(int i = 0; i < num; i++) {
			String uuid =  UUID.randomUUID().toString();
			//uuid = uuid.replace("-", " ");
	        coupon.setActiveCode(uuid);
	        couponDao.save(coupon);
		}
		
	}

	@Override
	public List<CouponDto> getAllCouponsByBatchNo(String batchNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchNo", batchNo);
		return couponDao.getAllCouponsByBatchNo(params);
	}

}
