package com.ksyun.vm.service;

import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.UserPo;
import com.ksyun.vm.pojo.stat.IpStat;
import com.ksyun.vm.pojo.stat.StatHost;
import com.ksyun.vm.pojo.stat.StatZone;
import com.ksyun.vm.pojo.user.UserPojo;
import com.ksyun.vm.utils.InitConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-11-4
 * Time: 下午12:38
 * Func:
 */
@Service
public class StatService {
    @Autowired
    private JSONService jsonService;

    public List<StatZone> getStatZone() throws ErrorCodeException, NoTokenException {
        List<StatZone> list = jsonService.getPoList(InitConst.KVM_STAT_ZONE, InitConst.ADMIN, InitConst.PASSWORD, StatZone.class);
        return list;
    }

    public List<StatHost> getStatHost(String hostname,List<UserPojo> userList) throws ErrorCodeException, NoTokenException {
        List<StatHost> list = jsonService.getPoList(InitConst.KVM_STAT_HOST, InitConst.ADMIN, InitConst.PASSWORD, StatHost.class, hostname);
        for(StatHost host:list){
            if(host.getResource().getProject().equals("(used_max)")){
                host.getResource().setEmail("");
                host.getResource().setProject("total");
            }
            for(UserPojo pojo:userList){
                if(host.getResource().getProject().equals(pojo.getUser_name())) {
                    host.getResource().setEmail(pojo.getEmail());
                    break;
                }
            }
        }
        return list;
    }

    public List<IpStat> getIpStat() throws ErrorCodeException, NoTokenException {
        return jsonService.getPoList(InitConst.KVM_STAT_IP, InitConst.ADMIN, InitConst.PASSWORD, IpStat.class);
    }
}
