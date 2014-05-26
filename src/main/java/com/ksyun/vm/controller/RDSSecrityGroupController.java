package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSON;
import com.ksyun.vm.pojo.rds.Backup;
import com.ksyun.vm.pojo.rds.RDSInstanceSecGroup;
import com.ksyun.vm.service.RDSBackupService;
import com.ksyun.vm.service.RDSSecrityGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ZHANGNAN4 on 2014-5-26.
 * Email: zn.share@gmail.com
 */
@Controller
@RequestMapping
public class RDSSecrityGroupController {
    private Logger log = LoggerFactory.getLogger(RDSSecrityGroupController.class);
    @Resource
    private RDSSecrityGroupService rdsSecrityGroupService;

    @RequestMapping(value = "/g/user/rds/secrityGroup/", method = RequestMethod.GET)
    @ResponseBody
    public String getSecrityGroup(HttpServletRequest request,
                                  @RequestParam("username") String username, @RequestParam("securityGroupId") String securityGroupId) {
        log.info("username:{},securityGroupId:{}", username, securityGroupId);
        try {
            checkNotNull(username);
            checkNotNull(securityGroupId);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        RDSInstanceSecGroup rdsInstanceSecGroup = null;
        try {
            rdsInstanceSecGroup = rdsSecrityGroupService.getSecrityGroup(username, securityGroupId);
        } catch (Exception e) {
            log.error("[ getSecrityGroup---user:" + username + " ]Error:", e);
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        request.setAttribute("userId", username);
        String result = "{\"result\":\"success\",\"content\":" + JSON.toJSONString(rdsInstanceSecGroup) + "}";
        log.debug("result:{}", result);
        return result;
    }
}
