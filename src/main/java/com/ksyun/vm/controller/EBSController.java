package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSONArray;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.ebs.EBSPojo;
import com.ksyun.vm.service.EBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-10-14
 * Time: 上午10:47
 * Func:
 */
@Controller
public class EBSController {
    @Autowired
    private EBSService ebsService;

    // 创建EBS
    @RequestMapping(value = "/g/user/createebs/{tenantid}/{userid}", method = RequestMethod.POST)
    @ResponseBody
    public String createEBS(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("name") String name,
                            @RequestParam("desc") String desc, @RequestParam("size") String size) {
        try {
            ebsService.addEBS(userId,tenantId,name,size,desc);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
    //删除ebs
    @RequestMapping(value = "/g/user/deleteebs/{tenantid}/{userid}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteEBS(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("ebsid") String ebsid) {
        try {
            ebsService.delEBS(userId,tenantId,ebsid);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    // 获取ebs列表
    @RequestMapping(value = "/g/user/ebslist/{tenantid}/{userid}/{region}")
    @ResponseBody
    public String getVMEBSList(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,@PathVariable("region") String region) {
        List<EBSPojo> list = null;
        try {
            list = ebsService.getEBS(userId, tenantId);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        return JSONArray.toJSONString(list);
    }

    // 绑定虚拟机的ebs
    @RequestMapping(value = "/g/user/setebs/{tenantid}/{userid}/{region}")
    @ResponseBody
    public String setEBS(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("vmid") String vmid,
                         @RequestParam("ebsid") String ebsId, @RequestParam("device") String device,@RequestParam("region") String region){
        try {
            ebsService.attachEBS(userId, tenantId, ebsId, vmid, device,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
    //解绑虚拟机的ebs
    @RequestMapping(value="/g/user/unbind/{tenantid}/{userid}/{region}", method=RequestMethod.POST)
    @ResponseBody
    public String unsetEBS(@PathVariable("tenantid")String tenantid,@PathVariable("userid")String userid,@RequestParam("attach_id")String attach_id,@RequestParam("volumeid")String volumeid,@RequestParam("region")String region) throws Exception {
        try {
            ebsService.detachEBS(userid,tenantid,attach_id,volumeid,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
}
