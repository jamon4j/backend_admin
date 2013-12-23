package com.ksyun.vm.pojo.chart;

import com.ksyun.vm.pojo.chart.VmBasePo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class MonitorVmNetworkPo implements VmBasePo {
    private Integer id;

    private String vmUuid;

    private String vmName;

    private String rxp;

    private String txp;

    private String rxb;

    private String txb;

    private String mac;

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

    public String getRxp() {
        return rxp;
    }

    public void setRxp(String rxp) {
        this.rxp = rxp;
    }

    public String getTxp() {
        return txp;
    }

    public void setTxp(String txp) {
        this.txp = txp;
    }

    public String getRxb() {
        return rxb;
    }

    public void setRxb(String rxb) {
        this.rxb = rxb;
    }

    public String getTxb() {
        return txb;
    }

    public void setTxb(String txb) {
        this.txb = txb;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);	
	}
}