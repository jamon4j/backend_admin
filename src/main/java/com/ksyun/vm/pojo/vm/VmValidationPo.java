package com.ksyun.vm.pojo.vm;

import com.ksyun.vm.pojo.BasePo;

import java.util.Date;

public class VmValidationPo extends BasePo {
    private Long id;

    private String name;

    private String imageId;

    private String instanceCount;

    private String vcpu;

    private String securityGroups;

    private String networkBandwidth;

    private String rootDisk;

    private String memory;

    private String vmId;

    private String userName;

    private String status;

    private Date createTime;

    private Date updateTime;

    private Date validTime;
    
    private String password;
    
    private String orderId;
    
    private Integer orderType;

    public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(String instanceCount) {
        this.instanceCount = instanceCount;
    }

    public String getVcpu() {
        return vcpu;
    }

    public void setVcpu(String vcpu) {
        this.vcpu = vcpu;
    }

    public String getSecurityGroups() {
        return securityGroups;
    }

    public void setSecurityGroups(String securityGroups) {
        this.securityGroups = securityGroups;
    }

    public String getNetworkBandwidth() {
        return networkBandwidth;
    }

    public void setNetworkBandwidth(String networkBandwidth) {
        this.networkBandwidth = networkBandwidth;
    }

    public String getRootDisk() {
        return rootDisk;
    }

    public void setRootDisk(String rootDisk) {
        this.rootDisk = rootDisk;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }
}