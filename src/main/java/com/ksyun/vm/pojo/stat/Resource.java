package com.ksyun.vm.pojo.stat;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-11-4
 * Time: 下午5:47
 * Func:
 */
public class Resource extends BasePo{
    //用户名
    private String project;
    private String email;

    //当前使用内存数
    private String memory_mb;
    //当前cpu数
    private String cpu;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemory_mb() {
        return memory_mb;
    }

    public void setMemory_mb(String memory_mb) {
        this.memory_mb = memory_mb;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }
}
