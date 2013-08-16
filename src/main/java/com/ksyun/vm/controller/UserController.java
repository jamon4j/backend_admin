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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ksyun.vm.dto.ebs.EBSDto;
import com.ksyun.vm.dto.images.SnapshotsDto;
import com.ksyun.vm.dto.images.SystemImageDto;
import com.ksyun.vm.dto.images.SystemSnapshotDto;
import com.ksyun.vm.dto.securitygroup.SecurityGroupDto;
import com.ksyun.vm.dto.user.UserDto;
import com.ksyun.vm.dto.vm.FlavorDto;
import com.ksyun.vm.dto.vm.ServerDto;
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
		PageWithoutSize page = new PageWithoutSize(Integer.valueOf(pageNum));
		if (list == null) {
			page.setData(null);
		}
		
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
		List<SystemSnapshotDto> imageList = JsonParser.returnSysImagesList(tenantId, userId);
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
		request.setAttribute("tenantid", tenantId);
		request.setAttribute("userid", userId);
		mav.addObject("vmlist", vmList);
		mav.setViewName("/gestion/user/vm_list");
		return mav;
	}

	// 查找单独的虚机信息
	@RequestMapping(value = "/g/user/vmlist/{tenant_id}/{user_id}/{vm_id}")
	public ModelAndView vm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @PathVariable("vm_id") String vmId,
			ModelAndView mav) throws HttpException, IOException {
		// String admintenantId =
		// Constants.getPropertyValue(InitConst.ADMINNAME);
		ServerDto dto = JsonParser.returnServerDto(vmId);
		mav.addObject("dto", dto);
		mav.setViewName("/gestion/user/vm");
		return mav;
	}

	// 创建vm(ajax请求)
	@RequestMapping(value = "/g/user/createvm/{tenant_id}/{user_id}")
	@ResponseBody
	public String createVm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @RequestParam("name") String name,
			@RequestParam("imageRef") String imageRef, @RequestParam("vcpu") String vcpu, @RequestParam("network") String network,
			@RequestParam("create_on_ebs") String isOnEbs, @RequestParam("root_disk") String rootDisk, @RequestParam("ram") String ram,
			@RequestParam("create_zone") String zone, @RequestParam("count") String count, @RequestParam("adminPass") String adminPass,
			@RequestParam("security_groups[]") String security_groups, HttpServletResponse response, ModelAndView mav) throws HttpException, IOException {
		String result = JsonMaker.createVm(tenantId, userId, name, imageRef, count, security_groups, adminPass, isOnEbs, zone, vcpu, network, rootDisk, ram);
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
		List<SystemSnapshotDto> list = JsonParser.returnSysImagesList(tenantId, userId);
		String resultStr = JsonMaker.createFromListToJson(list);
		return resultStr;
	}

	// 编辑指定虚拟机
	@RequestMapping(value = "/g/user/edit_vm/{action}/{tenant_id}/{user_id}")
	@ResponseBody
	public String editVm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @PathVariable("action") String action,
			@RequestParam("vmids") String vmIds, ModelAndView mav) throws HttpException, IOException {
		String[] vmidArray = vmIds.split(",");
		for (String vmid : vmidArray) {
			OperationVm.editVm(vmid, action, tenantId, userId);
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
			@PathVariable("snapshot_name") String snapshotName, HttpServletResponse response, ModelAndView mav){
		try {
			String result = OperationVm.createSnapShot(vmId, tenantId, userId, snapshotName);
			return result;
		} catch (Exception e) {
			return EnumResult.failed.value();
		}
	}

	// 创建EBS快照
	@RequestMapping(value = "/g/user/createebsimage/{tenantid}/{userid}")
    @ResponseBody
	public String createEBSImage(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("ebsid")String ebsid,@RequestParam("name")String name,@RequestParam("desc")String desc) throws HttpException, IOException {
		JsonMaker.createEbsImage(tenantId, userId, ebsid,desc, name);
		return "success";
	}

	// 创建EBS
	@RequestMapping(value = "/g/user/createebs/{tenantid}/{userid}", method = RequestMethod.POST)
	@ResponseBody
	public String createEBS(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("name") String name,
			@RequestParam("desc") String desc, @RequestParam("size") String size) throws Exception {
		JsonMaker.createEBS(tenantId, userId, name, desc, size);
		return "success";
	}
    //删除ebs
    @RequestMapping(value = "/g/user/deleteebs/{tenantid}/{userid}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteEBS(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("ebsid") String ebsid) throws Exception {
        JsonParser.deleteEBS(tenantId, userId, ebsid);
        return "success";
    }
    //删除ebs快照
    @RequestMapping(value = "/g/user/deleteebssnapshot/{tenantid}/{userid}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteEBSSnapshot(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("ebssnapshotid") String ebssnapshotid) throws Exception {
        JsonParser.deleteEBSSnapshot(tenantId, userId, ebssnapshotid);
        return "success";
    }
	// 获取ebs列表
	@RequestMapping(value = "/g/user/ebslist/{tenantid}/{userid}")
	@ResponseBody
	public String getVMEBSList(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId) throws Exception {
		List<EBSDto> ebsList = JsonParser.returnEBSList(tenantId, userId);
		return JSON.toJSONString(ebsList);
	}

	// 绑定虚拟机的ebs
	@RequestMapping(value = "/g/user/setebs/{tenantid}/{userid}")
	@ResponseBody
	public String setEBS(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("vmid") String vmid,
			@RequestParam("ebsid") String ebsId, @RequestParam("device") String device) throws Exception {
		String status = JsonMaker.setEBS(tenantId, userId, vmid, ebsId, device);
		return status;
	}
    //解绑虚拟机的ebs
    @RequestMapping(value="/g/user/unbind/{tenantid}/{userid}", method=RequestMethod.POST)
    @ResponseBody
    public String unsetEBS(@PathVariable("tenantid")String tenantid,@PathVariable("userid")String userid,@RequestParam("attach_id")String attach_id,@RequestParam("volumeid")String volumeid) throws Exception {
    	String status = JsonMaker.unsetEBS(tenantid,userid,attach_id,volumeid);
        return status;
    }

	// 查找指定用户的安全组
	@RequestMapping(value = "/g/user/security_groups/{tenantid}/{userid}")
	public ModelAndView security_groups(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,ModelAndView mav, HttpServletRequest request) throws HttpException, IOException{
		List<SecurityGroupDto> list = JsonParser.returnSecurityGroupList(tenantId,userId);
		request.setAttribute("tenantid", tenantId);
		request.setAttribute("userid", userId);
		mav.addObject("sglist", list);
		mav.setViewName("/gestion/user/sg_list");
		return mav;
	}
	
	// 创建安全组
	@RequestMapping(value = "/g/user/createsg/{tenantid}/{userid}")
	@ResponseBody
	public String createsg(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,@RequestParam("name") String name,@RequestParam("desc") String desc, ModelAndView mav) {
		try {
			String result = JsonMaker.createSg(tenantId,userId,name,desc);
			return result;
		} catch (Exception e) {
			return EnumResult.failed.value();
		}
	}
	
	// 删除安全组
	@RequestMapping(value = "/g/user/deletesgs/{tenantid}/{userid}")
	@ResponseBody
	public String deletesgs(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,@RequestParam("sgids") String sgids) throws HttpException, IOException{
		return JsonMaker.deleteSgs(tenantId, userId, sgids);
	}
	// 查找指定用户安全组规则
	@RequestMapping(value = "/g/user/security_groups/rules/{sgid}/{tenantid}/{userid}")
	public ModelAndView rules(@PathVariable("sgid") String sgid,@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,ModelAndView mav, HttpServletRequest request) throws HttpException, IOException{
		SecurityGroupDto dto = JsonParser.returnSecurityGroup(sgid,tenantId,userId);
		mav.addObject("dto",dto);
		request.setAttribute("sgid", sgid);
		request.setAttribute("tenantid", tenantId);
		request.setAttribute("userid", userId);
		mav.setViewName("/gestion/user/rules");
		return mav;
	}
	
	// 创建安全组规则
	@RequestMapping(value = "/g/user/createrule/{sgid}/{tenantid}/{userid}")
	@ResponseBody
	public String createRule(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,@PathVariable("sgid") String sgid,
			@RequestParam("protocal") String protocal,@RequestParam("from_port") String fromPort,@RequestParam("to_port") String toPort,@RequestParam("cidr") String cidr) throws HttpException, IOException{
		return JsonMaker.createRule(tenantId,userId,sgid,protocal,fromPort,toPort,cidr);	
	}
	// 删除安全组规则
	@RequestMapping(value = "/g/user/deleterule/{ruleid}/{tenantid}/{userid}")
	@ResponseBody
	public String deleterule(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,@PathVariable("ruleid") String ruleId) throws HttpException, IOException{
		return JsonMaker.deleteRule(tenantId, userId, ruleId);
	}
	//获取安全组信息(ajax请求)
	@RequestMapping(value = "/g/user/security_groups/ajax/{tenantid}/{userid}")
	@ResponseBody
	public String security_groups_ajax(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId) throws HttpException, IOException{
		List<SecurityGroupDto> list = JsonParser.returnSecurityGroupList(tenantId,userId);
		String resultStr = JsonMaker.createFromListToJson(list);
		return resultStr;
	}
}
