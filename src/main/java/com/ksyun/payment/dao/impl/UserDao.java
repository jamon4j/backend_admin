package com.ksyun.payment.dao.impl;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IUserDao;

@Repository
public class UserDao<UserDto> extends BaseDao<UserDto, Long> implements IUserDao<UserDto> {

	@Override
	public void updateBalance(UserDto userDto) {
		update("updateBalance", userDto);
	}

	@Override
	public void updateAlarmBalance(UserDto userDto) {
		update("updateAlarmBalance", userDto);
	}
}
