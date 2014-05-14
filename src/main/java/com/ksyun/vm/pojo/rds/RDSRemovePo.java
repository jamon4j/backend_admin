package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 14-4-9
 * Time: 下午5:23
 * Func:
 */
public class RDSRemovePo extends BasePo {
    private String type;
    private String rds_id;
    private String user_id;
    private String email;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRds_id() {
        return rds_id;
    }

    public void setRds_id(String rds_id) {
        this.rds_id = rds_id;
    }

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
}
