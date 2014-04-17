package com.ksyun.vm.pojo.lbs;

import com.ksyun.vm.pojo.BasePo;

/**
 * member 机器
 * 
 * @author XueQi
 * 
 */
public class MemberPOJO extends BasePo {
	private String status;
	private String protocol_port;
	private String vip_id;
	private String weight;
	private String admin_state_up;
	private String tenant_id;
	private String create_time;
	private String address;
	private String deleted_time;
	private String status_description;
	private String vm_id;
	private String id;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProtocol_port() {
		return protocol_port;
	}

	public void setProtocol_port(String protocol_port) {
		this.protocol_port = protocol_port;
	}

	public String getVip_id() {
		return vip_id;
	}

	public void setVip_id(String vip_id) {
		this.vip_id = vip_id;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getAdmin_state_up() {
		return admin_state_up;
	}

	public void setAdmin_state_up(String admin_state_up) {
		this.admin_state_up = admin_state_up;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeleted_time() {
		return deleted_time;
	}

	public void setDeleted_time(String deleted_time) {
		this.deleted_time = deleted_time;
	}

	public String getStatus_description() {
		return status_description;
	}

	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}

	public String getVm_id() {
		return vm_id;
	}

	public void setVm_id(String vm_id) {
		this.vm_id = vm_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
