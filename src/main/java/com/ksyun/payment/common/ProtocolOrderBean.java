package com.ksyun.payment.common;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 协议订单数据
 * @author ZhangYanchun
 * 2013-01-03
 */
public class ProtocolOrderBean {
	private long userId;
	private String kvmId;
	private int cpu;
	private double mem;
	private int net;
	private BigDecimal kvmPrice = BigDecimal.ZERO;
	private Date kvmEndTime;
	private String ebsId;
	private int ebs;
	private BigDecimal ebsPrice = BigDecimal.ZERO;
	private Date ebsEndTime;
	
	private short status;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getKvmId() {
		return kvmId;
	}
	public void setKvmId(String kvmId) {
		this.kvmId = kvmId;
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
	public int getNet() {
		return net;
	}
	public void setNet(int net) {
		this.net = net;
	}
	public BigDecimal getKvmPrice() {
		return kvmPrice;
	}
	public void setKvmPrice(BigDecimal kvmPrice) {
		this.kvmPrice = kvmPrice;
	}

	public String getEbsId() {
		return ebsId;
	}
	public void setEbsId(String ebsId) {
		this.ebsId = ebsId;
	}
	public int getEbs() {
		return ebs;
	}
	public void setEbs(int ebs) {
		this.ebs = ebs;
	}
	public BigDecimal getEbsPrice() {
		return ebsPrice;
	}
	public void setEbsPrice(BigDecimal ebsPrice) {
		this.ebsPrice = ebsPrice;
	}
	public Date getKvmEndTime() {
		return kvmEndTime;
	}
	public void setKvmEndTime(Date kvmEndTime) {
		this.kvmEndTime = kvmEndTime;
	}
	public Date getEbsEndTime() {
		return ebsEndTime;
	}
	public void setEbsEndTime(Date ebsEndTime) {
		this.ebsEndTime = ebsEndTime;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	
	
	
	
	
}
