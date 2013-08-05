package com.ksyun.vm.dto.host;

import com.ksyun.vm.dto.common.CoreDto;

public class TopologyDto extends CoreDto{
	private String cores;
	private String threads;
	private String sockets;
	public String getCores() {
		return cores;
	}
	public void setCores(String cores) {
		this.cores = cores;
	}
	public String getThreads() {
		return threads;
	}
	public void setThreads(String threads) {
		this.threads = threads;
	}
	public String getSockets() {
		return sockets;
	}
	public void setSockets(String sockets) {
		this.sockets = sockets;
	}
	
}
