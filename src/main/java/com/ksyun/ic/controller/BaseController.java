package com.ksyun.ic.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.ksyun.payment.common.ParamValueCheckedException;

/**
 * 提供基础的controller所需功能，现在主要用于输入参数验证。
 * @author ZhangYanchun
 * @date   2013-08-19
 */
public class BaseController {
	//定义日志
	protected Logger logger = Logger.getLogger(getClass());
	
	//设定本地变量为简体中文
	protected Locale local = Locale.SIMPLIFIED_CHINESE;
	
	@Resource
	protected MessageSource messageSource;

	/**
	 * 检查参数值是否为空
	 * @param msgKey      resource properties key
	 * @param checkValue  需要检查的值
	 * @param msgs
	 */
	protected void checkIsEmpty(String msgKey, String checkValue, Map<String, String> checkMsgs){
		if("".equals(checkValue)){
			checkMsgs.put(msgKey, messageSource.getMessage(msgKey, new Object[] {}, local));
		}
	}
	/**
	 * 检查参数值必须大于 minValue
	 * @param msgKey
	 * @param checkValue
	 * @param minValue
	 * @param msgs
	 */
	protected void checkIntNum(String msgKey, int checkValue, int minValue, Map<String, String> checkMsgs) {
		if(checkValue <= minValue) {
			checkMsgs.put(msgKey, messageSource.getMessage(msgKey, new Object[] { minValue }, local));
		}
	}
	
	/**
	 * 检查参数值必须大于 minValue
	 * @param msgKey
	 * @param checkValue
	 * @param minValue
	 * @param msgs
	 */
	protected void checkDecimalNum(String msgKey, BigDecimal checkValue, BigDecimal minValue, Map<String, String> checkMsgs) {
		if(checkValue.compareTo(minValue) <= 0) {
			checkMsgs.put(msgKey, messageSource.getMessage(msgKey, new Object[] { minValue }, local));
		}
	}
	
	/**
	 * 检查参数值是否包含在数据字典列表里面
	 * @param msgKey
	 * @param checkValue
	 * @param allList
	 * @param checkMsgs
	 */
	protected void checkIsContainInList(String msgKey, String checkValue, List<String> allList, Map<String, String> checkMsgs) {
		if(! allList.contains(checkValue)) {
			checkMsgs.put(msgKey, messageSource.getMessage(msgKey, new Object[] { allList }, local));
		}
	}
	
	/**
	 * 检查参数值是否包含在数据字典Map里面
	 * @param msgKey
	 * @param checkValue
	 * @param allList
	 * @param checkMsgs
	 */
	protected void checkIsContainInMap(String msgKey, Integer checkValue, Map<Integer, String> allMaps, Map<String, String> checkMsgs) {
		if(! allMaps.containsKey(checkValue)) {
			checkMsgs.put(msgKey, messageSource.getMessage(msgKey, new Object[] { allMaps.values() }, local));
		}
	}
	
	/**
	 * 验证参数列表，验证不通过，将抛出异常
	 * @param request
	 * @param checkMsgs
	 */
	protected void verifyResult(HttpServletRequest request, Map<String, String> checkMsgs) {
		if(checkMsgs.size() > 0) {
			request.setAttribute("errorMsgs", checkMsgs);
			throw new ParamValueCheckedException();
		}
	}
	/**
	 * 写入返回值
	 * @param response
	 * @param value
	 */
	protected void writeToResponse(HttpServletResponse response, String value) {
		try {
			response.getWriter().write(value);
		} catch (IOException e) {
			logger.error(String.format("response write error: %s", value));
		}
	}
	/**
	 * 获取用户Id
	 * @param request
	 * @return
	 */
	protected long getUserId(HttpServletRequest request) {
		Object tmp = request.getAttribute("userId");
		if(tmp != null) 
		    return (Long)tmp;
		else 
		    return 1001;
	}

}
