package com.ksyun.payment.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IMsgTemplateDao;

/**
 * 信息模板DAO
 * @author ZhangYanchun
 * @date   2013-12-06
 */
@Repository
public class MsgTemplateDao<MsgTemplateDto> extends BaseDao<MsgTemplateDto, Map<String, String>> implements IMsgTemplateDao<MsgTemplateDto> {

	@Override
	public void updateTemplate(Map<String, Object> params) {
		sqlSession.update(String.format("%s.update", nameSpace), params);
		
	}

}
