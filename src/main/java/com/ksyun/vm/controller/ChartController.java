package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.pojo.chart.MonitorVmDiskPo;
import com.ksyun.vm.pojo.chart.MonitorVmLoadPo;
import com.ksyun.vm.pojo.chart.MonitorVmNetworkPo;
import com.ksyun.vm.pojo.chart.MonitorVmStatusFlowPo;
import com.ksyun.vm.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: liuchuandong
 * Date: 13-8-29
 * Time: 下午2:54
 * Func:
 */
@Controller
public class ChartController {
    @Autowired
    private ChartService chartService;

    @RequestMapping(value = "/g/chart/load/{id}")
    public ModelAndView getChartLoad(@PathVariable("id")String vmuuid,ModelAndView mav){
        mav.addObject("vmuuid",vmuuid);
        mav.setViewName("/gestion/chart/chart_load");
        return mav;
    }

    @RequestMapping(value = "/g/chart/{id}/initLoad")
    @ResponseBody
    public String initLoad(@PathVariable("id")String vmuuid){
        List<MonitorVmLoadPo> poList = chartService.getLoadOneDaysAgo(vmuuid);
        String result = JSONArray.toJSONString(poList);
        return result;
    }
    @RequestMapping(value = "/g/chart/{id}/getLoad")
    @ResponseBody
    public String getLoad(@PathVariable("id")String vmuuid){
        MonitorVmLoadPo load = chartService.getLoad(vmuuid);
        String result = JSONObject.toJSONString(load);
        return result;
    }
    @RequestMapping(value = "/g/chart/disk/{id}")
    public ModelAndView getChartDisk(@PathVariable("id")String vmuuid,ModelAndView mav){
        mav.addObject("vmuuid",vmuuid);
        mav.setViewName("/gestion/chart/chart_disk");
        return mav;
    }

    @RequestMapping(value = "/g/chart/{id}/initDisk")
    @ResponseBody
    public String initDisk(@PathVariable("id")String vmuuid){
        List<MonitorVmDiskPo> poList = chartService.getDiskOneDaysAgo(vmuuid);
        Map<String,List<MonitorVmDiskPo>> map = new HashMap<String,List<MonitorVmDiskPo>>();
        for(MonitorVmDiskPo po:poList){
            List<MonitorVmDiskPo> tmp = new ArrayList<MonitorVmDiskPo>();
            if(map.containsKey(po.getDisk())){
                tmp = map.get(po.getDisk());
            }
            tmp.add(po);
            map.put(po.getDisk(),tmp);
        }
        String result = JSONObject.toJSONString(map);
        return result;
    }
    @RequestMapping(value = "/g/chart/{id}/getDisk/{disk}")
    @ResponseBody
    public String getDisk(@PathVariable("id")String vmuuid,@PathVariable("disk")String disk){
        MonitorVmDiskPo diskResult = chartService.getDisk(vmuuid,disk);
        String result = JSONObject.toJSONString(diskResult);
        return result;
    }
    @RequestMapping(value = "/g/chart/status/{id}")
    public ModelAndView getChartStatus(@PathVariable("id")String vmuuid,ModelAndView mav){
        mav.addObject("vmuuid",vmuuid);
        mav.setViewName("/gestion/chart/chart_status");
        return mav;
    }

    @RequestMapping(value = "/g/chart/{id}/initStatus")
    @ResponseBody
    public String initStatus(@PathVariable("id")String vmuuid){
        List<MonitorVmStatusFlowPo> poList = chartService.getStatusOneDaysAgo(vmuuid);
        String result = JSONArray.toJSONString(poList);
        return result;
    }
    @RequestMapping(value = "/g/chart/{id}/getStatus")
    @ResponseBody
    public String getStatus(@PathVariable("id")String vmuuid){
        MonitorVmStatusFlowPo network = chartService.getStatus(vmuuid);
        String result = JSONObject.toJSONString(network);
        return result;
    }
    @RequestMapping(value = "/g/chart/network/{id}")
    public ModelAndView getChartNetwork(@PathVariable("id")String vmuuid,ModelAndView mav){
        mav.addObject("vmuuid",vmuuid);
        mav.setViewName("/gestion/chart/chart_network");
        return mav;
    }

    @RequestMapping(value = "/g/chart/{id}/initNetwork")
    @ResponseBody
    public String initNetwork(@PathVariable("id")String vmuuid){
        List<MonitorVmNetworkPo> poList = chartService.getNetworkOneDaysAgo(vmuuid);
        Map<String,List<MonitorVmNetworkPo>> map = new HashMap<String,List<MonitorVmNetworkPo>>();
        for(MonitorVmNetworkPo po:poList){
            List<MonitorVmNetworkPo> tmp = new ArrayList<MonitorVmNetworkPo>();
            if(map.containsKey(po.getMac())){
                tmp = map.get(po.getMac());
            }
            tmp.add(po);
            map.put(po.getMac(),tmp);
        }
        String result = JSONObject.toJSONString(map);
        return result;
    }
    @RequestMapping(value = "/g/chart/{id}/getNetwork/{mac}")
    @ResponseBody
    public String getNetwork(@PathVariable("id")String vmuuid,@PathVariable("mac")String mac){
        MonitorVmNetworkPo network = chartService.getNetwork(vmuuid,mac);
        String result = JSONObject.toJSONString(network);
        return result;
    }
    public String getLoad(){
       // MonitorVmNetworkPo network = chartService.getNetwork(vmuuid);
       // MonitorVmLoadPo load = chartService.getLoad(vmuuid);
       // MonitorVmDiskPo disk = chartService.getDisk(vmuuid);
       // MonitorVmStatusFlowPo status = chartService.getStatus(vmuuid);
        //mav.addObject("network",network);
       // mav.addObject("load",load);
        //mav.addObject("disk",disk);
        //mav.addObject("status",status);
        return null;
    }
}
