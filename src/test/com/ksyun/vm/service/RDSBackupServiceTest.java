package com.ksyun.vm.service;

import com.ksyun.BaseTest;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.rds.Backup;
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

        List<Backup> backups = rdsBackupService.getBackups("39490241", "700ea22e-006f-43cb-a32d-148786163f1c");
        logger.info("backups.size():" + backups.size());
        for (Backup b : backups) {
            logger.info("b:" + ToStringBuilder.reflectionToString(b));
        }
    }



    @Test
    public void testDeleteBackup() throws ErrorCodeException, NoTokenException {
        rdsBackupService.delBackup("39490241","085e21f4-dffd-4691-90c0-116e6ed01c1e");
    }

}
