package com.ksyun.payment.service;

/**
 * 现金交易流水号
 * @author ZhangYanchun
 * @date   2013-09-10
 */
public interface ICashSerialService {
	
	/**
	 * 获取唯一流水号
	 * @return
	 */
	String incrSerialNum();

}
