package com.ksyun.payment.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IBillDao;

/**
 * 账单DAO实现类
 * @author ZhangYanchun
 * @date   2013-08-20
 */
@Repository
public class BillDao<BillDto> extends BaseDao<BillDto, Long> implements IBillDao<BillDto> {

	@Override
	public void updateStatus(long baseBillId, short payStatus, short payType) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("payStatus", payStatus);
		params.put("payType", payType);
		params.put("baseBillId", baseBillId);
		sqlSession.update(String.format("%s.%s", nameSpace, "updateStatus"), params);
	}

	@Override
	public BillDto findLatestBill(long userId) {
		return sqlSession.selectOne(String.format("%s.%s", nameSpace, "selectLatelyBill"), userId);
	}

	@Override
	public List<BillDto> findAllBill(Map<String, Object> params, String sortString) {
		return find("findAllByUser", params, sortString);
	}

	@Override
	public List<BillDto> findAllKvmAndEbsBill(Map<String, Object> params, String sortString) {
		return find("findAllKvmAndEbsBills", params, sortString);
	}
	
	

}
