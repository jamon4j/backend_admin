package com.ksyun.vm.pojo.stat;

import com.ksyun.vm.pojo.BasePo;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-11-4
 * Time: 下午3:04
 * Func:
 */
public class HostUsage extends BasePo {

    /*使用的cpu数*/
    private String vcpus_used;
    /*hypervisor类型*/
    private String hypervisor_type;
    private String hostname;
    /*已使用磁盘大小*/
    private String local_gb_used;
    /*总内存数*/
    private String memory_mb;
    /*总cpu数*/
    private String vcpus;
    /*剩余内存数*/
    private String free_ram_mb;
    /*虚机个数*/
    private String running_vms;
    /*剩余磁盘数*/
    private String free_disk_gb;
    /*总磁盘空间*/
    private String local_gb;
    /*memory使用数*/
    private String memory_mb_used;
    /*cpu信息*/
    private String cpu_info;
    private CpuInfos cpu_infos;

    public CpuInfos getCpu_infos() {
        return cpu_infos;
    }

    public void setCpu_infos(CpuInfos cpu_infos) {
        this.cpu_infos = cpu_infos;
    }

    public String getVcpus_used() {
        return vcpus_used;
    }

    public void setVcpus_used(String vcpus_used) {
        this.vcpus_used = vcpus_used;
    }

    public String getHypervisor_type() {
        return hypervisor_type;
    }

    public void setHypervisor_type(String hypervisor_type) {
        this.hypervisor_type = hypervisor_type;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getLocal_gb_used() {
        return local_gb_used;
    }

    public void setLocal_gb_used(String local_gb_used) {
        this.local_gb_used = local_gb_used;
    }

    public String getMemory_mb() {
        return memory_mb;
    }

    public void setMemory_mb(String memory_mb) {
        this.memory_mb = memory_mb;
    }

    public String getVcpus() {
        return vcpus;
    }

    public void setVcpus(String vcpus) {
        this.vcpus = vcpus;
    }

    public String getFree_ram_mb() {
        return free_ram_mb;
    }

    public void setFree_ram_mb(String free_ram_mb) {
        this.free_ram_mb = free_ram_mb;
    }

    public String getRunning_vms() {
        return running_vms;
    }

    public void setRunning_vms(String running_vms) {
        this.running_vms = running_vms;
    }

    public String getFree_disk_gb() {
        return free_disk_gb;
    }

    public void setFree_disk_gb(String free_disk_gb) {
        this.free_disk_gb = free_disk_gb;
    }

    public String getLocal_gb() {
        return local_gb;
    }

    public void setLocal_gb(String local_gb) {
        this.local_gb = local_gb;
    }

    public String getMemory_mb_used() {
        return memory_mb_used;
    }

    public void setMemory_mb_used(String memory_mb_used) {
        this.memory_mb_used = memory_mb_used;
    }

    public String getCpu_info() {
        return cpu_info;
    }

    public void setCpu_info(String cpu_info) {
        this.cpu_info = cpu_info;
    }
}

