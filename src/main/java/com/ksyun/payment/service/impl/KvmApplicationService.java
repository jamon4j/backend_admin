package com.ksyun.payment.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ksyun.payment.common.KvmHttpUtil;
import com.ksyun.payment.dao.IKvmApplicationDao;
import com.ksyun.payment.dto.KvmApplicationDto;
import com.ksyun.payment.service.IKvmApplicationService;

/**
 * 试用申请实现类
 * @author ZhangYanchun
 *
 */
@Service
public class KvmApplicationService implements IKvmApplicationService {
	
	@Resource
	private IKvmApplicationDao<KvmApplicationDto> kvmApplicationDao;
	
	@Override
	public void saveApplication(KvmApplicationDto application) {
		kvmApplicationDao.save(application);
	}

	@Override
	public KvmApplicationDto getByUser(long userId) {
		
		return kvmApplicationDao.findByUser(userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KvmApplicationDto> getAllApplications(Map<String, Object> params) {
		List<KvmApplicationDto> list = kvmApplicationDao.getAllApplications(params);
		
		KvmHttpUtil kvmHttpUtil = new KvmHttpUtil();
		Map<String,String> opsMap = kvmHttpUtil.getRawImageList();
		for(KvmApplicationDto kvmDto :list){
			String opsName = opsMap.get(kvmDto.getOps());
			if(opsName==null) opsName = "非法数据";
			kvmDto.setOpsName(opsName);
		}
		
		
//		Map<String,Object> imageList = kvmHttpUtil.getImageList();
//		if(imageList!=null&&imageList.get("status")!=null){
//			if(Integer.valueOf(imageList.get("status").toString())==1){
//				JSONArray dataArray = JSON.parseArray(imageList.get("data").toString());	
//				Map<String,String> imageMap = new HashMap<String, String>();
//				
//				for(int i=0;i<dataArray.size();i++){
//					Map<String,String> tmpMap = (Map<String,String>)dataArray.get(i);
//					imageMap.put(tmpMap.get("id"), tmpMap.get("name"));
//				}		
//				
//				for(KvmApplicationDto kvmDto :list){
//					String opsName = imageMap.get(kvmDto.getOps());
//					if(opsName==null) opsName = "非法数据";
//					kvmDto.setOpsName(opsName);
//				}
//				
//			}
//		}
		
		
		return (PageList)list;
	}

	@Override
	public KvmApplicationDto findById(long id) {
		return kvmApplicationDao.findById(id);
	}

	@Override
	public void updateStatus(long id) {
		kvmApplicationDao.updateStatus(id);
	}
	
	@Override
	public void updateKvmOps(long id,String opsId) {
		kvmApplicationDao.updateKvmOps(id,opsId);
	}

	

	
}
