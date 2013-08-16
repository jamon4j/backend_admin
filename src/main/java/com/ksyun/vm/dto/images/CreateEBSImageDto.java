package com.ksyun.vm.dto.images;

import com.ksyun.vm.dto.common.CoreDto;

public class CreateEBSImageDto extends CoreDto{
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String ebs_id;
	private String name;
    private String description;

    public String getEbs_id() {
        return ebs_id;
    }

    public void setEbs_id(String ebs_id) {
        this.ebs_id = ebs_id;
    }
}
