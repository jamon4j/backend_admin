package com.ksyun.vm.pojo.vm;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong Date: 13-10-11 Time: 上午11:59 Func:
 */
public class InstanceType extends BasePo {
	private String ram;
	private String cpu;
	private String network_bandwidth;

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getNetwork_bandwidth() {
		return network_bandwidth;
	}

	public void setNetwork_bandwidth(String network_bandwidth) {
		this.network_bandwidth = network_bandwidth;
	}

}
