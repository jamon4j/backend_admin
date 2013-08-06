package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ksyun.vm.dto.ebs.EBSDto;
import com.ksyun.vm.dto.ebs.VolumeTypeDto;
import com.ksyun.vm.dto.images.SnapshotsDto;
import com.ksyun.vm.dto.images.SystemImageDto;
import com.ksyun.vm.dto.user.UserDto;
import com.ksyun.vm.dto.vm.FlavorDto;
import com.ksyun.vm.dto.vm.ServerDto;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.JsonMaker;
import com.ksyun.vm.utils.JsonParser;
import com.ksyun.vm.utils.OperationVm;
import com.ksyun.vm.utils.PageWithoutSize;
import com.ksyun.vm.utils.enumeration.EnumResult;

@Controller
public class UserController {
	// 用户列表
	@RequestMapping(value = "/g/user/list/{pagenum}")
	public ModelAndView userList(@PathVariable("pagenum") String pageNum, ModelAndView mav) throws HttpException, IOException {
		List<UserDto> list = JsonParser.returnUserList(pageNum);
		if (list == null) {
			return null;
		}
		PageWithoutSize page = new PageWithoutSize(Integer.valueOf(pageNum));
		page.setData(list);
		mav.addObject("page", page);
		mav.setViewName("/gestion/user/user_list");
		return mav;
	}

