package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 14-4-8
 * Time: 下午3:09
 * Func:
 */
public class RDSDataMap extends BasePo {
    private String message;
    private String order_id;
    private String user_id;
    private String rds_id;
    private boolean is_ok;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRds_id() {
        return rds_id;
    }

    public void setRds_id(String rds_id) {
        this.rds_id = rds_id;
    }

    public boolean isIs_ok() {
        return is_ok;
    }

    public void setIs_ok(boolean is_ok) {
        this.is_ok = is_ok;
    }
}
