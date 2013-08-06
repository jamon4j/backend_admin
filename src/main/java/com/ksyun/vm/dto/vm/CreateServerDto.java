package com.ksyun.vm.dto.vm;

import java.util.List;

import com.ksyun.vm.dto.common.CoreDto;
import com.ksyun.vm.dto.common.MetaDto;
import com.ksyun.vm.dto.securitygroup.SecurityGroupDto;

public class CreateServerDto extends CoreDto{
	private String name;
	private String imageRef;
	private String flavorRef;
	private String max_count;
	private String min_count;
	private List<SecurityGroupDto> security_groups;
	private String adminPass;
	private MetaDto metadata;
	private String availability_zone;

	public String getAvailability_zone() {
		return availability_zone;
	}
	public void setAvailability_zone(String availabilityZone) {
		availability_zone = availabilityZone;
	}
	public MetaDto getMetadata() {
		return metadata;
	}
	public void setMetadata(MetaDto metadata) {
		this.metadata = metadata;
	}
	public String getAdminPass() {
		return adminPass;
	}
	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageRef() {
		return imageRef;
	}
	public void setImageRef(String imageRef) {
		this.imageRef = imageRef;
	}
	public String getFlavorRef() {
		return flavorRef;
	}
	public void setFlavorRef(String flavorRef) {
		this.flavorRef = flavorRef;
	}
	public String getMax_count() {
		return max_count;
	}
	public void setMax_count(String maxCount) {
		max_count = maxCount;
	}
	public String getMin_count() {
		return min_count;
	}
	public void setMin_count(String minCount) {
		min_count = minCount;
	}
	public List<SecurityGroupDto> getSecurity_groups() {
		return security_groups;
	}
	public void setSecurity_groups(List<SecurityGroupDto> securityGroups) {
		security_groups = securityGroups;
	}
	
	
}
