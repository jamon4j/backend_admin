package com.ksyun.vm.dto.images;

import com.ksyun.vm.dto.common.CoreDto;

public class CreateEBSDto extends CoreDto{

	/**"name": "vol-001",
        "description": "Another volume.",
        "size": 30, //EBS size
        "volume_type": "8c318716-cfa4-49af-b8ab-af5a82ac2165",*/
	private String name;
	private String description;
	private String size;
	private String volume_type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getVolume_type() {
		return volume_type;
	}
	public void setVolume_type(String volume_type) {
		this.volume_type = volume_type;
	}
	
}
