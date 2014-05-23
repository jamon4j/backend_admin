package com.ksyun.vm.service;

import com.ksyun.BaseTest;
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

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ZHANGNAN4 on 2014-5-12.
 * Email: zn.share@gmail.com
 */
@ContextConfiguration(locations = {"classpath*:**/applicationContext*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RDSServiceTest extends BaseTest {

    @Autowired
    private RDSService rdsService;

    @Test
    public void testGetInstanceList() throws NoTokenException, ErrorCodeException {

        List<RDSInstance> instances = rdsService.getInstanceList("19780820");
        logger.info("instances.size():" + instances.size());
        for (RDSInstance i : instances) {
            logger.info("i:" + ToStringBuilder.reflectionToString(i));
        }
    }

    @Test
    public void testGetRDSInstanceListDTO() throws NoTokenException, ErrorCodeException {

        RDSGroupDTO rdsGroupDTO = rdsService.getRDSGroupDTO("39490241");
        logger.info("rdsGroupDTO.getRdsGroups().size():" + rdsGroupDTO.getRdsGroups().size());
        logger.info("rdsGroupDTO.getInstanceSize():" + rdsGroupDTO.getInstanceSize());
        for (RDSGroup group : rdsGroupDTO.getRdsGroups()) {
            logger.info("group.getGroup():" + group.getGroup());
            for(RDSInstance i : group.getRdsInstances()){
                logger.info("i:" + ToStringBuilder.reflectionToString(i));
            }
        }
    }

    @Test
    public void testGetInstance() throws NoTokenException, ErrorCodeException {

        RDSInstance rdsInstance=  rdsService.getInstanceFull("16552981", "bb23316f-0ef6-446a-b8cc-441e88f18086");
        logger.info("rdsInstance:" + ToStringBuilder.reflectionToString(rdsInstance));
    }

    @Test
    public void testRemoveRDS() throws NoTokenException, ErrorCodeException {

         rdsService.removeInstance("39490241", "75d6fc51-1908-4bac-807d-6771b3ca67f3");
    }

    @Test
    public void testResetAdminPassword() throws ErrorCodeException, NoTokenException {
        rdsService.resetAdminPassword("39490241", "2863185f-3d59-441c-bc1e-d5ed5e662bdf", "123456");
    }

    @Test
    public void testUpgrade() throws ErrorCodeException, NoTokenException {
        rdsService.upgrade("39490241", "87b55db1-f84b-4736-8961-52e51b2076b6");
    }


    @Test
    public void testResize() throws ErrorCodeException, NoTokenException {
        rdsService.resize("39490241", "87b55db1-f84b-4736-8961-52e51b2076b6","1024","1","1");
        RDSInstance rdsInstance=  rdsService.getInstanceFull("39490241", "87b55db1-f84b-4736-8961-52e51b2076b6");
        logger.info("rdsInstance:" + ToStringBuilder.reflectionToString(rdsInstance));
    }

    @Test
    public void testMigrate() throws ErrorCodeException, NoTokenException {
        try {
            rdsService.migrate("39490241", "6943e19f-2fc9-41da-a4e6-7df2a67d013d");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFailover() throws Exception {
        rdsService.failover("39490241", "be4e000b-4177-4596-9d3e-2fd0acba2e5b");
    }

    @Test
    public void testAddRDS() throws Exception {

        CreateInstance createInstance = new CreateInstance();
        InstancePo instancePo = new InstancePo();
        instancePo.setType("SG");
        instancePo.setName("test5-22");
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
        createInstance.setUser_id("39490241");

        RDSValidationPo rdsValidationPo = rdsService.addRDS(createInstance);
        logger.info("rdsValidationPo:" + ToStringBuilder.reflectionToString(rdsValidationPo));
    }
}
