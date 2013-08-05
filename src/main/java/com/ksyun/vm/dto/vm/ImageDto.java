package com.ksyun.vm.dto.vm;

import java.util.List;

import com.ksyun.vm.dto.common.CoreDto;
import com.ksyun.vm.dto.common.LinkDto;

public class ImageDto extends CoreDto{
	private String id;
	private List<LinkDto> links;
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
}
