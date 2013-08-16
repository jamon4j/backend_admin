package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ksyun.vm.dto.securitygroup.SecurityGroupDto;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.JsonParser;

@Controller
public class SecurityController {
/*	@RequestMapping("/g/sglist")
	public ModelAndView returnSecurityGroupList(HttpServletRequest request, ModelAndView mav) throws HttpException, IOException{
		String tenantId = Constants.getPropertyValue(InitConst.ADMINNAME);
		List<SecurityGroupDto> list = JsonParser.returnSecurityGroupList(tenantId);
		System.out.println(list);
		mav.addObject("sglist", list);
		mav.setViewName("/gestion/securitygroup/sg_list");
		return mav;
	}
	
	
	@RequestMapping("/g/sgrulelist")
	public ModelAndView returnSecurityGroupRuleList(HttpServletRequest request, ModelAndView mav) throws HttpException, IOException{
		String tenantId = Constants.getPropertyValue(InitConst.ADMINNAME);
		List<SecurityGroupDto> list = JsonParser.returnSecurityGroupList(tenantId);
		System.out.println(list);
		mav.addObject("sglist", list);
		mav.setViewName("/gestion/securitygroup/sg_list");
		return mav;
	}*/

}
