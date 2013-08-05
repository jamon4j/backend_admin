package com.ksyun.vm.dto.images;

import com.ksyun.vm.dto.common.CoreDto;

public class SnapshotsDto extends CoreDto {
	private String status;
	private String display_name;
	private String created_at;
	private String display_description;
	private String volume_id;
	private String metadata;
	private String id;
	private String size;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String displayName) {
		display_name = displayName;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String createdAt) {
		created_at = createdAt;
	}
	public String getDisplay_description() {
		return display_description;
	}
	public void setDisplay_description(String displayDescription) {
		display_description = displayDescription;
	}
	public String getVolume_id() {
		return volume_id;
	}
	public void setVolume_id(String volumeId) {
		volume_id = volumeId;
	}
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
}
