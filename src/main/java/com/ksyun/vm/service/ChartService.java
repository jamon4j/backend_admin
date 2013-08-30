package com.ksyun.vm.service;

import com.ksyun.vm.dao.MonitorVmDiskDao;
import com.ksyun.vm.dao.MonitorVmLoadDao;
import com.ksyun.vm.dao.MonitorVmNetworkDao;
import com.ksyun.vm.dao.MonitorVmStatusFlowDao;
import com.ksyun.vm.pojo.MonitorVmDiskPo;
import com.ksyun.vm.pojo.MonitorVmLoadPo;
import com.ksyun.vm.pojo.MonitorVmNetworkPo;
import com.ksyun.vm.pojo.MonitorVmStatusFlowPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-8-29
 * Time: 下午2:49
 * Func:
 */
@Service
public class ChartService {

    @Autowired
    private MonitorVmDiskDao monitorVmDiskDao;

    @Autowired
    private MonitorVmLoadDao monitorVmLoadDao;

    @Autowired
    private MonitorVmNetworkDao monitorVmNetworkDao;

    @Autowired
    private MonitorVmStatusFlowDao monitorVmStatusFlowDao;

    public MonitorVmLoadPo getLoad(String vmuuid){
        return (MonitorVmLoadPo) monitorVmLoadDao.getLastestVmLoad(vmuuid);
    }

    public MonitorVmDiskPo getDisk(String vmuuid){
        return (MonitorVmDiskPo) monitorVmDiskDao.getLastestVmDisk(vmuuid);
    }

    public MonitorVmNetworkPo getNetwork(String vmuuid){
        return (MonitorVmNetworkPo) monitorVmNetworkDao.getLastestVmNetwork(vmuuid);
    }

    public MonitorVmStatusFlowPo getStatus(String vmuuid){
        return (MonitorVmStatusFlowPo) monitorVmStatusFlowDao.getLastestVmStatusFlow(vmuuid);
    }

    public List<MonitorVmLoadPo> getLoadThreeDaysAgo(String vmuuid){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-3);
        long time = c.getTimeInMillis()/1000;
        return monitorVmLoadDao.getVmLoadByTime(vmuuid,time+"");
    }

    public List<MonitorVmNetworkPo> getNetworkThreeDaysAgo(String vmuuid) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-3);
        long time = c.getTimeInMillis()/1000;
        return monitorVmNetworkDao.getVmNetworkByTime(vmuuid,time+"");
    }

    public List<MonitorVmDiskPo> getDiskThreeDaysAgo(String vmuuid) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-3);
        long time = c.getTimeInMillis()/1000;
        return monitorVmDiskDao.getVmDiskByTime(vmuuid,time+"");
    }

    public List<MonitorVmStatusFlowPo> getStatusFlowThreeDaysAgo(String vmuuid) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-3);
        long time = c.getTimeInMillis()/1000;
        return monitorVmStatusFlowDao.getVmStatusFlowByTime(vmuuid,time+"");
    }
}
