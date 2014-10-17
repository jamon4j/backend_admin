package com.ksyun.vm.controller;

import java.io.IOException;
import java.lang.Object;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.Region;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.stat.CpuInfos;
import com.ksyun.vm.pojo.stat.HostUsage;
import com.ksyun.vm.pojo.stat.IpStat;
import com.ksyun.vm.pojo.stat.StatZone;
import com.ksyun.vm.pojo.zone.NetsInfo;
import com.ksyun.vm.pojo.zone.ZonePojo;
import com.ksyun.vm.service.StatService;
import com.ksyun.vm.service.ZoneService;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;
import org.jruby.RubyProcess;
import org.omg.CORBA.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA. User: yuri Date: 13-7-9 Time: 下午12:58 To change
 * this template use File | Settings | File Templates.
 */
@Controller
public class ZoneController {

    @Autowired
    private ZoneService zoneService;
    @Autowired
    private StatService statService;

    //zone列表
    @RequestMapping("/g/zonelist")
    public ModelAndView returnZoneList(HttpServletRequest request, ModelAndView mav) {
        String Region = request.getParameter("Region");
        List<ZonePojo> list = null;
        List<StatZone> stat_zone = null;
        ;
        List<IpStat> ip_stat = null;
        NetsInfo netsInfo = null;
        try {
            list = zoneService.getZoneList(Region);
            stat_zone = statService.getStatZone(Region);
            ip_stat = statService.getIpStat();


            for (ZonePojo pojo : list) {
                for (StatZone zone : stat_zone) {
                    if (pojo.getName().equals(zone.getZone_name())) {
                        pojo.setStatZone(zone);
                        for (HostUsage usage : zone.getHost_usage()) {
				if(usage!=null)
				{
                            usage.setCpu_infos(JSONObject.parseObject(usage.getCpu_info(), CpuInfos.class));
                            System.out.println(Integer.parseInt(usage.getVcpus_used()));
                            System.out.println(Integer.parseInt(usage.getLocal_gb_used()));
                            System.out.println(Integer.parseInt(usage.getMemory_mb_used()));//vcpus_used

				}
                        }
                        break;
                    }
                }
                for (IpStat ipStat : ip_stat) {
                    if (pojo.getName().equals(ipStat.getZone_name())) {
                        pojo.setIpStat(ipStat);
                    }
                }


            }
            netsInfo = NetsInfo.getInstance(ip_stat);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        mav.addObject("zonelist", list);
        mav.addObject("netsInfo", netsInfo);
        if (Region.equals("RegionOne")) {
            mav.setViewName("/gestion/zone/zone_list");
        } else {
            mav.setViewName("/gestion/zone/SHzone_list");
        }
        return mav;
    }

    //zone列表
    @RequestMapping("/g/zonelist/ajax/{region}")
    @ResponseBody
    public String returnZoneAjaxList(HttpServletRequest request, @PathVariable("region") String region, ModelAndView mav) {
        List<ZonePojo> list = null;
        try {

            list = zoneService.getZoneList(region);
            return JSONArray.toJSONString(list);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/api/zonelist", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String returnZoneListAPI(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<String, Object>();


        Constants.setPropertyValue(InitConst.HTTP_HOST,
                Constants.getPropertyValue(InitConst.HTTP_HOST_PUBLIC));
        Constants.setPropertyValue(InitConst.HTTP_PORT,
                Constants.getPropertyValue(InitConst.HTTP_PORT_PUBLIC));

        int vcpus_zones = 0;
        int vcpus_used_zones = 0;
        String vcpus_used_zones_scale_int = "0";
        int memory_mb_zones = 0;
        int memory_mb_used_zones = 0;
        String memory_mb_used_zones_scale_int = "0";
        int local_gb_zones = 0;
        int local_gb_used_zones = 0;
        String local_gb_used_zones_scale_int = "0";
        int wan_ipnum_zones = 0;
        int wan_used_ipnum_zones = 0;
        String wan_used_ipnum_zones_scale = "0";

        List<ZonePojo> list = null;
        List<StatZone> stat_zone = null;
        List<IpStat> ip_stat = null;
        NetsInfo netsInfo = null;
        String Region = request.getParameter("Region");
        try {

            list = zoneService.getZoneList(Region);
            stat_zone = statService.getStatZone(Region);


            for (StatZone zone : stat_zone) {
                for (HostUsage usage : zone.getHost_usage()) {
                    if (usage != null) {
                        vcpus_zones += Integer.parseInt(usage.getVcpus());
                        vcpus_used_zones += Integer.parseInt(usage.getVcpus_used());
                        memory_mb_zones += Integer.parseInt(usage.getMemory_mb());
                        memory_mb_used_zones += Integer.parseInt(usage.getMemory_mb_used());
                        local_gb_zones += Integer.parseInt(usage.getLocal_gb());
                        local_gb_used_zones += Integer.parseInt(usage.getLocal_gb_used());
                    }
                }
            }

            ip_stat = statService.getIpStat();
            netsInfo = NetsInfo.getInstance(ip_stat);
            if (netsInfo != null) {
                wan_ipnum_zones = netsInfo.getWan_ipnum_zones();
                wan_used_ipnum_zones = netsInfo.getWan_used_ipnum_zones();
            }

        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "{'msg':'error','data':''}";
        }

        float value = 0;
        if (vcpus_zones > 0) {

            value = (float) vcpus_used_zones / vcpus_zones * 100;
            vcpus_used_zones_scale_int = String.valueOf((int) value) + "%";
        }
        if (memory_mb_zones > 0) {

            value = (float) memory_mb_used_zones / memory_mb_zones * 100;
            memory_mb_used_zones_scale_int = String.valueOf((int) value) + "%";
        }
        if (local_gb_zones > 0) {
            value = (float) local_gb_used_zones / local_gb_zones * 100;
            local_gb_used_zones_scale_int = String.valueOf((int) value) + "%";
        }
        if (wan_ipnum_zones > 0) {
            value = (float) wan_used_ipnum_zones / wan_ipnum_zones * 100;
            wan_used_ipnum_zones_scale = String.valueOf((int) value) + "%";

        }


        result.put("vcpus_zones", String.format("%d核", vcpus_zones));
        result.put("vcpus_used_zones", String.format("%d核", vcpus_used_zones));
        result.put("vcpus_used_zones_scale_int", vcpus_used_zones_scale_int);
        result.put("memory_mb_zones", String.format("%dMB", memory_mb_zones));
        result.put("memory_mb_used_zones", String.format("%dMB", memory_mb_used_zones));
        result.put("memory_mb_used_zones_scale_int", memory_mb_used_zones_scale_int);
        result.put("local_gb_zones", String.format("%dGB", local_gb_zones));
        result.put("local_gb_used_zones", String.format("%dGB", local_gb_used_zones));
        result.put("local_gb_used_zones_scale_int", local_gb_used_zones_scale_int);
        if (Region!=null&&Region.equals("RegionOne")) {
            result.put("wan_ipnum_zones", String.format("%d个", wan_ipnum_zones));
            result.put("wan_used_ipnum_zones", String.format("%d个", wan_used_ipnum_zones));
            result.put("wan_used_ipnum_zones_scale", wan_used_ipnum_zones_scale);
        }
        //添加返回状态
        HashMap<String, Object> resulttotal = new HashMap<String, Object>();
        resulttotal.put("msg", "successful");
        resulttotal.put("data", result);
        return JSONObject.toJSONString(resulttotal);
    }


}
