package com.ksyun.vm.service;

import java.util.Map;

/**
 * User: liuchuandong
 * Date: 14-3-4
 * Time: 上午11:29
 * Func:
 */
public interface CheckListener {

    public boolean checkUpdate(Map<String, String> keyValues);
}
