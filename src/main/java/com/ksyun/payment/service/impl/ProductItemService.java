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
import com.ksyun.payment.dao.IProductItemDao;
import com.ksyun.payment.dao.IProductPriceRangeDao;
import com.ksyun.payment.dao.impl.ProductItemDao;
import com.ksyun.payment.dict.ProductItemDict;
import com.ksyun.payment.dict.ProductItemDict.PriceRange;
import com.ksyun.payment.dto.ProductItemDto;
import com.ksyun.payment.dto.ProductPriceRangeDto;
import com.ksyun.payment.service.IProductItemService;

/**
 * productItem 服务实现类
 * @author ZhangYanchun
 * @date   2013-08-27
 */
@Service
public class ProductItemService implements IProductItemService {

	@Resource
	private IProductItemDao<ProductItemDto> productItemDao;
	
	@Resource
	private IProductPriceRangeDao<ProductPriceRangeDto> productPriceRangeDao;
	
	private LoadingCache<String, Map<String, ProductItemDict>> cache;
	
	private static Logger logger = Logger.getLogger(ProductItemDao.class);
	
	/**
	 * 加入cache支撑， 此cache使用google guava基础包提供，内存的cache。
	 * guava说明请参看文档
	 */
	@PostConstruct
	public void init(){
		this.cache = CacheBuilder.newBuilder().maximumSize(1)
            .expireAfterAccess(2, TimeUnit.HOURS).build(new CacheLoader<String, Map<String, ProductItemDict>>() {
				@Override
				public Map<String, ProductItemDict> load(String key) throws Exception {
					return getAllProductItems();
				}
            });
	}
	/**
	 * 获取产品项目的列表
	 * @return
	 */
	private Map<String, ProductItemDict> getAllProductItems() {
		logger.info("get productItems from db ..................");
		Map<String, ProductItemDict> maps = new HashMap<String, ProductItemDict>();
		List<ProductItemDto> pItem = productItemDao.findAll(null);
		for(ProductItemDto item : pItem) {
			String itemNo = item.getItemNo();
			ProductItemDict pi = new ProductItemDict();
			pi.setItemNo(itemNo);
			pi.setUnitCount(item.getUnitCount());
			maps.put(itemNo, pi);
		}
		
		List<ProductPriceRangeDto> pprs = productPriceRangeDao.findAll(null);
		for(ProductPriceRangeDto ppr : pprs) {
			String itemNo = ppr.getItemNo();
			
			PriceRange pr = new PriceRange();
			pr.setPrice(ppr.getPrice());
			pr.setRangeBegin(ppr.getRangeBegin());
			pr.setRangeEnd(ppr.getRangeEnd());
			
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
	public Map<String, ProductItemDict> getProductItems() {
		Map<String, ProductItemDict> maps = null;
		try {
			maps = cache.get("productItems");
		} catch (ExecutionException e) {
			logger.error("the productItem cache is error, this is very serious");
		}
		return maps;
	}

}
