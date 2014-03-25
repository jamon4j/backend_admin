package com.ksyun.payment.dao.impl;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IOrderItemPriceRangeDao;
/**
 * Dao实现类
 * @author ZhangYanchun
 * @param <OrderItemPriceRangeDto>
 * @date  2013-08-29
 */
@Repository
public class OrderItemPriceRangeDao<OrderItemPriceRangeDto> extends BaseDao<OrderItemPriceRangeDto, Long> implements IOrderItemPriceRangeDao<OrderItemPriceRangeDto>{

}
