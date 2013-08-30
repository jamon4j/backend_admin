package com.ksyun.vm.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSON;
import com.ksyun.vm.dto.ebs.VmEBSDto;
import com.ksyun.vm.dto.images.CreateEBSDto;
import com.ksyun.vm.dto.images.CreateEBSImageDto;
import com.ksyun.vm.dto.securitygroup.CreateSGDto;
import com.ksyun.vm.dto.securitygroup.CreateSecurityGroupRuleDto;
import com.ksyun.vm.dto.user.CreateUserDto;
import com.ksyun.vm.dto.vm.CreateServerDto;
import com.ksyun.vm.utils.enumeration.EnumResult;

public class JsonMaker {


    public static String getVNC(String instanceId) throws IOException {
        String requestStr = Constants.getPropertyValue(InitConst.VNC);
        Map<String, String> header = new HashMap<String, String>();
        header.put("X-Auth-Token",HttpUtils.getAdminToken());
        header.put("Content-Type", "application/json");
        Map<String,String> map = new HashMap<String,String>();
        map.put("instance_id",instanceId);
        String requestBody = JSON.toJSONString(map);
        String resultJson = HttpUtils.getPostResponseData(requestStr,requestBody, header);
        return resultJson;
    }

    //创建虚拟机
    public static String createVm(String tenantId, String userId, String name, String imageRef, String count, String securityGroups, String adminPass, String isOnEbs, String zone, String vcpu, String network, String rootDisk, String ram) throws HttpException, IOException {
        String requestStr = Constants.getPropertyValue(InitConst.CREATEVM, tenantId);
        Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
        List<String> list = new LinkedList<String>();
        String[] groups = securityGroups.split(",");
        for (String group : groups) {
            list.add(group);
        }
        String requestBody = makeCreateVmJson(name, imageRef, count, list, adminPass, isOnEbs, zone, vcpu, network, rootDisk, ram);
        String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
        return resultJson;
    }

    public static String createFromListToJson(List<?> list) {
        return JSON.toJSONString(list);
    }

    //创建用户
    public static String createUser(String userName) throws HttpException, IOException {
        String requestStr = Constants.getPropertyValue(InitConst.CREATEUSER);
        String requestBody = makeCreateUserJson(userName);
        System.out.println(requestBody);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
        return resultJson;
    }

    //创建ebs镜像
    public static String createEbsImage(String tenantId, String userId,String ebsid, String desc, String name) throws HttpException, IOException {
        String requestStr = Constants.getPropertyValue(InitConst.CREATEEBSIMAGE);
        Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
        String requestBody = makeCreateEBSImageJson(ebsid,desc, name);
        String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
        return resultJson;

    }

    //创建ebs
    public static String createEBS(String tenantId, String userId, String name, String description, String size) throws Exception {
        String requestStr = Constants.getPropertyValue(InitConst.CREATEEBS, tenantId);
        Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
        String requestBody = makeCreateEBSJson(name, description, size);
        String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
        return resultJson;
    }

    //绑定ebs
    public static String setEBS(String tenantId, String userId, String vmid, String ebsid, String device) throws Exception {
        String requestStr = Constants.getPropertyValue(InitConst.SETEBS);
        Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
        String requestBody = makeSETEBSJson(ebsid, vmid, device);
        String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
        return resultJson;
    }

    public static String unsetEBS(String tenantid, String userid, String attach_id, String volumeid)throws Exception {
        String requestStr = Constants.getPropertyValue(InitConst.UNSETEBS);
        Map<String, String> header = HttpUtils.returnDefaultHeader(tenantid, userid);
        String requestBody = makeUNSETEBSJson(attach_id, volumeid);
        String resultJson = HttpUtils.getPostResponseData(requestStr,requestBody,header);
        return resultJson;
    }

    //创建安全组
    public static String createSg(String tenantId, String userId, String name, String desc) throws HttpException, IOException {
        String requestStr = Constants.getPropertyValue(InitConst.CREATESECURITYGROUP);
        Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
        String requestBody = makeCreateSGJson(name, desc);
        String resultJson = HttpUtils.getPostResponseData(requestStr, requestBody, header);
        return resultJson;
    }

