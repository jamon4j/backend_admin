package com.ksyun.payment.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 后扣费模块传入参数封装对象
 * @author ZhangYanchun
 * @data   2013-08-20
 */
public class Ks3MeasureBean {
	
	private int user_id;
	
	private Date charge_date;
	
	private String product_type;

	private Map<String, BigDecimal> data;
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getCharge_date() {
		return charge_date;
	}

	public void setCharge_date(Date charge_date) {
		this.charge_date = charge_date;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public Map<String, BigDecimal> getData() {
		return data;
	}

	public void setData(Map<String, BigDecimal> data) {
		this.data = data;
	}

	
	
}
