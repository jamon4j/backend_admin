package com.ksyun.payment.service;

import java.util.Map;

import com.ksyun.payment.dict.ProductItemDict;

/**
 * 产品条目 service
 * @author ZhangYanchun
 * @date   2013-08-27
 */
public interface IProductItemService {
	/**
	 * 列出所有产品明细
	 * @return
	 */
	Map<String, ProductItemDict> getProductItems();
}
