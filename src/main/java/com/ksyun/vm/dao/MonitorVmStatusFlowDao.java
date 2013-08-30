package com.ksyun.vm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

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
	
}
