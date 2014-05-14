package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 14-4-9
 * Time: 下午7:46 
 * Func:
 */
public class RDSInstanceRule extends BasePo{
    private String to_port;
    private String cidr;
    private String from_port;
    private String protocol;
    private String id;
    private String security_group_id;
    private String created;

    public String getSecurity_group_id() {
        return security_group_id;
    }

    public void setSecurity_group_id(String security_group_id) {
        this.security_group_id = security_group_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTo_port() {
        return to_port;
    }

    public void setTo_port(String to_port) {
        this.to_port = to_port;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public String getFrom_port() {
        return from_port;
    }

    public void setFrom_port(String from_port) {
        this.from_port = from_port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
