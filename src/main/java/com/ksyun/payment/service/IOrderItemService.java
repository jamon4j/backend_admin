package com.ksyun.payment.service;

import java.util.List;
import java.util.Map;

import com.ksyun.payment.dict.ProductItemDict;
import com.ksyun.payment.dto.OrderItemDto;

/**
 * 订单明细服务类（主要提供产品明细的一些快照）
 * @author ZhangYanchun
 * @date   2013-08-29
 */
public interface IOrderItemService {
	
	/**
	 * 根据订单ID返回此订单产品及价格快照
	 * @param orderId
	 * @return
	 */
	Map<String, ProductItemDict> getAllOrderItems(String orderId);
	
	/**
	 * 查询订单明细（不缓存）
	 * @param orderId
	 * @return
	 */
	List<OrderItemDto> getAllOrderItemsNoCache(String orderId);

}
