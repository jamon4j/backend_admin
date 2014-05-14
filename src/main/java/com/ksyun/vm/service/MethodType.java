package com.ksyun.vm.service;

/**
 * User: liuchuandong
 * Date: 14-2-24
 * Time: 下午7:54
 * Func:
 */
public enum MethodType {

    CREATE("create","创建"),
    UPDATE("update","更新"),
    SELECT("select","查询"),
    DELETE("delete","删除");

    private MethodType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private String type;
    private String desc;

    public String getType() {
        return type;
    }
    public String getDesc() {
        return desc;
    }
}
