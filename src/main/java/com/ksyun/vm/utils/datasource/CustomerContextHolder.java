package com.ksyun.vm.utils.datasource;

/**
 * 多数据源-利用ThreadLocal解决线程安全问题
 * 
 * @author XueQi
 * 
 */
public class CustomerContextHolder {
	public static final String DATA_SOURCE_A = "VMDataSource";
	public static final String DATA_SOURCE_B = "payDataSource";
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setCustomerType(String customerType) {
		contextHolder.set(customerType);
	}

	public static String getCustomerType() {
		if (contextHolder.get() == null) {
			return DATA_SOURCE_A;
		}
		return contextHolder.get();
	}

	public static void clearCustomerType() {
		contextHolder.remove();
	}
}
