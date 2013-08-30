package com.ksyun.vm.pojo;

import java.io.Serializable;

public class MonitorVmLoadPo implements VmBasePo {
    private Integer id;

    private String vmName;

    private String vmUuid;

    private String cpuLoad;

    private String memoryLoad;

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

    public String getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(String cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public String getMemoryLoad() {
        return memoryLoad;
    }

    public void setMemoryLoad(String memoryLoad) {
        this.memoryLoad = memoryLoad;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

	public Serializable getUniqeId() {
		return getVmUuid();
	}
	public String getTime() {
		return getLogTime();
	}
}