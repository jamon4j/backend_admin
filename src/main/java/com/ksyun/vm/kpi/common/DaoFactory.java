package com.ksyun.vm.kpi.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class DaoFactory {
    public static ApplicationContext ctx = null;
    public static long SysStartTime = System.currentTimeMillis();

    static {
        init();
    }

    /*

    public static DataSource getDB4() {
        return (DataSource) ctx.getBean("tcgDS");
    }
    */

    /**
     * 根据Spring bean配置文件获得命名对象的通用方法
     */
    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    public static synchronized void init() {
        //String[] locations = {"game-beans.xml","dao-beans.xml"};
        String[] locations = {"applicationContext.xml"};
        System.out.println("DaoFactory init() & loading ClassPathXmlApplicationContext @ " + SysStartTime);
        try {
            ctx = new ClassPathXmlApplicationContext(locations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
