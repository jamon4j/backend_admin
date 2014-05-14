package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 14-4-10
 * Time: 上午10:40
 * Func:
 */
public class RDSInstanceUpgradePo extends BasePo {
    private String user_id;
    private String email;
    private String rds_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRds_id() {
        return rds_id;
    }

    public void setRds_id(String rds_id) {
        this.rds_id = rds_id;
    }
}
