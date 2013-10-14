package com.ksyun.vm.pojo.snapshot;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-10-12
 * Time: 下午6:23
 * Func:
 */
public class SnapshotEBSPojo extends BasePo{

    private String status;
    private String ebs_id;
    private String description;
    private String created_at;
    private String name;
    private String progress;
    private String id;
    private String size;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEbs_id() {
        return ebs_id;
    }

    public void setEbs_id(String ebs_id) {
        this.ebs_id = ebs_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
