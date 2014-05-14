package com.ksyun.vm.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tools {
	public static String listToString(List<?> list) {
		String resultStr = "";
		for (Object item : list) {
			resultStr += item + ",";
		}
		resultStr = resultStr.substring(0, resultStr.lastIndexOf(","));
		return resultStr;
	}
	
	public static String makeRowKey(String vmuuid, String logTime){
		return vmuuid + "_"+ logTime;
	}
	
	public static String makeDiskRowKey(String vmuuid, String disk, String logTime){
		return vmuuid + "_"+ disk+"_"+logTime;
	}
	
	public static String makeNetworkRowKey(String vmuuid, String network, String logTime){
		return vmuuid + "_"+ network+"_"+logTime;
	}


    public static Map<String,String> collectProperties(String[] keys,String[] values) {
        Map<String, String> map = new HashMap<>();
        for(int i=0;i<keys.length;i++){
            String key = keys[i];
            String value = values[i];
            map.put(key, value);
        }
        return map;
    }
	
}