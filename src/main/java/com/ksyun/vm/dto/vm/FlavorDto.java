package com.ksyun.vm.dto.vm;

import java.util.List;

import com.ksyun.vm.dto.common.CoreDto;
import com.ksyun.vm.dto.common.LinkDto;

public class FlavorDto extends CoreDto{
	private String id;
	private List<LinkDto> links;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<LinkDto> getLinks() {
		return links;
	}
	public void setLinks(List<LinkDto> links) {
		this.links = links;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
