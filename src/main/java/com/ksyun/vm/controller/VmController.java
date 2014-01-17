package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.ebs.VmEBSPojo;
import com.ksyun.vm.pojo.vm.VNC;
import com.ksyun.vm.pojo.vm.VmPojo;
import com.ksyun.vm.service.EBSService;
import com.ksyun.vm.service.VmService;
import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class VmController {
    @Autowired
    private VmService vmService;

    @Autowired
    private EBSService ebsService;

    @RequestMapping(value="/g/vnc/{instanceId}")
    @ResponseBody
    public String getVNC(@PathVariable("instanceId") String instanceId){
        try {
            VNC vnc = vmService.getVNC(instanceId);
            return JSONObject.toJSONString(vnc);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        return "false";
    }
    // 按某用户查找虚拟机列表
    @RequestMapping(value = "/g/user/vmlist/{tenant_id}/{user_id}")
    public ModelAndView vmList(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, HttpServletRequest request, ModelAndView mav) {
        List<VmPojo> vmList = null;
        try {
            vmList = vmService.getVms(userId, tenantId);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        request.setAttribute("tenantid", tenantId);
        request.setAttribute("userid", userId);
        mav.addObject("vmlist", vmList);
        mav.setViewName("/gestion/user/vm_list");
        return mav;
    }

	//虚机绑定ebs列表
	@RequestMapping("/g/vm_ebs/{tenantid}/{userid}/{vm_id}")
	public ModelAndView returnVmEBSDetail(@PathVariable("tenantid") String tenantId,@PathVariable("userid")String userId,@PathVariable("vm_id") String vmId,ModelAndView mav){
        List<VmEBSPojo> list = null;
        try {
            list = ebsService.getVMEBS(userId, tenantId, vmId);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        mav.addObject("vmebslist", list);
        mav.addObject("tenantid", tenantId);
        mav.addObject("userid", userId);
        mav.setViewName("/gestion/user/vm_ebs_list");
        return mav;
    }

    // 编辑指定虚拟机
    @RequestMapping(value = "/g/user/edit_vm/{action}/{tenant_id}/{user_id}")
    @ResponseBody
    public String editVm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @PathVariable("action") String action,
                         @RequestParam("vmids") String vmIds, ModelAndView mav){
        String[] vmidArray = vmIds.split(",");
        for (String vmid : vmidArray) {
            try {
                vmService.editVm(vmid, action, tenantId, userId, "");
            } catch (ErrorCodeException | NoTokenException e) {
                e.printStackTrace();
                return "false";
            }
        }
        return "true";
    }

    // 创建vm(ajax请求)
    @RequestMapping(value = "/g/user/createvm/{tenant_id}/{user_id}")
    @ResponseBody
    public String createVm(@PathVariable("tenant_id") String tenantId, @PathVariable("user_id") String userId, @RequestParam("name") String name,
                           @RequestParam("imageRef") String imageRef, @RequestParam("vcpu") String vcpu, @RequestParam("network") String network,
                           @RequestParam("create_on_ebs") String isOnEbs, @RequestParam("root_disk") String rootDisk, @RequestParam("ram") String ram,
                           @RequestParam("create_zone") String zone, @RequestParam("count") String count, @RequestParam("adminPass") String adminPass,
                           @RequestParam("security_groups[]") String security_groups){
        try {
            vmService.createVm(tenantId, userId, name, imageRef, count, security_groups, adminPass, isOnEbs, zone, vcpu, network, rootDisk, ram);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    // 创建vm(ajax请求)
    @RequestMapping(value = "/g/user/vm/reset/{tenant_id}/{user_id}")
    @ResponseBody
    public String createVm(@PathVariable("tenant_id")String tenantId,@PathVariable("user_id")String userId,@RequestParam("vm_id")String vm_id,@RequestParam("password")String password,@RequestParam("image_id")String image_id){
        try {
            vmService.reset(tenantId,userId,vm_id,password,image_id);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
    
    
    @RequestMapping(value = "/g/user/vm/getvmsum")
    @ResponseBody
    public int getvmsum() throws ErrorCodeException, NoTokenException
    {
    	List<VmPojo> vmList = vmService.getVmsAll();
    	if (vmList == null)  return 0;
    	else return vmList.size();
    }
    
}

