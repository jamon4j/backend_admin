package com.ksyun.vm.dto.vm;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.ksyun.vm.dto.common.CoreDto;

public class AddressesDto extends CoreDto{
	@JSONField(name="public")
	private List<IPDto> publicAddress;
	@JSONField(name="private")
	private List<IPDto> privateAddress;
	public List<IPDto> getPublicAddress() {
		return publicAddress;
	}
	public void setPublicAddress(List<IPDto> publicAddress) {
		this.publicAddress = publicAddress;
	}
	public List<IPDto> getPrivateAddress() {
		return privateAddress;
	}
	public void setPrivateAddress(List<IPDto> privateAddress) {
		this.privateAddress = privateAddress;
	}
}
