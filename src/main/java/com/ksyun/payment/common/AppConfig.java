package com.ksyun.payment.common;

/**
 * 全局配置类(配置值写到配置文件中)
 * @author ZhangYanchun
 * @date   2013-09-04
 */
public class AppConfig {
	
	public final static String PAY_LOGIN_PAGE = "http://ks3.ksyun.com/user/login/page?result=107";
	
	public final static String WEB_URL = "http://i.ksyun.com";
	
	public final static String START_KVM = "{\"msgType\": \"control_kvm\",\"bizType\": \"startKvm\",\"dataMap\": {\"vm\":{\"order_id\":\"%s\",\"validtime\": \"%s\",\"name\": \"None\",\"image\": \"%s\",\"instance_count\": 1,\"security_groups\": [\"default\"],\"memory\": \"%d\",\"vcpu\": \"%d\",\"network_bandwidth\": \"%d\"},\"ebs\":{\"order_id\":\"%s\",\"name\":\"None\",\"description\":\"None\",\"size\": \"%d\",\"validtime\": \"%s\"},\"user_id\": \"%d\",\"email\": \"%s\"},\"timestamp\": \"%s\",\"retryCount\": \"0\"}";
	
	public final static String DESTROY_KVM = "{\"msgType\": \"control_kvm\",\"bizType\": \"destroyKvm\",\"dataMap\": {\"vm_id\": \"%s\",\"user_id\": \"%d\",\"email\": \"%s\"},\"timestamp\": \"%s\",\"retryCount\": \"0\"}";
	
	public final static String DESTROY_EBS = "{\"msgType\": \"control_kvm\",\"bizType\": \"destroyEbs\",\"dataMap\": {\"ebs_id\": \"%s\",\"user_id\": \"%d\",\"email\": \"%s\"},\"timestamp\": \"%s\",\"retryCount\": \"0\"}";
	
	public final static String EXTEND_KVM_OR_EBS = "{\"msgType\": \"control_kvm\",\"bizType\": \"extendKvmEbs\",\"dataMap\": {\"email\": \"%s\",\"user_id\": \"%d\", \"valid_time\": [{\"type\": \"%d\",\"uuid\": \"%s\",\"valid_time\": \"%s\",\"order_id\": \"%s\"}]},\"timestamp\": \"%s\",\"retryCount\": \"0\"}";
	
	public static final String AES_KEY = "LBSEFJMN6HQ5LG7XBK6A";
	
	private String loginPage;
	
	private String aliPayNotifyUrl;
	
	private String aliPayReturnUrl;
	
	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public String getAliPayNotifyUrl() {
		return aliPayNotifyUrl;
	}

	public void setAliPayNotifyUrl(String aliPayNotifyUrl) {
		this.aliPayNotifyUrl = aliPayNotifyUrl;
	}

	public String getAliPayReturnUrl() {
		return aliPayReturnUrl;
	}

	public void setAliPayReturnUrl(String aliPayReturnUrl) {
		this.aliPayReturnUrl = aliPayReturnUrl;
	}

	//登录失效跳转地址
	//public final static String PAY_LOGIN_PAGE = "http://test.ks3.ksyun.com/user/login/page?result=101";
	/*//ali支付通知页面
	public final static String ALIPAY_NOTIFY_URL = "http://test.i.ksyun.com/ali_pay_notify";
	//ali支付返回页面
	public final static String ALIPAY_RETURN_URL = "http://test.i.ksyun.com/ali_pay_return";*/

}
