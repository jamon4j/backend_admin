package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 14-4-8
 * Time: 下午3:08
 * Func:
 */
public class CallBackRDSPo extends BasePo {

    private String msgType = "control_kvm";
    private String bizType = "startRdsCallBack";
    private RDSDataMap dataMap;
    private String timestamp;
    private String retryCount;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public RDSDataMap getDataMap() {
        return dataMap;
    }

    public void setDataMap(RDSDataMap dataMap) {
        this.dataMap = dataMap;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(String retryCount) {
        this.retryCount = retryCount;
    }
}
