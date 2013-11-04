package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.stat.StatHost;
import com.ksyun.vm.pojo.user.UserPojo;
import com.ksyun.vm.pojo.vm.VmPojo;
import com.ksyun.vm.pojo.zone.HostPojo;
import com.ksyun.vm.service.HostService;
import com.ksyun.vm.service.StatService;
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

	//根据zone查找物理机信息
	@RequestMapping("/g/hostlistbyzone/{zoneid}")
	public ModelAndView returnHostListByZone(@PathVariable("zoneid")String zoneId,ModelAndView mav) throws IOException {
		//查找物理机
        List<HostPojo> hostPojos = null;
        try {
            hostPojos =hostService.getHosts(zoneId);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        mav.addObject("hostdtolist", hostPojos);
		mav.setViewName("/gestion/host/zone_host_list");
		return mav;
	}
    @RequestMapping("/g/vmlist/{hostname}")
    public ModelAndView returnZoneList(@PathVariable("hostname") String hostName, ModelAndView mav) throws IOException {
        List<VmPojo> list = null;
        try {
            list = hostService.getVms(hostName);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        mav.addObject("vmlist", list);
        mav.setViewName("/gestion/vm/vm_list");
        return mav;
    }
    @RequestMapping(value = "/g/stathost")
    @ResponseBody
    public String getStathost(@RequestParam("hostname")String hostName){
        List<UserPojo> userList;
        List<StatHost> list = null;
        try {
            userList = userService.getUsers();
            list = statService.getStatHost(hostName,userList);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        return JSONArray.toJSONString(list);
    }
}
