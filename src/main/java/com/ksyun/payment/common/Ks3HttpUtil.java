package com.ksyun.payment.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ksyun.tools.net.HttpClientUtil;

/**
 * ks3接口调用封装
 * @author ZhangYanchun
 * @date   2013-09-04
 */
public class Ks3HttpUtil {
	
	private static Logger logger = Logger.getLogger(Ks3HttpUtil.class);
	
	public String ks3ApiPost(String url, String postStr) {
		HttpClientUtil client = new HttpClientUtil(10000, 10000);
		
		String retStr = null;
		try {
			retStr = client.post(url, postStr, getHeads());
		} catch (Exception e) {
			logger.error(String.format("ks3 api error %s", url));
		}
		return retStr;
	}
	
	/**
	 * 封装http访问头
	 * @return
	 */
	private Map<String, String> getHeads() {
		
		Map<String, String> heads = new HashMap<String,String>();
		heads.put("User-Agent", "xlive-2.10.30");
		heads.put("v", "2");
		heads.put("deviceId", "KSYUN_WEBSITE");
		heads.put("user", "kpweb@kingsoft.com");
		heads.put("password", "WWcW59FqUFubBzmTe6BmOF1sHmaye");
		return heads;
	}

}
