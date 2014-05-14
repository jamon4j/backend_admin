package com.ksyun.vm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.dao.rds.RDSValidationDao;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.rds.*;
import com.ksyun.vm.service.rds.RDSValidUtils;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 上午10:44
 * Func: sssss
 */
@Service
public class RDSService {

    @Resource
    private JSONService jsonService;
    @Resource
    private RDSValidationDao rdsValidationDao;

    public List<RDSInstance> getInstanceList(String username) throws ErrorCodeException, NoTokenException {
        List<RDSInstance> list = jsonService.getPoList(InitConst.KVM_RDS_INSTANCE_LIST, username,null, RDSInstance.class);
        List<RDSInstance> list2 = new ArrayList<>();
        Iterator<RDSInstance> iterator = list.iterator();
        while(iterator.hasNext()) {
            RDSInstance instance = iterator.next();
            if(isPrivate(instance)) {
                iterator.remove();
                continue;
            }
            RDSInstance instance2 = getInstance(username,instance.getId());
            list2.add(instance2);
        }
        list2 = RDSValidUtils.addValidTime(list2, rdsValidationDao);
        return list2;
    }

    public RDSInstance getInstance(String username,String instance_id) throws ErrorCodeException, NoTokenException {
        RDSInstance instance = jsonService.poGet(InitConst.KVM_RDS_INSTANCE_GET, username, null, RDSInstance.class, instance_id);
        BackupConfig backupConfig = jsonService.poGet(InitConst.KVM_RDS_INSTANCE_BACKUP_CONFIG, username,null, BackupConfig.class, instance.getId());
        instance.setBackup_config(backupConfig);
        Flavor flavor = jsonService.poGet(InitConst.KVM_RDS_FLAVOR_GET, username, null, Flavor.class, instance.getFlavor().getId());
        instance.setFlavor(flavor);
        RDSInstanceSecGroup secGroups = jsonService.poGet(InitConst.KVM_RDS_SECURITYGROUP_GET, username, null, RDSInstanceSecGroup.class, instance.getSecurity_group());
        instance.setrDSInstanceSecGroup(secGroups);
        List<Backup> backups = getBackups(username,instance_id);
        instance.setBackups(backups);
        instance = RDSValidUtils.addValidTime(instance, rdsValidationDao);
        return instance;
    }

    public void resetAdminPassword(String username,String instance_id,String password) throws ErrorCodeException, NoTokenException {
        String requestBody = "{\"password\":\"" + password + "\"}";
        jsonService.poPut(InitConst.KVM_RDS_INSTANCE_RESET_ADMIN_PASSWORD, username, null, null,requestBody, instance_id);
    }

    public void upgrade(String username,String instance_id) throws ErrorCodeException, NoTokenException {
        jsonService.poPut(InitConst.KVM_RDS_INSTANCE_UPGRADE_HA, username, null, null, "", instance_id);
    }

    public void restart(String username,String instance_id) throws ErrorCodeException, NoTokenException {
        jsonService.poPut(InitConst.KVM_RDS_INSTANCE_RESTART, username, null, null, "", instance_id);
    }

    public void resize(String username,String instance_id,String ram,String vcpus,String disk) throws ErrorCodeException, NoTokenException {
        String requestBody = "{\"ram\":\"" + ram + "\",\"vcpus\":\"" + vcpus + "\",\"disk\":\"" + disk + "\"}";
        jsonService.poPut(InitConst.KVM_RDS_INSTANCE_RESIZE, username, null, null, requestBody, instance_id);
    }

    public void startInstance(String username,String instance_id) throws ErrorCodeException, NoTokenException {

    }

    public void stopInstance(String username,String instance_id) throws ErrorCodeException, NoTokenException {

    }

    public void removeInstance(String username,String instance_id) throws ErrorCodeException, NoTokenException {
        List<Backup> backups = getBackups(username, instance_id);
        for(Backup backup:backups) {
            jsonService.poDelete(InitConst.KVM_RDS_BACKUP_DEL, username, null, backup.getId());
        }
        jsonService.poDelete(InitConst.KVM_RDS_INSTANCE_DEL, username, null, instance_id);
    }

    public Backup createBackup(String username,String instance_id,String backup_name) throws ErrorCodeException, NoTokenException {
        String requestBody = "{\"name\":\""+backup_name+"\",\"instance_id\":\""+instance_id+"\",\"is_req_body\":\"true\"}";
        Backup backup = jsonService.poPost(InitConst.KVM_RDS_BACKUP_ADD, username, null,Backup.class,requestBody, instance_id);
        return backup;
    }

    public List<Backup> getBackups(String username,String instance_id) throws ErrorCodeException, NoTokenException {
        List<Backup> backups = jsonService.getPoList(InitConst.KVM_RDS_BACKUP_LIST, username, null, Backup.class, instance_id);
        return backups;
    }

    public List<Backup> delBackup(String username,String instance_id,String backup_id) throws ErrorCodeException, NoTokenException {
        List<Backup> backups = jsonService.getPoList(InitConst.KVM_RDS_BACKUP_LIST, username, null, Backup.class, instance_id);
        return backups;
    }

