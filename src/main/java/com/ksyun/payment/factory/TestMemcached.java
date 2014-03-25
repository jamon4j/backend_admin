package com.ksyun.payment.factory;

import org.junit.Test;

/**
 * 测试memcached的有效性
 * User: ChenJianxiong
 * Date: 13-6-18
 */
public class TestMemcached {
    public static final String cacheModule = "payment";
    public static SpyMemcachedFactory cache = new SpyMemcachedFactory(cacheModule);
    public static int TIMEOUT = 60 * 60 * 3; //3 hour
    @Test
    public void testMemc() {
        for(int i=0; i<100; i++) {
            String key = "testMemc" + i;
            String value = "value" + i;
            cache.set(key, TIMEOUT, value);
        }

        for(int i=0; i<100; i++) {
            String key = "testMemc" + i;
            String value = cache.getString(key);
            System.out.println(key + " : " + value);
        }

    }
}
