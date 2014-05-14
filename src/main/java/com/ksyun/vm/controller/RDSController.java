package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSON;
import com.ksyun.tools.net.ServletUtil;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.Result;
import com.ksyun.vm.pojo.rds.*;
import com.ksyun.vm.pojo.vm.VmValidationPo;
import com.ksyun.vm.service.*;
import com.ksyun.vm.service.rds.RDSInstanceAuth;
import com.ksyun.vm.service.rds.RDSInstanceCheck;
import com.ksyun.vm.utils.*;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 上午10:38
 * Func:
 */
@Controller
@RequestMapping()
public class RDSController {

    Logger log = LoggerFactory.getLogger(RDSController.class);
    @Resource
    private RdsService rdsService;
    @Resource
    private RDSInstanceAuth rdsInstanceAuth;
    @Resource(type = RDSInstanceCheck.class)
    private Validation rDSInstanceCheck;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/g/user/rdslist/{user_id}")
    @ResponseBody
    public ModelAndView getInstanceList(@PathVariable("user_id") String userId, HttpServletRequest request,ModelAndView mav) {
        Result result = new Result();
        List<RDSInstance> instances = null;
        boolean flag = true;
        boolean notExits = true;
        try {
        } catch (Exception e) {
            log.error("[ RDS_INSTANCE_LIST---user:" + userId + " ]Error:", e);
        } finally {
            if (notExits) {
                try {
                    instances = rdsService.getInstanceList(userId);
                } catch (Exception e) {
                    if (e instanceof ErrorCodeException) {
                        log.error("[ RDS_INSTANCE_LIST---user:" + userId + " ]Error:" + ((ErrorCodeException) e).getResult().getStatus() + "---" + ((ErrorCodeException) e).getResult().getMessage());
                    } else {
                        log.error("[ RDS_INSTANCE_LIST---user:" + userId + " ]Error:", e);
                    }
                    flag = false;
                }
            }
            ReturnRdsInstance returnRdsInstance= new ReturnRdsInstance();
            if (flag) {
                try {
                    returnRdsInstance.setRdsInstances(instances);
                    VmValidationPo vmValidationPo = new VmValidationPo();
                    vmValidationPo.setUserName(userId);
                    returnRdsInstance.setVmValidationPo(vmValidationPo);
                } catch (Exception e) {
                    log.error("[ RDS_INSTANCE_LIST---user:" + userId + " ]Error:", e);
                }
                result.setStatus("1");
                result.setStatusText("success");
                log.info("用户{}/rds/instance/list，返回结果{}", new Object[]{userId, result});
            } else {
                result.setStatus("0");
                result.setStatusText("获取失败!");
            }
            request.setAttribute("userid", userId);
            mav.addObject("rdslist", returnRdsInstance);
            mav.setViewName("/gestion/user/rds_list");
            return mav;
        }
    }

