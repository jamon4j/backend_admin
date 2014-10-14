package com.ksyun.vm.pojo.memdb;



import com.ksyun.vm.pojo.BasePo;
/**
 * Created by LiYang14 on 2014/9/16.
 */
public class ClusterPOJO extends  BasePo {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHa_cluster() {
        return ha_cluster;
    }

    public void setHa_cluster(Boolean ha_cluster) {
        this.ha_cluster = ha_cluster;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSecurity_group_id() {
        return security_group_id;
    }

    public void setSecurity_group_id(String security_group_id) {
        this.security_group_id = security_group_id;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public SecurityGroupPOJO getSecuritygroup() {
        return securitygroup;
    }

    public void setSecuritygroup(SecurityGroupPOJO securitygroup) {
        this.securitygroup = securitygroup;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }


    private  String status;
    private  String description;
    private  String vip;
    private  String id;
    private  int port;
    private  int size;
    private  String name;
    private  String security_group_id;
    private Boolean ha_cluster;
    private  String  task_status;
    private  SecurityGroupPOJO securitygroup;


    private  String protocol;
}
