package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSON;
//import com.ksyun.vm.common.enumeration.MethodType;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.Result;
//import com.ksyun.vm.pojo.log.RDSValidationPo;
import com.ksyun.vm.pojo.rds.*;
import com.ksyun.vm.pojo.result.ReturnRdsBackup;

import com.ksyun.vm.service.RDSBackupService;

import com.ksyun.vm.utils.*;

import com.ksyun.tools.net.ServletUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ksyun.vm.service.RDSService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * Created by LiYang on 2014/5/21.
 */
@Controller
public class TestController {

    @Resource
    private RDSBackupService rdsService;
    @Resource
    private UserService userService;
    @Resource
    private RDSService rdsServiceone;
    @RequestMapping(value = "/a/b")
    public String getpost(HttpServletRequest request ,HttpServletResponse response)
    {

        try {
            response.getWriter().println("<b>Hello Wrold</b>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @RequestMapping(value = "/instance/{instance_id}/backup/create", method = RequestMethod.POST)
    @ResponseBody
    public String addBackup(HttpServletRequest request, @PathVariable String instance_id) {
        String backup_name = ServletUtil.getString(request, "backup_name");
        System.out.println(backup_name);
        String username ="39490241"; //request.getAttribute("username").toString();

        Backup backup = null;
        boolean flag = true;
        Result result = null;
        try {
            backup = rdsService.createBackup(username, instance_id, backup_name,null);

        } catch (Exception e) {
            flag = false;
        }finally {
            if (flag) {
                List<ReturnRdsBackup> backups = new ArrayList<>();
                ReturnRdsBackup returnRdsBackup = new ReturnRdsBackup();
                try {
                    BeanUtils.copyProperties(returnRdsBackup, backup);
                    backups.add(returnRdsBackup);
                } catch (Exception e){
                    //log.error("[ RDS_INSTNACE_upgrade---user:" + username + " ]Error:", e);
                }

                result = new Result();
                result.setData(backups);
                result.setStatus("1");
                result.setStatusText("success");
                return  "success";
                //log.info("用户{}/rds/instance/upgrade，返回结果{}", new Object[]{username, result});
            } else {
                if (result == null) {
                    result = new Result();
                    result.setData(new ArrayList());
                    result.setStatus("0");
                    result.setStatusText("修改失败!");
                }


            }
            //return result;
        }
        return "error";
    }

    @RequestMapping(value = "/instance/{instance_id}/backup/del", method = RequestMethod.POST)
    @ResponseBody
    public Result delBackup(HttpServletRequest request,@PathVariable String instance_id,@RequestParam(value = "username", required = false) String username,
                            @RequestParam(value = "backup_id", required = false) String backup_id) {
        boolean flag = true;
        Result result = null;
        try {
            rdsService.delBackup(username,backup_id);

        } catch (Exception e) {
            //log.error("[ RDS_INSTNACE_Backups---user:" + username + " ]Error:", e);
            flag = false;
        } finally {
            if(flag){
                result = new Result();
                result.setData(new ArrayList());
                result.setStatus("1");
                result.setStatusText("success");
                //log.info("用户{}/instance/{}/backup/list，返回结果{}", new Object[]{username,instance_id, result});
            } else {
                if (result == null) {
                    result = new Result();
                    result.setData(new ArrayList());
                    result.setStatus("0");
                    result.setStatusText("删除失败!");
                }
            }
            return result;
        }
    }


    @RequestMapping(value = "/instance/{instance_id}/backup/list", method = RequestMethod.POST)
    @ResponseBody
    public Result getBackups(HttpServletRequest request,@PathVariable String instance_id) {
        String username ="39490241"; //request.getAttribute("username").toString();
        List<Backup> backups = new ArrayList<>();;

        boolean flag = true;
        boolean notExits = true;
        Result result = null;

                try {
                    backups = rdsService.getBackups(username, instance_id);

                } catch (Exception e) {
                    flag = false;
                }

            if (flag) {
                List<ReturnRdsBackup> list = new ArrayList<>();
                try {
                    for (Backup backup : backups) {
                        ReturnRdsBackup returnRdsBackup = new ReturnRdsBackup();
                        BeanUtils.copyProperties(returnRdsBackup, backup);
                        if (returnRdsBackup.getId() == null) {
                            returnRdsBackup.setId(backup.getId());
                        }
                        System.out.println(returnRdsBackup.getId());
                        list.add(returnRdsBackup);
                    }
                } catch (Exception e) {

                }
                result = new Result();
                result.setData(list);
                result.setStatus("1");
                result.setStatusText("success");

            } else {
                if (result == null) {
                    result = new Result();
                    result.setData(new ArrayList());
                    result.setStatus("0");
                    result.setStatusText("获取失败!");
                }
            }
            return result;
        }

    @RequestMapping(value = "/test/addrds", method = RequestMethod.GET)
    @ResponseBody
    public String testAddRDS() throws Exception {

        CreateInstance createInstance = new CreateInstance();
        InstancePo instancePo = new InstancePo();
        instancePo.setType("SG");
        instancePo.setName("test6-23");
        instancePo.setService_type("mysql");
        Extend extend = new Extend();
        extend.setAdmin_user("master");
        extend.setAdmin_password("123456");
        extend.setPort("3306");
        extend.setAutobackup_at("2300");//
        extend.setDuration("1440");//
        extend.setExpire_after("7");//
        instancePo.setExtend(extend);
        CreateFlavor createFlavor = new CreateFlavor();
        createFlavor.setDisk("5");
        createFlavor.setRam("512");
        createFlavor.setVcpus("1");
        instancePo.setFlavor(createFlavor);
        createInstance.setRds(instancePo);
        createInstance.setUser_id("46230816");

        RDSValidationPo rdsValidationPo = rdsServiceone.addRDS(createInstance);

        return "1111111";
    }

}









