package com.ksyun.payment.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ksyun.payment.common.KvmBean;
import com.ksyun.payment.common.ProtocolOrderBean;
import com.ksyun.payment.dto.OrderDto;
import com.ksyun.payment.dto.UserDto;


/**
 * service类： 用户管理用户订单，暂时取消DAO层
 * @author ZhangYanchun
 */
public interface IOrderService  {
	/**
	 * 保存订单，并返回订单编码
	 * @param order
	 * @return
	 */
	void saveOrder(OrderDto order);
	
	/**
	 * 根据map的查询条件，查询所有订单信息（分页）
	 * @param params
	 * @return
	 */
	List<OrderDto> getAllOrders(Map<String, Object> params, String sortString);
	
	/**
	 * 根据主键查询订单信息
	 * @param orderId
	 * @return
	 */
	OrderDto getOrderById(String orderId);
	/**
	 * 撤销订单
	 * @param OrderId
	 */
	void cancelOrder(String orderId);
	
	/**
	 * 查询用户ks3的订单，
	 * @param userId
	 * @return
	 */
	OrderDto findKs3Order(long userId);
	/**
	 * 支付订单
	 * @param ksi
	 * @param totalPrice
	 * @param incrSerialNum
	 * @return
	 */
	int payOrder(UserDto user, Map<String, KvmBean> orderItems, BigDecimal totalCost, BigDecimal realCost, String incrSerialNum);
	
	/**
	 * 更新订单的产品ID （后台）
	 * @param orderId
	 * @param productId
	 */
	void updateOrderProduct(String orderId, String productId);
	
	/**
	 * 延长有效期（后台 试用订单）
	 * @param OrderDto
	 * @param ServiceEndTime
	 */
	void updateApplicationOrderServiceEndTime(String orderId, int day);
	
	/**
	 * 查询订单信息
	 * @param orderId
	 * @return
	 */
	OrderDto findOrderById(String orderId);
	
	/**
	 * 协议订单保存
	 * @param bean
	 * @param kvmOrderId
	 * @param ebsOrderId
	 * @param cashSerial
	 */
	void saveProtocolOrder(UserDto user, ProtocolOrderBean bean, String kvmOrderId, String ebsOrderId, String cashSerial);

}
