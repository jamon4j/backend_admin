package com.ksyun.vm.service;

import com.alibaba.fastjson.JSONArray;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.zone.ZonePojo;
import com.ksyun.vm.utils.InitConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-10-10
 * Time: 上午11:08
 * Func:
 */
@Service
public class ZoneService {

    @Autowired
    private JSONService jsonService;

    public List<ZonePojo> getZoneList() throws ErrorCodeException, NoTokenException {
        return jsonService.getPoList(InitConst.KVM_ZONE_LIST,InitConst.ADMIN,InitConst.PASSWORD, ZonePojo.class);
    }
}
