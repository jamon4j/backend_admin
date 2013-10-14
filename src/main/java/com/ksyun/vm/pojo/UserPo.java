package com.ksyun.vm.pojo;

/**
 * User: liuchuandong
 * Date: 13-9-5
 * Time: 下午5:31
 * Func:
 */
public class UserPo extends BasePo{
    private String user_id;
    private String token;
    private String tenant_id;
    private String user_name;
    private String tenant_name;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }
}
