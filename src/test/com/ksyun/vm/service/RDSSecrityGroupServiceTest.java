package com.ksyun.vm.service;

import com.ksyun.BaseTest;
import com.ksyun.vm.pojo.rds.RDSInstanceSecGroup;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by ZHANGNAN4 on 2014-5-26.
 * Email: zn.share@gmail.com
 */
public class RDSSecrityGroupServiceTest extends BaseTest {
    @Resource
    private RDSSecrityGroupService rdsSecrityGroupService;

    @Test
    public void testGetSecrityGroup(){
        RDSInstanceSecGroup rdsInstanceSecGroup = rdsSecrityGroupService.getSecrityGroup("39490241","137");
        logger.info("rdsInstanceSecGroup:" + ToStringBuilder.reflectionToString(rdsInstanceSecGroup));
    }
}
