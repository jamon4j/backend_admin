package com.ksyun.vm.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.monitor.pojo.hbase.HBaseCell;
import com.ksyun.vm.hbase.HBaseDao;
import com.ksyun.vm.pojo.chart.MonitorVmDiskPo;
import com.ksyun.vm.pojo.chart.MonitorVmLoadPo;
import com.ksyun.vm.pojo.chart.MonitorVmNetworkPo;
import com.ksyun.vm.pojo.chart.MonitorVmStatusFlowPo;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.Tools;

/**
 * User: liuchuandong
 * Date: 13-8-29
 * Time: 下午2:49
 * Func:
 */
@Service
public class ChartService {
    @Autowired
    private HBaseDao hbaseDao;
    
    
	String status_table_name=InitConst.MONITOR_HBASE_TABLE_STATUS+"_"+Constants.getPropertyValue("sys.type");
	String load_table_name=InitConst.MONITOR_HBASE_TABLE_LOAD+"_"+Constants.getPropertyValue("sys.type");
	String network_table_name=InitConst.MONITOR_HBASE_TABLE_NETWORK+"_"+Constants.getPropertyValue("sys.type");
	String disk_table_name=InitConst.MONITOR_HBASE_TABLE_DISK+"_"+Constants.getPropertyValue("sys.type");
	
	
    public MonitorVmLoadPo getLoad(String vmuuid){
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,-30);
        String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        List<Map<String, HBaseCell>> result = hbaseDao.scaner(load_table_name, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        MonitorVmLoadPo po = null;
        if(result != null){
        	for(Map<String, HBaseCell> row : result){
            	HBaseCell cell = row.get("load");
            	po = (MonitorVmLoadPo)JSONObject.parseObject(cell.getValue(),MonitorVmLoadPo.class);
            }
        }
        return po;
    }

    public MonitorVmDiskPo getDisk(String vmuuid,String disk){
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,-30);
        String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        List<Map<String, HBaseCell>> result = hbaseDao.scaner(disk_table_name, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        List<MonitorVmDiskPo> list = new ArrayList<MonitorVmDiskPo>();
        if(result != null){
        	for(Map<String, HBaseCell> row : result){
            	Set<Map.Entry<String, HBaseCell>> set = row.entrySet();
                for (Iterator<Map.Entry<String, HBaseCell>> it = set.iterator(); it.hasNext();) {
                    Map.Entry<String, HBaseCell> entry = (Map.Entry<String, HBaseCell>) it.next();
                    MonitorVmDiskPo po = (MonitorVmDiskPo)JSONObject.parseObject(entry.getValue().getValue(),MonitorVmDiskPo.class);
                    list.add(po);
                }
            }
            System.out.println(list);
            MonitorVmDiskPo resultPo = new MonitorVmDiskPo();
            for(MonitorVmDiskPo po : list){
            	if(po.getDisk().equals(disk)){
            		resultPo = po;
            	}
            }
            return resultPo;
        }
        return null;
    }

    public MonitorVmNetworkPo getNetwork(String vmuuid,String mac){
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,-30);
        String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        List<Map<String, HBaseCell>> result = hbaseDao.scaner(network_table_name, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        List<MonitorVmNetworkPo> list = new ArrayList<MonitorVmNetworkPo>();
        if(result != null){
        	for(Map<String, HBaseCell> row : result){
            	Set<Map.Entry<String, HBaseCell>> set = row.entrySet();
                for (Iterator<Map.Entry<String, HBaseCell>> it = set.iterator(); it.hasNext();) {
                    Map.Entry<String, HBaseCell> entry = (Map.Entry<String, HBaseCell>) it.next();
                    MonitorVmNetworkPo po = (MonitorVmNetworkPo)JSONObject.parseObject(entry.getValue().getValue(),MonitorVmNetworkPo.class);
                    list.add(po);
                }
            }
            System.out.println(list);
            MonitorVmNetworkPo resultPo = new MonitorVmNetworkPo();
            for(MonitorVmNetworkPo po : list){
            	if(po.getMac().equals(mac)){
            		resultPo = po;
            	}
            }
            return resultPo;
        }
        return null;
    }

