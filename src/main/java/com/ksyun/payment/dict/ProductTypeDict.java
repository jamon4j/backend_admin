package com.ksyun.payment.dict;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典类的定义（后续可能会取配置文件或者数据库结果，需要加上缓存，缓存可以参考google guava cache实现）
 * @author ZhangYanchun
 * @date   2013-08-19
 */
public class ProductTypeDict {
	
	private static ProductTypeDict productType = new ProductTypeDict();
	
	//定义产品类型列表
	private Map<Integer, String> ptypes = new HashMap<Integer, String>();
	
	private ProductTypeDict(){
		ptypes.put(1, "kvm");
		ptypes.put(2, "ks3");
	}

	
	public static ProductTypeDict getInstance(){
		return productType;
	}
	
	public Map<Integer, String> getMaps(){
		return ptypes;
	}
}
