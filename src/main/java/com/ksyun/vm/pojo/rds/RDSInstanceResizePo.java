package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 14-4-9
 * Time: 下午8:49
 * Func:
 */
public class RDSInstanceResizePo extends BasePo {
    private String user_id;
    private String email;
    private String rds_id;
    private String ram;
    private String vcpus;
    private String disk;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRds_id() {
        return rds_id;
    }

    public void setRds_id(String rds_id) {
        this.rds_id = rds_id;
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
}
