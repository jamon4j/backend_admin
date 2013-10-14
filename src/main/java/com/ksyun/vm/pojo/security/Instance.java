package com.ksyun.vm.pojo.security;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-10-11
 * Time: 下午4:13
 * Func:
 */
public class Instance extends BasePo{
    private String vm_id;
    private String name;

    public String getVm_id() {
        return vm_id;
    }

    public void setVm_id(String vm_id) {
        this.vm_id = vm_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
