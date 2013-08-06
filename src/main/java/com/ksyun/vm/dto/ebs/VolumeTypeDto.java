package com.ksyun.vm.dto.ebs;

import com.ksyun.vm.dto.common.CoreDto;

public class VolumeTypeDto extends CoreDto{

	private String name;
	private Object extra_specs;
	private String id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getExtra_specs() {
		return extra_specs;
	}
	public void setExtra_specs(Object extra_specs) {
		this.extra_specs = extra_specs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
