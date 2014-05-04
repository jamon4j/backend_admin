package com.ksyun.payment.dict;

import com.ksyun.vm.pojo.BasePo;

/**
 * 带宽更新返回值的封装
 * 
 * @author XueQi
 * 
 */
public class NetUpdateMessageDict extends BasePo {
	private String msg;
	private String ret;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

}
