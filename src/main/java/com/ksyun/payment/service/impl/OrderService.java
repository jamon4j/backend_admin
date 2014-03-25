package com.ksyun.payment.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ksyun.payment.common.AppConfig;
import com.ksyun.payment.common.KvmBean;
import com.ksyun.payment.common.PaymentCommonUtils;
import com.ksyun.payment.common.ProtocolOrderBean;
import com.ksyun.payment.dao.IBillDao;
import com.ksyun.payment.dao.ICouponDao;
import com.ksyun.payment.dao.ICouponDetailDao;
import com.ksyun.payment.dao.IOrderDao;
import com.ksyun.payment.dao.IOrderItemDao;
import com.ksyun.payment.dao.IUserAccountDetailDao;
import com.ksyun.payment.dao.IUserDao;
import com.ksyun.payment.dao.IUserInfoDao;
import com.ksyun.payment.dto.BillDto;
import com.ksyun.payment.dto.CouponDetailDto;
import com.ksyun.payment.dto.CouponDto;
import com.ksyun.payment.dto.OrderDto;
import com.ksyun.payment.dto.OrderItemDto;
import com.ksyun.payment.dto.UserAccountDetailDto;
import com.ksyun.payment.dto.UserDto;
import com.ksyun.payment.dto.UserInfoDto;
import com.ksyun.payment.service.IOrderService;
import com.ksyun.tools.DateUtil;

/**
 * 订单服务实现
 * @author ZhangYanchun
 * @date   2013-08-29
 */
@Service
public class OrderService implements IOrderService {
	
	private static Logger logger = Logger.getLogger(OrderService.class);
	
	@Resource
	private IOrderDao<OrderDto> orderDao;
	
	@Resource
	private IOrderItemDao<OrderItemDto> orderItemDao;
	
	
	@Resource
	private ICouponDao<CouponDto> couponDao;
	
	@Resource
	private ICouponDetailDao<CouponDetailDto> couponDetailDao;
	
	@Resource
	private IUserDao<UserDto>   userDao;
	
	@Resource
	private IUserAccountDetailDao<UserAccountDetailDto> userAccountDetailDao;
	
	@Resource
	private IBillDao<BillDto>   billDao;
	
	@Resource
	private IUserInfoDao<UserInfoDto> userInfoDao;
	
	@Resource
	private AmqpTemplate amqpTemplate;
	
	private String queueName = "control_kvm_q";
	
	@Override
	public void saveOrder(OrderDto order) {
		orderDao.save(order);
	}

	@Override
	public List<OrderDto> getAllOrders(Map<String, Object> params, String sortString) {
		
		
		List<OrderDto> list = orderDao.findAllOrder(params, sortString);
		//获得结果集条总数
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageList<OrderDto> pageList = (PageList)list;
		return pageList;
	}

	@Override
	public OrderDto getOrderById(String orderId) {
		return orderDao.findById(orderId);
	}

	@Override
	public void cancelOrder(String orderId) {
		orderDao.updateOrderStatus(orderId, (short)-1);
	}

	@Override
	public OrderDto findKs3Order(long userId) {
		return orderDao.findKs3OrderByUserId(userId);
	}

