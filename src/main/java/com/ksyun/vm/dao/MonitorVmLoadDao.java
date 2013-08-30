package com.ksyun.vm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

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

}
