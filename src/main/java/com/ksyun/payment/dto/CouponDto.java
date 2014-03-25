package com.ksyun.payment.dto;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 优惠券DTO
 * @author ZhangYanchun
 * @Date 2013-08-08
 */
public class CouponDto implements DtoInterface {
	
	//优惠券ID
	private long couponId;
	//优惠券总额
	private BigDecimal sumMoney;
	//优惠券余额
	private BigDecimal balanceMoney;
	//优惠券是否激活
	private short isActive;
	//激活时间
	private Date activeTime;
	//到期时间
	private Date expireTime;
	//用户ID
	private long userId;
	//适用产品类型
	private short productType;
	//批号
	private String batchNo;
	//激活终止时间
	private Date activeExpireTime;
	//激活码
	private String activeCode; 
	
	
	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public BigDecimal getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}

	public BigDecimal getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(BigDecimal balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

	public short getIsActive() {
		return isActive;
	}

	public void setIsActive(short isActive) {
		this.isActive = isActive;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public short getProductType() {
		return productType;
	}

	public void setProductType(short productType) {
		this.productType = productType;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getActiveExpireTime() {
		return activeExpireTime;
	}

	public void setActiveExpireTime(Date activeExpireTime) {
		this.activeExpireTime = activeExpireTime;
	}

	
	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	@Override
	public String toJson() {
		return null;
	}

}
