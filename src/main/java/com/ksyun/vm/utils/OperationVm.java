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
	public static void editVm(String url, String action, String tenantId, String userId) throws HttpException, IOException{
		if(action.equals(EnumEditVm.reboot.value())){
			String requestBody = "{\"reboot\":{\"type\":\"soft\"}}";
			HttpUtils.getPostResponseData(url, requestBody, tenantId,userId);
		}else if(action.equals(EnumEditVm.pause.value())){
			String requestBody = "{\"pause\":null}";
			HttpUtils.getPostResponseData(url, requestBody, tenantId,userId);
		}else if(action.equals(EnumEditVm.resume.value())){
			String requestBody = "{\"resume\":null}";
			HttpUtils.getPostResponseData(url, requestBody, tenantId,userId);
		}else if(action.equals(EnumEditVm.suspend.value())){
			String requestBody = "{\"suspend\":null}";
			HttpUtils.getPostResponseData(url, requestBody, tenantId,userId);
		}else if(action.equals(EnumEditVm.stop.value())){
			String requestBody = "{\"os-stop\":null}";
			HttpUtils.getPostResponseData(url, requestBody, tenantId,userId);
		}else if(action.equals(EnumEditVm.start.value())){
			String requestBody = "{\"os-start\":null}";
			HttpUtils.getPostResponseData(url, requestBody, tenantId,userId);
		}
		
	}
	/**
	 * 删除虚拟机
	 * @param url
	 * @param tenantId
	 * @param userId
	 */
	public static Integer deleteVm(String url,String tenantId,String userId){
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
	public static void createSnapShot(String url, String tenantId, String userId, String snapShotName) throws HttpException, IOException{
		String requestBody = "{\"createImage\":{\"name\":"+"\""+snapShotName+"\""+"}}";
		System.out.println(requestBody);
		String result = HttpUtils.getPostResponseData(url, requestBody, tenantId,userId);
		System.out.println(result);
	}
}
