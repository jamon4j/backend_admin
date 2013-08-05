package com.ksyun.vm.dto.host;

import java.util.List;

import com.ksyun.vm.dto.common.CoreDto;


public class CpuInfoDto extends CoreDto{
	private String vendor;
	private String model;
	private String arch;
	private List<String> features;
	private TopologyDto topology;
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getArch() {
		return arch;
	}
	public void setArch(String arch) {
		this.arch = arch;
	}
	public List<String> getFeatures() {
		return features;
	}
	public void setFeatures(List<String> features) {
		this.features = features;
	}
	public TopologyDto getTopology() {
		return topology;
	}
	public void setTopology(TopologyDto topology) {
		this.topology = topology;
	}
	
}
