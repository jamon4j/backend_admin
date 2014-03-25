package com.ksyun.payment.dto;

import java.math.BigDecimal;

/**
 * 订单 产品详细表  
 * @author ZhangYanchun
 * @date   2013-08-29
 */
public class OrderItemDto {
	
	//产品编号
	private String itemNo;

	//订单ID
	private String orderId;
	
	//单位
    private String unit;

    //名称
    private String itemName;
    
    //订单数量，用于预收费产品，不存在价格区间的情况，
    private BigDecimal amount;

    //打包个数
    private int unitCount;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    
	public int getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(int unitCount) {
		this.unitCount = unitCount;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
}