package com.ksyun.vm.dao.interfaces;

import java.util.List;

public interface IMonitorVmDiskDao<MonitorVmDiskPo> {

	public abstract List<MonitorVmDiskPo> getLatestVmDiskData();

	public abstract MonitorVmDiskPo getLastestVmDisk(String vmuuid, String disk);

	public abstract List<MonitorVmDiskPo> getVmDiskByTime(String vmuuid,
			String log_time);

}