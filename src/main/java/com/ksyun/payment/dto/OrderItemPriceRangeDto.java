package com.ksyun.payment.dto;

import java.math.BigDecimal;

/**
 * 订单 项目价格区间
 * @author ZhangYanchun
 * @date   2013-08-29
 */
public class OrderItemPriceRangeDto {
	
	//主键
    private Long id;

    //价格区间 开始
    private Integer rangeBegin;

    //价格区间结束
    private Integer rangeEnd;

    //设定价格
    private BigDecimal price;
    
    //价格补偿量，为了不重复计算，存储当价格大于此价格区间时，其他价格区间价格总和
    private BigDecimal offsetCost;

    //项目编号
    private String itemNo;

    //订单ID
    private String orderId;

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRangeBegin() {
        return rangeBegin;
    }

    public void setRangeBegin(Integer rangeBegin) {
        this.rangeBegin = rangeBegin;
    }

    public Integer getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(Integer rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo == null ? null : itemNo.trim();
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getOffsetCost() {
		return offsetCost;
	}

	public void setOffsetCost(BigDecimal offsetCost) {
		this.offsetCost = offsetCost;
	}
    
    
}