package com.ksyun.vm.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.utils.enumeration.EnumResult;

public class HttpUtils {
	/**
	 * 构造httpClient对象
	 * @return
	 */
	private static HttpClient getHttpClient() {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpClient client = new HttpClient(connectionManager);
		// 设置连接和读取超时时间
		client.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.valueOf(Constants.getPropertyValue(InitConst.CONNECTION_TIMEOUT)));
		client.getHttpConnectionManager().getParams().setSoTimeout(Integer.valueOf(Constants.getPropertyValue(InitConst.SOCKET_TIMEOUT)));
		return client;
	}
	
	private static String inputStreamToString(InputStream input) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while ((count = input.read(data, 0, 1024)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "UTF-8");
	}

	/**
	 * admin账户get请求返回的信息
	 * 
	 * @param url
	 * @return
	 * @throws org.apache.commons.httpclient.HttpException
	 * @throws java.io.IOException
	 */
	public static String getAdminResponseData(String url) throws HttpException, IOException {
		HttpClient client = getHttpClient();
		GetMethod getMethod = new GetMethod(url);
		setAdminHeader(getMethod);
		Integer status = client.executeMethod(getMethod);
		if (status == 200) {
			InputStream input = getMethod.getResponseBodyAsStream();
			String responseBody = inputStreamToString(input);
			return responseBody;
		}
		return null;
	}

	/**
	 * 普通账户get请求返回的信息
	 * 
	 * @param url
	 * @param headerArgs
	 * @return
	 * @throws org.apache.commons.httpclient.HttpException
	 * @throws java.io.IOException
	 */
	public static String getResponseData(String url, Map<String, String> headerArgs) throws HttpException, IOException {
		HttpClient client = getHttpClient();
		GetMethod getMethod = new GetMethod(url);
		setHeader(getMethod, headerArgs);
		Integer status = client.executeMethod(getMethod);
		if (status == 200) {
			InputStream input = getMethod.getResponseBodyAsStream();
			String responseBody = inputStreamToString(input);
			return responseBody;
		}
		return null;
	}

	/**
	 * 设置普通账户查找时http request头信息
	 * 
	 * @param httpMethod
	 * @param headerArgs
	 */
	private static void setHeader(HttpMethod httpMethod, Map<String, String> headerArgs) {
		if(headerArgs == null)
			return;
		Set<Map.Entry<String, String>> set = headerArgs.entrySet();
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			httpMethod.setRequestHeader(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 设置admin账户查找时http request头信息
	 * 
	 * @param getMethod
	 */
	private static void setAdminHeader(GetMethod getMethod) {
		getMethod.setRequestHeader("Content-Type", "application/json");
		//	getMethod.setRequestHeader("X-Auth-Token", getAdminToken());
		getMethod.setRequestHeader("X-Auth-Token", "bf6f39d9024947c8ac91a0c2af1f6fd7:3ed3e7a66ec549a89f9fe8b713b07506");
	}
	/**
	 * 获取reponse数据
	 * @param url
	 * @param requestBody
	 * @param headerArgs
	 * @return
	 * @throws org.apache.commons.httpclient.HttpException
	 * @throws java.io.IOException
	 */
	public static String getPostResponseData(String url,String requestBody,Map<String,String> headerArgs) throws HttpException, IOException {
		HttpClient client = getHttpClient();
		PostMethod postMethod = new PostMethod(url);
		RequestEntity entity = new ByteArrayRequestEntity(requestBody.getBytes());
		postMethod.setRequestEntity(entity);
		setHeader(postMethod, headerArgs);
		client.executeMethod(postMethod);
		InputStream input = postMethod.getResponseBodyAsStream();
		if(input != null){
			String responseBody = inputStreamToString(input);
			return responseBody;
		}
		return null;
	}
	
	
	/**
	 * 获取reponse数据，如果传入了tenantId和userId则按照默认方式构建header
	 * @param url
	 * @param requestBody
	 * @param tenantId
	 * @param userId
	 * @return
	 * @throws org.apache.commons.httpclient.HttpException
	 * @throws java.io.IOException
	 */
	public static String getPostResponseData(String url,String requestBody,String tenantId, String userId) throws HttpException, IOException {
		HttpClient client = getHttpClient();
		PostMethod postMethod = new PostMethod(url);
		RequestEntity entity = new ByteArrayRequestEntity(requestBody.getBytes());
		postMethod.setRequestEntity(entity);
		Map<String,String> header = returnDefaultHeader(tenantId, userId);
		setHeader(postMethod, header);
		client.executeMethod(postMethod);
		InputStream input = postMethod.getResponseBodyAsStream();
		String responseBody = inputStreamToString(input);
		return responseBody;
	}
	
	
	/**
	 * 获得admin的token
	 * @return
	 * @throws org.apache.commons.httpclient.HttpException
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("unchecked")
	private static String getAdminToken() throws HttpException, IOException {
		String requestBody = "{ \"auth\":{ \"passwordCredentials\": { \"username\":\"admin\", \"password\":\"secrete\" }, \"tenantName\":\"admin\" } }";
		Map<String,String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		header.put("Accept", "application/json");
		header.put("Cache-Control", "no-cache");
		String tokenJson = HttpUtils.getPostResponseData(Constants.getPropertyValue(InitConst.TOKEN),requestBody,header);
		LinkedHashMap map = JSON.parseObject(tokenJson, LinkedHashMap.class);
		JSONObject access = (JSONObject) map.get("access");
		JSONObject token = (JSONObject) access.get("token");
		String result = (String) token.get("id");
		System.out.println("adminToken: "+ result);
		return result;
	}
	/**
	 * 返回普通用户的header
	 * @param tenantId
	 * @param userId
	 * @return
	 */
	public static Map<String, String> returnDefaultHeader(String tenantId, String userId){
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/json");
		header.put("X-Auth-Token", createDefaultToken(tenantId, userId));
		return header;
	}
	/**
	 * 创建普通用户token
	 * @param userId
	 * @param tenantId
	 * @return
	 */
	public static String createDefaultToken(String tenantId, String userId){
		return userId+":"+tenantId;
	}
	/**
	 * http delete方法
	 * @param url
	 * @param tenantId
	 * @param userId
	 */
	public static Integer deleteMethod(String url,String tenantId, String userId){
		HttpClient client = getHttpClient();
		DeleteMethod method = new DeleteMethod(url);
		Map<String,String> header = returnDefaultHeader(tenantId, userId);
		setHeader(method, header);
		try {
			client.executeMethod(method);
			return EnumResult.successful.value();
		} catch (Exception e) {
			e.printStackTrace();
			return EnumResult.failed.value();
		} 
	}
}
