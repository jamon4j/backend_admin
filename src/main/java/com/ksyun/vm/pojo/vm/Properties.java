package com.ksyun.vm.pojo.vm;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-10-14
 * Time: 下午4:39
 * Func:
 */
public class Properties extends BasePo{
    private String os_version;
    private String storage_locate;

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getStorage_locate() {
        return storage_locate;
    }

    public void setStorage_locate(String storage_locate) {
        this.storage_locate = storage_locate;
    }
}
