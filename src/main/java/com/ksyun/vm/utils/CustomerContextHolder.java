package com.ksyun.vm.utils;

/**
 * 多数据源-利用ThreadLocal解决线程安全问题
 * 
 * @author XueQi
 * 
 */
public class CustomerContextHolder {
	public static final String DATA_SOURCE_A = "dataSource";
	public static final String DATA_SOURCE_B = "payDS";
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setCustomerType(String customerType) {
		contextHolder.set(customerType);
	}

	public static String getCustomerType() {
		return contextHolder.get();
	}

	public static void clearCustomerType() {
		contextHolder.remove();
	}
}
