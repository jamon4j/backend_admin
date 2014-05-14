package com.ksyun.vm.pojo.rds;


import com.ksyun.vm.pojo.BasePo;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 上午10:50
 * Func:
 */
public class Links extends BasePo {

    private String href;
    private String rel;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}
