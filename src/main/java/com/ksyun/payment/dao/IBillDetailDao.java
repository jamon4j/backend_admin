package com.ksyun.payment.dao;

import java.util.List;

/**
 * 账单详细类DAO
 * @author ZhangYanchun
 * @param <BillDetailDto>
 * @date  2013-08-20
 */
public interface IBillDetailDao<BillDetailDto> extends IBaseDao<BillDetailDto, Long>{
	
	
	/**
	 * 批量写入账单明细
	 * @param bds
	 * @param BaseBillId
	 */
	void save(List<BillDetailDto> bds);
	
	/**
	 * 返回账单明细
	 * @param baseBillId
	 * @return
	 */
	List<BillDetailDto> findAllDetailByBillId(long baseBillId);

}
