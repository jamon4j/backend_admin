package com.ksyun.vm.service;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 下午1:11
 * Func:
 */
public enum InstanceUpdField {

    RAM("ram","内存"),
    VCPUS("vcpus","cpu"),
    DISK("disk","硬盘"),
    PASSWORD("password","密码"),
    AUTOBACKUP_AT("autobackup_at","备份时间");

    private InstanceUpdField(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private String type;
    private String desc;

    public String getType() {
        return type;
    }
    public String getDesc() {
        return desc;
    }
}
