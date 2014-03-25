package com.ksyun.payment.common;

import java.util.Map;

/**
 * 账户系统接口返回值
 * @author ZhangYanchun
 * @date   2013-09-04
 */
public class ApiRet {
	
	private String result;
	
	private Map<String, String> data;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
}
