package com.ksyun.vm.pojo.vm;

import com.alibaba.fastjson.annotation.JSONField;
import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-10-14
 * Time: 下午4:37
 * Func:
 */
public class ImagePojo extends BasePo {
    private String status;
    private String deleted;
    private String container_format;
    private String min_ram;
    private String updated_at;
    private String owner;
    private String min_disk;
    private String is_public;
    private String deleted_at;
    private Properties properties;
    private String size;
    private String name;
    private String checksum;
    private String created_at;
    private String disk_format;
    private String id;
    @JSONField(name = "protected")
    private String protect;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getContainer_format() {
        return container_format;
    }

    public void setContainer_format(String container_format) {
        this.container_format = container_format;
    }

    public String getMin_ram() {
        return min_ram;
    }

    public void setMin_ram(String min_ram) {
        this.min_ram = min_ram;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMin_disk() {
        return min_disk;
    }

    public void setMin_disk(String min_disk) {
        this.min_disk = min_disk;
    }

    public String getIs_public() {
        return is_public;
    }

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDisk_format() {
        return disk_format;
    }

    public void setDisk_format(String disk_format) {
        this.disk_format = disk_format;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProtect() {
        return protect;
    }

    public void setProtect(String protect) {
        this.protect = protect;
    }
}

