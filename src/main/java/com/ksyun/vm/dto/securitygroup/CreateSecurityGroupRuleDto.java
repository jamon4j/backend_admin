package com.ksyun.vm.dto.securitygroup;

import com.alibaba.fastjson.annotation.JSONField;
import com.ksyun.vm.dto.common.CoreDto;
import com.ksyun.vm.dto.securitygroup.SecurityGroupRuleDto.IpRange;

public class CreateSecurityGroupRuleDto extends CoreDto{

	
	private String fromPort;
	private String ipProtocol;
	private String toPort;
	private String parentGroupId;
	private String cidr;
	private String groupId;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@JSONField(name = "from_port")
	public String getFromPort() {
		return fromPort;
	}
	@JSONField(name = "from_port")
	public void setFromPort(String fromPort) {
		this.fromPort = fromPort;
	}
	@JSONField(name = "ip_protocol")
	public String getIpProtocol() {
		return ipProtocol;
	}
	@JSONField(name = "ip_protocol")
	public void setIpProtocol(String ipProtocol) {
		this.ipProtocol = ipProtocol;
	}
	@JSONField(name = "to_port")
	public String getToPort() {
		return toPort;
	}
	@JSONField(name = "to_port")
	public void setToPort(String toPort) {
		this.toPort = toPort;
	}
	@JSONField(name = "parent_group_id")
	public String getParentGroupId() {
		return parentGroupId;
	}
	@JSONField(name = "parent_group_id")
	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
	
	public String getCidr() {
		return cidr;
	}
	public void setCidr(String cidr) {
		this.cidr = cidr;
	}
	@JSONField(name = "group_id")
	public String getGroupId() {
		return groupId;
	}
	@JSONField(name = "group_id")
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
