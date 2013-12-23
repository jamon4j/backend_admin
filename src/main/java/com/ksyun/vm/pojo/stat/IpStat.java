package com.ksyun.vm.pojo.stat;

import com.ksyun.vm.pojo.BasePo;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-12-22
 * Time: 下午9:57
 * Func:
 */
public class IpStat extends BasePo {

    private String zone_name;
    private List<WLan> lan;
    private List<WLan> wan;

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public List<WLan> getLan() {
        return lan;
    }

    public void setLan(List<WLan> lan) {
        this.lan = lan;
    }

    public List<WLan> getWan() {
        return wan;
    }

    public void setWan(List<WLan> wan) {
        this.wan = wan;
    }
}
