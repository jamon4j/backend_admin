package com.ksyun.vm.dao;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MonitorVmLoadDao<MonitorVmLoadPo> extends BaseDao<MonitorVmLoadPo, Integer> {
	@SuppressWarnings("unchecked")
	public List<MonitorVmLoadPo> getLatestVmLoadData() {
		List<MonitorVmLoadPo> list = (List<MonitorVmLoadPo>) sqlSession.selectList(nameSpace + ".getLatestVmLoadData");
		if (list == null || list.isEmpty()) {
			return new ArrayList<MonitorVmLoadPo>();
		}
		return list;
	}

    public MonitorVmLoadPo getLastestVmLoad(String vmuuid){
        MonitorVmLoadPo po = sqlSession.selectOne(nameSpace+".getLatestVmLoad",vmuuid);
        return po;
    }

    public List<MonitorVmLoadPo> getVmLoadByTime(String vmuuid,String log_time){
        Map<String,String> map = new HashMap<String,String>();
        map.put("vmUuid",vmuuid);
        map.put("logTime",log_time);
        List<MonitorVmLoadPo> pos = sqlSession.selectList(nameSpace+".getVmLoadByTime",map);
        if (pos == null || pos.isEmpty()) {
            return new ArrayList<MonitorVmLoadPo>();
        }
        return pos;
    }
}
