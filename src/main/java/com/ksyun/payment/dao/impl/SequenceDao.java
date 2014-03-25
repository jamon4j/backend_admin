package com.ksyun.payment.dao.impl;

import org.springframework.stereotype.Repository;

import com.ksyun.payment.dao.ISequenceDao;

@Repository
public class SequenceDao<SequenceDto> extends BaseDao<SequenceDto, Long> implements ISequenceDao<SequenceDto> {

}
