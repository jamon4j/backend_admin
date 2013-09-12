package com.ksyun.vm.utils;

import com.ksyun.vm.utils.accessor.AccessorFactory;
import com.ksyun.vm.utils.accessor.PropertiesAccessor;

public class Constants {
	public static final Long DEFAULT_PAGEINDEX = 1L;
	public static final Integer DEFAULT_PAGESIZE = 10;

	private static PropertiesAccessor propertiesAccessor = AccessorFactory.createPropertiesAccessor(new String[]{"vm.properties","conf.properties"});

	public static String getPropertyValue(String key) {
		return propertiesAccessor.getValue(key);
	}

	public static String getPropertyValue(String key, Object... arguments) {
		return propertiesAccessor.getValue(key, arguments);
	}
}
