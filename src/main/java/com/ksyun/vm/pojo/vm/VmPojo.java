package com.ksyun.vm.pojo.vm;

import com.alibaba.fastjson.annotation.JSONField;
import com.ksyun.vm.pojo.BasePo;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-10-10
 * Time: 下午8:07
 * Func:
 */
public class VmPojo extends BasePo{
    private String status;
    private String vm_status;
    private String updated;
    private Address addresses;
    private String hypervisor_hostname;
    private String image_id;
    private String host;
    private String task_state;
    private String id;
    private List<SecGroup> security_groups;
    private String user_id;
    private String name;
    private String created;
    private String tenant_id;
    private String instance_name;
    private InstanceType instance_type;
    private String power_state;
    private String progress;
    private Metadatas metadata;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVm_status() {
        return vm_status;
    }

    public void setVm_status(String vm_status) {
        this.vm_status = vm_status;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Address getAddresses() {
        return addresses;
    }

    public void setAddresses(Address addresses) {
        this.addresses = addresses;
    }

    public String getHypervisor_hostname() {
        return hypervisor_hostname;
    }

    public void setHypervisor_hostname(String hypervisor_hostname) {
        this.hypervisor_hostname = hypervisor_hostname;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTask_state() {
        return task_state;
    }

    public void setTask_state(String task_state) {
        this.task_state = task_state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SecGroup> getSecurity_groups() {
        return security_groups;
    }

    public void setSecurity_groups(List<SecGroup> security_groups) {
        this.security_groups = security_groups;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getInstance_name() {
        return instance_name;
    }

    public void setInstance_name(String instance_name) {
        this.instance_name = instance_name;
    }

    public InstanceType getInstance_type() {
        return instance_type;
    }

    public void setInstance_type(InstanceType instance_type) {
        this.instance_type = instance_type;
    }

    public String getPower_state() {
        return power_state;
    }

    public void setPower_state(String power_state) {
        this.power_state = power_state;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Metadatas getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadatas metadata) {
        this.metadata = metadata;
    }
}


