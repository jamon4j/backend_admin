package com.ksyun.vm.service;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.rds.Backup;
import com.ksyun.vm.pojo.rds.BackupConfig;
import com.ksyun.vm.utils.InitConst;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ZHANGNAN4 on 2014-5-21.
 * Email: zn.share@gmail.com
 */
@Service
@Scope
public class RDSBackupService {
    protected Logger logger = Logger.getLogger(getClass());

    @Resource
    private JSONService jsonService;

    public Backup createBackup(String username, String instance_id, String backup_name, String type) throws ErrorCodeException, NoTokenException {
        String requestBody = "{\"name\":\"" + backup_name + "\",\"instance_id\":\""
                + instance_id + "\",\"type\":\"" + type + "\",\"is_req_body\":\"true\"}";
        Backup backup = jsonService.poPost(InitConst.KVM_RDS_BACKUP_ADD, username, null, Backup.class, requestBody, instance_id);
        return backup;
    }

    public List<Backup> getBackups(String username, String instance_id) throws ErrorCodeException, NoTokenException {
        logger.info("jsonService.hashCode():" + jsonService.hashCode());
        List<Backup> backups = jsonService.getPoList(InitConst.KVM_RDS_INSTANCE_BACKUPS, username, null, Backup.class, instance_id);
        return backups;
    }

    public List<Backup> getBackups(String username) throws ErrorCodeException, NoTokenException {
        List<Backup> backups = jsonService.getPoList(InitConst.KVM_RDS_BACKUP_LIST, username, null, Backup.class);
        return backups;
    }

    public void delBackup(String username, String backup_id) throws ErrorCodeException, NoTokenException {
        jsonService.poDelete(InitConst.KVM_RDS_BACKUP_DEL, username, null, backup_id);
    }


    public BackupConfig getBackupConfig(String username,String instance_id) throws Exception{
        BackupConfig backupConfig = null;
        try {
            backupConfig = jsonService.poGet(InitConst.KVM_RDS_INSTANCE_BACKUP_CONFIG, username, null, BackupConfig.class, instance_id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
        return backupConfig;
    }
}
