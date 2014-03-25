package com.ksyun.payment.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * rest api 返回封装
 * @author ZhangYanchun
 * @date   2013-08-20
 */
public class RestapiRet {
	
	private int ret;
	
	private String msg;
	
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	public void putMapToList(Map<String, Object> map){
		data.add(map);
	}
	
	public void addToList(Map<String, Object> map) {
		data.add(map);
	}

}
