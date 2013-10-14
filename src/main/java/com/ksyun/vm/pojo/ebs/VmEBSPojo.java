package com.ksyun.vm.pojo.ebs;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-10-14
 * Time: 下午3:46
 * Func:
 */
public class VmEBSPojo extends BasePo{
    private String device;
    private String ebs_id;
    private String vm_id;
    private String id;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getEbs_id() {
        return ebs_id;
    }

    public void setEbs_id(String ebs_id) {
        this.ebs_id = ebs_id;
    }

    public String getVm_id() {
        return vm_id;
    }

    public void setVm_id(String vm_id) {
        this.vm_id = vm_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
