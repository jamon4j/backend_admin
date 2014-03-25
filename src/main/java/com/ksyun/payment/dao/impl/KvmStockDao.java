package com.ksyun.payment.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IKvmStockDao;

/**
 * 实现类
 * @author ZhangYanchun
 * @date 2013-12-05
 */
@Repository
public class KvmStockDao<KvmStockDto> extends BaseDao<KvmStockDto, String> implements IKvmStockDao<KvmStockDto> {

	@Override
	public void updateAmount(String kvmType, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kvmType", kvmType);
		params.put("count", count);
		sqlSession.update(String.format("%s.%s", nameSpace, "updateAmount"), params);
		
	}

	@Override
	public void updateTotalAmount(String kvmType, int amount) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kvmType", kvmType);
		params.put("amount", amount);
		sqlSession.update(String.format("%s.%s", nameSpace, "updateTotalAmount"), params);
		
	}

}
