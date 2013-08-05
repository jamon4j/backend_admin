package com.ksyun.vm.dto.images;

import com.ksyun.vm.dto.common.CoreDto;

public class ImagesDto extends CoreDto {
	private String name;
	private String container_format;
	private String disk_format;
	private String checksum;
	private String id;
	private String size;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContainer_format() {
		return container_format;
	}
	public void setContainer_format(String containerFormat) {
		container_format = containerFormat;
	}
	public String getDisk_format() {
		return disk_format;
	}
	public void setDisk_format(String diskFormat) {
		disk_format = diskFormat;
	}
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
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
