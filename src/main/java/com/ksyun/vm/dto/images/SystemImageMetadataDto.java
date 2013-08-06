package com.ksyun.vm.dto.images;

import com.ksyun.vm.dto.common.CoreDto;

public class SystemImageMetadataDto extends CoreDto{

	private String instance_uuid;
	private String image_location;
	private String image_state;
	private String user_id;
	private String image_type;
	private String ramdisk_id;
	private String kernel_id;
	private String os_version;
	private String storage_locate;
	private String base_image_ref;
	private String owner_id;
	public String getInstance_uuid() {
		return instance_uuid;
	}
	public void setInstance_uuid(String instance_uuid) {
		this.instance_uuid = instance_uuid;
	}
	public String getImage_location() {
		return image_location;
	}
	public void setImage_location(String image_location) {
		this.image_location = image_location;
	}
	public String getImage_state() {
		return image_state;
	}
	public void setImage_state(String image_state) {
		this.image_state = image_state;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getImage_type() {
		return image_type;
	}
	public void setImage_type(String image_type) {
		this.image_type = image_type;
	}
	public String getRamdisk_id() {
		return ramdisk_id;
	}
	public void setRamdisk_id(String ramdisk_id) {
		this.ramdisk_id = ramdisk_id;
	}
	public String getKernel_id() {
		return kernel_id;
	}
	public void setKernel_id(String kernel_id) {
		this.kernel_id = kernel_id;
	}
	public String getOs_version() {
		return os_version;
	}
	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}
	public String getStorage_locate() {
		return storage_locate;
	}
	public void setStorage_locate(String storage_locate) {
		this.storage_locate = storage_locate;
	}
	public String getBase_image_ref() {
		return base_image_ref;
	}
	public void setBase_image_ref(String base_image_ref) {
		this.base_image_ref = base_image_ref;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
}
