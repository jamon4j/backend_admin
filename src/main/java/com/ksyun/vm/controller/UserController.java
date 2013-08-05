package com.ksyun.vm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksyun.vm.dto.ebs.EBSDto;
import com.ksyun.vm.dto.images.ImagesDto;
import com.ksyun.vm.dto.images.SnapshotsDto;
import com.ksyun.vm.dto.user.UserDto;
import com.ksyun.vm.dto.vm.FlavorDto;
import com.ksyun.vm.dto.vm.ImageDto;
import com.ksyun.vm.dto.vm.ServerDto;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.JsonMaker;
import com.ksyun.vm.utils.JsonParser;
import com.ksyun.vm.utils.OperationVm;
import com.ksyun.vm.utils.PageWithoutSize;
import com.ksyun.vm.utils.Tools;
import com.ksyun.vm.utils.enumeration.EnumResult;

@Controller
public class UserController {
	//用户列表
	@RequestMapping(value="/g/user/list/{pagenum}")
	public ModelAndView userList(@PathVariable("pagenum") String pageNum, ModelAndView mav) throws HttpException, IOException{
		List<UserDto> list = JsonParser.returnUserList(pageNum);
		if(list == null){
			return null;
		}
		PageWithoutSize page = new PageWithoutSize(Integer.valueOf(pageNum));
		page.setData(list);
		mav.addObject("page", page);
		mav.setViewName("/gestion/user/user_list");
		return mav;
	}
	//ebs及镜像列表
	@RequestMapping(value="/g/user/ebs_snapshot_list/{tenant_id}/{user_id}")
	public ModelAndView ebsSnapShotList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId,ModelAndView mav) throws HttpException, IOException{
		List<EBSDto> ebsList = JsonParser.returnEBSList(tenantId, userId);
		List<ImagesDto> imageList = JsonParser.returnImagesList(tenantId, userId);
		List<SnapshotsDto> snapshotList = JsonParser.returnSnapshotsList(tenantId, userId);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ebslist", ebsList);
		map.put("imagelist", imageList);
		map.put("snapshotlist", snapshotList);
		mav.addAllObjects(map);
		mav.setViewName("/gestion/user/ebs_snapshot_list");
		return mav;
	}
	
	//按某用户查找虚拟机列表
	@RequestMapping(value="/g/user/vmlist/{tenant_id}/{user_id}")
	public ModelAndView vmList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, HttpServletRequest request, ModelAndView mav) throws HttpException, IOException{
		List<ServerDto> vmList = JsonParser.returnServerListByUser(tenantId, userId);
		request.setAttribute("tenantid", tenantId);
		request.setAttribute("userid", userId);
		mav.addObject("vmlist", vmList);
		mav.setViewName("/gestion/user/vm_list");
		return mav;
	}
	//查找单独的虚机信息
	@RequestMapping(value="/g/user/vmlist/{tenant_id}/{user_id}/{vm_id}")
	public ModelAndView vm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @PathVariable("vm_id") String vmId, ModelAndView mav) throws HttpException, IOException{
		String admintenantId = Constants.getPropertyValue(InitConst.ADMINNAME);
		ServerDto dto = JsonParser.returnServerDto(admintenantId, vmId);
		mav.addObject("dto", dto);
		mav.setViewName("/gestion/user/vm");
		return mav;
	}
	
	//创建vm(ajax请求)
	@RequestMapping(value="/g/user/createvm/{tenant_id}/{user_id}")
	@ResponseBody
	public String createVm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @RequestParam("name")String name,@RequestParam("imageRef")String imageRef,@RequestParam("flavorRef")String flavorRef,@RequestParam("max_count")String maxCount,@RequestParam("min_count")String minCount,@RequestParam("security_groups[]")String security_groups,HttpServletResponse response, ModelAndView mav) throws HttpException, IOException{
		String result = JsonMaker.createVm(tenantId, userId, name, imageRef, flavorRef, maxCount, minCount, security_groups);
		return result;
	}
	//返回可选镜像列表(ajax请求)
	@RequestMapping(value="/g/user/image_public_id_list/{tenant_id}/{user_id}")
	@ResponseBody
	public String imageIdList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, ModelAndView mav) throws HttpException, IOException{
		List<ImagesDto> list = JsonParser.returnImagePublicIdList(tenantId, userId);
		String resultStr = JsonMaker.createFromListToJson(list);
		return resultStr;
	}
	//返回可选虚拟机类型(ajax请求 )
	@RequestMapping(value="/g/user/flavor_id_list/{tenant_id}/{user_id}")
	@ResponseBody
	public String flavorIdList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, ModelAndView mav) throws HttpException, IOException{
		List<FlavorDto> list = JsonParser.returnFlavorIdList(tenantId, userId);
		String resultStr = JsonMaker.createFromListToJson(list);
		return resultStr;
	}
	//删除指定虚拟机
	@RequestMapping(value="/g/user/delete_vm/{tenant_id}/{user_id}")
	@ResponseBody
	public String deleteVm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId,@RequestParam("vmids") String vmIds,ModelAndView mav){
		String[] vmidArray = vmIds.split(",");
		Integer result = EnumResult.successful.value();
		for(String vmid : vmidArray){
			String requestStr = Constants.getPropertyValue(InitConst.DELETEVM,tenantId,vmid);
			result = OperationVm.deleteVm(requestStr, tenantId, userId);
			if(result == EnumResult.failed.value()){
				result = EnumResult.failed.value();
			}
		}
		return String.valueOf(result);
	}
	
	//编辑指定虚拟机
	@RequestMapping(value="/g/user/edit_vm/{action}/{tenant_id}/{user_id}")
	@ResponseBody
	public String editVm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId,@PathVariable("action") String action,@RequestParam("vmids") String vmIds,ModelAndView mav) throws HttpException, IOException{
		String[] vmidArray = vmIds.split(",");
		for(String vmid : vmidArray){
			String requestStr = Constants.getPropertyValue(InitConst.EDITVM,tenantId,vmid);
			OperationVm.editVm(requestStr, action, tenantId, vmid);
		}
		return "success";
	}
}
