package com.ksyun.payment.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDto {
	
	//订单ID
    private String orderId;
    
    private String productId;

    //订单总金额
    private BigDecimal totalMoney;
    
    //订单实际金额
    private BigDecimal realMoney;

    //促销ID
    private Integer promotionId;

    //订单提交时间
    private Date submitTime;

    //产品类型
    private Short productType;

    //更新时间
    private Date utime;

    //订单状态     0 未支付      1已支付      2未开通      3已开通      -1已作废  -3 开通失败
    private Short status;

    //订单服务开始时间
    private Date serviceBeginTime;

    //订单服务结束时间
    private Date serviceEndTime;

    //用户ID
    private Long userId;

    //订购个数 kvm使用 
    private Short orderProductCount;

    //订单类型  1 试用订单  2 常规订单  3 协议订单
    private short orderType;

   
    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(BigDecimal realMoney) {
        this.realMoney = realMoney;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Short getProductType() {
        return productType;
    }

    public void setProductType(Short productType) {
        this.productType = productType;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getServiceBeginTime() {
        return serviceBeginTime;
    }

    public void setServiceBeginTime(Date serviceBeginTime) {
        this.serviceBeginTime = serviceBeginTime;
    }

    public Date getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(Date serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getOrderProductCount() {
        return orderProductCount;
    }

    public void setOrderProductCount(Short orderProductCount) {
        this.orderProductCount = orderProductCount;
    }

	public short getOrderType() {
		return orderType;
	}

	public void setOrderType(short orderType) {
		this.orderType = orderType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	
    
}