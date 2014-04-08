package com.ksyun.vm.utils.datasource;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源配置
 * 
 * @author XueQi
 * 
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		// TODO Auto-generated method stub
		super.setTargetDataSources(targetDataSources);
	}
	
	@Override  
    public Object unwrap(Class iface) throws SQLException {  
        return null;  
    }  
  
    @Override  
    public boolean isWrapperFor(Class iface) throws SQLException {  
        return false;  
    }  
	
	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		String string = CustomerContextHolder.getCustomerType();
		CustomerContextHolder.clearCustomerType();
		return string;
	}

}