    //删除安全组
    public static String deleteSgs(String tenantId, String userId, String sgids) {
    	Integer failedNo = 0;
        String[] sgidArr = sgids.split(",");
        for (String sgid : sgidArr) {
            String requestStr = Constants.getPropertyValue(InitConst.DELETESECURITYGROUP, sgid);
            String result = HttpUtils.deleteMethod(requestStr, tenantId, userId);
            if(result.equals(EnumResult.failed.value())){
            	failedNo++;
            }
        }
        if(failedNo > 0){
        	return EnumResult.failed.value();
        }
        return EnumResult.successful.value();
    }

    //创建安全组规则
    public static String createRule(String tenantId, String userId, String sgid, String protocal, String fromPort, String toPort, String cidr) throws HttpException, IOException {
        String requestStr = Constants.getPropertyValue(InitConst.CREATESGRULE);
        Map<String, String> header = HttpUtils.returnDefaultHeader(tenantId, userId);
        String requestBody = makeCreateRuleJson(sgid, protocal, fromPort, toPort, cidr);
        System.out.println(requestBody);
        return HttpUtils.getPostResponseData(requestStr, requestBody, header);
    }

    //删除安全组规则
    public static String deleteRule(String tenantId, String userId, String ruleId) {
        String requestStr = Constants.getPropertyValue(InitConst.DELETESGRULE, ruleId);
        return HttpUtils.deleteMethod(requestStr, tenantId, userId);
    }

    private static String makeCreateRuleJson(String sgid, String protocal, String fromPort, String toPort, String cidr) {
        CreateSecurityGroupRuleDto dto = new CreateSecurityGroupRuleDto();
        dto.setFromPort(fromPort);
        dto.setGroupId(sgid);
        dto.setIpProtocol(protocal);
        dto.setToPort(toPort);
        dto.setCidr(cidr);
        String json = JSON.toJSONString(dto);
        return json;
    }

    private static String makeCreateSGJson(String name, String desc) {
        CreateSGDto dto = new CreateSGDto();
        dto.setDesc(desc);
        dto.setName(name);
        String json = JSON.toJSONString(dto);
        return json;
    }

    private static String makeSETEBSJson(String ebsid, String vmid, String device) throws Exception {
        VmEBSDto dto = new VmEBSDto();
        dto.setEbs_id(ebsid);
        dto.setInstance_id(vmid);
        dto.setDevice(device);
        String json = JSON.toJSONString(dto);
        return json;
    }

    private static String makeUNSETEBSJson(String attach_id, String vmid) throws Exception {
        VmEBSDto dto = new VmEBSDto();
        dto.setAttach_id(attach_id);
        dto.setInstance_id(vmid);
        String json = JSON.toJSONString(dto);
        return json;
    }

    private static String makeCreateEBSJson(String name, String description, String size) {
        CreateEBSDto dto = new CreateEBSDto();
        dto.setName(name);
        dto.setDescription(description);
        dto.setSize(size);
        String json = JSON.toJSONString(dto);
        return json;
    }

    public static String makeCreateEBSImageJson(String ebsid,String desc, String name) {
        CreateEBSImageDto dto = new CreateEBSImageDto();
        dto.setEbs_id(ebsid);
        dto.setName(name);
        dto.setDescription(desc);
        String resultJson = JSON.toJSONString(dto);
        return resultJson;
    }

    public static String makeCreateVmJson(String name, String imageRef, String count, List<String> securityGroups, String adminPass, String isOnEbs, String zone, String vcpu, String network, String rootDisk, String ram) {
        CreateServerDto dto = new CreateServerDto();
        dto.setName(name);
        dto.setImage(imageRef);
        dto.setBoot_on_ebs(isOnEbs);
        dto.setInstance_count(count);
        dto.setMemory(ram);
        dto.setNetwork_bandwidth(network);
        dto.setRoot_disk(rootDisk);
        dto.setRootpwd(adminPass);
        dto.setSecurity_groups(securityGroups);
        dto.setVcpu(vcpu);
        if (zone != null && !zone.equals(""))
            dto.setZone(zone);

        String resultJson = JSON.toJSONString(dto);
        return resultJson;
    }

    public static String makeCreateUserJson(String userName) {
        CreateUserDto dto = new CreateUserDto();
        dto.setName(userName);
        dto.setIsAdmin("0");
        Map<String, CreateUserDto> map = new HashMap<String, CreateUserDto>();
        map.put("auth", dto);
        String resultJson = JSON.toJSONString(map);
        return resultJson;
    }
}
