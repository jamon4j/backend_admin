package com.ksyun.vm.pojo.stat;

import com.ksyun.vm.pojo.BasePo;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-11-4
 * Time: 下午12:39
 * Func:
 */
public class StatZone extends BasePo {

    private String zone_name;
    private List<HostUsage> host_usage;

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public List<HostUsage> getHost_usage() {
        return host_usage;
    }

    public void setHost_usage(List<HostUsage> host_usage) {
        this.host_usage = host_usage;
    }
}
