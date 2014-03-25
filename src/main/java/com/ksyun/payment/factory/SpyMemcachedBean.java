package com.ksyun.payment.factory;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.FactoryBean;

/**
 * Spring-memcached
 * User: chenjx
 * Date: 2012-3-5
 */

@SuppressWarnings("rawtypes")
public class SpyMemcachedBean implements FactoryBean {
    public String ipList = "";

    public Object getObject() throws Exception {
        MemcachedClient mcc = new MemcachedClient(AddrUtil.getAddresses(ipList.replaceAll(",", " ")));
        return mcc;
    }

    public void setIpList(String ipList) {
        this.ipList = ipList;
    }

    
    public Class getObjectType() {
        return MemcachedClient.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void shutdown() {
        System.out.println("com.ksyun.ks3.factory.SpyMemcachedBean.shutdown() " + System.currentTimeMillis());
    }
}
