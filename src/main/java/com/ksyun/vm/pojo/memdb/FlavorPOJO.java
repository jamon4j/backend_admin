package com.ksyun.vm.pojo.memdb;
import com.ksyun.vm.pojo.BasePo;
/**
 * Created by LiYang14 on 2014/9/16.
 */
public class FlavorPOJO extends BasePo  {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private  String name;
    private  int ram;
    private  String vcpus;
    private  String disk;
    private int id;
}
