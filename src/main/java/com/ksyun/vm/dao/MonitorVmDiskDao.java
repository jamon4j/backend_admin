package com.ksyun.vm.dao;

import org.springframework.stereotype.Repository;

import com.ksyun.vm.dao.interfaces.IMonitorVmDiskDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MonitorVmDiskDao<MonitorVmDiskPo> extends BaseDao<MonitorVmDiskPo,Integer> implements IMonitorVmDiskDao<MonitorVmDiskPo> {

    /* (non-Javadoc)
	 * @see com.ksyun.vm.dao.IMonitorVmDiskDao#getLatestVmDiskData()
	 */
    @Override
	public List<MonitorVmDiskPo> getLatestVmDiskData(){
		List<MonitorVmDiskPo> list = (List<MonitorVmDiskPo>)sqlSession.selectList(nameSpace + ".getLatestVmDiskData");
		if(list == null || list.isEmpty()){
			return new ArrayList<MonitorVmDiskPo>();
		}
		return list;
    }
    /* (non-Javadoc)
	 * @see com.ksyun.vm.dao.IMonitorVmDiskDao#getLastestVmDisk(java.lang.String, java.lang.String)
	 */
    @Override
	public MonitorVmDiskPo getLastestVmDisk(String vmuuid,String disk){
        Map<String,String> map = new HashMap<String, String>();
        map.put("vmUuid",vmuuid);
        map.put("disk",disk);
        MonitorVmDiskPo po = sqlSession.selectOne(nameSpace+".getLatestVmDisk",map);
        return po;
    }
    /* (non-Javadoc)
	 * @see com.ksyun.vm.dao.IMonitorVmDiskDao#getVmDiskByTime(java.lang.String, java.lang.String)
	 */
    @Override
	public List<MonitorVmDiskPo> getVmDiskByTime(String vmuuid,String log_time){
        Map<String,String> map = new HashMap<String,String>();
        map.put("vmUuid",vmuuid);
        map.put("logTime",log_time);
        List<MonitorVmDiskPo> pos = sqlSession.selectList(nameSpace+".getVmDiskByTime",map);
        if (pos == null || pos.isEmpty()) {
            return new ArrayList<MonitorVmDiskPo>();
        }
        return pos;
    }
}
