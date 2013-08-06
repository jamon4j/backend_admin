package com.ksyun.vm.dto.ebs;

import com.ksyun.vm.dto.common.CoreDto;

public class VmEBSDto extends CoreDto{

	private String id;
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
	private String volumeId;
	private String device;
	/**
	 * @return the volumeId
	 */
	public String getVolumeId() {
		return volumeId;
	}
	/**
	 * @param volumeId the volumeId to set
	 */
	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
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
