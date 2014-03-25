package com.ksyun.payment.common;

import java.math.BigDecimal;
import java.util.Map;


/**
 * 用于kvm产品价格计算
 * @author ZhangYanchun
 * @2010-10-09
 */
public class KvmMeasureBean {
	//{\"user_id\":42400473, \"month_count\":1, \"buy_count\":1, \"data\":{\"cpu\":1,\"mem\":1.5,\"ebs\":14600,\"net\":7450}}"
	
	//用户ID
	private long user_id;
	
	//购买月数
	private int month_count;
	
	//购买个数
	private int buy_count;
	
	//购买详细数据
	private Map<String, BigDecimal> data;

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public int getMonth_count() {
		return month_count;
	}

	public void setMonth_count(int month_count) {
		this.month_count = month_count;
	}

	public int getBuy_count() {
		return buy_count;
	}

	public void setBuy_count(int buy_count) {
		this.buy_count = buy_count;
	}

	public Map<String, BigDecimal> getData() {
		return data;
	}

	public void setData(Map<String, BigDecimal> data) {
		this.data = data;
	}
	
	
	
	
	
}
