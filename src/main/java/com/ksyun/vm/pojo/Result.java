package com.ksyun.vm.pojo;

import com.ksyun.vm.pojo.BasePo;

import java.util.List;


public class Result extends BasePo {
	private String status;
	private String statusText;
	private List<? extends BasePo> data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public List<? extends BasePo> getData() {
		return data;
	}
	public void setData(List<? extends BasePo> data) {
		this.data = data;
	}

}
