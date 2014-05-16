package com.ksyun.vm.pojo.zone;

import com.ksyun.vm.pojo.stat.IpStat;
import com.ksyun.vm.pojo.stat.WLan;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ZHANGNAN4 on 2014-5-16.
 * Email: zn.share@gmail.com
 */
public class NetsInfo {

    private int wan_ipnum_zones;
    private int wan_availability_ipnum_zones;
    private int wan_used_ipnum_zones;
    private int wan_network_bindwidth;
    private int wan_used_ipnum_zones_scale;


    private Set<String> wLanNames = new HashSet<>();

    private NetsInfo(List<IpStat> ipStats) {
        if (ipStats != null) {
            for (IpStat ipStat : ipStats) {
                if (ipStat.getWan() != null) {
                    for (WLan wLan : ipStat.getWan()) {
                        if (!wLanNames.contains(wLan.getName())) {
                            wan_ipnum_zones += Integer.parseInt(wLan.getIpnum());
                            wan_availability_ipnum_zones += Integer.parseInt(wLan.getAvailability_ipnum());
                            wan_used_ipnum_zones += Integer.parseInt(wLan.getUsed_ipnum());
                            wLanNames.add(wLan.getName());
                        }
                    }
                }
            }
            wan_used_ipnum_zones_scale = wan_used_ipnum_zones / wan_ipnum_zones * 100;
        }
    }


    public static final NetsInfo getInstance(List<IpStat> ipStats) {
        return new NetsInfo(ipStats);
    }

    public int getWan_used_ipnum_zones_scale() {
        return wan_used_ipnum_zones_scale;
    }

    public void setWan_used_ipnum_zones_scale(int wan_used_ipnum_zones_scale) {
        this.wan_used_ipnum_zones_scale = wan_used_ipnum_zones_scale;
    }

    public int getWan_ipnum_zones() {
        return wan_ipnum_zones;
    }

    public void setWan_ipnum_zones(int wan_ipnum_zones) {
        this.wan_ipnum_zones = wan_ipnum_zones;
    }

    public int getWan_availability_ipnum_zones() {
        return wan_availability_ipnum_zones;
    }

    public void setWan_availability_ipnum_zones(int wan_availability_ipnum_zones) {
        this.wan_availability_ipnum_zones = wan_availability_ipnum_zones;
    }

    public int getWan_used_ipnum_zones() {
        return wan_used_ipnum_zones;
    }

    public void setWan_used_ipnum_zones(int wan_used_ipnum_zones) {
        this.wan_used_ipnum_zones = wan_used_ipnum_zones;
    }

    public int getWan_network_bindwidth() {
        return wan_network_bindwidth;
    }

    public void setWan_network_bindwidth(int wan_network_bindwidth) {
        this.wan_network_bindwidth = wan_network_bindwidth;
    }
}
