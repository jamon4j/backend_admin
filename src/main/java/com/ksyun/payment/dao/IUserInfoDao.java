package com.ksyun.payment.dao;

import java.util.List;
import java.util.Map;

/**
 * 查询用户信息DAO
 * @author ZhangYanchun
 * @date   2013-01-02
 */
public interface IUserInfoDao<UserInfoDto> extends IBaseDao<UserInfoDto, Long> {
	/**
	 * 查询用户列表信息，包含用余额信息
	 * @param params
	 * @return
	 */
	List<UserInfoDto> findAllByEmailAndMobile(Map<String, Object> params);

	void updateUserType(UserInfoDto userInfoDto);
}