	@Override
	public void updateOrderProduct(String orderId, String productId) {
		orderDao.updateOrderProduct(orderId, productId);
	}

	
	@Override
	public int payOrder(UserDto user, Map<String, KvmBean> orderItems, BigDecimal totalCost, BigDecimal realCost, String incrSerialNum) {
		
		long userId = user.getUserId();
		String email = user.getName();
		//生成订单
		Set<String> orderIds = orderItems.keySet();
		Date now = new Date();
		StringBuilder oids = new StringBuilder();
		List<String> jmsMsgs = new ArrayList<String>();
		Set<String> updateOrderIds = new HashSet<String>();
		for(String orderId : orderIds) {
			KvmBean item = orderItems.get(orderId);
			Date serviceEndTime = DateUtil.getDateEndTime(DateUtil.addDay(now, item.getDay()));
			int ret = saveKvmOrder(userId, orderId, now, serviceEndTime, item);
			String ebs_order_id = changeToEbsOrder(orderId);
			if(ret == 1) {
				oids.append(ebs_order_id).append(",");
				updateOrderIds.add(ebs_order_id);
			}
			oids.append(orderId).append(",");
			updateOrderIds.add(orderId);
			//发送开启消息
			String jmsMsg = String.format(AppConfig.START_KVM, orderId, DateUtil.getAllStrDate(serviceEndTime), item.getOs(), new Double(item.getMem() * 1024).intValue(), item.getCpu(), item.getNet(), ebs_order_id, item.getEbs(), DateUtil.getAllStrDate(serviceEndTime), userId, email, DateUtil.getAllStrDate(now));
			jmsMsgs.add(jmsMsg);
		}
		
		short productType = 1;
		//生成账单
		BillDto bill = new BillDto();
		bill.setProductType(productType);
		bill.setUserId(userId);
		
		bill.setTotalCost(totalCost);
		bill.setRealCost(realCost);
		bill.setPayTmie(now);
		bill.setChargeBeginTime(now);
		bill.setChargeEndTime(now);
		bill.setPayStatus((short)0);
		bill.setUserOrderId(oids.toString());
		billDao.save(bill);
		long baseBillId = bill.getBillId();
		//获取用户代金券总余额及现金账户总金额，看是否可以用于支付
		short payType = 1;
		if(realCost.compareTo(BigDecimal.ZERO) > 0) {
			payType = updateAccount(user, productType, realCost, baseBillId, incrSerialNum);
		}
		//更新账单为提交完成状态
		billDao.updateStatus(baseBillId, (short)1, payType);
		//更新订单状态
		for(String orderId : updateOrderIds) {
			orderDao.updateOrderStatus(orderId, (short)1);
		}
		//发送控制信息
		for(String jmsMsg : jmsMsgs) {
			amqpTemplate.convertAndSend(queueName, jmsMsg);
			logger.info(String.format("send to start kvm queue %s", jmsMsg));
		}
		return 0;	
		
	}
	
