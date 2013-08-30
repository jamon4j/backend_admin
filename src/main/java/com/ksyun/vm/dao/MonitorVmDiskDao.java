package com.ksyun.vm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class MonitorVmDiskDao<MonitorVmDiskPo> extends BaseDao<MonitorVmDiskPo,Integer> {

    public List<MonitorVmDiskPo> getLatestVmDiskData(){
		List<MonitorVmDiskPo> list = (List<MonitorVmDiskPo>)sqlSession.selectList(nameSpace + ".getLatestVmDiskData");
		if(list == null || list.isEmpty()){
			return new ArrayList<MonitorVmDiskPo>();
		}
		return list;
    }
    
}
