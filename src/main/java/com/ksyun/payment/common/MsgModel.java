package com.ksyun.payment.common;

import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * jms消息接受model
 * @author zhangyanchun
 * @2013-11-07
 */
public class MsgModel {
	
	private String msgType;
	
	private String bizType;
	
	private String address;
	
	private Map<String, String> dataMap;
	
	private String timestamp;
	
	private int retryCount;

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
