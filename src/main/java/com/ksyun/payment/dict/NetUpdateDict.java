package com.ksyun.payment.dict;

/**
 * 带宽更新的参数封装
 * 
 * @author XueQi
 * 
 */
public class NetUpdateDict {
	private String userId;
	private String productId;
	private String net;
	private String isNeedPay;
	private String sign;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	public String getIsNeedPay() {
		return isNeedPay;
	}

	public void setIsNeedPay(String isNeedPay) {
		this.isNeedPay = isNeedPay;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