    public List<RDSInstanceRule> getRules(String username,String instance_id) throws ErrorCodeException, NoTokenException {
        RDSInstance instance = getInstance(username, instance_id);
        return instance.getrDSInstanceSecGroup().getRules();
    }

    public RDSInstanceRule createRule(String username,String instance_id,String cidr) throws ErrorCodeException, NoTokenException {
        RDSInstance instance = getInstance(username, instance_id);
        Map<String, String> map = new HashMap<>();
        map.put("group_id", instance.getSecurity_group());
        map.put("protocol", "tcp");
        map.put("from_port", instance.getPort());
        map.put("to_port", instance.getPort());
        map.put("cidr", cidr);
        map.put("is_req_body", "true");
        RDSInstanceRule rule = jsonService.poPost(InitConst.KVM_RDS_SECURITYGROUPRULE_ADD, username, null, RDSInstanceRule.class, JSONObject.toJSONString(map));
        instance.getrDSInstanceSecGroup().getRules().add(rule);
        instance.setPrimaryKey(instance.getId());
        return rule;
    }

    public void delRule(String username,String rule_id,String instance_id) throws ErrorCodeException, NoTokenException {
        jsonService.poDelete(InitConst.KVM_RDS_SECURITYGROUPRULE_DEL, username, null, rule_id);
        RDSInstance instance = getInstance(username, instance_id);
        for(RDSInstanceRule rule:instance.getrDSInstanceSecGroup().getRules()) {
            if(rule.getId().equals(rule_id)){
                instance.getrDSInstanceSecGroup().getRules().remove(rule);
            }
        }
        instance.setPrimaryKey(instance.getId());
    }

    public RDSValidationPo addRDS(CreateInstance createInstance) throws Exception {
        InstancePo instancePo = createInstance.getRds();
        RDSInstance instance = null;
        RDSValidationPo rdsValidationPo = new RDSValidationPo();
        if(instancePo.getType().equals("SG")||instancePo.getType().equals("HA")){
            instancePo.getExtend().setAutobackup_at("2300");
            instancePo.getExtend().setDuration("1440");
            instancePo.getExtend().setExpire_after("7");
            instance = jsonService.poPost(InitConst.KVM_RDS_INSTANCE_ADD, createInstance.getUser_id(), null, RDSInstance.class, JSON.toJSONString(instancePo));
            BackupConfig backupConfig = jsonService.poGet(InitConst.KVM_RDS_INSTANCE_BACKUP_CONFIG,createInstance.getUser_id(),null,BackupConfig.class,instance.getId());
            if(StringUtils.isNotEmpty(createInstance.getValid_time())){
                rdsValidationPo.setValidTime(TimeUtils.makeYMDHMSDateFormat(createInstance.getValid_time()));
            }
            rdsValidationPo.setType(instance.getType());
            rdsValidationPo.setAdminPassword(createInstance.getRds().getExtend().getAdmin_password());
            rdsValidationPo.setAdminUser(createInstance.getRds().getExtend().getAdmin_user());
            rdsValidationPo.setPort(createInstance.getRds().getExtend().getPort());
            rdsValidationPo.setDuration(backupConfig.getDuration());
            rdsValidationPo.setAutobackup_at(backupConfig.getAutobackup_at());
            rdsValidationPo.setExpireAfter(backupConfig.getExpire_after());
            rdsValidationPo.setCreateTime(Calendar.getInstance().getTime());
            rdsValidationPo.setRam(createInstance.getRds().getFlavor().getRam());
            rdsValidationPo.setVcpus(createInstance.getRds().getFlavor().getVcpus());
            rdsValidationPo.setDisk(createInstance.getRds().getFlavor().getDisk());
            rdsValidationPo.setEmail(createInstance.getEmail());
            rdsValidationPo.setName(instance.getName());
            rdsValidationPo.setOrderId(createInstance.getOrder_id());
            rdsValidationPo.setOrderType(1);
            rdsValidationPo.setRdsId(instance.getId());
            rdsValidationPo.setServiceType(createInstance.getRds().getService_type());
            rdsValidationPo.setUserName(createInstance.getUser_id());
        }else if(instancePo.getType().equals("RR")) {
            RDSInstance instance1 = getInstance(createInstance.getUser_id(), instancePo.getInstance_id());
            if(!instance1.getType().equals("MASTER")){
                throw new Exception("SG can't be Create RR Instance!");
            }
            instance = jsonService.poPost(InitConst.KVM_RDS_INSTANCE_ADD, createInstance.getUser_id(), null, RDSInstance.class, JSON.toJSONString(instancePo));
            rdsValidationPo = (RDSValidationPo) rdsValidationDao.getByRDSId(instance1.getId());
            rdsValidationPo.setRdsId(instance.getId());
            rdsValidationPo.setId(null);
        }
        rdsValidationDao.save(rdsValidationPo);
        instance.setPrimaryKey(instance.getId());
        return rdsValidationPo;
    }

    public boolean isPrivate(RDSInstance instance){
        String type = instance.getType();
        if(type.equalsIgnoreCase("single")||type.equalsIgnoreCase("master")||type.equalsIgnoreCase("rr")){
            return false;
        }else{
            return true;
        }
    }
}
