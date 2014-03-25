package com.ksyun.payment.service;

import java.util.List;
import java.util.Map;

import com.ksyun.payment.dto.BillDetailDto;
import com.ksyun.payment.dto.BillDto;

/**
 * 账单操作处理类
 * @author ZhangYanchun
 * @date   2013-08-20
 */
public interface IBillService {
	/**
	 * 保存账单操作
	 * @param bill
	 */
	int payBill(BillDto bill, String caSerial);
	
	/**
	 * 查询最近一次更新的账单信息
	 * @param userId
	 * @return
	 */
	BillDto getLatestBill(long userId);
	
	/**
	 * 根据用户ID返回账单列表
	 * @param userId
	 * @return
	 */
	List<BillDto> getAllBill(long userId);
	/**
	 * 获取账单详细列表
	 * @param baseBillId
	 * @return
	 */
	List<BillDetailDto> getAllBillDetail(long baseBillId);
	
	/**
	 * 查询所有kvm 和ebs账单（后台）
	 * @param params
	 * @return
	 */
	List<BillDto> getAllKvmAndEbsBill(Map<String, Object> params);
	
	/**
	 * 根据账单号获取账单信息
	 * @param billId
	 * @return
	 */
	BillDto getBillById(long billId);
	
	/**
	 * 账单撤销
	 * @param bill
	 * @return
	 */
	int cancelBill(BillDto bill);
	
}
