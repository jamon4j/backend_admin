package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        List<ZonePojo> list = null;
        List<StatZone> stat_zone = null;
        List<IpStat> ip_stat = null;
        NetsInfo netsInfo = null;
        try {
            list = zoneService.getZoneList();
            stat_zone = statService.getStatZone();
            ip_stat = statService.getIpStat();
            for(ZonePojo pojo:list){
                for(StatZone zone:stat_zone){
                    if(pojo.getName().equals(zone.getZone_name())){
                        pojo.setStatZone(zone);
                        for(HostUsage usage:zone.getHost_usage()){
                            usage.setCpu_infos(JSONObject.parseObject(usage.getCpu_info(),CpuInfos.class));
                        }
                        break;
                    }
                }
                for(IpStat ipStat:ip_stat){
                    if(pojo.getName().equals(ipStat.getZone_name())){
                        pojo.setIpStat(ipStat);
                    }
                }
            }
             netsInfo = NetsInfo.getInstance(ip_stat);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        mav.addObject("zonelist", list);
        mav.addObject("netsInfo",netsInfo);
		mav.setViewName("/gestion/zone/zone_list");
		return mav;
	}
	//zone列表
	@RequestMapping("/g/zonelist/ajax")
	@ResponseBody
	public String returnZoneAjaxList(HttpServletRequest request, ModelAndView mav) {
        List<ZonePojo> list = null;
        try{
            list = zoneService.getZoneList();
            return JSONArray.toJSONString(list);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        return null;
	}
}
