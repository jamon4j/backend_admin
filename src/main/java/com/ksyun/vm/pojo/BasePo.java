package com.ksyun.vm.pojo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * User: liuchuandong
 * Date: 13-9-5
 * Time: 下午4:19
 * Func:
 */
public class BasePo {
    private String primaryKey;

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);	
	}
}
