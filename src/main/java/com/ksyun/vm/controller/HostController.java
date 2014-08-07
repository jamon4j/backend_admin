package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.stat.StatHost;
import com.ksyun.vm.pojo.stat.StatZone;
import com.ksyun.vm.pojo.user.UserPojo;
import com.ksyun.vm.pojo.vm.VmPojo;
import com.ksyun.vm.pojo.zone.HostPojo;
import com.ksyun.vm.pojo.zone.ZonePojo;
import com.ksyun.vm.service.HostService;
import com.ksyun.vm.service.StatService;
import com.ksyun.vm.service.ZoneService;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HostController {

    @Autowired
    private HostService hostService;
    @Autowired
    private StatService statService;
    @Autowired
    private UserService userService;
    @Autowired
    private ZoneService zoneService;

    //根据zone查找物理机信息
    @RequestMapping("/g/hostlistbyzone/{zoneid}/{Region}")
    public ModelAndView returnHostListByZone(@PathVariable("zoneid") String zoneId, @PathVariable("Region") String Region, ModelAndView mav) throws IOException {
        //查找物理机
        List<HostPojo> hostPojos = null;
        try {
            hostPojos = hostService.getHosts(zoneId, Region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        mav.addObject("Region", Region);
        mav.addObject("hostdtolist", hostPojos);
        mav.setViewName("/gestion/host/zone_host_list");
        return mav;
    }

    @RequestMapping("/g/vmlist")
    public ModelAndView returnZoneList(@RequestParam("hostname") String hostName, @RequestParam("Region") String Region, ModelAndView mav) throws IOException {
        List<VmPojo> list = null;
        List<ZonePojo> zoneList = null;
        List<HostPojo> hostPojos = new ArrayList<>();
        try {
            list = hostService.getVms(hostName, Region);
            zoneList = zoneService.getZoneList(Region);
            for (ZonePojo zonePojo : zoneList) {
                hostPojos.addAll(hostService.getHosts(zonePojo.getId(), Region));
            }
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        List<VmPojo> validList = new ArrayList<>();
        if (list != null) {
            for (VmPojo pojo : list) {
                if (pojo.getMetadata().getStorage_location() != null && pojo.getMetadata().getStorage_location().equals("ebs")) {
                    validList.add(pojo);
                }
            }
        }
        mav.addObject("Region", Region);
        mav.addObject("vmlist", list);
        mav.addObject("validList", validList);
        mav.addObject("hosts", hostPojos);
        mav.addObject("hostname", hostName);
        mav.setViewName("/gestion/vm/vm_list");
        return mav;
    }

    @RequestMapping(value = "/g/stathost")
    @ResponseBody
    public String getStathost(@RequestParam("hostname") String hostName,@RequestParam("Region") String Region) {
        List<UserPojo> userList;
        List<StatHost> list = null;
        try {
            userList = userService.getUsers();
            list = statService.getStatHost(hostName, userList,Region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        return JSONArray.toJSONString(list);
    }

    @RequestMapping(value = "/g/coldmove")
    @ResponseBody
    public String coldMove(@RequestParam("target") String target, @RequestParam("igores") String igore, @RequestParam("vms") String vms) {
        List<String> igore_list = new ArrayList<>();
        if (!igore.equals("")) {
            String[] igores = igore.split(",");
            for (String ig : igores) {
                igore_list.add(ig);
            }
        }
        String[] vmss = vms.split(",");
        if (target.equals("0")) {
            target = "";
        }
        try {
            for (String vm : vmss) {
                hostService.coldmove(vm, igore_list, target);
            }
            return "success";
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
    }
}
