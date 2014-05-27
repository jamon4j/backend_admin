package com.ksyun.vm.controller;

/**
 * Created by ZHANGNAN4 on 2014-5-21.
 * Email: zn.share@gmail.com
 */

import com.alibaba.fastjson.JSON;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.pojo.Result;
import com.ksyun.vm.pojo.rds.Backup;
import com.ksyun.vm.pojo.rds.BackupConfig;
import com.ksyun.vm.pojo.rds.RDSGroupDTO;
import com.ksyun.vm.service.RDSBackupService;
import org.apache.commons.lang.StringUtils;
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
                             @RequestParam("username") String username, @RequestParam(value = "instance_id", required = false) String instance_id) {
        log.info("username:{},instance_id:{}", username, instance_id);
        List<Backup> backups;
        String result = null;
        try {
            try {
                checkNotNull(username);
            } catch (Exception e) {
                result = "{\"result\":\"参数不能为空!\"}";
                return result;
            }
            backups = null;
            try {
                if (StringUtils.isEmpty(instance_id)) {
                    backups = rdsBackupService.getBackups(username);
                } else {
                    backups = rdsBackupService.getBackups(username, instance_id);
                }
            } catch (Exception e) {
                log.error("[ getBackups---user:" + username + " ]Error:", e);
                result = "{\"result\":\"" + e.getMessage() + "\"}";
                return result;
            }
            request.setAttribute("userId", username);
            result = "{\"result\":\"success\",\"content\":" + JSON.toJSONString(backups) + "}";
            return result;
        } finally {
            log.debug("result:{}", result);
        }
    }


    @RequestMapping(value = "/g/user/rds/backup/delete")
    @ResponseBody
    public String delBackup(HttpServletRequest request,
                            @RequestParam("username") String username, @RequestParam(value = "backup_id") String backup_id) {
        log.info("username:{},backup_id:{}", username, backup_id);
        try {
            checkNotNull(username);
            checkNotNull(backup_id);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        try {
            rdsBackupService.delBackup(username, backup_id);
        } catch (Exception e) {
            log.error("[ delBackup---user:" + username + " ]Error:", e);
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        request.setAttribute("userId", username);
        String result = "{\"result\":\"success\"}";
        log.debug("result:{}", result);
        return result;
    }


    @RequestMapping(value = "/g/user/rds/backup/", method = RequestMethod.POST)
    @ResponseBody
    public String createBackup(HttpServletRequest request,
                               @RequestParam("username") String username, @RequestParam("instance_id") String instance_id
            , @RequestParam("backup_name") String backup_name, @RequestParam("type") String type) {
        log.info("username:{},instance_id:{},backup_name:{}", username, instance_id, backup_name);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
            checkNotNull(backup_name);
            checkNotNull(type);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        Backup backup = null;
        try {
            backup = rdsBackupService.createBackup(username, instance_id, backup_name, type);
        } catch (Exception e) {
            log.error("[ createBackup---user:" + username + " ]Error:", e);
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        request.setAttribute("userId", username);
        String result = "{\"result\":\"success\",\"content\":" + JSON.toJSONString(backup) + "}";
        log.debug("result:{}", result);
        return result;
    }

    @RequestMapping(value = "/g/user/rds/backupConfig/", method = RequestMethod.GET)
    @ResponseBody
    public String getBackupConfig(HttpServletRequest request,
                                  @RequestParam("username") String username, @RequestParam("instance_id") String instance_id) {
        log.info("username:{},instance_id:{}", username, instance_id);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        BackupConfig backupConfig = null;
        try {
            backupConfig = rdsBackupService.getBackupConfig(username, instance_id);
        } catch (Exception e) {
            log.error("[ getBackupConfig---user:" + username + " ]Error:", e);
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        request.setAttribute("userId", username);
        String result = "{\"result\":\"success\",\"content\":" + JSON.toJSONString(backupConfig) + "}";
        log.debug("result:{}", result);
        return result;
    }


}
