package com.ksyun.vm.service;

import com.ksyun.BaseTest;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.rds.Backup;
import com.ksyun.vm.pojo.rds.BackupConfig;
import com.ksyun.vm.utils.InitConst;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ZHANGNAN4 on 2014-5-21.
 * Email: zn.share@gmail.com
 */
@ContextConfiguration(locations = {"classpath*:**/applicationContext*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RDSBackupServiceTest extends BaseTest {
    @Resource
    private RDSBackupService rdsBackupService;

    @Test
    public void testGetBackups() throws NoTokenException, ErrorCodeException {

        List<Backup> backups = rdsBackupService.getBackups("39490241", "d95ae418-f848-434f-bff1-fbec1973007d");
        logger.info("backups.size():" + backups.size());
        for (Backup b : backups) {
            logger.info("b:" + ToStringBuilder.reflectionToString(b));
        }
    }

    @Test
    public void testCreateBackup() throws NoTokenException, ErrorCodeException {
//        String type = "snapshot";
        String type = "autobackup";
        Backup backup = rdsBackupService.createBackup("39490241", "d95ae418-f848-434f-bff1-fbec1973007d","test5-26",type);
        logger.info("b:" + ToStringBuilder.reflectionToString(backup));
    }




    @Test
    public void testDeleteBackup() throws ErrorCodeException, NoTokenException {
        rdsBackupService.delBackup("39490241","085e21f4-dffd-4691-90c0-116e6ed01c1e");
    }

    @Test
    public void testGetBackupConfig() throws Exception {
        BackupConfig backupConfig = rdsBackupService.getBackupConfig("39490241", "d95ae418-f848-434f-bff1-fbec1973007d");
        logger.info("backupConfig:" + ToStringBuilder.reflectionToString(backupConfig));
    }

}
