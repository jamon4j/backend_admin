package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ksyun.vm.dto.host.HypervisorDto;
import com.ksyun.vm.utils.JsonParser;

@Controller
public class HostController {

	//根据zone查找物理机信息
	@RequestMapping("/g/hostlistbyzone/{zoneid}")
	public ModelAndView returnHostListByZone(@PathVariable("zoneid")String zoneId, HttpServletRequest request, ModelAndView mav) throws IOException {
		//查找物理机
		List<HypervisorDto> hostDtoList = JsonParser.returnHypervisorsList(zoneId);
		mav.addObject("hostdtolist", hostDtoList);
		mav.setViewName("/gestion/host/zone_host_list");
		return mav;
	}
}
