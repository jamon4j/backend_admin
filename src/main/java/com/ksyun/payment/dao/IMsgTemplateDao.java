package com.ksyun.payment.dao;

import java.util.Map;

/**
 * 信息模板DAO类
 * @author ZhangYanchun
 * @date   2013-12-06
 */
public interface IMsgTemplateDao<MsgTemplateDto> extends IBaseDao<MsgTemplateDto, Map<String, String>> {
	/**
	 * 更新模板信息
	 * @param params
	 */
	void updateTemplate(Map<String, Object> params);

}
