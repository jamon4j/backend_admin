package com.ksyun.payment.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ksyun.payment.dao.ISequenceDao;
import com.ksyun.payment.dto.SequenceDto;
import com.ksyun.payment.service.ISequenceService;

@Service
public class SequenceService implements ISequenceService {
	
	@Resource
	private ISequenceDao<SequenceDto> sequenceDao;

	@Override
	public long incrNum() {
		SequenceDto sequence = new SequenceDto();
		sequenceDao.save(sequence);
		long id = sequence.getId();
		sequenceDao.deleteById(id);
		return id;
	}

}
