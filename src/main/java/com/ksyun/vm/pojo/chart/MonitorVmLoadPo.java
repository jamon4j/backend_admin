package com.ksyun.vm.pojo.chart;

import com.ksyun.vm.pojo.chart.VmBasePo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class MonitorVmLoadPo implements VmBasePo {
    private Integer id;

    private String vmName;

    private String vmUuid;

    private String cpuPCLoad;

    private String memoryPCLoad;

    private String cpuVMLoad;

    private String memoryVMLoad;

    private String logTime;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getVmUuid() {
		return vmUuid;
	}
	public void setVmUuid(String vmUuid) {
		this.vmUuid = vmUuid;
	}
	public String getCpuPCLoad() {
		return cpuPCLoad;
	}
	public void setCpuPCLoad(String cpuPCLoad) {
		this.cpuPCLoad = cpuPCLoad;
	}
	public String getMemoryPCLoad() {
		return memoryPCLoad;
	}
	public void setMemoryPCLoad(String memoryPCLoad) {
		this.memoryPCLoad = memoryPCLoad;
	}
	public String getCpuVMLoad() {
		return cpuVMLoad;
	}
	public void setCpuVMLoad(String cpuVMLoad) {
		this.cpuVMLoad = cpuVMLoad;
	}
	public String getMemoryVMLoad() {
		return memoryVMLoad;
	}
	public void setMemoryVMLoad(String memoryVMLoad) {
		this.memoryVMLoad = memoryVMLoad;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);	
	}
	public Serializable getUniqeId() {
		return getVmUuid();
	}
	public String getTime() {
		return getLogTime();
	}
}