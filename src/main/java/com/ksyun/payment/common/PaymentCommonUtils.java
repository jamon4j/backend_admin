package com.ksyun.payment.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.ksyun.payment.dto.CouponDto;
import com.ksyun.tools.DateUtil;

/**
 * 工程使用的公共类
 * @author ZhangYanchun
 * @date   2013-10-08
 */
public class PaymentCommonUtils {
	
	private static Logger logger = Logger.getLogger(PaymentCommonUtils.class);

	/**
	 * 解析json字符串为对象，错误记录字符串信息(使用泛型)
	 * @param jsonStr
	 * @return
	 */
	public static <T> T  jsonStrToObject(String jsonStr, Class<T> clazz)  {
		T ret = null;
		if(jsonStr == null) return null;
		try{
			jsonStr = jsonStr.trim();
			ret = JSON.parseObject(jsonStr, clazz);
		}  catch(Exception e) {
			logger.error(String.format("the json parse error: %s  %s", jsonStr, e.getMessage()));
		}
		return ret;
	}
	
	/**
	 * 封装订单ID
	 * @param incrNum
	 * @param date
	 * @param productType
	 * @return
	 */
	public static String gerOrderId(long incrNum, Date date, short productType) {
		StringBuilder sb = new StringBuilder("");
		sb.append(productType);
		sb.append(DateUtil.getStrDateyyMMdd(date));
		DecimalFormat df = new DecimalFormat("000000");
		sb.append(df.format(incrNum));
		return sb.toString();
		
	}
	
	/**
	 * 扣除所有代金券的金额
	 * @param coupons
	 * @return
	 */
	public static Map<Long, BigDecimal> findFeeCoupon(List<CouponDto> coupons) {
		Map<Long, BigDecimal> maps = new HashMap<Long, BigDecimal>();
		for(CouponDto coupon : coupons) {
			maps.put(coupon.getCouponId(), coupon.getBalanceMoney());
		}
		return maps;
	}
	/**
	 * 获取需要进行操作的代金券ID及金额
	 * @param realCost
	 * @param coupons
	 * @return
	 */
	public static Map<Long, BigDecimal> findFeeCoupon(BigDecimal realCost, List<CouponDto> coupons) {
		Map<Long, BigDecimal> maps = new HashMap<Long, BigDecimal>();
		for(CouponDto coupon : coupons) {
			BigDecimal balance = coupon.getBalanceMoney();
			long couponId = coupon.getCouponId();
			if(balance.compareTo(realCost) >= 0) {
				maps.put(couponId, realCost);
				realCost = BigDecimal.ZERO;
				break;
			} else {
				realCost = realCost.subtract(balance);
				maps.put(couponId, balance);
				continue;
			}
		}
		return maps;
	}
	
	/**
	 * 构造分页使用的排序语句
	 * @param map
	 * @return
	 */
	public static String changeToSortStr(Map<String, Integer> map) {
		
		if(map == null) return "";
		int size =  map.size();
		if(size == 0) return "";
		
		StringBuilder sb = new StringBuilder();
		Set<String> keys = map.keySet();
		for(String key : keys) {
			int flag = map.get(key);
			sb.append(key);
			if(flag == 1) sb.append(".asc");
			if(flag == -1) sb.append(".desc");
			sb.append(",");
		}
		String sortString = sb.toString();
		sortString = sortString.substring(0, sortString.length() - 1);
		
		return sortString;
	}
	/*private String encoder(String cvalue) {
		try {
			cvalue = URLEncoder.encode(cvalue, "utf-8");
		} catch (UnsupportedEncodingException e) {
			cvalue = null;
		}
		return cvalue;
	}
	private String decoder(String cvalue) {
		try {
			cvalue = URLDecoder.decode(cvalue, "utf-8");
		} catch (UnsupportedEncodingException e) {
			cvalue = null;
		}
		return cvalue;
	}*/
}
