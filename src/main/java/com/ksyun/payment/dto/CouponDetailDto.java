package com.ksyun.payment.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代金券消费记录DTO
 * @author ZhangYanchun
 * @ 2013-08-012
 */
public class CouponDetailDto implements DtoInterface {
	
	//消费ID
	private long cdId;
	//代金券ID
	private long couponId;
	//用户ID
	private long userId;
	//账单ID
	private long baseBillId;
	//处理方式 1 激活   2 消费  3 业务退回
	private short dealType;
	//收入金额
	private BigDecimal incomeMoney;
	//支出金额
	private BigDecimal payMoney;
	//处理时间
	private Date dealTime;
	//处理状态
	private short dealStatus;
	//备注
	private String memo;
	
	
	
	public long getCdId() {
		return cdId;
	}

	public void setCdId(long cdId) {
		this.cdId = cdId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getBaseBillId() {
		return baseBillId;
	}

	public void setBaseBillId(long baseBillId) {
		this.baseBillId = baseBillId;
	}

	public short getDealType() {
		return dealType;
	}

	public void setDealType(short dealType) {
		this.dealType = dealType;
	}

	public BigDecimal getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public short getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(short dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toJson() {
		return null;
	}

}
