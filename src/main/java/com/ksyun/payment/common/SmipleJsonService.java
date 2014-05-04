package com.ksyun.payment.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;

import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.pojo.BasePo;
import com.ksyun.vm.pojo.OpenStackResult;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.HttpUtils;
import com.ksyun.vm.utils.InitConst;

/**
 * 简单的JSON请求类 <br/>
 * 为了不修改vm下的JSONService<br/>
 * 服务iConsole
 * 
 * @author XueQi
 * 
 */
public class SmipleJsonService {
	/**
	 * post方法
	 * 
	 * @param key
	 * @param requestBody
	 * @param clazz
	 * @param param
	 * @return
	 * @throws ErrorCodeException
	 */
	public static <T extends BasePo> T post(String key, String requestBody,
			Class<T> clazz, Object... param) throws ErrorCodeException {
		Map<String, String> map = new HashMap<>();
		map.put("Content-Type", "application/json");
		OpenStackResult result = HttpUtils.post(getUrl(key, param), map,
				requestBody);
		if (result != null
				&& (result.getStatus() == HttpStatus.SC_OK || result
						.getStatus() == HttpStatus.SC_NO_CONTENT)) {
			if (clazz == null) {
				return null;
			}
			return JSONObject.parseObject(result.getMessage(), clazz);
		} else {
			// 错误日志入库
			throw new ErrorCodeException(result);
		}
	}

	private static String getUrl(String key, Object... param) {
		String host = Constants.getPropertyValue(InitConst.HTTP_HOST_ICONSOLE);
		String port = Constants.getPropertyValue(InitConst.HTTP_PORT_ICONSOLE);
		String uri = Constants.getPropertyValue(key, param);
		return "http://" + host + ":" + port + uri;
	}
}
