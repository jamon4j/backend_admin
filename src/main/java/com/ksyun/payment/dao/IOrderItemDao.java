package com.ksyun.payment.dao;


/**
 * 订单明细类  （此主键为联合主键，暂时以String代替，因为此模块没有按照ID查询的功能）
 * @author ZhangYanchun
 * @param <OrderItemDto>
 * @date  2013-08-29
 */
public interface IOrderItemDao<OrderItemDto> extends IBaseDao<OrderItemDto, String> {
	
	

}
