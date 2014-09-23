package com.ksyun.vm.controller;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.ic.controller.BaseController;
import com.ksyun.vm.pojo.memdb.ClusterPOJO;
import com.ksyun.vm.pojo.memdb.InstancePOJO;
import com.ksyun.vm.pojo.memdb.SecurityGroupPOJO;
import com.ksyun.vm.pojo.memdb.SeurityGroupRulePOJO;
import com.ksyun.vm.pojo.memdb.FlavorPOJO;

import com.ksyun.vm.service.JSONService;
import com.ksyun.vm.service.MemdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LiYang14 on 2014/9/16.
 */

@Controller
public class MemdbController extends BaseController {

    @Autowired
    private MemdbService memdbService;


    @RequestMapping(value = "/g/memdb/getclusterlist/{tenantid}/{userid}/{region}")
    @ResponseBody
    public ModelAndView getClusterList(@PathVariable("tenantid") String tenantId, @PathVariable("userid") String userId,
                                       @PathVariable("region") String region) {
        List<ClusterPOJO> clusterlist = null;
        try {
            clusterlist = memdbService.getClusterList(userId, tenantId, region);
            if (clusterlist != null) {
                for (ClusterPOJO pojo : clusterlist) {
                    System.out.println("****************" + pojo.getSecurity_group_id() + "******************");
                    pojo.setSecuritygroup(memdbService.clusterSecuritygroupid(userId, tenantId, region, pojo.getSecurity_group_id()));
                }
            }
        } catch (Exception e) {
            logger.error("getclusterlist:", e);
        }
        ModelAndView modelview = new ModelAndView("/gestion/memdb/cluster_list");
        modelview.addObject("userId", userId);
        modelview.addObject("tenantid", tenantId);
        modelview.addObject("region", region);
        modelview.addObject("clusterlist", clusterlist);
        return modelview;
    }

    @RequestMapping(value = "/g/memdb/getinstancelist/{tenantid}/{userid}/{clusterid}/{region}")
    @ResponseBody
    public ModelAndView getInstanceList(@PathVariable("tenantid") String tenantId,
                                        @PathVariable("userid") String userId,
                                        @PathVariable("region") String region,
                                        @PathVariable("clusterid") String cluster_id) {
        List<InstancePOJO> list = null;
        try {
            list = memdbService.getInstanceList(userId, tenantId, region, cluster_id);
            if (list != null) {
                for (InstancePOJO pojo : list) {
                    pojo.setFlavordisplay(memdbService.flavor(userId, tenantId, region, pojo.getFlavor().getId()));
                }
            }


        } catch (Exception e) {
            logger.error("getinstanceList:", e);

        }
        ModelAndView view = new ModelAndView("/gestion/memdb/instance_list");
        view.addObject("cluster_id", cluster_id);
        view.addObject("userId", userId);
        view.addObject("tenantid", tenantId);
        view.addObject("region", region);
        view.addObject("instancelist", list);
        return view;
    }


    @RequestMapping(value = "/g/memdb/addcluster/{tenantid}/{userid}/{region}")
    @ResponseBody
    public String addCluster(@PathVariable("tenantid") String tenantId,
                             @PathVariable("userid") String userId,
                             @PathVariable("region") String region,
                             @RequestParam(value = "name", required = true) String name,
                             @RequestParam(value = "ha_cluster", required = true) String ha_cluster,
                             @RequestParam(value = "protocol", required = true) String protocol,
                             @RequestParam(value = "size", required = true) String size) {
        try {
            memdbService.addCluster(userId, tenantId, region, name, ha_cluster, protocol, Integer.parseInt(size));
        } catch (Exception e) {
            logger.error("getclusterlist:", e);
            return "fail";
        }
        return "ok";
    }


    /**
     * 对cluster进行销毁
     *
     * @param tenantId
     * @param userId
     * @param region
     * @param cluster_id
     * @return
     */
    @RequestMapping(value = "/g/memdb/deletecluster/{tenantid}/{userid}/{clusterid}/{region}")
    @ResponseBody
    public String deleteCluster(@PathVariable("tenantid") String tenantId,
                                @PathVariable("userid") String userId,
                                @PathVariable("region") String region,
                                @PathVariable("clusterid") String cluster_id) {
        try {
            memdbService.clusterDelete(userId, tenantId, region, cluster_id);
        } catch (Exception e) {
            logger.error("deletecluster:", e);
            return "{\"result\":\"error\"}";
        }
        return "{\"result\":\"success\"}";
    }


    /**
     * 对cluster进行扩容
     *
     * @param tenantId
     * @param userId
     * @param region
     * @param cluster_id
     * @param size
     * @return
     */
    @RequestMapping(value = "/g/memdb/clusterresize/{tenantid}/{userid}/{clusterid}/{region}")
    @ResponseBody
    public String clusterResize(@PathVariable("tenantid") String tenantId,
                                @PathVariable("userid") String userId,
                                @PathVariable("region") String region,
                                @PathVariable("clusterid") String cluster_id,
                                @RequestParam(value = "size", required = true) String size) {
        try {
            memdbService.clusterResize(userId, tenantId, region, cluster_id, Integer.parseInt(size));
        } catch (Exception e) {
            logger.error("clusterresize:", e);
            return "{\"result\":\"error\"}";

        }
        return "{\"result\":\"success\"}";
    }