    public MonitorVmStatusFlowPo getStatus(String vmuuid){
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,-30);
        String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        List<Map<String, HBaseCell>> result = hbaseDao.scaner(status_table_name, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        MonitorVmStatusFlowPo po = null;
        if(result != null){
	    	 for(Map<String, HBaseCell> row : result){
	         	HBaseCell cell = row.get("load");
	         	po = (MonitorVmStatusFlowPo)JSONObject.parseObject(cell.getValue(),MonitorVmStatusFlowPo.class);
	         }
        }
       
        return po;
    }

    public List<MonitorVmLoadPo> getLoadOneDaysAgo(String vmuuid){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        List<Map<String, HBaseCell>> result = hbaseDao.scaner(load_table_name, Tools.makeRowKey(vmuuid, startTime), Tools.makeRowKey(vmuuid, endTime));
        List<MonitorVmLoadPo> poList = new ArrayList<MonitorVmLoadPo>();
        if(result != null){
        	for(Map<String, HBaseCell> row : result){
            	HBaseCell cell = row.get("load");
            	MonitorVmLoadPo po = (MonitorVmLoadPo)JSONObject.parseObject(cell.getValue(),MonitorVmLoadPo.class);
                poList.add(po);
            }
            System.out.println(poList);
            return poList;
        }
        return null;
    }

    public List<MonitorVmNetworkPo> getNetworkOneDaysAgo(String vmuuid) {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE,-1);
    	String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        List<Map<String, HBaseCell>> result = hbaseDao.scaner(network_table_name, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        List<MonitorVmNetworkPo> poList = new ArrayList<MonitorVmNetworkPo>();
        if(result != null){
        	for(Map<String, HBaseCell> row : result){
            	Set<Map.Entry<String, HBaseCell>> set = row.entrySet();
                for (Iterator<Map.Entry<String, HBaseCell>> it = set.iterator(); it.hasNext();) {
                    Map.Entry<String, HBaseCell> entry = (Map.Entry<String, HBaseCell>) it.next();
                    MonitorVmNetworkPo po = (MonitorVmNetworkPo)JSONObject.parseObject(entry.getValue().getValue(),MonitorVmNetworkPo.class);
                    poList.add(po);
                }
            }
            System.out.println(poList);
            return poList;
        }
        return null;
    }

    public List<MonitorVmDiskPo> getDiskOneDaysAgo(String vmuuid) {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE,-1);
    	String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        List<Map<String, HBaseCell>> result = hbaseDao.scaner(disk_table_name, Tools.makeRowKey(vmuuid, startTime), Tools.makeRowKey(vmuuid,endTime));
        List<MonitorVmDiskPo> poList = new ArrayList<MonitorVmDiskPo>();
        if(result != null){
        	for(Map<String, HBaseCell> row : result){
            	Set<Map.Entry<String, HBaseCell>> set = row.entrySet();
                for (Iterator<Map.Entry<String, HBaseCell>> it = set.iterator(); it.hasNext();) {
                    Map.Entry<String, HBaseCell> entry = (Map.Entry<String, HBaseCell>) it.next();
                    MonitorVmDiskPo po = (MonitorVmDiskPo)JSONObject.parseObject(entry.getValue().getValue(),MonitorVmDiskPo.class);
                    poList.add(po);
                }
            }
            System.out.println(poList);
            return poList;
        }
        return null;
    }

    public List<MonitorVmStatusFlowPo> getStatusOneDaysAgo(String vmuuid) {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE,-1);
    	String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        List<Map<String, HBaseCell>> result = hbaseDao.scaner(status_table_name, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        List<MonitorVmStatusFlowPo> poList = new ArrayList<MonitorVmStatusFlowPo>();
        if(result != null){
        	for(Map<String, HBaseCell> row : result){
            	HBaseCell cell = row.get("status");
            	MonitorVmStatusFlowPo po = (MonitorVmStatusFlowPo)JSONObject.parseObject(cell.getValue(),MonitorVmStatusFlowPo.class);
                poList.add(po);
            }
            System.out.println(poList);
            return poList;
        }
        return null;
    }
    public static void main(String[] args) {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE,-1);
    	String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        System.out.println(startTime + ":" + endTime);
	}
}
