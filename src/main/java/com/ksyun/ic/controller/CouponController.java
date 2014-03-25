package com.ksyun.ic.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksyun.payment.dto.CouponDto;
import com.ksyun.payment.service.ICouponService;
import com.ksyun.tools.DateUtil;
import com.ksyun.tools.net.ServletUtil;

/**
 * 代金券控制页面
 * @author ZhangYanchun
 * @date   2013-10-15
 */

@Controller
@RequestMapping("/admin")
public class CouponController extends BaseController {
	
	@Resource
	private ICouponService couponService;
	
	@RequestMapping("/coupon/index")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		Map<String, Object> params = new HashMap<String, Object>();
		List<CouponDto> pageList = couponService.getAllCoupons(params);
		request.setAttribute("pageList", pageList);
		return "admin/coupon/coupon_index";
	}
	
	@RequestMapping("/coupon/batch_produce")
	public String batch_produce(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		
		String batchNo = ServletUtil.getStr(req, "batchNo");
		if(batchNo != null) batchNo =  batchNo.trim();
		int num = ServletUtil.getInt(req, "num", 0);
		int activeMonth = ServletUtil.getInt(req, "activeMonth", 0);
		int expireMonth = ServletUtil.getInt(req, "expireMonth", 0);
		int productType = ServletUtil.getInt(req, "productType", 0);
		BigDecimal cash = ServletUtil.getDecimal(req, "sumMoney");
		CouponDto coupon = new CouponDto();
		Date now = new Date();
		coupon.setActiveExpireTime(DateUtil.getDateEndTime(DateUtil.addMonth(now, activeMonth)));
		coupon.setBatchNo(batchNo);
		coupon.setExpireTime(com.ksyun.tools.DateUtil.getDateEndTime(DateUtil.addMonth(now, expireMonth)));
		coupon.setIsActive((short)0);
		coupon.setProductType((short)productType);
		coupon.setBalanceMoney(cash);
		coupon.setSumMoney(cash);
		
		couponService.saveCoupon(coupon, num);
		
		return "redirect:/admin/coupon/index";
		
	}
	
	@RequestMapping("/coupon/get_activeCode")
	public String get_activeCode(HttpServletRequest req, HttpServletResponse response, ModelMap model){
		String batchNo = ServletUtil.getStr(req, "batchNo");
		if(batchNo != null) batchNo = batchNo.trim();
		model.put("list", couponService.getAllCouponsByBatchNo(batchNo));
		return "admin/coupon/active_codes";
	}
}
