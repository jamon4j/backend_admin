package com.ksyun.vm.dto.vm;

import com.ksyun.vm.dto.common.CoreDto;

public class FaultDto extends CoreDto {
	private String message;
	private String code;
	private String created;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
}
