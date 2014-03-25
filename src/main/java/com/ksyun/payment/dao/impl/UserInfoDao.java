package com.ksyun.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IUserInfoDao;

/**
 * 用户信息实现类
 * @author ZhangYanchun
 * @param <UserInfoDto>
 * @date  2013-01-02
 */
@Repository
public class UserInfoDao<UserInfoDto> extends BaseDao<UserInfoDto, Long> implements IUserInfoDao<UserInfoDto> {

	@Override
	public List<UserInfoDto> findAllByEmailAndMobile(Map<String, Object> params) {
		return super.find("findAllByEmailAndMobile", params, "");
	}

	@Override
	public void updateUserType(UserInfoDto userInfoDto) {
		update("updateUserType",userInfoDto);
	}

}
