package com.ksyun.vm.dao.interfaces;

import java.util.List;

public interface IMonitorVmLoadDao<MonitorVmLoadPo> {

	public abstract List<MonitorVmLoadPo> getLatestVmLoadData();

	public abstract MonitorVmLoadPo getLastestVmLoad(String vmuuid);

	public abstract List<MonitorVmLoadPo> getVmLoadByTime(String vmuuid,
			String log_time);

}