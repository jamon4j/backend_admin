package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Encoder;

import com.ksyun.vm.dto.host.HypervisorsListDto;
import com.ksyun.vm.dto.zone.AggregatesDto;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.HttpUtils;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.JsonParser;

@Controller
public class HostController {
	//全部物理机列表 
	@RequestMapping("/g/allhostlist")
	public ModelAndView returnHostList(HttpServletRequest request, ModelAndView mav) throws IOException {
		String tenantId = Constants.getPropertyValue(InitConst.ADMINNAME);
		Map<String,HypervisorsListDto> hostMap = new HashMap<String,HypervisorsListDto>();
		//查找所有的物理机
		List<HypervisorsListDto> hostDtoList = JsonParser.returnHypervisorsList(tenantId);
			for(HypervisorsListDto dto : hostDtoList){
				String name = dto.getHypervisor_hostname();
				dto.setHypervisor_hostname(new BASE64Encoder().encode(name.getBytes()));
				hostMap.put(name, dto);
		}
		mav.addObject("hostMap", hostMap);
		mav.setViewName("/gestion/host/all_host_list");
		return mav;
	}
	//物理机详情
	@RequestMapping("/g/hostdetail/{hostid}")
	@ResponseBody
	public String returnHostDetail(@PathVariable("hostid") String hostid, HttpServletRequest request,  ModelAndView mav) throws HttpException, IOException{
		String tenantId = Constants.getPropertyValue(InitConst.ADMINNAME);
		String requestStr = Constants.getPropertyValue(InitConst.HYPERVISOR, tenantId,hostid);
		String resultJson = HttpUtils.getAdminResponseData(requestStr);
		return resultJson;
	}
	//根据zone查找物理机信息
	@RequestMapping("/g/hostlistbyzone/{zoneid}")
	public ModelAndView returnHostListByZone(@PathVariable("zoneid")String zoneId, HttpServletRequest request, ModelAndView mav) throws IOException {
		String tenantId = Constants.getPropertyValue(InitConst.ADMINNAME);
		List<AggregatesDto> list = JsonParser.returnAggregatesList(tenantId);
		List<String> hostList = JsonParser.findHostList(list, zoneId);
		
		Map<String,HypervisorsListDto> hostMap = new HashMap<String,HypervisorsListDto>();
		//查找所有的物理机
		List<HypervisorsListDto> hostDtoList = JsonParser.returnHypervisorsList(tenantId);
		for(String name : hostList){
			for(HypervisorsListDto dto : hostDtoList){
				if(name.equals(dto.getHypervisor_hostname())){
					HypervisorsListDto resultDto = new HypervisorsListDto();
					resultDto.setId(dto.getId());
					resultDto.setHypervisor_hostname(new BASE64Encoder().encode(name.getBytes()));
					hostMap.put(name, resultDto);
				}
			}
		}
		mav.addObject("hostMap", hostMap);
		mav.setViewName("/gestion/host/zone_host_list");
		return mav;
	}
}
