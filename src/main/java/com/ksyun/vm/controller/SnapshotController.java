package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSONArray;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.snapshot.SnapshotVmPojo;
import com.ksyun.vm.service.SnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-10-14
 * Time: 上午11:45
 * Func:
 */
@Controller
public class SnapshotController {
    @Autowired
    private SnapshotService snapshotService;

    // 系统快照List(ajax操作)
    @RequestMapping(value = "/g/user/sys_image_id_list/{tenantid}/{userid}/{region}")
    @ResponseBody
    public String getVMSnapshot(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,@PathVariable("region") String region){
        List<SnapshotVmPojo> list = null;
        try {
            list = snapshotService.getVmSnapshots(userId, tenantId,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
        }
        return JSONArray.toJSONString(list);
    }

    // 创建系统快照(ajax操作)
    @RequestMapping(value = "/g/user/createsnapshot/{tenantid}/{userid}/{vmid}/{snapshot_name}/{region}")
    @ResponseBody
    public String createSnapShot(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @PathVariable("vmid") String vmId,@PathVariable("snapshot_name") String snapshotName,@PathVariable("region") String region){
        try {
            snapshotService.addVMSnapshot(userId,tenantId,vmId,snapshotName,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
    //删除系统快照
    @RequestMapping(value = "/g/user/deletesnapshot/{tenantid}/{userid}/{region}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteSnapshot(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("snapshotid") String snapshotid,@PathVariable("region") String region){
        try {
            snapshotService.delVMSnapshot(userId, tenantId, snapshotid,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
    // 创建EBS快照
    @RequestMapping(value = "/g/user/createebsimage/{tenantid}/{userid}/{region}")
    @ResponseBody
    public String createEBSImage(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("ebsid")String ebsid,@PathVariable("name")String name,@RequestParam("desc")String desc,@PathVariable("region") String region) {
        try {
            snapshotService.addEBSSnapshot(userId,tenantId,ebsid,name,desc,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    //删除ebs快照
    @RequestMapping(value = "/g/user/deleteebssnapshot/{tenantid}/{userid}/{region}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteEBSSnapshot(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId, @RequestParam("ebssnapshotid") String ebssnapshotid,@PathVariable("region") String region){
        try {
            snapshotService.delEBSSnapshot(userId,tenantId,ebssnapshotid,region);
        } catch (ErrorCodeException | NoTokenException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
}
