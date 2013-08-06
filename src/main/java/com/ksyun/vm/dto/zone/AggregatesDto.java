package com.ksyun.vm.dto.zone;

import java.util.List;

import com.ksyun.vm.dto.common.CoreDto;
import com.ksyun.vm.dto.common.MetaDto;
import com.ksyun.vm.utils.Tools;

/**
 * zone Dto
 * @author yuri
 *
 */
public class AggregatesDto extends CoreDto{
	private String name;
	private String availability_zone;
	private boolean deleted;
	private String created_at;
	private String updated_at;
	private List<String> hosts;
	private String hostStr;
	private String deleted_at;
	private String id;
	private MetaDto metadata;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvailability_zone() {
		return availability_zone;
	}
	public void setAvailability_zone(String availabilityZone) {
		availability_zone = availabilityZone;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String createdAt) {
		created_at = createdAt;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updatedAt) {
		updated_at = updatedAt;
	}

	public List<String> getHosts() {
		return hosts;
	}
	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}
	public String getDeleted_at() {
		return deleted_at;
	}
	public void setDeleted_at(String deletedAt) {
		deleted_at = deletedAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MetaDto getMetadata() {
		return metadata;
	}
	public void setMetadata(MetaDto metadata) {
		this.metadata = metadata;
	}
	
	public String getHostStr() {
		return hostStr;
	}
	public void setHostStr(String hostStr) {
		this.hostStr = hostStr;
	}
	@Override
	public String toString() {
		hostStr = Tools.listToString(hosts);
		return "AggregatesDto [availability_zone=" + availability_zone + ", created_at=" + created_at + ", deleted=" + deleted + ", deleted_at=" + deleted_at + ", hosts=" + hostStr + ", id=" + id + ", metadata=" + metadata + ", name=" + name + ", updated_at=" + updated_at + "]";
	}
}
