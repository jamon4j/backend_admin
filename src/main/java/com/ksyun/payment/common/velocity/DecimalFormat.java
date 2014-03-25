package com.ksyun.payment.common.velocity;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

/**
 * 简单实现demo
 * 自定义velocity标签，可以使用类似  #decimal()的方式写入，需要在velocity.properties中配置，
 * userdirective=com.ksyun.payment.test.DecimalFormat多个用逗号隔开
 * @author ZhangYanchun
 * @date 2013-08-22
 */
public class DecimalFormat extends Directive {

	@Override
	public String getName() {
		return "decimal";//定义标签名称
	}

	@Override
	public int getType() {
		return LINE; //定义命令方式， LINE/BLOCK两种
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException,
			MethodInvocationException {
		//#set($name="") 可以通过context.get("name")获取
		//获取参数 #decimal($num)
		Object tmp = node.jjtGetChild(0).value(context);
		String rst_str = "0";
		if(tmp != null) {
			BigDecimal var = (BigDecimal)tmp;
			BigDecimal rst = var.setScale(2, RoundingMode.HALF_EVEN);
			rst_str = rst.toString();
			if(rst.compareTo(BigDecimal.ZERO) == 0) rst_str = "0";
		}
			
		writer.write(rst_str);
		return true;
	}

}
