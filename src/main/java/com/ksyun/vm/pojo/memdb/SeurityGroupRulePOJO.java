package com.ksyun.vm.pojo.memdb;
import com.ksyun.vm.pojo.BasePo;
/**
 * Created by LiYang14 on 2014/9/16.
 */
public class SeurityGroupRulePOJO extends BasePo {

    public int getTo_port() {
        return to_port;
    }

    public void setTo_port(int to_port) {
        this.to_port = to_port;
    }

    public String getCidir() {
        return cidir;
    }

    public void setCidir(String cidir) {
        this.cidir = cidir;
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

    private  int to_port;
    private  String cidir;
    private   String from_port;
    private   String protocol;
    private  String id;


}
