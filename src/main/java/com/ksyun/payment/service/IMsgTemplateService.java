package com.ksyun.payment.service;

import java.util.List;
import java.util.Map;

import com.ksyun.payment.dto.MsgTemplateDto;


/**
 * 信息模板服务接口类
 * @author ZhangYanchun
 * @date   2013-12-06
 */
public interface IMsgTemplateService {

	/**
	 * 获取所有信息模板
	 * @return
	 */
	List<MsgTemplateDto> getAllTemplates();
	
	/**
	 * 获取模板信息
	 * @param bizType
	 * @param msgType
	 * @return
	 */
	MsgTemplateDto getTemplate(String bizType, String msgType);
	
	/**
	 * 更新用户信息模板
	 * @param msgTemplate
	 */
	void updateTemplate(Map<String, Object> params);
	
	/**
	 * 保存新的模板信息
	 * @param msgTemplate
	 */
	void saveTemplate(MsgTemplateDto msgTemplate);
}
