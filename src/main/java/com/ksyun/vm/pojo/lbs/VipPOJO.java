package com.ksyun.vm.pojo.lbs;

import java.util.List;

import com.ksyun.vm.pojo.BasePo;

/**
 * 规则VIP POJO
 * 
 * @author XueQi
 * 
 */
public class VipPOJO extends BasePo {
	private String status;
	private String lb_method;
	private String protocol;
	private String description;
	private List<String> health_monitors;
	private String subnet_id;
	private String deleted_time;
	private String admin_state_up;
	private String connection_limit;
	private String pool_id;
	private SessionPersistencePOJO session_persistence;
	private String protocol_port;
	private String create_time;
	private List<String> members;
	private String address;
	private String port_id;
	private String status_description;
	private List<HealthMonitorsStatusPOJO> health_monitors_status;
	private String id;
	private String tenant_id;
	private String name;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLb_method() {
		return lb_method;
	}

	public void setLb_method(String lb_method) {
		this.lb_method = lb_method;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getHealth_monitors() {
		return health_monitors;
	}

	public void setHealth_monitors(List<String> health_monitors) {
		this.health_monitors = health_monitors;
	}

	public String getSubnet_id() {
		return subnet_id;
	}

	public void setSubnet_id(String subnet_id) {
		this.subnet_id = subnet_id;
	}

	public String getDeleted_time() {
		return deleted_time;
	}

	public void setDeleted_time(String deleted_time) {
		this.deleted_time = deleted_time;
	}

	public String getAdmin_state_up() {
		return admin_state_up;
	}

	public void setAdmin_state_up(String admin_state_up) {
		this.admin_state_up = admin_state_up;
	}

	public String getConnection_limit() {
		return connection_limit;
	}

	public void setConnection_limit(String connection_limit) {
		this.connection_limit = connection_limit;
	}

	public String getPool_id() {
		return pool_id;
	}

	public void setPool_id(String pool_id) {
		this.pool_id = pool_id;
	}

	public SessionPersistencePOJO getSession_persistence() {
		return session_persistence;
	}

	public void setSession_persistence(
			SessionPersistencePOJO session_persistence) {
		this.session_persistence = session_persistence;
	}

	public String getProtocol_port() {
		return protocol_port;
	}

	public void setProtocol_port(String protocol_port) {
		this.protocol_port = protocol_port;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPort_id() {
		return port_id;
	}

	public void setPort_id(String port_id) {
		this.port_id = port_id;
	}

	public String getStatus_description() {
		return status_description;
	}

	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}

	public List<HealthMonitorsStatusPOJO> getHealth_monitors_status() {
		return health_monitors_status;
	}

	public void setHealth_monitors_status(
			List<HealthMonitorsStatusPOJO> health_monitors_status) {
		this.health_monitors_status = health_monitors_status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
