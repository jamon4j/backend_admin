package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

import java.util.Date;

/**
 * User: liuchuandong
 * Date: 14-3-31
 * Time: 下午5:42
 * Func:
 */
public class RDSValidationPo extends BasePo {

    private String id;
    private String userName;
    private String email;
    private String name;
    private String orderId;
    private String rdsId;
    private Integer orderType;
    private String type;
    private String autobackup_at;
    private String duration;
    private String expireAfter;
    private String adminUser;
    private String adminPassword;
    private String port;
    private String ram;
    private String vcpus;
    private String disk;
    private String serviceType;
    private Date validTime;
    private Date createTime;
    private Date updateTime;


    public String getRdsId() {
        return rdsId;
    }

    public void setRdsId(String rdsId) {
        this.rdsId = rdsId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAutobackup_at() {
        return autobackup_at;
    }

    public void setAutobackup_at(String autobackup_at) {
        this.autobackup_at = autobackup_at;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getExpireAfter() {
        return expireAfter;
    }

    public void setExpireAfter(String expireAfter) {
        this.expireAfter = expireAfter;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getVcpus() {
        return vcpus;
    }

    public void setVcpus(String vcpus) {
        this.vcpus = vcpus;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
