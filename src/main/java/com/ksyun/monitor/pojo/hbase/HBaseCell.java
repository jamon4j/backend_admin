package com.ksyun.monitor.pojo.hbase;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-11-8
 * Time: 上午10:47
 * Func:
 */
public class HBaseCell extends BasePo {

    private String rowkey;
    private String family;
    private String qulifer;
    private String value;
    private String timestamp;

    public String getRowkey() {
        return rowkey;
    }

    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getQulifer() {
        return qulifer;
    }

    public void setQulifer(String qulifer) {
        this.qulifer = qulifer;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
