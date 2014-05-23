package com.ksyun.vm.controller;

/**
 * Created by ZHANGNAN4 on 2014-5-21.
 * Email: zn.share@gmail.com
 */

import com.alibaba.fastjson.JSON;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.pojo.Result;
import com.ksyun.vm.pojo.rds.Backup;
import com.ksyun.vm.pojo.rds.RDSGroupDTO;
import com.ksyun.vm.service.RDSBackupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
@RequestMapping
public class RDSBackupController {

    private Logger log = LoggerFactory.getLogger(RDSBackupController.class);
    @Resource
    private RDSBackupService rdsBackupService;

    @RequestMapping(value = "/g/user/rds/backup/", method = RequestMethod.GET)
    @ResponseBody
    public String getBackups(HttpServletRequest request,
                             @RequestParam("username") String username, @RequestParam("instance_id") String instance_id) {
        log.info("username:{},instance_id:{}",username,instance_id);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        List<Backup> backups = null;
        try {
            backups = rdsBackupService.getBackups(username, instance_id);
        } catch (Exception e) {
            log.error("[ getBackups---user:" + username + " ]Error:", e);
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        request.setAttribute("userId", username);
        String result = "{\"result\":\"success\",\"content\":" + JSON.toJSONString(backups) + "}";
        log.debug("result:{}", result);
        return result;
    }


}
