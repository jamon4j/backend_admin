package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 14-4-8
 * Time: 下午2:03
 * Func:
 */
public class CreateInstance extends BasePo{

    private InstancePo rds;
    private String user_id;
    private String order_id;
    private String email;
    private String valid_time;

    public InstancePo getRds() {
        return rds;
    }

    public void setRds(InstancePo rds) {
        this.rds = rds;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValid_time() {
        return valid_time;
    }

    public void setValid_time(String valid_time) {
        this.valid_time = valid_time;
    }
}
