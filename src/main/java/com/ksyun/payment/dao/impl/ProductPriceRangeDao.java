package com.ksyun.payment.dao.impl;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IProductPriceRangeDao;

/**
 * productItem DAO实现类
 * @author ZhangYanchun
 * @date   2013-08-27
 */
@Repository
public class ProductPriceRangeDao<ProductPriceRangeDto> extends BaseDao<ProductPriceRangeDto, Long> implements IProductPriceRangeDao<ProductPriceRangeDto> {

}
