package com.ksyun.payment.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ksyun.payment.dto.UserAccountDetailDto;
import com.ksyun.payment.dto.UserDto;
import com.ksyun.payment.dto.UserInfoDto;

/**
 * 用户账户业务处理
 * @author ZhangYanchun
 * @Date 2013-08-15
 */

public interface IUserService {
	/**
	 * 根据用户ID查询用户账户信息
	 * @param userId
	 * @return
	 */
	UserDto getUserAccount(long userId);
	/**
	 * 返回用户所有的现金账户明细
	 * @param userId
	 * @return
	 */
	List<UserAccountDetailDto> findAllDetail(long userId, short status);
	
	/**
	 * 更新用户的报警金额
	 * @param userId
	 * @param alarmBalance
	 */
	void updateAlarmBalance(long userId, BigDecimal alarmBalance, short isAlarm);
	
	/**
	 * 保存账单流水记录
	 * @param detail
	 */
	void saveAccountDetail(UserAccountDetailDto detail);
	
	/**
	 * 根据流水号获取流水信息
	 * @param caSerial
	 * @return
	 */
	UserAccountDetailDto findDetail(String caSerial);
	
	/**
	 * 保存充值记录，及更改用户余额
	 * @param userId
	 * @param payId
	 * @param caSerial
	 * @param money
	 * @return
	 */
	BigDecimal saveIncomMoney(long userId, String payId, String caSerial, BigDecimal money);
	/**
	 * 获取所有充值记录
	 * @param userId
	 * @param status
	 * @return
	 */
	List<UserAccountDetailDto> findAllIncomeDetail(long userId, short dealType);
	
	/**
	 * 查询所有现金明细
	 * @param params
	 * @return
	 */
	List<UserAccountDetailDto> findAllDetail(Map<String, Object> params);
	
	
	/**
	 * 后台充值接口
	 * @param userId
	 * @param payId
	 * @param caSerial
	 * @param money
	 */
	void saveBackRechargeMoney(long userId, String payId, String caSerial, BigDecimal money);
	
	/**
	 * 后台提现接口
	 * @param userId
	 * @param payId
	 * @param caSerial
	 * @param money
	 */
	void saveBackDrawMoney(long userId, String payId, String caSerial, BigDecimal money);
	
	/**
	 * 后台充值作废操作
	 * @param userId
	 * @param payId
	 * @param caSerial
	 * @param money
	 */
	void saveBackDeductMoney(long userId, String payId, String caSerial, BigDecimal money);
	
	/**
	 * 获取用户信息，根据用户ID（后台接口）
	 * @param userId
	 * @return
	 */
	UserInfoDto findUserInfo(long userId);
	
	/**
	 * 根据用户email、mobile查询用户信息（后台）
	 * @param userId
	 * @param email
	 * @param mobile
	 * @return
	 */
    List<UserInfoDto> findUserInfoByEmailAndMobile(long userId, String email, String mobile);
    
    
    /**
     * 更新用户的类型  0--普通客户     1--关系客户
     * @param userId
     * @param type
     */
    void updateUserType(long userId, short type, String userFrom);
	
}
