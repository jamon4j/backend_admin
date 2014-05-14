package com.ksyun.vm.service;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.zone.ZonePojo;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
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
public class ZoneServiceTest {
    protected Logger logger = Logger.getLogger(getClass());
    @Autowired
    private ZoneService zoneService;

    @Test
    public void testGetZoneList() throws NoTokenException, ErrorCodeException {
        logger.info("test");
        Constants.setPropertyValue(InitConst.HTTP_HOST,
                Constants.getPropertyValue(InitConst.HTTP_HOST_PRIVATE));
        Constants.setPropertyValue(InitConst.HTTP_PORT,
                Constants.getPropertyValue(InitConst.HTTP_PORT_PRIVATE));
        List<ZonePojo> zoneList = zoneService.getZoneList();
        for(ZonePojo z:zoneList){
            logger.info("z:"+ ToStringBuilder.reflectionToString(z));
        }
    }
}
