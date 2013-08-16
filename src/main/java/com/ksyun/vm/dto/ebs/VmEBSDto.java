package com.ksyun.vm.dto.ebs;

import com.ksyun.vm.dto.common.CoreDto;

public class VmEBSDto extends CoreDto{

	private String id;
	private String ebs_id;
	private String instance_id;
	private String device;
	private String volume_id;
	private String attach_id;

    public String getAttach_id() {
        return attach_id;
    }

    public void setAttach_id(String attach_id) {
        this.attach_id = attach_id;
    }

    public String getVolume_id() {
		return volume_id;
	}
	public void setVolume_id(String volume_id) {
		this.volume_id = volume_id;
	}
	/**
	 * @return the ebs_id
	 */
	public String getEbs_id() {
		return ebs_id;
	}
	/**
	 * @param ebs_id the ebs_id to set
	 */
	public void setEbs_id(String ebs_id) {
		this.ebs_id = ebs_id;
	}
	/**
	 * @return the instance_id
	 */
	public String getInstance_id() {
		return instance_id;
	}
	/**
	 * @param instance_id the instance_id to set
	 */
	public void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}
	private String serverId;
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
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}
	/**
	 * @param serverId the serverId to set
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}
	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}
}
