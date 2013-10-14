package com.ksyun.vm.pojo.snapshot;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-10-12
 * Time: 下午6:16
 * Func:
 */
public class SnapshotVmPojo extends BasePo {
    private String status;
    private String updated;
    private String name;
    private String created;
    private String minDisk;
    private String progress;
    private String minRam;
    private String vm_id;
    private String id;
    private VMMetadata metadata;

    public VMMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(VMMetadata metadata) {
        this.metadata = metadata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMinDisk() {
        return minDisk;
    }

    public void setMinDisk(String minDisk) {
        this.minDisk = minDisk;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getMinRam() {
        return minRam;
    }

    public void setMinRam(String minRam) {
        this.minRam = minRam;
    }

    public String getVm_id() {
        return vm_id;
    }

    public void setVm_id(String vm_id) {
        this.vm_id = vm_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
