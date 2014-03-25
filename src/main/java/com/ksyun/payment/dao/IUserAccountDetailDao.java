package com.ksyun.payment.dao;

import java.util.List;
import java.util.Map;
/**
 * 现金账户明细 DAO接口
 * @author ZhangYanchun
 * @param <UserAccountDetailDto>
 * @Date 2013-08-15
 */
public interface IUserAccountDetailDao<UserAccountDetailDto> extends IBaseDao<UserAccountDetailDto, String> {
	
	/**
	 * 根据用户ID查询用户现金账单明细
	 * @param params
	 * @param sortString
	 * @return
	 */
	List<UserAccountDetailDto> findAllByUser(Map<String, Object> params, String sortString);
	/**
	 * 更新用户账户明细的状态（用于用户充值成功记录）
	 * @param detail
	 */
	void updateDetailStatus(UserAccountDetailDto detail);
	
	/**
	 * 根据账单ID返回用户消费的明细
	 * @param params
	 * @return
	 */
	UserAccountDetailDto findByBill(Map<String, Object> params);
	
	/**
	 * 查询所有的现金明细，后台分页
	 * @param params
	 * @return
	 */
	List<UserAccountDetailDto> findAllDetails(Map<String, Object> params);
	
}
