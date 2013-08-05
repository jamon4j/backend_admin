package com.ksyun.vm.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSON;
import com.ksyun.vm.dto.vm.CreateServerDto;
import com.ksyun.vm.dto.vm.SecurityGroupDto;

public class JsonMaker {
	public static String makeCreateVmJson(String name, String imageRef, String flavorRef, String maxCount,String minCount, List<SecurityGroupDto> securityGroups){
		CreateServerDto dto = new CreateServerDto();
		dto.setName(name);
		dto.setImageRef(imageRef);
		dto.setFlavorRef(flavorRef);
		dto.setMax_count(maxCount);
		dto.setMin_count(minCount);
		dto.setSecurity_groups(securityGroups);
		Map<String,CreateServerDto> map = new HashMap<String,CreateServerDto>();
		map.put("server", dto);
		String resultJson = JSON.toJSONString(map);
		return resultJson;
	}
	
	//创建虚拟机
	public static String createVm(String tenantId, String userId,String name, String imageRef, String flavorRef, String maxCount,String minCount, String securityGroups) throws HttpException, IOException{
		String requestStr = Constants.getPropertyValue(InitConst.CREATEVM,tenantId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		List<SecurityGroupDto> list = new LinkedList<SecurityGroupDto>();
		String[] groups = securityGroups.split(",");
		for(String group : groups){
			SecurityGroupDto dto = new SecurityGroupDto();
			dto.setName(group);
			list.add(dto);
		}
		String requestBody = makeCreateVmJson(name, imageRef, flavorRef, maxCount, minCount, list);
		String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
		return resultJson;
	}
	
	public static String createFromListToJson(List<?> list){
		return JSON.toJSONString(list);
	}
}
