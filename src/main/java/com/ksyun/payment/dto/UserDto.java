package com.ksyun.payment.dto;

import java.math.BigDecimal;

/**
 * 用户账户DTO
 * @author ZhangYanchun
 * @date 2013-08-10
 */
public class UserDto implements DtoInterface {
	
	//用户ID
	private long userId;
	//账户余额
	private BigDecimal balance;
	//是否被挂起
	private int isSuspended;
	//报警金额设定
	private BigDecimal alarmBalance;
	//是否余额不足报警  1 报警  0 不报警
	private short isAlarm;
	//欠费天数
	private int arrearageDay;
	//用户登录名
	private String name;
	
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getIsSuspended() {
		return isSuspended;
	}

	public void setIsSuspended(int isSuspended) {
		this.isSuspended = isSuspended;
	}
	
	public BigDecimal getAlarmBalance() {
		return alarmBalance;
	}

	public void setAlarmBalance(BigDecimal alarmBalance) {
		this.alarmBalance = alarmBalance;
	}

	public short getIsAlarm() {
		return isAlarm;
	}

	public void setIsAlarm(short isAlarm) {
		this.isAlarm = isAlarm;
	}
	
	public int getArrearageDay() {
		return arrearageDay;
	}

	public void setArrearageDay(int arrearageDay) {
		this.arrearageDay = arrearageDay;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toJson() {
		return null;
	}
	
}
