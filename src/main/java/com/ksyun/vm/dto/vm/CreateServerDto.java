package com.ksyun.vm.dto.vm;

import java.util.List;

import com.ksyun.vm.dto.common.CoreDto;

public class CreateServerDto extends CoreDto{
	private String name;
	private String image;
	private String instance_count;
	private List<String> security_groups;
	private String memory;
	private String vcpu;
	private String root_disk;
	private String network_bandwidth;
	private String boot_on_ebs;
	private String zone;
	private String rootpwd;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getInstance_count() {
		return instance_count;
	}
	public void setInstance_count(String instance_count) {
		this.instance_count = instance_count;
	}

	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getVcpu() {
		return vcpu;
	}
	public void setVcpu(String vcpu) {
		this.vcpu = vcpu;
	}
	public String getRoot_disk() {
		return root_disk;
	}
	public void setRoot_disk(String root_disk) {
		this.root_disk = root_disk;
	}
	public String getNetwork_bandwidth() {
		return network_bandwidth;
	}
	public void setNetwork_bandwidth(String network_bandwidth) {
		this.network_bandwidth = network_bandwidth;
	}
	public String getBoot_on_ebs() {
		return boot_on_ebs;
	}
	public void setBoot_on_ebs(String boot_on_ebs) {
		this.boot_on_ebs = boot_on_ebs;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getRootpwd() {
		return rootpwd;
	}
	public void setRootpwd(String rootpwd) {
		this.rootpwd = rootpwd;
	}
	public List<String> getSecurity_groups() {
		return security_groups;
	}
	public void setSecurity_groups(List<String> security_groups) {
		this.security_groups = security_groups;
	}
	
	
}
