package com.ksyun.vm.service;

import com.ksyun.vm.pojo.rds.RDSInstanceSecGroup;
import com.ksyun.vm.utils.InitConst;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ZHANGNAN4 on 2014-5-26.
 * Email: zn.share@gmail.com
 */
@Service
public class RDSSecrityGroupService {
    protected Logger logger = Logger.getLogger(getClass());

    @Resource
    private JSONService jsonService;

    public RDSInstanceSecGroup getSecrityGroup(String username, String securityGroupId) {
        RDSInstanceSecGroup secGroup = null;
        try {
            secGroup = jsonService.poGet(InitConst.KVM_RDS_SECURITYGROUP_GET, username, null, RDSInstanceSecGroup.class, securityGroupId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return secGroup;
    }
}
