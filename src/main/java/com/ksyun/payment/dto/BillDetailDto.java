package com.ksyun.payment.dto;

import java.math.BigDecimal;

/**
 * 账单明细Dto类
 * @author ZhangYanchun
 * @date   2013-08-19
 */
public class BillDetailDto implements DtoInterface {
	
    //账单明细ID
	private Long billDetailId;

    //账单条目编码
	private String itemNo;

    //账单条目名称
	private String itemName;

    //此条目总用量
	private BigDecimal appAmount;

    //此条目总花费 
	private BigDecimal totalCost;

    //账单ID
	private Long baseBillId;

    public Long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(Long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo == null ? null : itemNo.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }
    
	public BigDecimal getAppAmount() {
		return appAmount;
	}

	public void setAppAmount(BigDecimal appAmount) {
		this.appAmount = appAmount;
	}

	public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Long getBaseBillId() {
        return baseBillId;
    }

    public void setBaseBillId(Long baseBillId) {
        this.baseBillId = baseBillId;
    }

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
}