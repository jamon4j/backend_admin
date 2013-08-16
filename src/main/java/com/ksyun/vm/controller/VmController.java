package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Decoder;

import com.ksyun.vm.dto.ebs.VmEBSDto;
import com.ksyun.vm.dto.vm.ServerDto;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.HttpUtils;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.JsonParser;

@Controller
public class VmController {
	//虚拟机列表
	@RequestMapping("/g/vmlist/{hostname}")
	public ModelAndView returnZoneList(@PathVariable("hostname") String hostName, HttpServletRequest request, ModelAndView mav) throws IOException {
	//	String tenantId = Constants.getPropertyValue(InitConst.ADMINNAME,hostName);
		
	//	hostName = new String(new BASE64Decoder().decodeBuffer(hostName));
		List<ServerDto> list = JsonParser.returnServerList(hostName);
		mav.addObject("vmlist", list);
		mav.setViewName("/gestion/vm/vm_list");
		return mav;            
	}
/*	//虚拟机详情(ajax请求)
	@RequestMapping("/g/vmdetail/{vm_id}")
	@ResponseBody
	public String returnVmDetail(@PathVariable("vm_id") String vmId,HttpServletRequest request) throws HttpException, IOException{
		String tenantId = Constants.getPropertyValue(InitConst.ADMINNAME);
		String requestStr = Constants.getPropertyValue(Constants.getPropertyValue(InitConst.SERVER,tenantId,vmId));
		String resultJson = HttpUtils.getAdminResponseData(requestStr);
		return resultJson;
	}*/
	
	//虚机绑定ebs列表
	@RequestMapping("/g/vm_ebs/{tenantid}/{userid}/{vm_id}")
	public ModelAndView returnVmEBSDetail(@PathVariable("tenantid") String tenantId,@PathVariable("userid")String userId,@PathVariable("vm_id") String vmId,ModelAndView mav) throws HttpException, IOException{
		List<VmEBSDto> list = JsonParser.returnVMEBSLIST(tenantId, userId, vmId);
		mav.addObject("vmebslist", list);
        mav.addObject("tenantid",tenantId);
        mav.addObject("userid",userId);
		mav.setViewName("/gestion/user/vm_ebs_list");
		return mav;
	}
}
