package com.ksyun.payment.dto;


/**
 * 库存Dto类
 * @author ZhangYanchun
 * @2013-12-05
 */
public class KvmStockDto implements DtoInterface {
	
	//产品类型
	private String kvmType;
	
	//库存数量
	private int amount;
	
	//备注信息
	private String memo;
	
	public String getKvmType() {
		return kvmType;
	}

	public void setKvmType(String kvmType) {
		this.kvmType = kvmType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toJson() {
		return null;
	}

}
