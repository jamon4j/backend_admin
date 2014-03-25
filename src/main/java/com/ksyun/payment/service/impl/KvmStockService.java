package com.ksyun.payment.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ksyun.payment.dao.IKvmStockDao;
import com.ksyun.payment.dto.KvmStockDto;
import com.ksyun.payment.service.IKvmStockService;

/**
 * @服务实现类
 * @author ZhangYanchun
 * @date 2013-12-05
 */
@Service
public class KvmStockService implements IKvmStockService {
	
	@Resource
	private IKvmStockDao<KvmStockDto> kvmStockDao;

	@Override
	public KvmStockDto getByType(String kvmType) {
		return kvmStockDao.findById(kvmType);
	}

	@Override
	public List<KvmStockDto> getAllStock() {
		return kvmStockDao.findAll(null);
	}

	@Override
	public void updateTotalAmount(String kvmType, int amount) {
		kvmStockDao.updateTotalAmount(kvmType, amount);
	}

}
