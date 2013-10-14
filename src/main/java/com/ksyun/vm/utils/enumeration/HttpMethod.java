package com.ksyun.vm.utils.enumeration;

/**
 * User: liuchuandong
 * Date: 13-9-5
 * Time: 下午4:24
 * Func:
 */
public enum HttpMethod {

    PUT("PUT"),
    DELETE("DELETE"),
    POST("POST"),
    GET("GET");
    private HttpMethod(String action) {
        this.action = action;
    }

    private String action;

    public String value() {
        return action;
    }
}
