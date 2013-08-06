package com.ksyun.vm.dto.images;

import java.util.List;

import com.ksyun.vm.dto.common.CoreDto;
import com.ksyun.vm.dto.common.LinkDto;

public class SystemImageDto extends CoreDto {
	private String name;
	private String updated;
	private List<LinkDto> links;
	private String created;
	private String minDisk;
	private String progress;
	private String minRam;
	private String id;
	private String status;
	private SystemImageMetadataDto metadata;
	private SystemImageServerDto server;
	
	public SystemImageServerDto getServer() {
		return server;
	}
	public void setServer(SystemImageServerDto server) {
		this.server = server;
	}
	public SystemImageMetadataDto getMetadata() {
		return metadata;
	}
	public void setMetadata(SystemImageMetadataDto metadata) {
		this.metadata = metadata;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public List<LinkDto> getLinks() {
		return links;
	}
	public void setLinks(List<LinkDto> links) {
		this.links = links;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getMinDisk() {
		return minDisk;
	}
	public void setMinDisk(String minDisk) {
		this.minDisk = minDisk;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getMinRam() {
		return minRam;
	}
	public void setMinRam(String minRam) {
		this.minRam = minRam;
	}
}
