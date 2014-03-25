package test.ksyun;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ksyun.vm.dao.LoginDao;

/**
 * 多数据源测试类
 * 
 * @author XueQi
 * 
 */
public class DynamicDataSourceTest {
	private LoginDao loginDao;

	@Before
	public void init() {
		// TODO Auto-generated method stub
		// 这里获取spring总配置文件
		ApplicationContext aCtx = new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml",
						"classpath:mybatis-config.xml" });
		// 获取在spring注入的service名字
		LoginDao service = (LoginDao) aCtx.getBean("loginDao");
		this.loginDao = service;
	}

	@Test
	public void test1() {
		List list = loginDao.getUser("zhangzongli1@kingsoft.com",
				"kingsoft");
	}
}
