package com.ksyun.vm.utils;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.UserPo;
import com.ksyun.vm.pojo.user.UserPojo;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;
    protected Logger logger = Logger.getLogger(getClass());

    @Test
    public void testGetUsers() throws NoTokenException, ErrorCodeException {
        logger.info("test");
        Constants.setPropertyValue(InitConst.HTTP_HOST,
                Constants.getPropertyValue(InitConst.HTTP_HOST_PUBLIC));
        Constants.setPropertyValue(InitConst.HTTP_PORT,
                Constants.getPropertyValue(InitConst.HTTP_PORT_PUBLIC));
        List<UserPojo> userPos = userService.getUsers();
        for(UserPojo pojo:userPos){
            logger.info("pojo:"+ ToStringBuilder.reflectionToString(pojo));
        }
    }
}
