package com.ksyun.vm.pojo.chart;

import com.ksyun.vm.pojo.chart.VmBasePo;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class MonitorVmDiskPo implements VmBasePo {
    private Integer id;

    private String vmUuid;

    private String vmName;

    private String disk;

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    private String readTimes;

    private String writeTimes;

    private String readBytes;

    private String writeBytes;

    public String getReadBytes() {
        return readBytes;
    }

    public void setReadBytes(String readBytes) {
        this.readBytes = readBytes;
    }

    public String getWriteBytes() {
        return writeBytes;
    }

    public void setWriteBytes(String writeBytes) {
        this.writeBytes = writeBytes;
    }

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

    public String getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(String readTimes) {
        this.readTimes = readTimes;
    }

    public String getWriteTimes() {
        return writeTimes;
    }

    public void setWriteTimes(String writeTimes) {
        this.writeTimes = writeTimes;
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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);	
	}

	public String getTime() {
		return getLogTime();
	}
}