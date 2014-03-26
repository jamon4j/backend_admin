package com.ksyun.vm.utils;

import java.io.File;
import java.util.Locale;

import org.springframework.web.servlet.view.JstlView;

/**
 * 多视图共存时，解决JSP解析器无法使用
 * 
 * @author XueQi
 * 
 */
public class CustomJstlView extends JstlView {
	@Override
	public boolean checkResource(Locale locale) throws Exception {
		// TODO Auto-generated method stub
		File file = new File(this.getServletContext().getRealPath("/")
				+ getUrl());
		return file.exists();// 判断该jsp页面是否存在
	}
}
