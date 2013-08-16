package com.ksyun.vm.dto.ebs;

import com.ksyun.vm.dto.common.CoreDto;

public class Attachments extends CoreDto{
	private String device;
	private String server_id;
	private String id;
	private String volume_id;
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
	/**
	 * @return the server_id
	 */
	public String getServer_id() {
		return server_id;
	}
	/**
	 * @param server_id the server_id to set
	 */
	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}
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
	 * @return the volume_id
	 */
	public String getVolume_id() {
		return volume_id;
	}
	/**
	 * @param volume_id the volume_id to set
	 */
	public void setVolume_id(String volume_id) {
		this.volume_id = volume_id;
	}
}