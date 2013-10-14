package com.ksyun.vm.pojo.security;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 13-10-11
 * Time: 下午4:07
 * Func:
 */
public class Rule extends BasePo {
    private String from_port;
    private String ip_protocol;
    private String to_port;
    private String parent_group_id;
    private IPRange ip_range;
    private String id;

    public String getFrom_port() {
        return from_port;
    }

    public void setFrom_port(String from_port) {
        this.from_port = from_port;
    }

    public String getIp_protocol() {
        return ip_protocol;
    }

    public void setIp_protocol(String ip_protocol) {
        this.ip_protocol = ip_protocol;
    }

    public String getTo_port() {
        return to_port;
    }

    public void setTo_port(String to_port) {
        this.to_port = to_port;
    }

    public String getParent_group_id() {
        return parent_group_id;
    }

    public void setParent_group_id(String parent_group_id) {
        this.parent_group_id = parent_group_id;
    }

    public IPRange getIp_range() {
        return ip_range;
    }

    public void setIp_range(IPRange ip_range) {
        this.ip_range = ip_range;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
