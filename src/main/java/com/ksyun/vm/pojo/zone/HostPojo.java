package com.ksyun.vm.pojo.zone;

import com.ksyun.vm.pojo.BasePo;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-10-10
 * Time: 上午11:16
 * Func:
 */
public class HostPojo extends BasePo {
    private Service service;
    private String vcpus_used;
    private String hypervisor_type;
    private String local_gb_used;
    private String hypervisor_hostname;
    private String id;
    private String memory_mb;
    private String current_workload;
    private String vcpus;
    private String free_ram_mb;
    private String running_vms;
    private String free_disk_gb;
    private String hypervisor_version;
    private String disk_available_least;
    private String local_gb;
    private String cpu_info;
    private String memory_mb_used;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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

    public String getLocal_gb_used() {
        return local_gb_used;
    }

    public void setLocal_gb_used(String local_gb_used) {
        this.local_gb_used = local_gb_used;
    }

    public String getHypervisor_hostname() {
        return hypervisor_hostname;
    }

    public void setHypervisor_hostname(String hypervisor_hostname) {
        this.hypervisor_hostname = hypervisor_hostname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemory_mb() {
        return memory_mb;
    }

    public void setMemory_mb(String memory_mb) {
        this.memory_mb = memory_mb;
    }

    public String getCurrent_workload() {
        return current_workload;
    }

    public void setCurrent_workload(String current_workload) {
        this.current_workload = current_workload;
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

    public String getHypervisor_version() {
        return hypervisor_version;
    }

    public void setHypervisor_version(String hypervisor_version) {
        this.hypervisor_version = hypervisor_version;
    }

    public String getDisk_available_least() {
        return disk_available_least;
    }

    public void setDisk_available_least(String disk_available_least) {
        this.disk_available_least = disk_available_least;
    }

    public String getLocal_gb() {
        return local_gb;
    }

    public void setLocal_gb(String local_gb) {
        this.local_gb = local_gb;
    }

    public String getCpu_info() {
        return cpu_info;
    }

    public void setCpu_info(String cpu_info) {
        this.cpu_info = cpu_info;
    }

    public String getMemory_mb_used() {
        return memory_mb_used;
    }

    public void setMemory_mb_used(String memory_mb_used) {
        this.memory_mb_used = memory_mb_used;
    }
}
class Service{
    private String host;
    private String id;

    String getHost() {
        return host;
    }

    void setHost(String host) {
        this.host = host;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }
}
class CpuInfo{
    private String vendor;
    private String model;
    private String arch;
    private List<String> features;
    private Topology topology;

    String getVendor() {
        return vendor;
    }

    void setVendor(String vendor) {
        this.vendor = vendor;
    }

    String getModel() {
        return model;
    }

    void setModel(String model) {
        this.model = model;
    }

    String getArch() {
        return arch;
    }

    void setArch(String arch) {
        this.arch = arch;
    }

    List<String> getFeatures() {
        return features;
    }

    void setFeatures(List<String> features) {
        this.features = features;
    }

    Topology getTopology() {
        return topology;
    }

    void setTopology(Topology topology) {
        this.topology = topology;
    }
}
class Topology{
    private String cores;
    private String threads;
    private String sockets;

    String getCores() {
        return cores;
    }

    void setCores(String cores) {
        this.cores = cores;
    }

    String getThreads() {
        return threads;
    }

    void setThreads(String threads) {
        this.threads = threads;
    }

    String getSockets() {
        return sockets;
    }

    void setSockets(String sockets) {
        this.sockets = sockets;
    }
}
