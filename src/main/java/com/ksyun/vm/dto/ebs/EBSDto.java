package com.ksyun.vm.dto.ebs;

import com.alibaba.fastjson.annotation.JSONField;
import com.ksyun.vm.dto.common.CoreDto;
import com.ksyun.vm.dto.common.MetaDto;

public class EBSDto extends CoreDto{
	private String status;
	private String display_name;
	private String[] attachments;
	private String availability_zone;
	private String bootable;
	private String created_at;
	private String os_vol_tenant_attr_tenant_id;
	private String display_description;
	private String os_vol_host_attr_host;
	private String volume_type;
	private String snapshot_id;
	private String source_volid;
	private MetaDto metadata;
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
	public String[] getAttachments() {
		return attachments;
	}
	public void setAttachments(String[] attachments) {
		this.attachments = attachments;
	}
	public String getAvailability_zone() {
		return availability_zone;
	}
	public void setAvailability_zone(String availabilityZone) {
		availability_zone = availabilityZone;
	}
	public String getBootable() {
		return bootable;
	}
	public void setBootable(String bootable) {
		this.bootable = bootable;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String createdAt) {
		created_at = createdAt;
	}
	@JSONField(name = "os-vol-tenant-attr:tenant_id")
	public String getOs_vol_tenant_attr_tenant_id() {
		return os_vol_tenant_attr_tenant_id;
	}
	@JSONField(name = "os-vol-tenant-attr:tenant_id")
	public void setOs_vol_tenant_attr_tenant_id(String osVolTenantAttrTenantId) {
		os_vol_tenant_attr_tenant_id = osVolTenantAttrTenantId;
	}
	public String getDisplay_description() {
		return display_description;
	}
	public void setDisplay_description(String displayDescription) {
		display_description = displayDescription;
	}
	@JSONField(name = "os-vol-host-attr:host")
	public String getOs_vol_host_attr_host() {
		return os_vol_host_attr_host;
	}
	@JSONField(name = "os-vol-host-attr:host")
	public void setOs_vol_host_attr_host(String osVolHostAttrHost) {
		os_vol_host_attr_host = osVolHostAttrHost;
	}
	public String getVolume_type() {
		return volume_type;
	}
	public void setVolume_type(String volumeType) {
		volume_type = volumeType;
	}
	public String getSnapshot_id() {
		return snapshot_id;
	}
	public void setSnapshot_id(String snapshotId) {
		snapshot_id = snapshotId;
	}
	public String getSource_volid() {
		return source_volid;
	}
	public void setSource_volid(String sourceVolid) {
		source_volid = sourceVolid;
	}
	public MetaDto getMetadata() {
		return metadata;
	}
	public void setMetadata(MetaDto metadata) {
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
