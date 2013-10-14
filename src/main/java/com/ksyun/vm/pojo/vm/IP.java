package com.ksyun.vm.pojo.vm;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-10-11
 * Time: 上午11:59
 * Func:
 */
public class IP extends BasePo {
    private String version;
    private String addr;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
