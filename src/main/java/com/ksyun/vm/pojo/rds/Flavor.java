package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 上午10:49
 * Func:
 */
public class Flavor extends BasePo{

    private String id;
    private List<Links> links;
    private String name;
    private String local_storage;
    private String ram;
    private String vcpus;
    private String disk;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocal_storage() {
        return local_storage;
    }

    public void setLocal_storage(String local_storage) {
        this.local_storage = local_storage;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Links> getLinks() {
        return links;
    }

    public void setLinks(List<Links> links) {
        this.links = links;
    }
}
