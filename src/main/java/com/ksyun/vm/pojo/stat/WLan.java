package com.ksyun.vm.pojo.stat;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-12-22
 * Time: 下午10:13
 * Func:
 */
public class WLan extends BasePo {
    private String ipnum;
    private String availability_ipnum;
    private String name;
    private String used_ipnum;

    public String getIpnum() {
        return ipnum;
    }

    public void setIpnum(String ipnum) {
        this.ipnum = ipnum;
    }

    public String getAvailability_ipnum() {
        return availability_ipnum;
    }

    public void setAvailability_ipnum(String availability_ipnum) {
        this.availability_ipnum = availability_ipnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsed_ipnum() {
        return used_ipnum;
    }

    public void setUsed_ipnum(String used_ipnum) {
        this.used_ipnum = used_ipnum;
    }
}
