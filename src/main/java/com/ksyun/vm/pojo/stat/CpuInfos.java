package com.ksyun.vm.pojo.stat;

import com.ksyun.vm.pojo.BasePo;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-11-4
 * Time: 下午5:02
 * Func:
 */
public class CpuInfos extends BasePo{
    /*厂商*/
    private String vendor;
    //model
    private String model;
    //架构
    private String arch;
    //特性
    private List<String> features;
    //拓扑
    private Topologys topology;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public Topologys getTopology() {
        return topology;
    }

    public void setTopology(Topologys topology) {
        this.topology = topology;
    }
}