	/** 
	 * 保存订单（kvm订单需要拆单，）
	 * @param userId
	 * @param orderId
	 * @param serviceBeginTime
	 * @param serviceEndTime
	 * @param item
	 */
	private int saveKvmOrder(long userId, String orderId, Date serviceBeginTime, Date serviceEndTime, KvmBean item) {
		int ret = 0;
		OrderDto order = new OrderDto();
		order.setOrderType(item.getOrderType());
		order.setProductType((short)1);
		order.setOrderProductCount((short)1);
		order.setPromotionId(0);
		int ebsPrice = item.getEbsPrice();
		int realEbsPrice = ebsPrice;
		if(item.getOrderType() == 1) 
			realEbsPrice = 0;
		order.setTotalMoney(new BigDecimal(item.getPrice() - ebsPrice));
		order.setRealMoney(new BigDecimal(item.getRealPrice() - realEbsPrice));
		
		order.setServiceBeginTime(serviceBeginTime);
		order.setServiceEndTime(serviceEndTime);
		order.setUtime(serviceBeginTime);
		order.setStatus((short)0);
		order.setSubmitTime(serviceBeginTime);
		order.setUserId(userId);
		order.setOrderId(orderId);
		//保存订单
		orderDao.save(order);
		//保存订单明细
		OrderItemDto oitem = new OrderItemDto();
		oitem.setOrderId(orderId);
		
		//保存cup信息
		oitem.setItemNo("cpu");
		oitem.setItemName("cpu核数");
		oitem.setAmount(new BigDecimal(item.getCpu()));
		oitem.setUnit("个");
		oitem.setUnitCount(1);
		orderItemDao.save(oitem);
		
		//保存内存信息
		oitem.setItemNo("mem");
		oitem.setItemName("内存");
		oitem.setAmount(new BigDecimal(item.getMem()));
		oitem.setUnit("G");
		oitem.setUnitCount(1);
		orderItemDao.save(oitem);
		
		
		
		//带宽信息
		oitem.setItemNo("net");
		oitem.setItemName("网络带宽");
		oitem.setAmount(new BigDecimal(item.getNet()));
		oitem.setUnit("M");
		oitem.setUnitCount(1);
		orderItemDao.save(oitem);
		
		//操作系统
		oitem.setItemNo("ops");
		oitem.setItemName(item.getOs());
		oitem.setAmount(new BigDecimal(1));
		oitem.setUnit("个");
		oitem.setUnitCount(1);
		orderItemDao.save(oitem);
		
		int ebs = item.getEbs();
		if(ebs > 0){
			ret = 1;
			String ebs_order_id = changeToEbsOrder(orderId);
			order.setOrderId(ebs_order_id);
			order.setProductType((short)3);
			order.setTotalMoney(new BigDecimal(ebsPrice));
			
			order.setRealMoney(new BigDecimal(realEbsPrice));
			orderDao.save(order);
			
			//ebs信息
			oitem.setOrderId(ebs_order_id);
			oitem.setItemNo("ebs");
			oitem.setItemName("数据盘");
			oitem.setAmount(new BigDecimal(ebs));
			oitem.setUnit("G");
			oitem.setUnitCount(1);
			orderItemDao.save(oitem);
		}
		
		
		return ret;
	}
	/**
	 * 执行账户扣款操作
	 * @param user
	 * @param productType
	 * @param realCost
	 * @param billId
	 * @param incrSerialNum
	 * @return
	 */
	private short updateAccount(UserDto user, short productType, BigDecimal realCost, long billId, String incrSerialNum) {
		
		short payType = 1;
		long userId = user.getUserId();
		BigDecimal couponBalance = couponDao.getCouponBalance(userId, productType);
		//如果代金券余额大于需要支付的金额，则不用使用现金账户去支付，直接使用代金券支付
		if(couponBalance == null) couponBalance = BigDecimal.ZERO;
		if(couponBalance.compareTo(realCost) >= 0) {
			payType = 2;
			//查询所有可用代金券列表
			List<CouponDto> coupons = couponDao.getValidCoupon(userId, productType);
			//找出可扣款的代金券账户
			Map<Long, BigDecimal> feeMaps = PaymentCommonUtils.findFeeCoupon(realCost, coupons);
			//代金券进行扣费操作，并计入代金券明细
			couponDao.updateBalance(feeMaps);
			saveAllCouponDetail(userId, billId, feeMaps);
		} else {
			//计算用户现金账户余额
			BigDecimal cashBalance = user.getBalance();
			//设置为混合支付
			payType = 3;
			//查询可使用的代金券信息
			List<CouponDto> coupons = couponDao.getValidCoupon(userId, productType);
			if(coupons.size() == 0) {
				payType = 1;
			} else {
				//进行代金券账户的扣费操作（所有的代金券都将扣费为0）
				Map<Long, BigDecimal> feeMaps = PaymentCommonUtils.findFeeCoupon(coupons);
				couponDao.updateBalance(feeMaps);
				saveAllCouponDetail(userId, billId, feeMaps);
			}
			
			//剩余金额将使用现金账户支付
			BigDecimal remainderMoney = realCost.subtract(couponBalance);
			//更新现金账户余额
			UserDto updateUser = new UserDto();
			updateUser.setUserId(userId);
			cashBalance = cashBalance.subtract(remainderMoney);
			updateUser.setBalance(cashBalance);
			
			int arrearageDay = user.getArrearageDay();
			if(cashBalance.compareTo(BigDecimal.ZERO) >= 0) {
				updateUser.setArrearageDay(0);
			} else {
				updateUser.setArrearageDay(arrearageDay + 1);
			}
			userDao.updateBalance(updateUser);
			//增加现金账户明细
			UserAccountDetailDto cashDto = new UserAccountDetailDto();
			cashDto.setCaSerial(incrSerialNum);
			cashDto.setUserId(userId);
			cashDto.setDealType((short)3);
			cashDto.setBalance(cashBalance);
			cashDto.setIncomeMoney(BigDecimal.ZERO);
			cashDto.setPayMoney(remainderMoney);
			cashDto.setDealTime(new Date());
			cashDto.setBaseBillId(billId);
			cashDto.setStatus((short)1);
			userAccountDetailDao.save(cashDto);
		}
		return payType;
	}
	
	
	
