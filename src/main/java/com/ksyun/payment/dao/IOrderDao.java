package com.ksyun.payment.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 订单DAO 接口
 * @author ZhangYanchun
 * @param <OrderDto>
 * @date   2013-08-30
 */
public interface IOrderDao<OrderDto> extends IBaseDao<OrderDto, String> {
	
	/**
	 * 根据map查询条件查询所有订单，按照
	 * @param params
	 * @param sortString
	 * @return
	 */
	List<OrderDto> findAllOrder(Map<String, Object> params, String sortString);
	
	/**
	 * 根据订单ID更新订单状态
	 * @param orderId
	 * @param status
	 */
	void updateOrderStatus(String orderId, short status);
	
	/**
	 * 获取用户的ks3订单
	 * @param userId
	 * @return
	 */
	OrderDto findKs3OrderByUserId(long userId);
	
	/**
	 * 更新订单的产品ID  （后台接口）
	 * @param orderId
	 * @param productId
	 */
	void updateOrderProduct(String orderId, String productId);
	
	/**
	 * 根据订单ID更新订单的服务到期时间（只适用于试用订单）
	 * @param orderId
	 * @param serviceEndTime
	 */
	void updateApplicationOrderServiceEndTime(String orderId, Date serviceEndTime);
	
	
}
