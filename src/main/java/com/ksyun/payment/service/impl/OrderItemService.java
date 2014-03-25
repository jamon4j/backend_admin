package com.ksyun.payment.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ksyun.payment.dao.IOrderItemDao;
import com.ksyun.payment.dao.IOrderItemPriceRangeDao;
import com.ksyun.payment.dict.ProductItemDict;
import com.ksyun.payment.dict.ProductItemDict.PriceRange;
import com.ksyun.payment.dto.OrderItemDto;
import com.ksyun.payment.dto.OrderItemPriceRangeDto;
import com.ksyun.payment.service.IOrderItemService;

/**
 * 订单产品快照明细实现类(使用cache，所以没有合并到orderService里面)
 * @author ZhangYanchun
 * @date   2013-08-29
 */

@Service
public class OrderItemService implements IOrderItemService {
	
	@Resource
	private IOrderItemDao<OrderItemDto> orderItemDao;
	
	@Resource
	private IOrderItemPriceRangeDao<OrderItemPriceRangeDto> orderItemPriceRangeDao;

    private LoadingCache<String, Map<String, ProductItemDict>> cache;
	
	private static Logger logger = Logger.getLogger(IOrderItemService.class);
	
	/**
	 * 初始化缓存内容
	 */
	@PostConstruct
	public void inti(){
		this.cache = CacheBuilder.newBuilder().maximumSize(100)
	            .expireAfterAccess(2, TimeUnit.HOURS).build(new CacheLoader<String, Map<String, ProductItemDict>>() {
					@Override
					public Map<String, ProductItemDict> load(String key) throws Exception {
						return getAllItems(key);
					}
	            });
	}
	/**
	 * 获取产品项目的列表
	 * @return
	 */
	private Map<String, ProductItemDict> getAllItems(String orderId) {
		
		logger.info("get productItems from order product snapshot ..................");
		Map<String, ProductItemDict> maps = new HashMap<String, ProductItemDict>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		List<OrderItemDto> pItem = orderItemDao.findAll(params);
		for(OrderItemDto item : pItem) {
			String itemNo = item.getItemNo().trim();
			ProductItemDict pi = new ProductItemDict();
			pi.setItemNo(itemNo);
			pi.setUnitCount(item.getUnitCount());
			pi.setItemName(item.getItemName());
			pi.setUnit(item.getUnit());
			maps.put(itemNo, pi);
		}
		
		List<OrderItemPriceRangeDto> pprs = orderItemPriceRangeDao.findAll(params);
		for(OrderItemPriceRangeDto ppr : pprs) {
			String itemNo = ppr.getItemNo().trim();
			
			PriceRange pr = new PriceRange();
			pr.setPrice(ppr.getPrice());
			pr.setRangeBegin(ppr.getRangeBegin());
			pr.setRangeEnd(ppr.getRangeEnd());
			pr.setOffesetCost(ppr.getOffsetCost());
			ProductItemDict tmp = maps.get(itemNo);
			if(tmp != null) {
				tmp.addToList(pr);
			}
		}
		//结果排序
		for(Map.Entry<String, ProductItemDict> entry : maps.entrySet()) {
			entry.getValue().sortDesc();
		}
		
		return maps;
	}
	
	@Override
	public Map<String, ProductItemDict> getAllOrderItems(String orderId) {
		Map<String, ProductItemDict> maps = null;
		try {
			maps = cache.get(orderId);
		} catch (ExecutionException e) {
			logger.error("the productItem cache is error, this is very serious");
		}
		return maps;
	}
	@Override
	public List<OrderItemDto> getAllOrderItemsNoCache(String orderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		return orderItemDao.findAll(params);
	}
	
	
	

}