	/**
	 * 报错所有的代金券明细
	 * @param userId
	 * @param baseBillId
	 * @param feeMaps
	 */
	private void saveAllCouponDetail(long userId, long baseBillId, Map<Long, BigDecimal> feeMaps) {
		Set<Long> coupons = feeMaps.keySet();
		Date now = new Date();
		for(long couponId : coupons) {
			CouponDetailDto couponDetail = new CouponDetailDto();
			couponDetail.setBaseBillId(baseBillId);
			couponDetail.setCouponId(couponId);
			couponDetail.setIncomeMoney(BigDecimal.ZERO);
			couponDetail.setPayMoney(feeMaps.get(couponId));
			couponDetail.setDealType((short) 2);
			couponDetail.setDealStatus((short)1);
			couponDetail.setDealTime(now);
			couponDetail.setUserId(userId);
			couponDetailDao.save(couponDetail);
		}
		
	}
	@Override
	public OrderDto findOrderById(String orderId) {
		return orderDao.findById(orderId);
	}
	/**
	 * 转变订单为ebs订单
	 * @param orderId
	 * @return
	 */
	private String changeToEbsOrder(String orderId) {
		StringBuilder sb = new StringBuilder();
		sb.append("3").append(orderId.substring(1));
		return sb.toString();
	}

