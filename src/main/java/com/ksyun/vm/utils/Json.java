package com.ksyun.vm.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSON;

/**
 * 提取公共接口方法
 * 
 * @author liuchuandong
 * @param <T>
 * 
 */
public class Json {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> returnList(Class<T> clazz,String order,Object... param) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(order, param);
		String resultJson = HttpUtils.getAdminResponseData(requestStr);
		if (resultJson != null) {
			List<T> resultList = new ArrayList<T>();
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				LinkedList list = JSON.parseObject(entry.getValue().toString(), LinkedList.class);
				Iterator innerIter = list.iterator();
				while (innerIter.hasNext()) {
					T t = (T) JSON.parseObject(innerIter.next().toString(), clazz);
					resultList.add(t);
				}
			}
			return resultList;
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T returnObj(Class<T> clazz,String order, Object... param) throws HttpException, IOException {
		String requestStr = Constants.getPropertyValue(order, param);
		String resultJson = HttpUtils.getAdminResponseData(requestStr);
		T t = null;
		if (resultJson != null) {
			LinkedHashMap map = JSON.parseObject(resultJson, LinkedHashMap.class);
			Iterator<Map.Entry> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				String testStr = entry.getValue().toString();
				t = (T) JSON.parseObject(testStr,clazz);
			}
			return t;
		}
		return null;
	}
	public static String getRemoteProperty(String order, String... param)throws HttpException, IOException{
		return Constants.getPropertyValue(order, param);
	}
}
