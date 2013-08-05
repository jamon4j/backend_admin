package com.ksyun.vm.dto;

import java.util.List;

import com.ksyun.vm.dto.common.CoreDto;
import com.ksyun.vm.dto.common.LinkDto;

public class VmListDto extends CoreDto {
	private String id;
	private List<LinkDto> linkDtoList;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<LinkDto> getLinkDtoList() {
		return linkDtoList;
	}
	public void setLinkDtoList(List<LinkDto> linkDtoList) {
		this.linkDtoList = linkDtoList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
