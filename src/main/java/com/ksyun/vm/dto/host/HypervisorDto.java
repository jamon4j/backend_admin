package com.ksyun.vm.dto.host;

import com.ksyun.vm.dto.common.CoreDto;
import com.ksyun.vm.dto.common.ServiceDto;

public class HypervisorDto extends CoreDto{
	private ServiceDto service;
	private String vcpus_used;
	private String hypervisor_type;
	private String local_gb_used;
	private String hypervisor_hostname;
	private String memory_mb_used;
	private String memory_mb;
	private String current_workload;
	private String vcpus;
	private String running_vms;
	private String free_disk_gb;
	private String hypervisor_version;
	private String disk_available_least;
	private String local_gb;
	private String free_ram_mb;
	private String id;
	private String cpu_info;
	public ServiceDto getService() {
		return service;
	}
	public void setService(ServiceDto service) {
		this.service = service;
	}
	public String getVcpus_used() {
		return vcpus_used;
	}
	public void setVcpus_used(String vcpusUsed) {
		vcpus_used = vcpusUsed;
	}
	public String getHypervisor_type() {
		return hypervisor_type;
	}
	public void setHypervisor_type(String hypervisorType) {
		hypervisor_type = hypervisorType;
	}
	public String getLocal_gb_used() {
		return local_gb_used;
	}
	public void setLocal_gb_used(String localGbUsed) {
		local_gb_used = localGbUsed;
	}
	public String getHypervisor_hostname() {
		return hypervisor_hostname;
	}
	public void setHypervisor_hostname(String hypervisorHostname) {
		hypervisor_hostname = hypervisorHostname;
	}
	public String getMemory_mb_used() {
		return memory_mb_used;
	}
	public void setMemory_mb_used(String memoryMbUsed) {
		memory_mb_used = memoryMbUsed;
	}
	public String getMemory_mb() {
		return memory_mb;
	}
	public void setMemory_mb(String memoryMb) {
		memory_mb = memoryMb;
	}
	public String getCurrent_workload() {
		return current_workload;
	}
	public void setCurrent_workload(String currentWorkload) {
		current_workload = currentWorkload;
	}
	public String getVcpus() {
		return vcpus;
	}
	public void setVcpus(String vcpus) {
		this.vcpus = vcpus;
	}
	public String getRunning_vms() {
		return running_vms;
	}
	public void setRunning_vms(String runningVms) {
		running_vms = runningVms;
	}
	public String getFree_disk_gb() {
		return free_disk_gb;
	}
	public void setFree_disk_gb(String freeDiskGb) {
		free_disk_gb = freeDiskGb;
	}
	public String getHypervisor_version() {
		return hypervisor_version;
	}
	public void setHypervisor_version(String hypervisorVersion) {
		hypervisor_version = hypervisorVersion;
	}
	public String getDisk_available_least() {
		return disk_available_least;
	}
	public void setDisk_available_least(String diskAvailableLeast) {
		disk_available_least = diskAvailableLeast;
	}
	public String getLocal_gb() {
		return local_gb;
	}
	public void setLocal_gb(String localGb) {
		local_gb = localGb;
	}
	public String getFree_ram_mb() {
		return free_ram_mb;
	}
	public void setFree_ram_mb(String freeRamMb) {
		free_ram_mb = freeRamMb;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCpu_info() {
		return cpu_info;
	}
	public void setCpu_info(String cpuInfo) {
		cpu_info = cpuInfo;
	}
}
