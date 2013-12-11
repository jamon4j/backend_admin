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

import com.ksyun.monitor.pojo.hbase.HBaseCell;
import com.ksyun.vm.hbase.HBaseDao;
import com.ksyun.vm.pojo.chart.MonitorVmDiskPo;
import com.ksyun.vm.pojo.chart.MonitorVmLoadPo;
import com.ksyun.vm.pojo.chart.MonitorVmNetworkPo;
import com.ksyun.vm.pojo.chart.MonitorVmStatusFlowPo;
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
    
    public MonitorVmLoadPo getLoad(String vmuuid){
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,-30);
        String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        Map<String, List<HBaseCell>> result = hbaseDao.scaner(InitConst.MONITOR_HBASE_TABLE_LOAD, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        MonitorVmLoadPo po = null;
        Set<Map.Entry<String, List<HBaseCell>>> set = result.entrySet();
        for (Iterator<Map.Entry<String, List<HBaseCell>>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, List<HBaseCell>> entry = (Map.Entry<String, List<HBaseCell>>) it.next();
            po = new MonitorVmLoadPo();
            for(HBaseCell cell : entry.getValue()){
            	if(cell.getQulifer().equals("cpupcload")){
            		po.setCpuPCLoad(cell.getValue());
            	}else if(cell.getQulifer().equals("cpuvmload")){
            		po.setCpuVMLoad(cell.getValue());
            	}else if(cell.getQulifer().equals("memorypcload")){
            		po.setMemoryPCLoad(cell.getValue());
            	}else if(cell.getQulifer().equals("memoryvmload")){
            		po.setMemoryVMLoad(cell.getValue());
            	}else if(cell.getQulifer().equals("vmname")){
            		po.setVmName(cell.getValue());
            	}else if(cell.getQulifer().equals("uuid")){
            		po.setVmUuid(cell.getValue());
            	}else if(cell.getQulifer().equals("time")){
            		po.setLogTime(cell.getValue());
            	}
            }
        }
        return po;
    }

    public MonitorVmDiskPo getDisk(String vmuuid,String disk){
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,-30);
        String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        Map<String, List<HBaseCell>> result = hbaseDao.scaner(InitConst.MONITOR_HBASE_TABLE_DISK, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        List<MonitorVmDiskPo> list = new ArrayList<MonitorVmDiskPo>();
        Set<Map.Entry<String, List<HBaseCell>>> set = result.entrySet();
        for (Iterator<Map.Entry<String, List<HBaseCell>>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, List<HBaseCell>> entry = (Map.Entry<String, List<HBaseCell>>) it.next();
            MonitorVmDiskPo po = new MonitorVmDiskPo();
            for(HBaseCell cell : entry.getValue()){
            	if(cell.getQulifer().equals("rb")){
            		po.setReadBytes(cell.getValue());
            	}else if(cell.getQulifer().equals("wb")){
            		po.setWriteBytes(cell.getValue());
            	}else if(cell.getQulifer().equals("rt")){
            		po.setReadTimes(cell.getValue());
            	}else if(cell.getQulifer().equals("wt")){
            		po.setWriteTimes(cell.getValue());
            	}else if(cell.getQulifer().equals("vmname")){
            		po.setVmName(cell.getValue());
            	}else if(cell.getQulifer().equals("uuid")){
            		po.setVmUuid(cell.getValue());
            	}else if(cell.getQulifer().equals("time")){
            		po.setLogTime(cell.getValue());
            	}else if(cell.getQulifer().equals("disk")){
            		po.setDisk(cell.getValue());
            	}
            }
            list.add(po);
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

    public MonitorVmNetworkPo getNetwork(String vmuuid,String mac){
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,-30);
        String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        Map<String, List<HBaseCell>> result = hbaseDao.scaner(InitConst.MONITOR_HBASE_TABLE_NETWORK, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        List<MonitorVmNetworkPo> list = new ArrayList<MonitorVmNetworkPo>();
        Set<Map.Entry<String, List<HBaseCell>>> set = result.entrySet();
        for (Iterator<Map.Entry<String, List<HBaseCell>>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, List<HBaseCell>> entry = (Map.Entry<String, List<HBaseCell>>) it.next();
            MonitorVmNetworkPo po = new MonitorVmNetworkPo();
            for(HBaseCell cell : entry.getValue()){
            	if(cell.getQulifer().equals("mac")){
            		po.setMac(cell.getValue());
            	}else if(cell.getQulifer().equals("rxb")){
            		po.setRxb(cell.getValue());
            	}else if(cell.getQulifer().equals("rxp")){
            		po.setRxp(cell.getValue());
            	}else if(cell.getQulifer().equals("txb")){
            		po.setTxb(cell.getValue());
            	}else if(cell.getQulifer().equals("txp")){
            		po.setTxp(cell.getValue());
            	}else if(cell.getQulifer().equals("vmname")){
            		po.setVmName(cell.getValue());
            	}else if(cell.getQulifer().equals("uuid")){
            		po.setVmUuid(cell.getValue());
            	}else if(cell.getQulifer().equals("time")){
            		po.setLogTime(cell.getValue());
            	}
            }
            list.add(po);
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

    public MonitorVmStatusFlowPo getStatus(String vmuuid){
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,-30);
        String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        Map<String, List<HBaseCell>> result = hbaseDao.scaner(InitConst.MONITOR_HBASE_TABLE_STATUS, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        MonitorVmStatusFlowPo po = null;
        Set<Map.Entry<String, List<HBaseCell>>> set = result.entrySet();
        for (Iterator<Map.Entry<String, List<HBaseCell>>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, List<HBaseCell>> entry = (Map.Entry<String, List<HBaseCell>>) it.next();
            po = new MonitorVmStatusFlowPo();
            for(HBaseCell cell : entry.getValue()){
            	if(cell.getQulifer().equals("status")){
            		po.setStatus(cell.getValue());
            	}else if(cell.getQulifer().equals("vmname")){
            		po.setVmName(cell.getValue());
            	}else if(cell.getQulifer().equals("uuid")){
            		po.setVmUuid(cell.getValue());
            	}else if(cell.getQulifer().equals("time")){
            		po.setLogTime(cell.getValue());
            	}
            }
        }
        return po;
    }

    public List<MonitorVmLoadPo> getLoadOneDaysAgo(String vmuuid){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        Map<String, List<HBaseCell>> result = hbaseDao.scaner(InitConst.MONITOR_HBASE_TABLE_LOAD, Tools.makeRowKey(vmuuid, startTime), Tools.makeRowKey(vmuuid, endTime));
        List<MonitorVmLoadPo> poList = new ArrayList<MonitorVmLoadPo>();
        Set<Map.Entry<String, List<HBaseCell>>> set = result.entrySet();
        for (Iterator<Map.Entry<String, List<HBaseCell>>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, List<HBaseCell>> entry = (Map.Entry<String, List<HBaseCell>>) it.next();
            MonitorVmLoadPo po = new MonitorVmLoadPo();
            for(HBaseCell cell : entry.getValue()){
            	if(cell.getQulifer().equals("cpupcload")){
            		po.setCpuPCLoad(cell.getValue());
            	}else if(cell.getQulifer().equals("cpuvmload")){
            		po.setCpuVMLoad(cell.getValue());
            	}else if(cell.getQulifer().equals("memorypcload")){
            		po.setMemoryPCLoad(cell.getValue());
            	}else if(cell.getQulifer().equals("memoryvmload")){
            		po.setMemoryVMLoad(cell.getValue());
            	}else if(cell.getQulifer().equals("vmname")){
            		po.setVmName(cell.getValue());
            	}else if(cell.getQulifer().equals("uuid")){
            		po.setVmUuid(cell.getValue());
            	}else if(cell.getQulifer().equals("time")){
            		po.setLogTime(cell.getValue());
            	}
            }
            poList.add(po);
        }
        System.out.println(poList);
        return poList;
    }

    public List<MonitorVmNetworkPo> getNetworkOneDaysAgo(String vmuuid) {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE,-1);
    	String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        Map<String, List<HBaseCell>> result = hbaseDao.scaner(InitConst.MONITOR_HBASE_TABLE_NETWORK, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        List<MonitorVmNetworkPo> poList = new ArrayList<MonitorVmNetworkPo>();
        Set<Map.Entry<String, List<HBaseCell>>> set = result.entrySet();
        for (Iterator<Map.Entry<String, List<HBaseCell>>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, List<HBaseCell>> entry = (Map.Entry<String, List<HBaseCell>>) it.next();
            MonitorVmNetworkPo po = new MonitorVmNetworkPo();
            for(HBaseCell cell : entry.getValue()){
            	if(cell.getQulifer().equals("mac")){
            		po.setMac(cell.getValue());
            	}else if(cell.getQulifer().equals("rxb")){
            		po.setRxb(cell.getValue());
            	}else if(cell.getQulifer().equals("rxp")){
            		po.setRxp(cell.getValue());
            	}else if(cell.getQulifer().equals("txb")){
            		po.setTxb(cell.getValue());
            	}else if(cell.getQulifer().equals("txp")){
            		po.setTxp(cell.getValue());
            	}else if(cell.getQulifer().equals("vmname")){
            		po.setVmName(cell.getValue());
            	}else if(cell.getQulifer().equals("uuid")){
            		po.setVmUuid(cell.getValue());
            	}else if(cell.getQulifer().equals("time")){
            		po.setLogTime(cell.getValue());
            	}
            }
            poList.add(po);
        }
        System.out.println(poList);
        return poList;
    }

    public List<MonitorVmDiskPo> getDiskOneDaysAgo(String vmuuid) {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE,-1);
    	String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        Map<String, List<HBaseCell>> result = hbaseDao.scaner(InitConst.MONITOR_HBASE_TABLE_DISK, Tools.makeRowKey(vmuuid, startTime), Tools.makeRowKey(vmuuid,endTime));
        List<MonitorVmDiskPo> poList = new ArrayList<MonitorVmDiskPo>();
        Set<Map.Entry<String, List<HBaseCell>>> set = result.entrySet();
        for (Iterator<Map.Entry<String, List<HBaseCell>>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, List<HBaseCell>> entry = (Map.Entry<String, List<HBaseCell>>) it.next();
            MonitorVmDiskPo po = new MonitorVmDiskPo();
            for(HBaseCell cell : entry.getValue()){
            	if(cell.getQulifer().equals("rb")){
            		po.setReadBytes(cell.getValue());
            	}else if(cell.getQulifer().equals("wb")){
            		po.setWriteBytes(cell.getValue());
            	}else if(cell.getQulifer().equals("rt")){
            		po.setReadTimes(cell.getValue());
            	}else if(cell.getQulifer().equals("wt")){
            		po.setWriteTimes(cell.getValue());
            	}else if(cell.getQulifer().equals("vmname")){
            		po.setVmName(cell.getValue());
            	}else if(cell.getQulifer().equals("uuid")){
            		po.setVmUuid(cell.getValue());
            	}else if(cell.getQulifer().equals("time")){
            		po.setLogTime(cell.getValue());
            	}else if(cell.getQulifer().equals("disk")){
            		po.setDisk(cell.getValue());
            	}
            }
            poList.add(po);
        }
        System.out.println(poList);
        return poList;
    }

    public List<MonitorVmStatusFlowPo> getStatusOneDaysAgo(String vmuuid) {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE,-1);
    	String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        Map<String, List<HBaseCell>> result = hbaseDao.scaner(InitConst.MONITOR_HBASE_TABLE_STATUS, Tools.makeRowKey(vmuuid, startTime),Tools.makeRowKey(vmuuid, endTime));
        List<MonitorVmStatusFlowPo> poList = new ArrayList<MonitorVmStatusFlowPo>();
        Set<Map.Entry<String, List<HBaseCell>>> set = result.entrySet();
        for (Iterator<Map.Entry<String, List<HBaseCell>>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, List<HBaseCell>> entry = (Map.Entry<String, List<HBaseCell>>) it.next();
            MonitorVmStatusFlowPo po = new MonitorVmStatusFlowPo();
            for(HBaseCell cell : entry.getValue()){
            	if(cell.getQulifer().equals("status")){
            		po.setStatus(cell.getValue());
            	}else if(cell.getQulifer().equals("vmname")){
            		po.setVmName(cell.getValue());
            	}else if(cell.getQulifer().equals("uuid")){
            		po.setVmUuid(cell.getValue());
            	}else if(cell.getQulifer().equals("time")){
            		po.setLogTime(cell.getValue());
            	}
            }
            poList.add(po);
        }
        System.out.println(poList);
        return poList;
    }
    public static void main(String[] args) {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE,-1);
    	String startTime = String.valueOf(c.getTimeInMillis()/1000);
        String endTime = String.valueOf(new Date().getTime()/1000);
        System.out.println(startTime + ":" + endTime);
	}
}
