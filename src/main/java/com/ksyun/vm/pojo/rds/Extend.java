package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 14-4-8
 * Time: 下午2:08
 * Func:
 */
public class Extend extends BasePo {

    private String autobackup_at;
    private String duration;
    private String expire_after;
    private String admin_user;
    private String admin_password;
    private String port;

    public String getAutobackup_at() {
        return autobackup_at;
    }

    public void setAutobackup_at(String autobackup_at) {
        this.autobackup_at = autobackup_at;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getExpire_after() {
        return expire_after;
    }

    public void setExpire_after(String expire_after) {
        this.expire_after = expire_after;
    }

    public String getAdmin_user() {
        return admin_user;
    }

    public void setAdmin_user(String admin_user) {
        this.admin_user = admin_user;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