    /**
     * 修改cluster的 maxmemorypolicy 值
     *
     * @param tenantId
     * @param userId
     * @param region
     * @param cluster_id
     * @param policy
     * @return
     */
    @RequestMapping(value = "/g/memdb/clustersetmaxmemorypolicy/{tenantid}/{userid}/{clusterid}/{region}")
    @ResponseBody
    public String clusterSetMaxmemoryPolicy(@PathVariable("tenantid") String tenantId,
                                            @PathVariable("userid") String userId,
                                            @PathVariable("region") String region,
                                            @PathVariable("clusterid") String cluster_id,
                                            @RequestParam(value = "policy", required = true) String policy) {
        try {
            memdbService.clusterSetMaxmemoryPolicy(userId, tenantId, region, cluster_id, policy);
        } catch (Exception e) {
            logger.error("clustersetmaxmemorypolicy:", e);
            return "{\"result\":\"error\"}";
        }
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/g/memdb/clusterflushdb/{tenantid}/{userid}/{clusterid}/{region}")
    @ResponseBody
    public String clusterFlushdb(@PathVariable("tenantid") String tenantId,
                                 @PathVariable("userid") String userId,
                                 @PathVariable("region") String region,
                                 @PathVariable("clusterid") String cluster_id) {
        try {
            memdbService.clusterFlushdb(userId, tenantId, region, cluster_id);
        } catch (Exception e) {
            logger.error("clusterflushdb:", e);
            return "{\"result\":\"error\"}";
        }
        return "{\"result\":\"success\"}";
    }


    /**
     * 在cluster上添加instance
     *
     * @param tenantId
     * @param userId
     * @param region
     * @param role
     * @param flavor_id
     * @return
     */
    @RequestMapping(value = "/g/memdb/addinstance/{tenantid}/{userid}/{clusterid}/{region}")
    @ResponseBody
    public String addInstance(@PathVariable("tenantid") String tenantId,
                              @PathVariable("userid") String userId,
                              @PathVariable("region") String region,
                              @PathVariable("clusterid") String clusterid,
                              @RequestParam(value = "role", required = true) String role,
                              @RequestParam(value = "flavor_id", required = true) String flavor_id) {

        try {
            memdbService.addInstance(userId, tenantId, region, role, flavor_id, clusterid);
        } catch (Exception e) {
            logger.error("addinstance:", e);
            return "{\"result\":\"error\"}";

        }
        return "{\"result\":\"success\"}";
    }

    //failureoverInstance

    @RequestMapping(value = "/g/memdb/failureoverinstance/{tenantid}/{userid}/{clusterid}/{region}")
    @ResponseBody
    public String failureoverInstance(@PathVariable("tenantid") String tenantId,
                                      @PathVariable("userid") String userId,
                                      @PathVariable("region") String region,
                                      @PathVariable("clusterid") String cluster_id,
                                      @RequestParam(value = "old_instance_id", required = true) String old_instance_id) {

        try {
            memdbService.failureoverInstance(userId, tenantId, region,old_instance_id ,cluster_id);
        } catch (Exception e) {
            logger.error("failureoverinstance:", e);
            return "{\"result\":\"error\"}";
        }
        return "{\"result\":\"success\"}";

    }


    @RequestMapping(value = "/g/memdb/clustersecuritygrouprules/{tenantid}/{userid}/{region}")
    @ResponseBody
    public ModelAndView clusterSecuritygroupRules(@PathVariable("tenantid") String tenantId,
                                                  @PathVariable("userid") String userId,
                                                  @PathVariable("region") String region,
                                                  @PathVariable("clusterid") String cluster_id) {
        List<SeurityGroupRulePOJO> list = null;
        try {
            list = memdbService.clusterSecuritygroupRules(userId, tenantId, region, cluster_id);
        } catch (Exception e) {
            logger.error("clustersecuritygrouprules:", e);

        }
        ModelAndView view = new ModelAndView("");
        view.addObject("instances", list);
        return view;
    }

    @RequestMapping(value = "/g/memdb/clustersecuritygroupruleadd/{tenantid}/{userid}/{region}")
    @ResponseBody
    public String clusterSecuritygroupRuleAdd(@PathVariable("tenantid") String tenantId,
                                              @PathVariable("userid") String userId,
                                              @PathVariable("region") String region,
                                              @RequestParam("group_id") String group_id,
                                              @RequestParam("protocol") String protocol,
                                              @RequestParam("from_port") String from_port,
                                              @RequestParam("to_port") String to_port,
                                              @RequestParam("cdir") String cdir) {
        try {
            memdbService.clusterSecuritygroupRuleAdd(userId, tenantId, region, group_id, protocol, from_port, to_port, cdir);
        } catch (Exception e) {
            logger.error("clustersecuritygroupruleadd:", e);
            return "{\"result\":\"error\"}";

        }
        return "{\"result\":\"success\"}";
    }

    @RequestMapping(value = "/g/memdb/clustersecuritygroupRuleDelete/{tenantid}/{userid}/{region}")
    @ResponseBody
    public String clusterSecuritygroupRuleDelete(@PathVariable("tenantid") String tenantId,
                                                 @PathVariable("userid") String userId,
                                                 @PathVariable("region") String region,
                                                 @RequestParam("security_group_rule_id") String security_group_rule_id) {
        try {
            memdbService.clusterSecuritygroupRuleDelete(userId, tenantId, region, security_group_rule_id);
        } catch (Exception e) {
            logger.error("clustersecuritygroupRuleDelete:", e);
            return "fail";
        }
        return "ok";

    }

    @RequestMapping(value = "/g/memdb/flavorlist/{tenantid}/{userid}/{region}")
    @ResponseBody
    public String flavorList(@PathVariable("tenantid") String tenantId,
                             @PathVariable("userid") String userId,
                             @PathVariable("region") String region) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            List<FlavorPOJO> list = memdbService.flavorList(userId, tenantId, region);
            if (list != null) {
                result.put("code", 0);
                result.put("data", JSONObject.toJSONString(list));
            }
        } catch (Exception e) {
            logger.error("flavorList:", e);
            result.put("code", 1);
        }
        return JSONObject.toJSONString(result);

    }


}
