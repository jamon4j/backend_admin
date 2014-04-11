package com.ksyun.payment.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.ksyun.tools.net.HttpClientUtil;

/**
 * kvm相关接口调用封装
 * @author YangJi
 * @date   2013-09-04
 */
public class KvmHttpUtil {
	
	private static Logger logger = Logger.getLogger(KvmHttpUtil.class);
	private static final String imageUrl = "http://kvm.ksyun.com/image/list/open";
	private static final String loginUrl = "http://pubkvm.ks-cdn.com/login";
	private static final String rawImageRul = "http://pubkvm.ks-cdn.com/g/user/image_public_id_list/49e049ab04f846a48e5f0fd152a729fc/191532849a034f2cb555d8d8fa08027a";
	private static final String adminUser = "winnerku@126.com";
	private static final String adminPwd = "winnerku";
	
	public Map<String,Object> getImageList(){
		
		HttpClientUtil client = new HttpClientUtil(10000, 10000);
		
		String retStr = null;
		Map<String,String> headers = new HashMap<String, String>();
		headers.put("Referer", "www.ksyun.com");
		
		try {
			retStr = client.post(imageUrl, "", headers);
		} catch (Exception e) {
			logger.error(String.format("request image list error %s", imageUrl));
		}
		
		return JSON.parseObject(retStr, new TypeReference<Map<String, Object>>() {});		
	}
	
	public Map<String,String> getRawImageList(){
		
		HttpClientUtil client = new HttpClientUtil(10000, 10000);
		Map<String,String> loginMap = new HashMap<String, String>();
		loginMap.put("username", adminUser);
		loginMap.put("password", adminPwd);
		loginMap.put("scloud", "public");
		String retStr = null;
		try {
			retStr = client.post(loginUrl,loginMap);
		} catch (IOException e) {
			logger.error(String.format("request login error %s", loginUrl +"," + adminUser + "," + adminPwd));
		}
		
		//取得cookie
		Map<String, String> retMap = JSON.parseObject(retStr, new TypeReference<Map<String, String>>() {});
		String cookie = retMap.get("cookie");	
		
		//构造带cookie的请求
		HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        HttpPost httppost = new HttpPost(rawImageRul);
		httppost.addHeader(new BasicHeader("Cookie","backend="+cookie));
		
		String jsonImageList = null;
		
		try {
			HttpResponse response = httpclient.execute(httppost);
	        HttpEntity httpEntity = response.getEntity();
	        
	        if (httpEntity != null) {
	        	jsonImageList = EntityUtils.toString(httpEntity);
	            httpEntity.consumeContent();
	        }
		} catch (Exception e) {
			logger.error(String.format("request raw list error %s", rawImageRul));
		}
		
        
        //解析
        JSONArray dataArray = JSON.parseArray(jsonImageList);	
		Map<String,String> imageMap = new HashMap<String, String>();
		
		for(int i=0;i<dataArray.size();i++){
			@SuppressWarnings("unchecked")
			Map<String,String> tmpMap = (Map<String,String>)dataArray.get(i);
			imageMap.put(tmpMap.get("id"), tmpMap.get("name"));
		}		
        
		return imageMap;
		
	}
	
	
	public static void main(String[] args){
		
		KvmHttpUtil kvmutil = new KvmHttpUtil();
//		int status = Integer.valueOf(kvmutil.getImageList().get("status").toString());
//		System.out.println(status);
//		JSONArray dataArray = JSON.parseArray(kvmutil.getImageList().get("data").toString());	
//		Map<String,String> imageMap = new HashMap<String, String>();
//		
//		for(int i=0;i<dataArray.size();i++){
//			@SuppressWarnings("unchecked")
//			Map<String,String> tmpMap = (Map<String,String>)dataArray.get(i);
//			imageMap.put(tmpMap.get("id"), tmpMap.get("name"));
//		}
//		System.out.println(imageMap.toString());		
		
		Map<String,String> imageMap = kvmutil.getRawImageList();
		System.out.println(imageMap);
			
	}
}