    @RequestMapping(value = "/instance/get/{instance_id}", method = RequestMethod.POST)
    @ResponseBody
    public Result getInstance(HttpServletRequest request, @PathVariable String instance_id) {
        String username = request.getAttribute("username").toString();
        List<RDSInstance> instances = null;
        Map<String, String> checkMap = Tools.collectProperties(new String[]{"id"}, new String[]{instance_id});
        boolean flag = true;
        boolean notExits = true;
        Result result = null;
        try {
            rDSInstanceCheck.setBaseAuth(rdsInstanceAuth);
            Map<String, Object> map = rDSInstanceCheck.check(MethodType.SELECT, username, checkMap);
            if (map.size() > 0) {
                if (Boolean.valueOf(map.get("isNotPass").toString())) {
                    flag = false;
                    notExits = false;
                    return result = (Result) map.get("result");
                }
            }
        } catch (Exception e) {
            log.error("[ RDS_INSTNACE_GET---user:" + username + " ]Error:", e);
        } finally {
            if (notExits) {
                try {
                    instances = new ArrayList<>();
                    RDSInstance instance = rdsService.getInstance(username, instance_id);
                    instances.add(instance);
                } catch (Exception e) {
                    if (e instanceof ErrorCodeException) {
                        log.error("[ RDS_INSTNACE_GET---user:" + username + " ]Error:" + ((ErrorCodeException) e).getResult().getStatus() + "---" + ((ErrorCodeException) e).getResult().getMessage());
                    } else {
                        log.error("[ RDS_INSTNACE_GET---user:" + username + " ]Error:", e);
                    }
                    flag = false;
                }
            }
            if (flag) {
                List<ReturnRdsInstance> list = new ArrayList<>();
                try {
                    for (RDSInstance instance : instances) {
                        instance.getFlavor().setLinks(null);
                        instance.getFlavor().setId(null);
                        instance.getFlavor().setLocal_storage(null);
                        instance.getFlavor().setName(null);
                        ReturnRdsInstance returnRdsInstance = new ReturnRdsInstance();
                        BeanUtils.copyProperties(returnRdsInstance, instance);
//                        if (returnRdsInstance.getId() == null) {
//                            returnRdsInstance.setId(instance.getId());
//                        }
                        list.add(returnRdsInstance);
                    }
                } catch (Exception e) {
                    log.error("[ RDS_INSTNACE_GET---user:" + username + " ]Error:", e);
                }
                result = new Result();
                result.setData(list);
                result.setStatus("1");
                result.setStatusText("success");
                log.info("用户{}/rds/instance/get，返回结果{}", new Object[]{username, result});
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
    }

    @RequestMapping(value = "/instance/create", method = RequestMethod.POST)
    @ResponseBody
    public String addInstance(HttpServletRequest request, @RequestBody final CreateInstance createInstance) {
        final String username = createInstance.getUser_id();
        if (!userService.isRegister(createInstance.getUser_id(), null, createInstance.getEmail())) {
            log.error("用户{}获取用户信息失败", new Object[] { createInstance.getUser_id() });
            return "{\"result\":\"failed\"}";
        }
        new Thread(new Runnable() {
            boolean flag = true;
            String message = "";

            public void run() {
                CallBackRDSPo callBackRDSPo = new CallBackRDSPo();
                RDSValidationPo rdsValidationPo = null;
                try {
                    log.info("准备创建RDS-Instance:" + JSON.toJSONString(createInstance));
                    rdsValidationPo = rdsService.addRDS(createInstance);
                } catch (NoTokenException e) {
                    log.error("[ RDS CREATE---user:" + username + " ]Error:", e);
                    flag = false;
                    message = "NoTokenException:" + e.getMessage();
                } catch (ErrorCodeException e) {
                    log.error("[ RDS CREATE---user:" + username + " ]Error:" + e.getResult().getStatus() + "---" + e.getResult().getMessage());
                    flag = false;
                    message = "ErrorCodeException:" + e.getResult().getStatus() + "---" + e.getResult().getMessage();
                } catch (Exception e) {
                    log.error("[ RDS CREATE---user:" + username + " ]Error:", e);
                    flag = false;
                    message = "Exception:" + e.getMessage();
                } finally {
                    RDSDataMap dataMap = new RDSDataMap();
                    callBackRDSPo.setTimestamp(TimeUtils.makeYMDHMSStringFormat(new Date()));
                    callBackRDSPo.setRetryCount(0 + "");
                    if (flag) {
                        dataMap.setIs_ok(flag);
                        dataMap.setRds_id(rdsValidationPo.getRdsId());
                        dataMap.setMessage(message);
                        dataMap.setOrder_id(rdsValidationPo.getOrderId());
                        dataMap.setUser_id(rdsValidationPo.getUserName());
                        callBackRDSPo.setDataMap(dataMap);
                        final String rdsId = rdsValidationPo.getRdsId();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int retry = 60;
                                RDSInstance po = null;
                                try {
                                    while (retry >= 0 && ((po = rdsService.getInstance(username, rdsId)).getStatus().equalsIgnoreCase("building"))) {
                                        Thread.currentThread().sleep(3000);
                                        retry--;
                                        log.info(String.format("RDS %s 状态为 %s", po.getId(), po.getStatus()));
                                    }
                                    po.setPrimaryKey(po.getId());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        log.info("创建RDS成功--回调RDS接口:" + JSON.toJSONString(callBackRDSPo));
                    } else {
                        dataMap.setIs_ok(flag);
                        dataMap.setRds_id("");
                        dataMap.setUser_id(createInstance.getUser_id());
                        dataMap.setOrder_id(createInstance.getOrder_id());
                        dataMap.setMessage(message);
                        callBackRDSPo.setDataMap(dataMap);
                        log.info("创建RDS失败--回调RDS接口:" + JSON.toJSONString(callBackRDSPo));
                    }
                    try {
                        HttpUtils.post(Constants.getPropertyValue(InitConst.CREATE_RDS_URL), null, JSON.toJSONString(callBackRDSPo));
                    } catch (Exception e) {
                        log.error("回调创建RDS接口失败:", e);
                    }
                }
            }
        }).start();
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/instance/{instance_id}/rule/del", method = RequestMethod.POST)
    @ResponseBody
    public Result delRule(HttpServletRequest request, @PathVariable String instance_id) {
        String username = request.getAttribute("username").toString();
        String rule_id = ServletUtil.getString(request, "rule_id");
        Map<String, String> checkMap = Tools.collectProperties(new String[]{"id", "rule_id"}, new String[]{instance_id, rule_id});
        boolean flag = true;
        Result result = null;
        try {
            rDSInstanceCheck.setBaseAuth(rdsInstanceAuth);
            Map<String, Object> map = rDSInstanceCheck.check(MethodType.DELETE, username, checkMap);
            if (map.size() > 0) {
                if (Boolean.valueOf(map.get("isNotPass").toString())) {
                    flag = false;
                    return result = (Result) map.get("result");
                }
            }
            rdsService.delRule(username, rule_id, instance_id);
        } catch (Exception e) {
            if (e instanceof ErrorCodeException) {
                log.error("[ RDS_INSTNACE_upgrade---user:" + username + " ]Error:" + ((ErrorCodeException) e).getResult().getStatus() + "---" + ((ErrorCodeException) e).getResult().getMessage());
            } else {
                log.error("[ RDS_INSTNACE_upgrade---user:" + username + " ]Error:", e);
            }
            flag = false;
        } finally {
            if (flag) {
                result = new Result();
                result.setData(new ArrayList());
                result.setStatus("1");
                result.setStatusText("success");
                log.info("用户{}/rds/instance/upgrade，返回结果{}", new Object[]{username, result});
            } else {
                if (result == null) {
                    result = new Result();
                    result.setData(new ArrayList());
                    result.setStatus("0");
                    result.setStatusText("修改失败!");
                }
            }
            return result;
        }
    }
}


