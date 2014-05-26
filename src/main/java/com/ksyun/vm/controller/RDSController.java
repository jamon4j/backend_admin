package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSON;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.Result;
import com.ksyun.vm.pojo.rds.*;
import com.ksyun.vm.pojo.vm.VmValidationPo;
import com.ksyun.vm.service.RDSService;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.HttpUtils;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.TimeUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 上午10:38
 * Func:
 */
@Controller
@RequestMapping()
public class RDSController {

    private Logger log = LoggerFactory.getLogger(RDSController.class);
    @Resource
    private RDSService rdsService;

    @RequestMapping(value = "/g/rds/init")
    public ModelAndView initLBS(ModelAndView mav) {
        mav.setViewName("/gestion/rds/rds_list");
        return mav;
    }

    @Deprecated
    @RequestMapping(value = "/g/user/rdslist1/{user_id}")
    @ResponseBody
    public ModelAndView getInstanceList(@PathVariable("user_id") String userId, HttpServletRequest request, ModelAndView mav) {
        Result result = new Result();
        List<RDSInstance> instances = null;
        boolean flag = true;
        try {
        } catch (Exception e) {
            log.error("[ RDS_INSTANCE_LIST---user:" + userId + " ]Error:", e);
        } finally {
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
            ReturnRdsInstance returnRdsInstance = new ReturnRdsInstance();
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
            request.setAttribute("userId", userId);
            mav.addObject("rdslist", returnRdsInstance);
            mav.setViewName("/gestion/rds/rds_list");
            return mav;
        }
    }

    @RequestMapping(value = "/g/user/rdslist/")
    @ResponseBody
    public ModelAndView getInstanceList2(@RequestParam(value = "user_id", required = false) String userId,
                                         @RequestParam(value = "group", required = false) String group,
                                         @RequestParam(value = "instance_id", required = false) String instance_id, HttpServletRequest request, ModelAndView mav) {
        log.info("userId:{},group:{},instance_id:{}", userId, group, instance_id);
        RDSGroupDTO rdsGroupDTO = null;
        try {
        } catch (Exception e) {
            log.error("[ RDS_INSTANCE_LIST---user:" + userId + " ]Error:", e);
        } finally {
            try {
                rdsGroupDTO = rdsService.getRDSGroupDTO(userId);
            } catch (Exception e) {
                if (e instanceof ErrorCodeException) {
                    log.error("[ RDS_INSTANCE_LIST---user:" + userId + " ]Error:" + ((ErrorCodeException) e).getResult().getStatus() + "---" + ((ErrorCodeException) e).getResult().getMessage());
                } else {
                    log.error("[ RDS_INSTANCE_LIST---user:" + userId + " ]Error:", e);
                }
            }

            request.setAttribute("userId", userId);
            mav.addObject("rdsGroupDTO", rdsGroupDTO);
            mav.setViewName("/gestion/rds/rds_list");
            return mav;
        }
    }

    @RequestMapping(value = "/instance/get/{instance_id}", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getInstance(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("instance_id") String instance_id
            , ModelAndView mav) {
        RDSInstance instance;
        try {
            instance = rdsService.getInstance(username, instance_id);
        } catch (Exception e) {
            if (e instanceof ErrorCodeException) {
                log.error("[ RDS_INSTNACE_GET---user:" + username + " ]Error:" + ((ErrorCodeException) e).getResult().getStatus() + "---" + ((ErrorCodeException) e).getResult().getMessage());
            } else {
                log.error("[ RDS_INSTNACE_GET---user:" + username + " ]Error:", e);
            }
            return mav;
        }
        log.info("用户{}/rds/instance/get，返回结果{}", new Object[]{username, ToStringBuilder.reflectionToString(instance)});
        return mav;
    }

    @RequestMapping(value = "/g/user/createrds/", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String addInstance(HttpServletRequest request, @RequestBody final CreateInstance createInstance) {
        final String username = createInstance.getUser_id();

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
                                    while (retry >= 0 && ((po = rdsService.getInstanceFull(username, rdsId)).getStatus().equalsIgnoreCase("building"))) {
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

    @RequestMapping(value = "/g/user/rds/resetAdminPassword/")
    @ResponseBody
    public String resetAdminPassword(@RequestParam("username") String username, @RequestParam("instance_id") String instance_id
            , @RequestParam("password") String password) {
        log.info("username:{},instance_id:{},password:{}", username, instance_id, password);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
            checkNotNull(password);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        try {
            rdsService.resetAdminPassword(username, instance_id, password);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/g/user/rds/upgrade/")
    @ResponseBody
    public String upgrade(@RequestParam("username") String username, @RequestParam("instance_id") String instance_id) {
        log.info("username:{},instance_id:{}", username, instance_id);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        try {
            rdsService.upgrade(username, instance_id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/g/user/rds/restart/")
    @ResponseBody
    public String restart(@RequestParam("username") String username, @RequestParam("instance_id") String instance_id) {
        log.info("username:{},instance_id:{}", username, instance_id);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        try {
            rdsService.restart(username, instance_id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/g/user/rds/resize/")
    @ResponseBody
    public String resize(@RequestParam("username") String username, @RequestParam("instance_id") String instance_id,
                         @RequestParam("ram") String ram, @RequestParam("vcpus") String vcpus, @RequestParam("disk") String disk) {
        log.info("username:{},instance_id:{},ram:{},vcpus:{},disk:{}", username, instance_id, ram, vcpus, disk);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
            checkNotNull(ram);
            checkNotNull(vcpus);
            checkNotNull(disk);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        try {
            rdsService.resize(username, instance_id, ram, vcpus, disk);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/g/user/rds/migrate/")
    @ResponseBody
    public String migrate(@RequestParam("username") String username, @RequestParam("instance_id") String instance_id) {
        log.info("username:{},instance_id:{}", username, instance_id);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        try {
            rdsService.migrate(username, instance_id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/g/user/rds/failover/")
    @ResponseBody
    public String failover(@RequestParam("username") String username, @RequestParam("instance_id") String instance_id) {
        log.info("username:{},instance_id:{}", username, instance_id);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        try {
            rdsService.failover(username, instance_id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/g/user/rds/startInstance/")
    @ResponseBody
    public String startInstance(@RequestParam("username") String username, @RequestParam("instance_id") String instance_id) {
        log.info("username:{},instance_id:{}", username, instance_id);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        try {
            rdsService.startInstance(username, instance_id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/g/user/rds/stopInstance/")
    @ResponseBody
    public String stopInstance(@RequestParam("username") String username, @RequestParam("instance_id") String instance_id) {
        log.info("username:{},instance_id:{}", username, instance_id);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        try {
            rdsService.stopInstance(username, instance_id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/g/user/rds/removeInstance/")
    @ResponseBody
    public String removeInstance(@RequestParam("username") String username, @RequestParam("instance_id") String instance_id) {
        log.info("username:{},instance_id:{}", username, instance_id);
        try {
            checkNotNull(username);
            checkNotNull(instance_id);
        } catch (Exception e) {
            return "{\"result\":\"参数不能为空!\"}";
        }
        try {
            rdsService.removeInstance(username, instance_id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return "{\"result\":\"" + e.getMessage() + "\"}";
        }
        return "{\"result\":\"success\"}";
    }
}


