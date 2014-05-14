package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 14-4-8
 * Time: 下午4:44
 * Func:
 */
public class BackupConfig extends BasePo {
    private String duration;
    private String expire_after;
    private String autobackup_at;

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

    public String getAutobackup_at() {
        return autobackup_at;
    }

    public void setAutobackup_at(String autobackup_at) {
        this.autobackup_at = autobackup_at;
    }
}
