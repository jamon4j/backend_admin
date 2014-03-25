/**
 * @author: yangji
 * @data:   Feb 13, 2014
 */
package com.ksyun.payment.common;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ksyun.tools.net.HttpClientUtil;

public class DocsvrHttpUtil {
	
	private static final String DOCSVR_PREFIX = "http://devuser.ksyun.com/xsvr/";
	private static final String DEVICE_ID_PREFIX = "KSYUN_BACK_ADMIN-";
	private static final String XML_ROOT = "xLive";
	private static final String ADMINUSERNAME = "kpweb@kingsoft.com";
	private static final String ADMINPASSWORD = "WWcW59FqUFubBzmTe6BmOF1sHmaye";
	private static Logger logger = Logger.getLogger(DocsvrHttpUtil.class);
		
	private Map<String, String> getHeads() {
		
		Map<String, String> heads = new HashMap<String,String>();
		heads.put("User-Agent", "xlive-2.10.30");
		heads.put("v", "2");
		heads.put("deviceId", "KSYUN_WEBSITE");
		return heads;
	}
	
	private String map2XmlString(Map<String, String> params,String rootText) {

        Document document = DocumentHelper.createDocument();
        Element xLiveElement = document.addElement(rootText);
        for (Entry<String, String> param : params.entrySet()) {
            Element element = xLiveElement.addElement(param.getKey().trim());
            element.setText(param.getValue().trim());
        }
        return document.asXML();
    }
	
	private Document xmlString2Document(String xml){
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(new ByteArrayInputStream(xml.getBytes()));
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
		return document;
	}
	
	private ApiRet document2ApiRet(Document document){
		//document为null说明网络请求有问题或者返回数据异常		
		if(document==null)
			return null;

        Map<String, String> map = null;
        ApiRet apiRet = new ApiRet();
        
        try {       	
        	
            Element root = document.getRootElement();
            Attribute resultAttr = root.attribute("result");
            String result = resultAttr.getValue();

            @SuppressWarnings("unchecked")
            List<Element> list = root.elements();
            //构造指定大小的map，更省内存:p
            map = new HashMap<String, String>(list.size());
            
            apiRet.setResult(result);
            
            for(Element element:list){
                map.put(element.getName(), element.getText());
            }
            
            apiRet.setData(map);
			
		} catch (Exception e) {
			String singleParam = String.format("{%s=%s}", "document",document.asXML());
			String errorInfo = String.format("[CommonUtils][xml2ApiRet][params=%s][%s]", singleParam,e.toString());
			logger.error(errorInfo);
		}
        
        return apiRet;
	}
	

	private ApiRet login(String username, String password) {
		
		//构造参数
		Map<String, String> mapParams = new HashMap<String, String>();
		String device = DEVICE_ID_PREFIX + System.currentTimeMillis();
		mapParams.put("deviceId", device);
		mapParams.put("user", username);
		mapParams.put("password", password);
		String xmlParams = map2XmlString(mapParams, XML_ROOT);
		String url = DOCSVR_PREFIX + "kscLogin";
		
		//请求
		HttpClientUtil client = new HttpClientUtil(10000, 10000);
		String result = null;
		try {
			result = client.post(url, xmlParams, getHeads());
		} catch (Exception e) {			
			logger.error(String.format("request login from docsvr error %s", url));
		}
		
		Document document = xmlString2Document(result);
		ApiRet apiRet = document2ApiRet(document);
		
		return apiRet;
	}
	
	public String adminLogin(){
		
		ApiRet apiRet = login(ADMINUSERNAME,ADMINPASSWORD);
		if(apiRet!=null&&apiRet.getResult().equals("ok"))
			return apiRet.getData().get("token");
		return null;
	}
	
	public String adminGetUserAuth(String suToken,String email){
		
		//构造参数
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("token", suToken);
		mapParams.put("email", email);
		String xmlParams = map2XmlString(mapParams, XML_ROOT);
		String url = DOCSVR_PREFIX + "kscAdminGetUserAuth";
		
		//请求
		HttpClientUtil client = new HttpClientUtil(10000, 10000);
		String result = null;
		try {
			result = client.post(url, xmlParams, getHeads());
		} catch (Exception e) {			
			logger.error(String.format("request admin get user auth from  docsvr error %s", url));
		}
		
		Document document = xmlString2Document(result);
		ApiRet apiRet = document2ApiRet(document);		
		
		if(apiRet!=null&&apiRet.getResult().equals("ok"))
			return apiRet.getData().get("token");
		return null;
	}
	
	
		
	
	
	public static void main(String[] args) {
		
		DocsvrHttpUtil httpUtil = new DocsvrHttpUtil();
		String suToken = httpUtil.adminLogin();
		String token = httpUtil.adminGetUserAuth(suToken, "yangji@kingsoft.com");
		System.out.println(token);
		
	}

}
