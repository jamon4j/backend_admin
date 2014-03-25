package com.ksyun.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IUserAccountDetailDao;
/**
 * 用户现金账户明细DAO实现类
 * @author ZhangYanchun
 * @param <UserAccountDetailDto>
 * @Date  2013-08-15
 */
@Repository
public class UserAccountDetailDao<UserAccountDetailDto> extends BaseDao<UserAccountDetailDto, String> implements IUserAccountDetailDao<UserAccountDetailDto> {

	@Override
	public List<UserAccountDetailDto> findAllByUser(Map<String, Object> params, String sortString) {
		return super.find("findAllByUser", params, sortString);
	}

	@Override
	public void updateDetailStatus(UserAccountDetailDto detail) {
		super.update("updateIncomeSerial", detail);
	}

	@Override
	public UserAccountDetailDto findByBill(Map<String, Object> params) {
		return sqlSession.selectOne(String.format("%s.findByBillId", nameSpace), params);
	}

	@Override
	public List<UserAccountDetailDto> findAllDetails(Map<String, Object> params) {
		return  super.find("findAllDetails", params, "");
	}

}
