package com.ksyun.payment.dao.impl;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IOrderItemDao;
/**
 * 产品订单条目实现类
 * @author ZhangYanchun
 * @param <OrderItemDto>
 * @date  2013-08-29
 */
@Repository
public class OrderItemDao<OrderItemDto> extends BaseDao<OrderItemDto, String> implements IOrderItemDao<OrderItemDto>{

	
}
