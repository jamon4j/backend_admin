package com.ksyun.vm.utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源配置
 * 
 * @author XueQi
 * 
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return CustomerContextHolder.getCustomerType();
	}

}
