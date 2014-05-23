package com.ksyun.vm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.dao.rds.RDSValidationDao;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.rds.*;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

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
    protected Logger logger = Logger.getLogger(getClass());
    @Resource
    private JSONService jsonService;
    @Resource
    private RDSValidationDao rdsValidationDao;

    @Resource
    private RDSBackupService rdsBackupService;

    public List<RDSInstance> getInstanceList(String username) throws ErrorCodeException, NoTokenException {
        List<RDSInstance> list = jsonService.getPoList(InitConst.KVM_RDS_INSTANCE_LIST, username, null, RDSInstance.class);
        checkNotNull(list);
        List<RDSInstance> list2 = new ArrayList<>();
        for (RDSInstance instance : list) {
            RDSInstance instance2 = getInstanceFull(username, instance.getId());
            list2.add(instance2);
        }
        //list2 = RDSValidUtils.addValidTime(list2, rdsValidationDao);
        return list2;
    }

    public RDSGroupDTO getRDSGroupDTO(String username) throws ErrorCodeException, NoTokenException {
        logger.info("jsonService.hashCode():"+jsonService.hashCode());
        List<RDSInstance> list = jsonService.getPoList(InitConst.KVM_RDS_INSTANCE_LIST, username, null, RDSInstance.class);
        checkNotNull(list);
        RDSGroupDTO rdsGroupDTO = new RDSGroupDTO();
        for (RDSInstance instance : list) {
            RDSInstance instance2 = getInstanceFull(username, instance.getId());
            rdsGroupDTO.addRDSGroup(instance2);
        }
        return rdsGroupDTO;
    }


    public RDSInstance getInstance(String username, String instance_id) {
        RDSInstance instance = null;
        try {
            instance = jsonService.poGet(InitConst.KVM_RDS_INSTANCE_GET, username, null, RDSInstance.class, instance_id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return instance;
    }

    public RDSInstance getInstanceFull(String username, String instance_id) {
        RDSInstance instance = null;
        try {
            instance = jsonService.poGet(InitConst.KVM_RDS_INSTANCE_GET, username, null, RDSInstance.class, instance_id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return instance;
        }
        BackupConfig backupConfig = null;
        try {
            backupConfig = jsonService.poGet(InitConst.KVM_RDS_INSTANCE_BACKUP_CONFIG, username, null, BackupConfig.class, instance.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        instance.setBackup_config(backupConfig);
        Flavor flavor = null;
        try {
            flavor = jsonService.poGet(InitConst.KVM_RDS_FLAVOR_GET, username, null, Flavor.class, instance.getFlavor().getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        instance.setFlavor(flavor);
        RDSInstanceSecGroup secGroups = null;
        try {
            secGroups = jsonService.poGet(InitConst.KVM_RDS_SECURITYGROUP_GET, username, null, RDSInstanceSecGroup.class, instance.getSecurity_group());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        instance.setrDSInstanceSecGroup(secGroups);
//        List<Backup> backups = null;
//        try {
//            backups = rdsBackupService.getBackups(username, instance_id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//        }
//        instance.setBackups(backups);
        //instance = RDSValidUtils.addValidTime(instance, rdsValidationDao);
        return instance;
    }

    public void resetAdminPassword(String username, String instance_id, String password) throws ErrorCodeException, NoTokenException {
        String requestBody = "{\"password\":\"" + password + "\"}";
        jsonService.poPut(InitConst.KVM_RDS_INSTANCE_RESET_ADMIN_PASSWORD, username, null, null, requestBody, instance_id);
    }

    public void upgrade(String username, String instance_id) throws ErrorCodeException, NoTokenException {
        jsonService.poPut(InitConst.KVM_RDS_INSTANCE_UPGRADE_HA, username, null, null, "", instance_id);
    }

    public void restart(String username, String instance_id) throws ErrorCodeException, NoTokenException {
        jsonService.poPut(InitConst.KVM_RDS_INSTANCE_RESTART, username, null, null, "", instance_id);
    }

    public void resize(String username, String instance_id, String ram, String vcpus, String disk) throws ErrorCodeException, NoTokenException {
        String requestBody = "{\"ram\":\"" + ram + "\",\"vcpus\":\"" + vcpus + "\",\"disk\":\"" + disk + "\"}";
        jsonService.poPut(InitConst.KVM_RDS_INSTANCE_RESIZE, username, null, null, requestBody, instance_id);
    }

    public void migrate(String username, String instance_id) throws Exception {
        String requestBody = "{}";
        RDSInstance rdsInstance = getInstance(username, instance_id);
        if (rdsInstance != null && ("MASTER".equalsIgnoreCase(rdsInstance.getType()) || "STANDBY".equalsIgnoreCase(rdsInstance.getType()))) {
            jsonService.poPut(InitConst.KVM_RDS_INSTANCE_MIGRATE, username, null, null, requestBody, instance_id);
        } else {
            throw new Exception("instance_id:" + instance_id + "的实例类型为:" + rdsInstance.getType() + ",不可以做迁移.");
        }
    }

    public void failover(String username, String instance_id) throws Exception {
        String requestBody = "{}";
        RDSInstance rdsInstance = getInstance(username, instance_id);
        if (rdsInstance != null && ("MASTER".equalsIgnoreCase(rdsInstance.getType()) || "STANDBY".equalsIgnoreCase(rdsInstance.getType()))) {
            jsonService.poPut(InitConst.KVM_RDS_INSTANCE_FAILOVER, username, null, null, requestBody, instance_id);
        } else {
            throw new Exception("instance_id:" + instance_id + "的实例类型为:" + rdsInstance.getType() + ",不可以做failover.");
        }
    }

    public void startInstance(String username, String instance_id) throws ErrorCodeException, NoTokenException {

    }

    public void stopInstance(String username, String instance_id) throws ErrorCodeException, NoTokenException {

    }

    public void removeInstance(String username, String instance_id) throws ErrorCodeException, NoTokenException {
//        List<Backup> backups = getBackups(username, instance_id);
//        for(Backup backup:backups) {
//            jsonService.poDelete(InitConst.KVM_RDS_BACKUP_DEL, username, null, backup.getId());
//        }
        //TODO
        jsonService.poDelete(InitConst.KVM_RDS_INSTANCE_DEL, username, null, instance_id);
    }


    public List<RDSInstanceRule> getRules(String username, String instance_id) throws ErrorCodeException, NoTokenException {
        RDSInstance instance = getInstanceFull(username, instance_id);
        return instance.getrDSInstanceSecGroup().getRules();
    }

    public RDSInstanceRule createRule(String username, String instance_id, String cidr) throws ErrorCodeException, NoTokenException {
        RDSInstance instance = getInstanceFull(username, instance_id);
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

    public void delRule(String username, String rule_id, String instance_id) throws ErrorCodeException, NoTokenException {
        jsonService.poDelete(InitConst.KVM_RDS_SECURITYGROUPRULE_DEL, username, null, rule_id);
        RDSInstance instance = getInstanceFull(username, instance_id);
        for (RDSInstanceRule rule : instance.getrDSInstanceSecGroup().getRules()) {
            if (rule.getId().equals(rule_id)) {
                instance.getrDSInstanceSecGroup().getRules().remove(rule);
            }
        }
        instance.setPrimaryKey(instance.getId());
    }

    public RDSValidationPo addRDS(CreateInstance createInstance) throws Exception {
        InstancePo instancePo = createInstance.getRds();
        RDSInstance instance = null;
        RDSValidationPo rdsValidationPo = new RDSValidationPo();
        if (instancePo.getType().equals("SG") || instancePo.getType().equals("HA")) {
            instancePo.getExtend().setAutobackup_at("2300");
            instancePo.getExtend().setDuration("1440");
            instancePo.getExtend().setExpire_after("7");
            instance = jsonService.poPost(InitConst.KVM_RDS_INSTANCE_ADD, createInstance.getUser_id(), null, RDSInstance.class, JSON.toJSONString(instancePo));
            BackupConfig backupConfig = jsonService.poGet(InitConst.KVM_RDS_INSTANCE_BACKUP_CONFIG, createInstance.getUser_id(), null, BackupConfig.class, instance.getId());
            if (StringUtils.isNotEmpty(createInstance.getValid_time())) {
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
        } else if (instancePo.getType().equals("RR")) {
            RDSInstance instance1 = getInstanceFull(createInstance.getUser_id(), instancePo.getInstance_id());
            if (!instance1.getType().equals("MASTER")) {
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
}
