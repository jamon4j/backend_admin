package com.ksyun.payment.service;

import com.ksyun.payment.common.KvmBean;
import com.ksyun.payment.common.RestapiRet;

/**
 * kvm 价格计算接口
 * @author ZhangYanchun
 * @date   2013-10-10
 */
public interface IKvmPriceService {
	
	/**
	 * 计算kvm产品明细的价格
	 * @param kbean
	 * @return
	 */
	RestapiRet calcPrice(KvmBean kbean);
	
	/**
	 * 计算ebs价格
	 * @param kbean
	 * @return
	 */
	RestapiRet calcEbsPrice(KvmBean kbean);
	
	

}
