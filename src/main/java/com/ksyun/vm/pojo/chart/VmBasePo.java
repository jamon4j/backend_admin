package com.ksyun.vm.pojo.chart;

import java.io.Serializable;


/**
 * User: LCD
 * Date: 13-8-21
 * Time: 下午1:10
 * Func: 基类
 */
public interface VmBasePo extends Serializable{
	public Serializable getUniqeId();
	public String getTime();
    public String getVmUuid();
    public String getVmName();
    public String getLogTime();
}
