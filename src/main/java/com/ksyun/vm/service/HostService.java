package com.ksyun.vm.service;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.vm.VmPojo;
import com.ksyun.vm.pojo.zone.HostPojo;
import com.ksyun.vm.utils.InitConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-10-10
 * Time: 下午7:28
 * Func:
 */
@Service
public class HostService {

    @Autowired
    private JSONService jsonService;

    public List<HostPojo> getHosts(String zoneid) throws ErrorCodeException, NoTokenException {
        List<HostPojo> list = jsonService.getPoList(InitConst.KVM_ZONE_HOST_LIST,InitConst.ADMIN,InitConst.PASSWORD,HostPojo.class,zoneid);
        return list;
    }

    public List<VmPojo> getVms(String hostid) throws ErrorCodeException, NoTokenException {
        List<VmPojo> list = jsonService.getPoList(InitConst.KVM_HOST_VM_LIST,InitConst.ADMIN,InitConst.PASSWORD,VmPojo.class,hostid);
        return list;
    }
}
