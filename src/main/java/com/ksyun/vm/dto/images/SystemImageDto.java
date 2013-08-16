package com.ksyun.vm.dto.images;

import com.alibaba.fastjson.annotation.JSONField;
import com.ksyun.vm.dto.common.CoreDto;

public class SystemImageDto extends CoreDto {
	private String status;
	private String name;
	private String deleted;
	private String container_format;
	private String created_at;
	private String disk_format;
	private String updated_at;
	private String id;
	private String owner;
	private String protect;
	private String min_ram;
	private String checksum;
	private String min_disk;
	private String is_public;
	private String deleted_at;
	private String size;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the deleted
	 */
	public String getDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return the container_format
	 */
	public String getContainer_format() {
		return container_format;
	}
	/**
	 * @param container_format the container_format to set
	 */
	public void setContainer_format(String container_format) {
		this.container_format = container_format;
	}
	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	/**
	 * @return the disk_format
	 */
	public String getDisk_format() {
		return disk_format;
	}
	/**
	 * @param disk_format the disk_format to set
	 */
	public void setDisk_format(String disk_format) {
		this.disk_format = disk_format;
	}
	/**
	 * @return the updated_at
	 */
	public String getUpdated_at() {
		return updated_at;
	}
	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * @return the protect
	 */
	@JSONField(name = "protected")	
	public String getProtect() {
		return protect;
	}
	/**
	 * @param protect the protect to set
	 */
	@JSONField(name = "protected")	
	public void setProtect(String protect) {
		this.protect = protect;
	}
	/**
	 * @return the min_ram
	 */
	public String getMin_ram() {
		return min_ram;
	}
	/**
	 * @param min_ram the min_ram to set
	 */
	public void setMin_ram(String min_ram) {
		this.min_ram = min_ram;
	}
	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}
	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	/**
	 * @return the min_disk
	 */
	public String getMin_disk() {
		return min_disk;
	}
	/**
	 * @param min_disk the min_disk to set
	 */
	public void setMin_disk(String min_disk) {
		this.min_disk = min_disk;
	}
	/**
	 * @return the is_public
	 */
	public String getIs_public() {
		return is_public;
	}
	/**
	 * @param is_public the is_public to set
	 */
	public void setIs_public(String is_public) {
		this.is_public = is_public;
	}
	/**
	 * @return the deleted_at
	 */
	public String getDeleted_at() {
		return deleted_at;
	}
	/**
	 * @param deleted_at the deleted_at to set
	 */
	public void setDeleted_at(String deleted_at) {
		this.deleted_at = deleted_at;
	}
	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	private Properties properties;
}

class Properties {
	private String os_version;
	private String storage_locate;
	/**
	 * @return the os_version
	 */
	public String getOs_version() {
		return os_version;
	}
	/**
	 * @param os_version the os_version to set
	 */
	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}
	/**
	 * @return the storage_locate
	 */
	public String getStorage_locate() {
		return storage_locate;
	}
	/**
	 * @param storage_locate the storage_locate to set
	 */
	public void setStorage_locate(String storage_locate) {
		this.storage_locate = storage_locate;
	}
	
}
