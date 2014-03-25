package com.ksyun.payment.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ksyun.payment.dao.IBillDao;
import com.ksyun.payment.dao.IBillDetailDao;
import com.ksyun.payment.dao.ICouponDao;
import com.ksyun.payment.dao.ICouponDetailDao;
import com.ksyun.payment.dao.IUserAccountDetailDao;
import com.ksyun.payment.dao.IUserDao;
import com.ksyun.payment.dto.BillDetailDto;
import com.ksyun.payment.dto.BillDto;
import com.ksyun.payment.dto.CouponDetailDto;
import com.ksyun.payment.dto.CouponDto;
import com.ksyun.payment.dto.UserAccountDetailDto;
import com.ksyun.payment.dto.UserDto;
import com.ksyun.payment.service.IBillService;

/**
 * 账单操作实现类
 * @author ZhangYanchun
 * @date   2013-08-20
 */
@Service
public class BillService implements IBillService {
	
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
	private IBillDetailDao<BillDetailDto> billDetailDao;
	
	
	@Override
	public int payBill(BillDto bill, String caSerial) {
		long userId = bill.getUserId();
		short productType = bill.getProductType();
		BigDecimal realCost = bill.getRealCost();
		
		short payType = 0;
		//保存账单信息
		billDao.save(bill);
		List<BillDetailDto> bds = bill.getBds();
		long baseBillId = bill.getBillId();
        for(BillDetailDto detailDto : bds){
			detailDto.setBaseBillId(baseBillId);
			billDetailDao.save(detailDto);
		}
		//获取用户代金券总余额及现金账户总金额，看是否可以用于支付
		BigDecimal couponBalance = couponDao.getCouponBalance(userId, productType);
		//如果代金券余额大于需要支付的金额，则不用使用现金账户去支付，直接使用代金券支付
		if(couponBalance == null) couponBalance = BigDecimal.ZERO;
		if(couponBalance.compareTo(realCost) >= 0) {
			payType = 2;
			//查询所有可用代金券列表
			List<CouponDto> coupons = couponDao.getValidCoupon(userId, productType);
			//找出可扣款的代金券账户
			Map<Long, BigDecimal> feeMaps = findFeeCoupon(realCost, coupons);
			//代金券进行扣费操作，并计入代金券明细
			couponDao.updateBalance(feeMaps);
			saveAllCouponDetail(userId, baseBillId, feeMaps);
			
			
		} else {
			//计算用户现金账户余额
			UserDto user = userDao.findById(userId);
			BigDecimal cashBalance = user.getBalance();
			//设置为混合支付
			payType = 3;
			//查询可使用的代金券信息
			List<CouponDto> coupons = couponDao.getValidCoupon(userId, productType);
			if(coupons.size() == 0) {
				payType = 1;
			} else {
				//进行代金券账户的扣费操作（所有的代金券都将扣费为0）
				Map<Long, BigDecimal> feeMaps = findFeeCoupon(coupons);
				couponDao.updateBalance(feeMaps);
				saveAllCouponDetail(userId, baseBillId, feeMaps);
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
			cashDto.setCaSerial(caSerial);
			cashDto.setUserId(userId);
			cashDto.setDealType((short)3);
			cashDto.setBalance(cashBalance);
			cashDto.setIncomeMoney(BigDecimal.ZERO);
			cashDto.setPayMoney(remainderMoney);
			cashDto.setDealTime(new Date());
			cashDto.setBaseBillId(baseBillId);
			cashDto.setStatus((short)1);
			userAccountDetailDao.save(cashDto);
		}
		//更新账单为提交完成状态
		billDao.updateStatus(baseBillId, (short)1, payType);
		return 0;	
	}
	
	@Override
	public BillDto getLatestBill(long userId) {
		return billDao.findLatestBill(userId);
	}
	
	
	@Override
	public List<BillDto> getAllBill(long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String sortString = "";//如果你想排序的话逗号分隔可以排序多列
		List<BillDto> list = billDao.findAllBill(params, sortString);
		//获得结果集条总数
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageList<BillDto> pageList = (PageList)list;
		return pageList;
	}
	
	@Override
	public List<BillDetailDto> getAllBillDetail(long baseBillId) {
		return billDetailDao.findAllDetailByBillId(baseBillId);
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
			couponDetail.setDealStatus((short)0);
			couponDetail.setDealTime(now);
			couponDetailDao.save(couponDetail);
		}
		
	}
	
	/**
	 * 扣除所有代金券的金额
	 * @param coupons
	 * @return
	 */
	private Map<Long, BigDecimal> findFeeCoupon(List<CouponDto> coupons) {
		Map<Long, BigDecimal> maps = new HashMap<Long, BigDecimal>();
		for(CouponDto coupon : coupons) {
			maps.put(coupon.getCouponId(), coupon.getBalanceMoney());
		}
		return maps;
	}
	/**
	 * 获取需要进行操作的代金券ID及金额
	 * @param realCost
	 * @param coupons
	 * @return
	 */
	private Map<Long, BigDecimal> findFeeCoupon(BigDecimal realCost, List<CouponDto> coupons) {
		Map<Long, BigDecimal> maps = new HashMap<Long, BigDecimal>();
		for(CouponDto coupon : coupons) {
			BigDecimal balance = coupon.getBalanceMoney();
			long couponId = coupon.getCouponId();
			if(balance.compareTo(realCost) >= 0) {
				maps.put(couponId, realCost);
				realCost = BigDecimal.ZERO;
				break;
			} else {
				realCost = realCost.subtract(balance);
				maps.put(couponId, balance);
				continue;
			}
		}
		return maps;
	}

	@Override
	public List<BillDto> getAllKvmAndEbsBill(Map<String, Object> params) {
		List<BillDto> list = billDao.findAllKvmAndEbsBill(params, "");
		//获得结果集条总数
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageList<BillDto> pageList = (PageList)list;
		return pageList;
	}

	@Override
	public BillDto getBillById(long billId) {
		return billDao.findById(billId);
	}

	@Override
	public int cancelBill(BillDto bill) {
		int ret = -1;
		short payStatus = bill.getPayStatus();
		if(payStatus == -1 || payStatus == 0) return -1;
		//获取基本信息
		long userId = bill.getUserId();
		//BigDecimal realCost = bill.getRealCost();
		short payType = bill.getPayType();
	    long billId = bill.getBillId();
		//首先处理现金
		if(payType == 1 || payType == 3) {
			//查询此账单的消费记录
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("billId", billId);
			UserAccountDetailDto accountDetail = userAccountDetailDao.findByBill(params);
			
			if(accountDetail != null) {
				String new_cashSerial = accountDetail.getCaSerial() + "c";
				BigDecimal cashCost = accountDetail.getPayMoney();
				UserDto user = userDao.findById(userId);
				
				BigDecimal balance = user.getBalance();
				
				
				//退费后现金总余额为
				BigDecimal balance_now = balance.add(cashCost);
				
				
				accountDetail.setBalance(balance_now);
				accountDetail.setPayMoney(BigDecimal.ZERO);
				accountDetail.setIncomeMoney(cashCost);
				accountDetail.setDealTime(new Date());
				accountDetail.setDealType((short)2);
				accountDetail.setCaSerial(new_cashSerial);
				
				userAccountDetailDao.save(accountDetail);
				//更新账户余额
				user.setBalance(balance_now);
				userDao.updateBalance(user);
				//更新账单状态为 -1
				
			    billDao.updateStatus(billId, (short)-1, bill.getPayType());
			    ret = 0;
				
			}
			
		}
		
		return ret;
	}

}
