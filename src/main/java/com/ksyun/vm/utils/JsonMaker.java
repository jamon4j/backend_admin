package com.ksyun.vm.utils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSON;
import com.ksyun.vm.dto.common.MetaDto;
import com.ksyun.vm.dto.ebs.VmEBSDto;
import com.ksyun.vm.dto.images.CreateEBSDto;
import com.ksyun.vm.dto.images.CreateEBSImageDto;
import com.ksyun.vm.dto.securitygroup.SecurityGroupDto;
import com.ksyun.vm.dto.user.CreateUserDto;
import com.ksyun.vm.dto.vm.CreateFlavorDto;
import com.ksyun.vm.dto.vm.CreateServerDto;

public class JsonMaker {

	
	//创建虚拟机
	public static String createVm(String tenantId, String userId,String name, String imageRef, String flavorRef, String count, String securityGroups, String adminPass, String isOnEbs, String zone) throws HttpException, IOException{
		String requestStr = Constants.getPropertyValue(InitConst.CREATEVM,tenantId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		List<SecurityGroupDto> list = new LinkedList<SecurityGroupDto>();
		String[] groups = securityGroups.split(",");
		for(String group : groups){
			SecurityGroupDto dto = new SecurityGroupDto();
			dto.setName(group);
			list.add(dto);
		}
		String requestBody = makeCreateVmJson(name, imageRef, flavorRef, count, list,adminPass,isOnEbs,zone);
		System.out.println(requestBody);
		String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
		System.out.println(resultJson);
		return resultJson;
	}
	
	public static String createFromListToJson(List<?> list){
		return JSON.toJSONString(list);
	}
	//创建用户
	public static String createUser(String userName) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.CREATEUSER);
		String requestBody = makeCreateUserJson(userName);
		Map<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
		return resultJson;
	}
	
	//创建flavor
	public static String createFlavor(String tenantId, String userId, String vcpu, String network, String dataDisk, String ram) throws HttpException, IOException{
		String requestStr = Constants.getPropertyValue(InitConst.CREATEFLAVOR,tenantId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String requestBody = makeCreateFlavorJson(userId, vcpu, network, dataDisk, ram);
		String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
		return resultJson;
	}
	//创建ebs快照
	public static String createEbsImage(String tenantId, String userId, String volumeId, String name) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.CREATEEBSIMAGE,tenantId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String requestBody = makeCreateEBSImageJson(volumeId, name);
		String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
		return resultJson;
		
	}
	//创建ebs
	public static String createEBS(String tenantId,String userId,String name,String description,String size,String volume_type)throws Exception{
		String requestStr = Constants.getPropertyValue(InitConst.CREATEEBS,tenantId);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String requestBody = makeCreateEBSJson(name,description,size,volume_type);
		String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
		return resultJson;
	}
	//关联vm的ebs
	public static String setEBS(String tenantId,String userId,String vmid,String ebsid,String device)throws Exception{
		String requestStr = Constants.getPropertyValue(InitConst.SETEBS,tenantId,ebsid);
		Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
		String requestBody = makeSETEBSJson(ebsid,device);
		String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
		return resultJson;
	}
	private static String makeSETEBSJson(String ebsid,String device)throws Exception{
		VmEBSDto dto = new VmEBSDto();
		dto.setVolumeId(ebsid);
		dto.setDevice(device);
		Map<String,VmEBSDto> map = new HashMap<String,VmEBSDto>();
		map.put("volumeAttachment", dto);
		String json = JSON.toJSONString(map);
		return json;
	}
	
	private static String makeCreateEBSJson(String name, String description, String size, String volume_type) {
		CreateEBSDto dto = new CreateEBSDto();
		dto.setName(name);
		dto.setDescription(description);
		dto.setSize(size);
		dto.setVolume_type(volume_type);
		Map<String,CreateEBSDto> map = new HashMap<String,CreateEBSDto>();
		map.put("volume", dto);
		String json = JSON.toJSONString(map);
		return json;
	}

	public static String makeCreateEBSImageJson(String volumeId, String name) {
		CreateEBSImageDto dto = new CreateEBSImageDto();
		dto.setDisplay_name(name);
		dto.setVolume_id(volumeId);
		dto.setDisplay_description(name);
		dto.setForce("False");
		Map<String,CreateEBSImageDto> map = new HashMap<String,CreateEBSImageDto>();
		map.put("snapshot", dto);
		String resultJson = JSON.toJSONString(map);
		return resultJson;
	}

	public static String makeCreateVmJson(String name, String imageRef, String flavorRef, String count,List<SecurityGroupDto> securityGroups, String adminPass,String isOnEbs, String zone){
		CreateServerDto dto = new CreateServerDto();
		dto.setName(name);
		dto.setImageRef(imageRef);
		dto.setFlavorRef(flavorRef);
		dto.setMax_count(count);
		dto.setMin_count(count);
		dto.setAdminPass(adminPass);
		dto.setSecurity_groups(securityGroups);
		if(zone != null && !zone.equals(""))
			dto.setAvailability_zone(zone);
		if(isOnEbs.equals("yes")){
			MetaDto meta = new MetaDto();
			meta.setStorage_location("ebs");
			dto.setMetadata(meta);
		}
		Map<String,CreateServerDto> map = new HashMap<String,CreateServerDto>();
		map.put("server", dto);
		String resultJson = JSON.toJSONString(map);
		return resultJson;
	}
	
	public static String makeCreateUserJson(String userName) {
		CreateUserDto dto = new CreateUserDto();
		dto.setName(userName);
		dto.setIsAdmin("0");
		Map<String,CreateUserDto> map = new HashMap<String,CreateUserDto>();
		map.put("user", dto);
		String resultJson = JSON.toJSONString(map);
		return resultJson;
	}
	
	private static String makeCreateFlavorJson(String userId, String vcpu, String network, String dataDisk, String ram) {
		CreateFlavorDto dto = new CreateFlavorDto();
		dto.setDisk(Integer.valueOf(Constants.getPropertyValue(InitConst.SYSTEMDISK)));
		dto.setName(userId+new Date().getTime());
		dto.setOs_flavor_access_is_public("True");
		dto.setOs_flv_ext_data_ephemeral(Integer.valueOf(dataDisk));
		dto.setRam(Integer.valueOf(ram));
		dto.setRxtx_factor(Float.valueOf(network));
		dto.setSwap(0);
		dto.setVcpus(Integer.valueOf(vcpu));
		Map<String,CreateFlavorDto> map = new HashMap<String,CreateFlavorDto>();
		map.put("flavor", dto);
		String resultJson = JSON.toJSONString(map);
		return resultJson;
	}


}
