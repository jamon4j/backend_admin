package com.ksyun.payment.service.impl;

import java.text.DecimalFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.stereotype.Service;

import com.ksyun.payment.service.ICashSerialService;
import com.ksyun.tools.DateUtil;

@Service
public class CashSerialService implements ICashSerialService {
     
	@Resource
	private MySQLMaxValueIncrementer cashSerialIdGenarater;
	
	@Override
	public String incrSerialNum() {
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtil.getStrDateyyMM(new Date()));
		DecimalFormat df = new DecimalFormat("00000000");
		long incrNum = cashSerialIdGenarater.nextLongValue();
		sb.append(df.format(incrNum));
		return sb.toString();
	}

}
