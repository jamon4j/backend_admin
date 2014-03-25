package com.ksyun.payment.dao.impl;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.IProductItemDao;

/**
 * productItem DAO实现类
 * @author ZhangYanchun
 * @date   2013-08-27
 */
@Repository
public class ProductItemDao<ProductItemDto> extends BaseDao<ProductItemDto, String> implements IProductItemDao<ProductItemDto> {

}
