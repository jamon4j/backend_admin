package com.ksyun.payment.common;

import com.alibaba.fastjson.JSON;

/**
 * kvmBean  {\"userId\":42400473, \"month\":1, \"os\":\"123f5678\",\"cpu\":3,\"mem\":1.5,\"ebs\":100,\"net\":4,\"productType\":1}
 * @author ZhangYanchun
 * @date   2013-10-16
 */
public class KvmBean {

	private String os;
	
	private int cpu;
	
	private double mem;
	
	private int ebs;
	
	private int net;
	
	private short productType;
	
	private int price;
	
	private int realPrice;
	
	private int ebsPrice;
	
	private int month;
	
	private int freeMonth;
	
	private int day;
	
	private long utime;
	
	private short orderType;
	
	
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public double getMem() {
		return mem;
	}

	public void setMem(double mem) {
		this.mem = mem;
	}

	public int getEbs() {
		return ebs;
	}

	public void setEbs(int ebs) {
		this.ebs = ebs;
	}

	public int getNet() {
		return net;
	}

	public void setNet(int net) {
		this.net = net;
	}

	public short getProductType() {
		return productType;
	}

	public void setProductType(short productType) {
		this.productType = productType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public long getUtime() {
		return utime;
	}

	public void setUtime(long utime) {
		this.utime = utime;
	}
	
	public int getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(int realPrice) {
		this.realPrice = realPrice;
	}

	public short getOrderType() {
		return orderType;
	}

	public void setOrderType(short orderType) {
		this.orderType = orderType;
	}
	
	public int getEbsPrice() {
		return ebsPrice;
	}

	public void setEbsPrice(int ebsPrice) {
		this.ebsPrice = ebsPrice;
	}
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getFreeMonth() {
		return freeMonth;
	}

	public void setFreeMonth(int freeMonth) {
		this.freeMonth = freeMonth;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	

}
