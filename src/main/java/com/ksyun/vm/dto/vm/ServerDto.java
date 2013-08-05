package com.ksyun.vm.dto.vm;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.ksyun.vm.dto.common.CoreDto;
import com.ksyun.vm.dto.common.LinkDto;
import com.ksyun.vm.dto.common.MetaDto;

public class ServerDto extends CoreDto{
	private String status;
	private String updated;
	private String hostId;
	private String OS_EXT_SRV_ATTR_host;
	private AddressesDto addresses;
	private List<LinkDto> links;
	private String key_name;
	private ImageDto image;
	private String OS_EXT_STS_task_state;
	private String OS_EXT_STS_vm_state;
	private String OS_EXT_SRV_ATTR_instance_name;
	private String OS_EXT_SRV_ATTR_hypervisor_hostname;
	private FlavorDto flavor;
	private String id;
	private List<SecurityGroupDto> security_groups;
	private String user_id;
	private String name;
	private String created;
	private String tenant_id;
	private String OS_DCF_diskConfig;
	private String accessIPv4;
	private String accessIPv6;
	private String progress;
	private String OS_EXT_STS_power_state;
	private String config_drive;
	private MetaDto metadata;
	
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	@JSONField(name = "OS-EXT-SRV-ATTR:host")
	public String getOS_EXT_SRV_ATTR_host() {
		return OS_EXT_SRV_ATTR_host;
	}
	@JSONField(name = "OS-EXT-SRV-ATTR:host")
	public void setOS_EXT_SRV_ATTR_host(String oSEXTSRVATTRHost) {
		OS_EXT_SRV_ATTR_host = oSEXTSRVATTRHost;
	}
	public AddressesDto getAddresses() {
		return addresses;
	}
	public void setAddresses(AddressesDto addresses) {
		this.addresses = addresses;
	}
	public List<LinkDto> getLinks() {
		return links;
	}
	public void setLinks(List<LinkDto> links) {
		this.links = links;
	}
	public String getKey_name() {
		return key_name;
	}
	public void setKey_name(String keyName) {
		key_name = keyName;
	}
	public ImageDto getImage() {
		return image;
	}
	public void setImage(ImageDto image) {
		this.image = image;
	}
	@JSONField(name = "OS-EXT-STS:task_state")
	public String getOS_EXT_STS_task_state() {
		return OS_EXT_STS_task_state;
	}
	@JSONField(name = "OS-EXT-STS:task_state")
	public void setOS_EXT_STS_task_state(String oSEXTSTSTaskState) {
		OS_EXT_STS_task_state = oSEXTSTSTaskState;
	}
	@JSONField(name = "OS-EXT-STS:vm_state")
	public String getOS_EXT_STS_vm_state() {
		return OS_EXT_STS_vm_state;
	}
	@JSONField(name = "OS-EXT-STS:vm_state")
	public void setOS_EXT_STS_vm_state(String oSEXTSTSVmState) {
		OS_EXT_STS_vm_state = oSEXTSTSVmState;
	}
	@JSONField(name = "OS-EXT-SRV-ATTR:instance_name")
	public String getOS_EXT_SRV_ATTR_instance_name() {
		return OS_EXT_SRV_ATTR_instance_name;
	}
	@JSONField(name = "OS-EXT-SRV-ATTR:instance_name")
	public void setOS_EXT_SRV_ATTR_instance_name(String oSEXTSRVATTRInstanceName) {
		OS_EXT_SRV_ATTR_instance_name = oSEXTSRVATTRInstanceName;
	}
	@JSONField(name = "OS-EXT-SRV-ATTR:hypervisor_hostname")
	public String getOS_EXT_SRV_ATTR_hypervisor_hostname() {
		return OS_EXT_SRV_ATTR_hypervisor_hostname;
	}
	@JSONField(name = "OS-EXT-SRV-ATTR:hypervisor_hostname")
	public void setOS_EXT_SRV_ATTR_hypervisor_hostname(String oSEXTSRVATTRHypervisorHostname) {
		OS_EXT_SRV_ATTR_hypervisor_hostname = oSEXTSRVATTRHypervisorHostname;
	}
	public FlavorDto getFlavor() {
		return flavor;
	}
	public void setFlavor(FlavorDto flavor) {
		this.flavor = flavor;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<SecurityGroupDto> getSecurity_groups() {
		return security_groups;
	}
	public void setSecurity_groups(List<SecurityGroupDto> securityGroups) {
		security_groups = securityGroups;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(String tenantId) {
		tenant_id = tenantId;
	}
	@JSONField(name = "OS-DCF:diskConfig")
	public String getOS_DCF_diskConfig() {
		return OS_DCF_diskConfig;
	}
	@JSONField(name = "OS-DCF:diskConfig")
	public void setOS_DCF_diskConfig(String oSDCFDiskConfig) {
		OS_DCF_diskConfig = oSDCFDiskConfig;
	}
	public String getAccessIPv4() {
		return accessIPv4;
	}
	public void setAccessIPv4(String accessIPv4) {
		this.accessIPv4 = accessIPv4;
	}
	public String getAccessIPv6() {
		return accessIPv6;
	}
	public void setAccessIPv6(String accessIPv6) {
		this.accessIPv6 = accessIPv6;
	}
	@JSONField(name = "OS-EXT-STS:power_state")
	public String getOS_EXT_STS_power_state() {
		return OS_EXT_STS_power_state;
	}
	@JSONField(name = "OS-EXT-STS:power_state")
	public void setOS_EXT_STS_power_state(String oSEXTSTSPowerState) {
		OS_EXT_STS_power_state = oSEXTSTSPowerState;
	}
	public String getConfig_drive() {
		return config_drive;
	}
	public void setConfig_drive(String configDrive) {
		config_drive = configDrive;
	}
	public MetaDto getMetadata() {
		return metadata;
	}
	public void setMetadata(MetaDto metadata) {
		this.metadata = metadata;
	}
	
}
