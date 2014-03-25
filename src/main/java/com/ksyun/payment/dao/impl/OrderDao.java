package com.ksyun.payment.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IOrderDao;

/**
 * 订单 Dao实现类
 * @author ZhangYanchun
 * @param <OrderDto>
 * @date  2013-08-30
 */
@Repository
public class OrderDao<OrderDto> extends BaseDao<OrderDto, String> implements IOrderDao<OrderDto>  {

	@Override
	public List<OrderDto> findAllOrder(Map<String, Object> params, String sortString) {
		
		return find("findAllByUser", params, sortString);
	}

	@Override
	public void updateOrderStatus(String orderId, short status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("status", status);
		sqlSession.update(String.format("%s.updateOrderStatus", nameSpace), params);
		
	}

	@Override
	public OrderDto findKs3OrderByUserId(long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("productType", 2);
		
		return sqlSession.selectOne(String.format("%s.findByUserId", nameSpace), params);
	}

	@Override
	public void updateOrderProduct(String orderId, String productId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("productId", productId);
		sqlSession.update(String.format("%s.updateOrderProduct", nameSpace), params);
		
	}

	@Override
	public void updateApplicationOrderServiceEndTime(String orderId, Date serviceEndTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("serviceEndTime", serviceEndTime);
		sqlSession.update(String.format("%s.updateOrderServiceEndTime", nameSpace), params);
		
	}
	

}
