package com.ksyun.pcloud.common.account; /**
 * @author: yangji
 * @data:   Jul 10, 2013
 */
//package com.ksyun.ks3.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class RestfulRequestor {

	//client level params
	private static final int CONNECTION_TIMEOUT = 20000;
	private static final int SOCKET_TIME = 20000;
	
	
	//httpclient instance 
	private static HttpClient client;
	
	
	static {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		client = new HttpClient(connectionManager);
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(CONNECTION_TIMEOUT);
		client.getHttpConnectionManager().getParams()
				.setSoTimeout(SOCKET_TIME);
	}
	


	private static PostMethod getPostMethod(String agent, int version , Document document, String url) {
		PostMethod postMethod = new PostMethod(url);
		RequestEntity entity = new ByteArrayRequestEntity(document.asXML()
				.getBytes());
		postMethod.setRequestHeader("User-Agent", agent);
		postMethod.setRequestHeader("v", String.valueOf(version));
		postMethod.setRequestHeader("Accept", "*/*");
		postMethod.setRequestEntity(entity);
		return postMethod;
	}
	
	private static PostMethod getPostMethod(JSONObject document,String url){
		PostMethod postMethod = new PostMethod(url);
		RequestEntity entity = new ByteArrayRequestEntity(document.toString().getBytes());
		postMethod.setRequestHeader("content-type", "application/json");
		postMethod.setRequestEntity(entity);
		return postMethod;		
	}

	public static Document map2Xml(Map<String, String> params) {

		Document document = DocumentHelper.createDocument();
		Element xLiveElement = document.addElement("xLive");
		for (Entry<String, String> param : params.entrySet()) {
			Element element = xLiveElement.addElement(param.getKey().trim());
			element.setText(param.getValue().trim());
		}
		return document;
	}
	
	public static Map<String, String> xml2Map(Document document){
		
		Map<String, String> map = null;
		Element root = document.getRootElement();
		Attribute resultAttr = root.attribute("result");
		String result = resultAttr.getValue();
			
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		//构造指定大小的map，更省内存:p
		map = new HashMap<String, String>(list.size()+1);
		map.put("result", result);
		for(Element element:list){
			map.put(element.getName(), element.getText());
		}
		
		return map;
	}
	
	public static List<String> xml2List(Document document){
		List<String> listRes = null;
		Element root = document.getRootElement();
		Attribute resultAttr = root.attribute("result");
		String result = resultAttr.getValue();
			
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		listRes = new ArrayList<String>(list.size()+1);
		listRes.add(result);
		for(Element element:list){
			listRes.add(element.getText());
		}
		
		return listRes;
	}

	//使用XML进行请求
	public static Document request(String agent, int version , Document document, String url) {

		PostMethod postMethod = getPostMethod(agent,version,document,url);
		Document resultDocument = null;

		try {
			int statusCode = client.executeMethod(postMethod);
			for (int i = 0; i < 3; i++) {
				if (statusCode == HttpStatus.SC_OK) {
					break;
				} else {
					statusCode = client.executeMethod(postMethod);
				}
			}
			InputStream input = postMethod.getResponseBodyAsStream();
			resultDocument = new SAXReader().read(input);	

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return resultDocument;
	}
	
	//使用Json进行请求
	public static JSONObject request(JSONObject document, String url) {

		PostMethod postMethod = getPostMethod(document,url);
		JSONObject resultDocument = null;

		try {
			int statusCode = client.executeMethod(postMethod);
			for (int i = 0; i < 3; i++) {
				if (statusCode == HttpStatus.SC_OK) {
					break;
				} else {
					statusCode = client.executeMethod(postMethod);
				}
			}
			String result = postMethod.getResponseBodyAsString();
			resultDocument = JSONObject.fromObject(result);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return resultDocument;
	}
}
