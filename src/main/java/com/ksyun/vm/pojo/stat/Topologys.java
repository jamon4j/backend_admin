package com.ksyun.vm.pojo.stat;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-11-4
 * Time: 下午5:02
 * Func:
 */
public class Topologys extends BasePo {
    //cpu核信息
    private String cores;
    //每个核线程数
    private String threads;
    //
    private String sockets;

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public String getSockets() {
        return sockets;
    }

    public void setSockets(String sockets) {
        this.sockets = sockets;
    }
}
