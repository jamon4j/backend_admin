package com.ksyun.payment.dto;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 用户现金账单明细
 * @author ZhangYanchun
 * @ 2013-08-15
 */
public class UserAccountDetailDto {
	
    //明细主键
	private String caSerial;

    //业务类型  1充值  2业务退款  3消费   4提现
	private Short dealType;
    
	//业务处理时间
    private Date dealTime;
    
    //创建时间
    private Date ctime;

    //支付ID，保存第三方支付流水号
    private String payId;
    
    //收入金额
    private BigDecimal incomeMoney;

    //支出金额
    private BigDecimal payMoney;
    
    //消费账单ID
    private Long baseBillId;

    //用户ID
    private Long userId;

    //当前余额
    private BigDecimal balance;

    //备注
    private String memo;
    
    //状态   0 失败     1 成功
    private short status;

    public String getCaSerial() {
		return caSerial;
	}

	public void setCaSerial(String caSerial) {
		this.caSerial = caSerial;
	}

	public Short getDealType() {
        return dealType;
    }

    public void setDealType(Short dealType) {
        this.dealType = dealType;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }
    
    public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public BigDecimal getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(BigDecimal incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public Long getBaseBillId() {
        return baseBillId;
    }

    public void setBaseBillId(Long baseBillId) {
        this.baseBillId = baseBillId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
    
	
    
}