package com.ksyun.payment.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IBillDetailDao;

/**
 * 账单详细DAO实现
 * @author ZhangYanchun
 * @param <BillDetailDto>
 * @date  2013-08-20
 */
@Repository
public class BillDetailDao<BillDetailDto> extends BaseDao<BillDetailDto, Long> implements IBillDetailDao<BillDetailDto> {
	
	@Override
	public void save(List<BillDetailDto> bds) {
		for(BillDetailDto detailDto : bds){
			sqlSession.insert(String.format("%s.insert", nameSpace), detailDto);
		}
		
	}

	@Override
	public List<BillDetailDto> findAllDetailByBillId(long baseBillId) {
		return sqlSession.selectList(String.format("%s.findAllByBillId", nameSpace), baseBillId);
	}

}
