package com.ksyun.vm.dao;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MonitorVmStatusFlowDao<MonitorVmStatusFlowPo> extends BaseDao<MonitorVmStatusFlowPo,Integer> {

    @SuppressWarnings("unchecked")
	public List<MonitorVmStatusFlowPo> getLatestVmStatusData(){
    	System.out.println("nameSpace: "+ nameSpace);
		List<MonitorVmStatusFlowPo> list = (List<MonitorVmStatusFlowPo>)sqlSession.selectList(nameSpace + ".getLatestVmStatusData");
		if(list == null || list.isEmpty()){
			return new ArrayList<MonitorVmStatusFlowPo>();
		}
		return list;
    }
    public MonitorVmStatusFlowPo getLastestVmStatusFlow(String vmuuid){
        MonitorVmStatusFlowPo po = sqlSession.selectOne(nameSpace+".getLatestVmStatusFlow",vmuuid);
        return po;
    }
    public List<MonitorVmStatusFlowPo> getVmStatusFlowByTime(String vmuuid, String log_time) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("vmUuid",vmuuid);
        map.put("logTime",log_time);
        List<MonitorVmStatusFlowPo> pos = sqlSession.selectList(nameSpace+".getVmStatusFlowByTime",map);
        if (pos == null || pos.isEmpty()) {
            return new ArrayList<MonitorVmStatusFlowPo>();
        }
        return pos;
    }
}
