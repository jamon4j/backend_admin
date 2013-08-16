package com.ksyun.vm.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.ksyun.vm.utils.enumeration.EnumEditVm;

public class OperationVm {
	/**
	 * 编辑虚拟机
	 * @param url
	 * @param action
	 * @param tenantId
	 * @param userId
	 * @throws org.apache.commons.httpclient.HttpException
	 * @throws java.io.IOException
	 */
	public static void editVm(String vmId, String action, String tenantId, String userId) throws HttpException, IOException{
		if(action.equals(EnumEditVm.restart.value())){
			String requestStr = Constants.getPropertyValue(InitConst.RESTARTVM);
			String requestBody = "{\"instance_id\":"+"\""+vmId+"\""+"}";
			HttpUtils.getPostResponseData(requestStr, requestBody, tenantId,userId);
		}else if(action.equals(EnumEditVm.changepwd.value())){
			String requestStr = Constants.getPropertyValue(InitConst.CHANGEPWDVM);
			String requestBody = "{\"instance_id\":"+"\""+vmId+"\""+"}";
			HttpUtils.getPostResponseData(requestStr, requestBody, tenantId,userId);
		}else if(action.equals(EnumEditVm.terminal.value())){
			String requestStr = Constants.getPropertyValue(InitConst.TERMINALVM,vmId);
			HttpUtils.deleteMethod(requestStr, tenantId, userId);
		}else if(action.equals(EnumEditVm.stop.value())){
			String requestStr = Constants.getPropertyValue(InitConst.STOPVM);
			String requestBody = "{\"instance_id\":"+"\""+vmId+"\""+"}";
			HttpUtils.getPostResponseData(requestStr, requestBody, tenantId,userId);
		}else if(action.equals(EnumEditVm.start.value())){
			String requestStr = Constants.getPropertyValue(InitConst.STARTVM);
			String requestBody = "{\"instance_id\":"+"\""+vmId+"\""+"}";
			HttpUtils.getPostResponseData(requestStr, requestBody, tenantId,userId);
		}
		
	}
	/**
	 * 删除虚拟机
	 * @param url
	 * @param tenantId
	 * @param userId
	 */
	public static String deleteVm(String url,String tenantId,String userId){
		System.out.println("delete vm: " + url);
		return HttpUtils.deleteMethod(url, tenantId, userId);
	}
	
	/**
	 * 创建系统镜像
	 * @param url
	 * @param tenantId
	 * @param userId
	 * @param snapShotName
	 * @throws org.apache.commons.httpclient.HttpException
	 * @throws java.io.IOException
	 */
	public static String createSnapShot(String vmId, String tenantId, String userId, String snapShotName) throws HttpException, IOException{
		String requestStr = Constants.getPropertyValue(InitConst.CREATESYSTEMSNAPSHOT);
		String requestBody = "{\"instance_id\":"+"\""+vmId+"\""+", \"name\":"+"\""+snapShotName+"\""+"}";
		String result = HttpUtils.getPostResponseData(requestStr, requestBody, tenantId,userId);
		return result;
	}
}
