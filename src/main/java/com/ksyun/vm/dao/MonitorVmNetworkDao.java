package com.ksyun.vm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class MonitorVmNetworkDao<MonitorVmNetworkPo> extends BaseDao<MonitorVmNetworkPo,Integer> {
    @SuppressWarnings("unchecked")
	public List<MonitorVmNetworkPo> getLatestVmNetworkData(){
		List<MonitorVmNetworkPo> list = (List<MonitorVmNetworkPo>)sqlSession.selectList(nameSpace + ".getLatestVmNetworkData");
		if(list == null || list.isEmpty()){
			return new ArrayList<MonitorVmNetworkPo>();
		}
		return list;
    }
}
