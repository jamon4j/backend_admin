package com.ksyun.vm.dao;

import com.ksyun.vm.pojo.MonitorVmLoadPo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public MonitorVmNetworkPo getLastestVmNetwork(String vmuuid){
        MonitorVmNetworkPo po = sqlSession.selectOne(nameSpace+".getLatestVmNetwork",vmuuid);
        return po;
    }

    public List<MonitorVmNetworkPo> getVmNetworkByTime(String vmuuid, String log_time) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("vmUuid",vmuuid);
        map.put("logTime",log_time);
        List<MonitorVmNetworkPo> pos = sqlSession.selectList(nameSpace+".getVmNetworkByTime",map);
        if (pos == null || pos.isEmpty()) {
            return new ArrayList<MonitorVmNetworkPo>();
        }
        return pos;
    }
}
