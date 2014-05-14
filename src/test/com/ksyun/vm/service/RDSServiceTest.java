package com.ksyun.vm.service;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.rds.*;
import com.ksyun.vm.routedatasource.DataSourceInstances;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by ZHANGNAN4 on 2014-5-12.
 * Email: zn.share@gmail.com
 */
@ContextConfiguration(locations = {"classpath*:**/applicationContext*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RDSServiceTest {

    @Autowired
    private RDSService rdsService;
    protected Logger logger = Logger.getLogger(getClass());
    @Autowired
    private DataSwitchService dataSwitchService;

    @Before
    public void before(){
        logger.info("before");
        Constants.setPropertyValue(InitConst.HTTP_HOST,
                Constants.getPropertyValue(InitConst.HTTP_HOST_PUBLIC));
        Constants.setPropertyValue(InitConst.HTTP_PORT,
                Constants.getPropertyValue(InitConst.HTTP_PORT_PUBLIC));
        dataSwitchService.setDataSource(DataSourceInstances.DS1);
    }

    @Test
    public void testGetInstanceList() throws NoTokenException, ErrorCodeException {

        List<RDSInstance> instances = rdsService.getInstanceList("39490241");
        logger.info("instances.size():" + instances.size());
        for (RDSInstance i : instances) {
            logger.info("i:" + ToStringBuilder.reflectionToString(i));
        }

    }

    @Test
    public void testGetInstance() throws NoTokenException, ErrorCodeException {

        RDSInstance rdsInstance=  rdsService.getInstance("39490241","f4fa78a2-e9b7-4408-9660-b0b63d384247");
        logger.info("rdsInstance:" + ToStringBuilder.reflectionToString(rdsInstance));
    }

    @Test
    public void testGetBackups() throws NoTokenException, ErrorCodeException {

        List<Backup> backups=  rdsService.getBackups("39490241", "f4fa78a2-e9b7-4408-9660-b0b63d384247");
        logger.info("backups.size():" + backups.size());
        for (Backup b : backups) {
            logger.info("b:" + ToStringBuilder.reflectionToString(b));
        }
    }

    @Test
    public void testAddRDS() throws Exception {

        CreateInstance createInstance = new CreateInstance();
        InstancePo instancePo = new InstancePo();
        instancePo.setType("SG");
        instancePo.setName("test5-14");
        instancePo.setService_type("mysql");
        Extend extend = new Extend();
        extend.setAdmin_user("master");
        extend.setAdmin_password("123456");
        extend.setPort("3306");
        extend.setAutobackup_at("2300");//
        extend.setDuration("1440");//
        extend.setExpire_after("7");//
        instancePo.setExtend(extend);
        CreateFlavor createFlavor = new CreateFlavor();
        createFlavor.setDisk("5");
        createFlavor.setRam("512");
        createFlavor.setVcpus("1");
        instancePo.setFlavor(createFlavor);
        createInstance.setRds(instancePo);
        createInstance.setEmail("winnerku@126.com");
        createInstance.setUser_id("39490241");
        createInstance.setOrder_id(System.currentTimeMillis() + "");

        RDSValidationPo rdsValidationPo = rdsService.addRDS(createInstance);
        logger.info("rdsValidationPo:" + ToStringBuilder.reflectionToString(rdsValidationPo));
    }
}