	@Override
	public void saveProtocolOrder(UserDto user, ProtocolOrderBean bean, String kvmOrderId, String ebsOrderId, String cashSerial) {
		Date now = new Date();
		long userId = user.getUserId();
		//保存订单
		if(!"".equals(kvmOrderId)) {
			OrderDto order = new OrderDto();
			order.setOrderType((short)3);
			order.setProductType((short)1);
			order.setOrderProductCount((short)1);
			order.setPromotionId(0);
			
			order.setTotalMoney(bean.getKvmPrice());
			order.setRealMoney(bean.getKvmPrice());
			
			order.setServiceBeginTime(now);
			order.setServiceEndTime(bean.getKvmEndTime());
			order.setUtime(now);
			order.setStatus(bean.getStatus());
			order.setProductId(bean.getKvmId());
			order.setSubmitTime(now);
			order.setUserId(userId);
			order.setOrderId(kvmOrderId);
			//保存订单
			orderDao.save(order);
			//保存订单明细
			OrderItemDto oitem = new OrderItemDto();
			oitem.setOrderId(kvmOrderId);
			//保存cup信息
			oitem.setItemNo("cpu");
			oitem.setItemName("cpu核数");
			oitem.setAmount(new BigDecimal(bean.getCpu()));
			oitem.setUnit("个");
			oitem.setUnitCount(1);
			orderItemDao.save(oitem);
			//保存内存信息
			oitem.setItemNo("mem");
			oitem.setItemName("内存");
			oitem.setAmount(new BigDecimal(bean.getMem()));
			oitem.setUnit("G");
			oitem.setUnitCount(1);
			orderItemDao.save(oitem);
			//带宽信息
			oitem.setItemNo("net");
			oitem.setItemName("网络带宽");
			oitem.setAmount(new BigDecimal(bean.getNet()));
			oitem.setUnit("M");
			oitem.setUnitCount(1);
			orderItemDao.save(oitem);
		}
		
		if(!"".equals(ebsOrderId)) {
			OrderDto order = new OrderDto();
			order.setOrderType((short)3);
			order.setProductType((short)3);
			order.setOrderProductCount((short)1);
			order.setPromotionId(0);
			
			order.setTotalMoney(bean.getEbsPrice());
			order.setRealMoney(bean.getEbsPrice());
			
			order.setServiceBeginTime(now);
			order.setServiceEndTime(bean.getEbsEndTime());
			order.setUtime(now);
			order.setStatus(bean.getStatus());
			order.setProductId(bean.getEbsId());
			order.setSubmitTime(now);
			order.setUserId(userId);
			order.setOrderId(ebsOrderId);
			//保存订单
			orderDao.save(order);
			
			//ebs信息
			OrderItemDto oitem = new OrderItemDto();
			oitem.setOrderId(ebsOrderId);
			oitem.setItemNo("ebs");
			oitem.setItemName("数据盘");
			oitem.setAmount(new BigDecimal(bean.getEbs()));
			oitem.setUnit("G");
			oitem.setUnitCount(1);
			orderItemDao.save(oitem);
		}
		
		BigDecimal totalCost = bean.getEbsPrice().add(bean.getKvmPrice());
		//生成账单
		BillDto bill = new BillDto();
		bill.setProductType((short)0);
		bill.setUserId(userId);
		bill.setPayType((short)1);
		bill.setTotalCost(totalCost);
		bill.setRealCost(totalCost);
		bill.setPayTmie(now);
		bill.setChargeBeginTime(now);
		bill.setChargeEndTime(now);
		bill.setPayStatus((short)1);
		bill.setUserOrderId((new StringBuilder(kvmOrderId)).append(",").append(ebsOrderId).toString());
		billDao.save(bill);
		long billId = bill.getBillId();
		
		if(!"".equals(cashSerial)) {
			if(totalCost.compareTo(BigDecimal.ZERO) > 0) {
				UserDto updateUser = new UserDto();
				updateUser.setUserId(userId);
				BigDecimal cashBalance = user.getBalance().subtract(totalCost);
				updateUser.setBalance(cashBalance);
				userDao.updateBalance(updateUser);
				UserAccountDetailDto cashDto = new UserAccountDetailDto();
				cashDto.setCaSerial(cashSerial);
				cashDto.setUserId(userId);
				cashDto.setDealType((short)3);
				cashDto.setBalance(cashBalance);
				cashDto.setIncomeMoney(BigDecimal.ZERO);
				cashDto.setPayMoney(totalCost);
				cashDto.setDealTime(new Date());
				cashDto.setBaseBillId(billId);
				cashDto.setStatus((short)1);
				userAccountDetailDao.save(cashDto);
				
			}
		}
		
	}

	@Override
	public void updateApplicationOrderServiceEndTime(String orderId, int day) {
		OrderDto order = orderDao.findById(orderId);
		if(order == null ) return;
		long userId = order.getUserId();
		short type = order.getProductType();
		String productId = order.getProductId();
		Date serviceEndTime = order.getServiceEndTime();
		Date serviceEndTimeNow = DateUtil.addDay(serviceEndTime, day);
		//获取用户信息
		UserInfoDto uInfo = userInfoDao.findById(userId);
		//根据Id更新试用订单的到期时间
		orderDao.updateApplicationOrderServiceEndTime(orderId, serviceEndTimeNow);
		//发送更新有效期jms消息
		String jmsMsg = String.format(AppConfig.EXTEND_KVM_OR_EBS, uInfo.getEmail(), userId, type, productId, DateUtil.getAllStrDate(serviceEndTimeNow) , orderId, DateUtil.getAllStrDate(new Date()));
		amqpTemplate.convertAndSend(queueName, jmsMsg);
		logger.info(String.format("send to extend kvm or ebs serviceEndTime queue %s", jmsMsg));
		
	}

	

	
}
