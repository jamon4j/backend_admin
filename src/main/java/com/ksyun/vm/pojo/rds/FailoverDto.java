package com.ksyun.vm.pojo.rds;

/**
 * Created by ZHANGNAN4 on 2014-5-27.
 * Email: zn.share@gmail.com
 */
public class FailoverDto {
    private String force_host;
    private String is_req_body;

    public String getForce_host() {
        return force_host;
    }

    public void setForce_host(String force_host) {
        this.force_host = force_host;
    }

    public String getIs_req_body() {
        return is_req_body;
    }

    public void setIs_req_body(String is_req_body) {
        this.is_req_body = is_req_body;
    }
}
