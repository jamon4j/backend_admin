package com.ksyun.payment.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 账单信息dto类
 * @author ZhangYanchun
 * @date   2013-08-19
 */
public class BillDto implements DtoInterface{
	
    //账单ID
	private Long billId;
	
	//产品ID
    private Long productId;
    
    //产品类型，存储对应产品值
    private short productType;
    
    //计费开始时间
    private Date chargeBeginTime;

    //计费结束时间
    private Date chargeEndTime;

    //总花费
    private BigDecimal totalCost;

    //实际花费
    private BigDecimal realCost;

    //付款方式（1 现金   2 优惠券   3 混合支付）
    private Short payType;

    //支付状态 （0 未成功  1 成功）
    private Short payStatus;

    //促销ID
    private String promotionId;
    
    //支付时间
    private Date payTmie;
    
    //用户订单ID
    private String userOrderId;
    
    //ali支付流水号
    private Long userAliPayId;
    
    //用户ID
    private Long userId;
    
    private List<BillDetailDto> bds;

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public short getProductType() {
		return productType;
	}

	public void setProductType(short productType) {
		this.productType = productType;
	}

	public Date getChargeBeginTime() {
        return chargeBeginTime;
    }

    public void setChargeBeginTime(Date chargeBeginTime) {
        this.chargeBeginTime = chargeBeginTime;
    }

    public Date getChargeEndTime() {
        return chargeEndTime;
    }

    public void setChargeEndTime(Date chargeEndTime) {
        this.chargeEndTime = chargeEndTime;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getRealCost() {
        return realCost;
    }

    public void setRealCost(BigDecimal realCost) {
        this.realCost = realCost;
    }

    public Short getPayType() {
        return payType;
    }

    public void setPayType(Short payType) {
        this.payType = payType;
    }

    public Short getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Short payStatus) {
        this.payStatus = payStatus;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId == null ? null : promotionId.trim();
    }

    public Date getPayTmie() {
        return payTmie;
    }

    public void setPayTmie(Date payTmie) {
        this.payTmie = payTmie;
    }

    public String getUserOrderId() {
		return userOrderId;
	}

	public void setUserOrderId(String userOrderId) {
		this.userOrderId = userOrderId;
	}

	public Long getUserAliPayId() {
        return userAliPayId;
    }

    public void setUserAliPayId(Long userAliPayId) {
        this.userAliPayId = userAliPayId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	public List<BillDetailDto> getBds() {
		return bds;
	}

	public void setBds(List<BillDetailDto> bds) {
		this.bds = bds;
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
}