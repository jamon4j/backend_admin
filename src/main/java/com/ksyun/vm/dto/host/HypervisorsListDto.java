package com.ksyun.vm.dto.host;

import com.ksyun.vm.dto.common.CoreDto;

/**
 * 物理机列表Dto
 * @author yuri
 *
 */
public class HypervisorsListDto extends CoreDto{
	private String id;
	private String hypervisor_hostname;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getHypervisor_hostname() {
		return hypervisor_hostname;
	}
	public void setHypervisor_hostname(String hypervisorHostname) {
		hypervisor_hostname = hypervisorHostname;
	}

	
}
