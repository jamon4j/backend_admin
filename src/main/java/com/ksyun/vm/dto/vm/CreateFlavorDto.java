package com.ksyun.vm.dto.vm;

import com.alibaba.fastjson.annotation.JSONField;

public class CreateFlavorDto {
	private Integer vcpus;
	private Integer disk;
	private String name;
	private String os_flavor_access_is_public;
	private Float rxtx_factor;
	private Integer os_flv_ext_data_ephemeral;
	private Integer ram;
	private Integer swap;

	public Integer getVcpus() {
		return vcpus;
	}
	public void setVcpus(Integer vcpus) {
		this.vcpus = vcpus;
	}
	public Integer getDisk() {
		return disk;
	}
	public void setDisk(Integer disk) {
		this.disk = disk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getRxtx_factor() {
		return rxtx_factor;
	}
	public void setRxtx_factor(Float rxtxFactor) {
		rxtx_factor = rxtxFactor;
	}
	public Integer getRam() {
		return ram;
	}
	public void setRam(Integer ram) {
		this.ram = ram;
	}
	@JSONField(name = "os-flavor-access:is_public")
	public String getOs_flavor_access_is_public() {
		return os_flavor_access_is_public;
	}
	@JSONField(name = "os-flavor-access:is_public")
	public void setOs_flavor_access_is_public(String osFlavorAccessIsPublic) {
		os_flavor_access_is_public = osFlavorAccessIsPublic;
	}

	@JSONField(name = "OS-FLV-EXT-DATA:ephemeral")
	public Integer getOs_flv_ext_data_ephemeral() {
		return os_flv_ext_data_ephemeral;
	}
	@JSONField(name = "OS-FLV-EXT-DATA:ephemeral")
	public void setOs_flv_ext_data_ephemeral(Integer osFlvExtDataEphemeral) {
		os_flv_ext_data_ephemeral = osFlvExtDataEphemeral;
	}
	public Integer getSwap() {
		return swap;
	}
	public void setSwap(Integer swap) {
		this.swap = swap;
	}
	
}
