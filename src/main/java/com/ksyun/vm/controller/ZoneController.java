package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.zone.ZonePojo;
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
	//zone列表
	@RequestMapping("/g/zonelist")
	public ModelAndView returnZoneList(HttpServletRequest request, ModelAndView mav) {
        List<ZonePojo> list = null;
        try {
            list = zoneService.getZoneList();
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        mav.addObject("zonelist", list);
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
