package com.ksyun.payment.dao;
/**
 * user account DAO
 * @author ZhangYanchun
 * @param <UserDto>
 * @param <Long>
 * @Date 2013-08-15
 */
public interface IUserDao<UserDto> extends IBaseDao<UserDto, Long> {
	/**
	 * 更新用户账户余额
	 * @param userDto
	 */
	void updateBalance(UserDto userDto);
	
	/**
	 * 更新用户报警设置金额
	 * @param userDto
	 */
	void updateAlarmBalance(UserDto userDto);

}
