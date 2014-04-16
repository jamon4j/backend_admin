package com.ksyun.vm.pojo.lbs;

import java.util.List;

import com.ksyun.vm.pojo.BasePo;

/**
 * 负载均衡POOL的POJO
 * 
 * @author XueQi
 * 
 */
public class PoolPOJO extends BasePo {
	private String status;
	private List<String> vips;
	private String description;
	private String admin_state_up;
	private String subnet_id;
	private String deleted_time;
	private String tenant_id;
	private String egress;
	private String create_time;
	private String provider;
	private String status_description;
	private String ip_address;
	private String id;
	private String name;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdmin_state_up() {
		return admin_state_up;
	}

	public void setAdmin_state_up(String admin_state_up) {
		this.admin_state_up = admin_state_up;
	}

	public String getSubnet_id() {
		return subnet_id;
	}

	public void setSubnet_id(String subnet_id) {
		this.subnet_id = subnet_id;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

	public String getEgress() {
		return egress;
	}

	public void setEgress(String egress) {
		this.egress = egress;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getStatus_description() {
		return status_description;
	}

	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
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

	public List<String> getVips() {
		return vips;
	}

	public void setVips(List<String> vips) {
		this.vips = vips;
	}

	public String getDeleted_time() {
		return deleted_time;
	}

	public void setDeleted_time(String deleted_time) {
		this.deleted_time = deleted_time;
	}

}
