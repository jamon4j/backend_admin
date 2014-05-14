package com.ksyun.vm.service.rds;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.rds.RDSInstance;
import com.ksyun.vm.service.BaseAuth;
import com.ksyun.vm.service.RdsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 上午11:27
 * Func:
 */
@Service
public class RDSInstanceAuth  implements BaseAuth {
    @Resource
    private RdsService rdsService;

    public boolean isOwner(String username, String id) throws ErrorCodeException, NoTokenException {
        if (existRDS(username, id)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean existRDS(String username,String id) throws  ErrorCodeException, NoTokenException {
        {
            List<RDSInstance> list = rdsService.getInstanceList(username);
            if(list!=null&&!list.isEmpty()){
                for(RDSInstance instance:list){
                    if(instance.getId().equals(id)){
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
