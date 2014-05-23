package com.ksyun;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.rds.*;
import com.ksyun.vm.routedatasource.DataSourceInstances;
import com.ksyun.vm.service.DataSwitchService;
import com.ksyun.vm.service.JSONService;
import com.ksyun.vm.service.RDSService;
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
public abstract class BaseTest {

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
}
