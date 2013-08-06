package com.ksyun.vm.dto.images;

import com.ksyun.vm.dto.common.CoreDto;

public class CreateEBSImageDto extends CoreDto{
	private String volume_id;
	private String force;
	private String display_name;
	private String display_description;
	public String getVolume_id() {
		return volume_id;
	}
	public void setVolume_id(String volumeId) {
		volume_id = volumeId;
	}
	public String getForce() {
		return force;
	}
	public void setForce(String force) {
		this.force = force;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String displayName) {
		display_name = displayName;
	}
	public String getDisplay_description() {
		return display_description;
	}
	public void setDisplay_description(String displayDescription) {
		display_description = displayDescription;
	}
	
	
}
