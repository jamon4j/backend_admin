package com.ksyun.vm.pojo.chart;

import com.ksyun.vm.pojo.chart.VmBasePo;

import java.io.Serializable;

public class MonitorVmStatusFlowPo implements VmBasePo {
    private Integer id;

    private String vmUuid;

    private String vmName;

    private String status;

    private String logTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVmUuid() {
        return vmUuid;
    }

    public void setVmUuid(String vmUuid) {
        this.vmUuid = vmUuid;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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