package com.ksyun.vm.dto.securitygroup;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.ksyun.vm.dto.common.CoreDto;

public class SecurityGroupDto extends CoreDto{
	private String name;
	private String tenantId;
	private String id;
	private String description;
	private List<SecurityGroupRuleDto> rules;
	@JSONField(name = "tenant_id")	
	public String getTenantId() {
		return tenantId;
	}
	@JSONField(name = "tenant_id")
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SecurityGroupRuleDto> getRules() {
		return rules;
	}

	public void setRules(List<SecurityGroupRuleDto> rules) {
		this.rules = rules;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}	
