package com.ksyun.payment.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ksyun.payment.dao.IMsgTemplateDao;
import com.ksyun.payment.dto.MsgTemplateDto;
import com.ksyun.payment.service.IMsgTemplateService;

/**
 * 信息模板服务类
 * @author ZhangYanchun
 * @date   2013-12-06
 */
@Service
public class MsgTemplateService implements IMsgTemplateService {
	
	@Resource
	private IMsgTemplateDao<MsgTemplateDto> msgTemplateDao;

	@Override
	public List<MsgTemplateDto> getAllTemplates() {
		return msgTemplateDao.findAll(null);
	}

	@Override
	public MsgTemplateDto getTemplate(String bizType, String msgType) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bizType", bizType);
		map.put("msgType", msgType);
		return msgTemplateDao.findById(map);
	}

	@Override
	public void updateTemplate(Map<String, Object> params) {
		msgTemplateDao.updateTemplate(params);
		
	}

	@Override
	public void saveTemplate(MsgTemplateDto msgTemplate) {
		msgTemplateDao.save(msgTemplate);
	}

}
