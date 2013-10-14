package com.ksyun.vm.pojo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * User: liuchuandong
 * Date: 13-9-5
 * Time: 下午4:19
 * Func:
 */
public class BasePo {
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);	
	}
}
