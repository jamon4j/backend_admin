package com.ksyun.payment.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IKvmApplicationDao;

/**
 * 试用申请DAO实现里类
 * @author ZhangYanchun
 * @date   2013-10-28
 */
@Repository
public class KvmApplicationDao<KvmApplicationDto> extends BaseDao<KvmApplicationDto, Long>  implements IKvmApplicationDao<KvmApplicationDto> {

	@Override
	public KvmApplicationDto findByUser(long userId) {
		return sqlSession.selectOne(String.format("%s.findApplicationByUserId", nameSpace), userId);
	}

	@Override
	public List<KvmApplicationDto> getAllApplications(Map<String, Object> params) {
		return super.find("getAllApplications", params, "");
	}

	@Override
	public void updateStatus(long id) {
		sqlSession.update(String.format("%s.updateStatus", nameSpace), id);
	}
	
	@Override
	public void updateKvmOps(long id,String opsId) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("ops", opsId);
		
		sqlSession.update(String.format("%s.updateKvmOps", nameSpace), paramMap);
	}
	
}