	// ebs及镜像列表
	@RequestMapping(value = "/g/user/ebs_snapshot_list/{tenant_id}/{user_id}")
	public ModelAndView ebsSnapShotList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, ModelAndView mav)
			throws HttpException, IOException {
		List<EBSDto> ebsList = JsonParser.returnEBSList(tenantId, userId);
		List<SystemImageDto> imageList = JsonParser.returnSysImagesList(tenantId, userId);
		List<SnapshotsDto> snapshotList = JsonParser.returnSnapshotsList(tenantId, userId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tenantId", tenantId);
		map.put("userId", userId);
		map.put("ebslist", ebsList);
		map.put("systemImagelist", imageList);
		map.put("snapshotlist", snapshotList);
		mav.addAllObjects(map);
		mav.setViewName("/gestion/user/ebs_snapshot_list");
		return mav;
	}

	// 按某用户查找虚拟机列表
	@RequestMapping(value = "/g/user/vmlist/{tenant_id}/{user_id}")
	public ModelAndView vmList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, HttpServletRequest request, ModelAndView mav)
			throws HttpException, IOException {
		List<ServerDto> vmList = JsonParser.returnServerListByUser(tenantId, userId);
		request.setAttribute("tenantId", tenantId);
		request.setAttribute("userId", userId);
		mav.addObject("vmlist", vmList);
		mav.setViewName("/gestion/user/vm_list");
		return mav;
	}

	// 查找单独的虚机信息
	@RequestMapping(value = "/g/user/vmlist/{tenant_id}/{user_id}/{vm_id}")
	public ModelAndView vm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @PathVariable("vm_id") String vmId,
			ModelAndView mav) throws HttpException, IOException {
		String admintenantId = Constants.getPropertyValue(InitConst.ADMINNAME);
		ServerDto dto = JsonParser.returnServerDto(admintenantId, vmId);
		mav.addObject("dto", dto);
		mav.setViewName("/gestion/user/vm");
		return mav;
	}

	// 创建vm(ajax请求)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/g/user/createvm/{tenant_id}/{user_id}")
	@ResponseBody
	public String createVm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @RequestParam("name") String name,
			@RequestParam("imageRef") String imageRef, @RequestParam("vcpu") String vcpu, @RequestParam("network") String network,
			@RequestParam("create_on_ebs") String isOnEbs, @RequestParam("data_disk") String dataDisk, @RequestParam("ram") String ram,
			@RequestParam("create_zone") String zone, @RequestParam("count") String count, @RequestParam("adminPass") String adminPass,
			@RequestParam("security_groups[]") String security_groups, HttpServletResponse response, ModelAndView mav) throws HttpException, IOException {
		String flavorJsonStr = JsonMaker.createFlavor(tenantId, userId, vcpu, network, dataDisk, ram);
		System.out.println(flavorJsonStr);
		LinkedHashMap map = JSON.parseObject(flavorJsonStr, LinkedHashMap.class);
		Iterator<Map.Entry> iter = map.entrySet().iterator();
		String flavorRef = null;
		while (iter.hasNext()) {
			Map.Entry entry = iter.next();
			FlavorDto dto = JSON.parseObject(entry.getValue().toString(), FlavorDto.class);
			flavorRef = dto.getId();
		}
		String result = JsonMaker.createVm(tenantId, userId, name, imageRef, flavorRef, count, security_groups, adminPass, isOnEbs, zone);
		return result;
	}

	// 返回可选镜像列表(ajax请求)
	@RequestMapping(value = "/g/user/image_public_id_list/{tenant_id}/{user_id}")
	@ResponseBody
	public String imageIdList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, ModelAndView mav) throws HttpException,
			IOException {
		List<SystemImageDto> list = JsonParser.returnImagePublicIdList(tenantId, userId);
		String resultStr = JsonMaker.createFromListToJson(list);
		return resultStr;
	}

	// 返回可选虚拟机类型(ajax请求 )
	@RequestMapping(value = "/g/user/flavor_id_list/{tenant_id}/{user_id}")
	@ResponseBody
	public String flavorIdList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, ModelAndView mav) throws HttpException,
			IOException {
		List<FlavorDto> list = JsonParser.returnFlavorIdList(tenantId, userId);
		String resultStr = JsonMaker.createFromListToJson(list);
		return resultStr;
	}

	// 返回系统镜像列表(ajax请求)
	@RequestMapping(value = "/g/user/sys_image_id_list/{tenant_id}/{user_id}")
	@ResponseBody
	public String userSysImageIdList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, ModelAndView mav)
			throws HttpException, IOException {
		List<SystemImageDto> list = JsonParser.returnSysImagesList(tenantId, userId);
		String resultStr = JsonMaker.createFromListToJson(list);
		return resultStr;
	}

	// 删除指定虚拟机
	@RequestMapping(value = "/g/user/delete_vm/{tenant_id}/{user_id}")
	@ResponseBody
	public String deleteVm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @RequestParam("vmids") String vmIds,
			ModelAndView mav) {
		String[] vmidArray = vmIds.split(",");
		Integer result = EnumResult.successful.value();
		for (String vmid : vmidArray) {
			String requestStr = Constants.getPropertyValue(InitConst.DELETEVM, tenantId, vmid);
			result = OperationVm.deleteVm(requestStr, tenantId, userId);
			if (result == EnumResult.failed.value()) {
				result = EnumResult.failed.value();
			}
		}
		return String.valueOf(result);
	}

	// 编辑指定虚拟机
	@RequestMapping(value = "/g/user/edit_vm/{action}/{tenant_id}/{user_id}")
	@ResponseBody
	public String editVm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @PathVariable("action") String action,
			@RequestParam("vmids") String vmIds, ModelAndView mav) throws HttpException, IOException {
		String[] vmidArray = vmIds.split(",");
		for (String vmid : vmidArray) {
			String requestStr = Constants.getPropertyValue(InitConst.EDITVM, tenantId, vmid);
			OperationVm.editVm(requestStr, action, tenantId, vmid);
		}
		return "success";
	}

	// 创建用户(ajax请求)
	@RequestMapping(value = "/g/user/createuser")
	@ResponseBody
	public String createUser(@RequestParam("name") String userName, HttpServletResponse response, ModelAndView mav) throws HttpException, IOException {
		String result = JsonMaker.createUser(userName);
		return result;
	}

	// 创建系统快照(ajax操作)
	@RequestMapping(value = "/g/user/createsnapshot/{tenantid}/{userid}/{vmid}/{snapshot_name}")
	@ResponseBody
	public String createSnapShot(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @PathVariable("vmid") String vmId,
			@PathVariable("snapshot_name") String snapshotName, HttpServletResponse response, ModelAndView mav) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(InitConst.EDITVM, tenantId, vmId);
		System.out.println(requestStr);
		OperationVm.createSnapShot(requestStr, tenantId, userId, snapshotName);
		return "success";
	}

	// 创建EBS快照
	@RequestMapping(value = "/g/user/createebsimage/{tenantid}/{userid}/{volumeid}")
	public String createEBSImage(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @PathVariable("volumeid") String volumeId,
			@PathVariable("imagename") String name) throws HttpException, IOException {
		JsonMaker.createEbsImage(tenantId, userId, volumeId, name);
		return "success";
	}

	// 创建EBS
	@RequestMapping(value = "/g/user/createebs/{tenantid}/{userid}",method=RequestMethod.POST)
	@ResponseBody
	public String createEBS(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("name") String name,
			@RequestParam("desc") String desc, @RequestParam("size") String size, @RequestParam("volume_type") String volume_type) throws Exception {
		JsonMaker.createEBS(tenantId, userId, name, desc, size, volume_type);
		return "success";
	}

	// 获取volume_type列表
	@RequestMapping(value = "/g/user/volume_type/{tenantid}/{userid}")
	@ResponseBody
	public String getVolumeType(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId) throws Exception {
		Map<String, List<VolumeTypeDto>> map = JsonParser.returnVolumeTypeList(tenantId, userId);
		String resultStr = JSON.toJSONString(map.get("volume_types"));
		return resultStr;
	}
	
	//获取ebs列表
	@RequestMapping(value="/g/user/ebslist/{tenantid}/{userid}")
	@ResponseBody
	public String getVMEBSList(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId)throws Exception{
		List<EBSDto> ebsList = JsonParser.returnEBSList(tenantId, userId);
		return JSON.toJSONString(ebsList);
	}
	//绑定虚拟机的ebs
	@RequestMapping(value="/g/user/setebs/{tenantid}/{userid}")
	@ResponseBody
	public String setEBS(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,@RequestParam("vmid")String vmid,@RequestParam("ebsid")String ebsId,@RequestParam("device")String device)throws Exception{
		JsonMaker.setEBS(tenantId, userId, vmid, ebsId, device);
		return "success";
	}
	
}
