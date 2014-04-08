package com.ksyun.vm.utils.datasource;

import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.ClassUtils;

/**
 * 数据源切换拦截器
 * 
 * @author XueQi
 * 
 */
public class DataSourceMethodInterceptor implements MethodInterceptor {
	//private String packageName;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		Class<?> clazz = invocation.getThis().getClass();
		String packageName = clazz.getName();
		if (ClassUtils.isAssignable(clazz, Proxy.class)) {
			packageName = invocation.getMethod().getDeclaringClass()
					.getPackage().getName();
		}
		if (packageName.contains("com.ksyun.payment.dao")) {
			CustomerContextHolder
					.setCustomerType(CustomerContextHolder.DATA_SOURCE_B);
		} else if (packageName.contains("com.ksyun.vm.service")) {//backend_admin将数据源切换拦截到Service层
			CustomerContextHolder
					.setCustomerType(CustomerContextHolder.DATA_SOURCE_A);
		}
		Object result = invocation.proceed();
		return result;
	}
}
