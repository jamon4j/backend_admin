package com.ksyun.payment.common;

/**
 * 自定义异常类
 * @author ZhangYanchun
 * @date   2013-08-19
 */
public class ParamValueCheckedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ParamValueCheckedException(){
		super();
	}
	
	public ParamValueCheckedException(String msg){
		super(msg);
	}

}
