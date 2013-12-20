package com.ksyun.vm.utils;

import java.util.List;

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
	
}