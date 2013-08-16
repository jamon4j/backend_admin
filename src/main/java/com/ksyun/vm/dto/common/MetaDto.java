package com.ksyun.vm.dto.common;

public class MetaDto extends CoreDto {
	private String storage_location;
	private String contents;
	public String getStorage_location() {
		return storage_location;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setStorage_location(String storageLocation) {
		storage_location = storageLocation;
	}
	
}
