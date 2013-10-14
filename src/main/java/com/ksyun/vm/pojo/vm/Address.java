package com.ksyun.vm.pojo.vm;

import com.alibaba.fastjson.annotation.JSONField;
import com.ksyun.vm.pojo.BasePo;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-10-11
 * Time: 上午11:58
 * Func:
 */
public class Address extends BasePo {
    @JSONField(name = "public")
    private List<IP> pubaddress;
    @JSONField(name = "private")
    private List<IP> priaddress;

    public List<IP> getPubaddress() {
        return pubaddress;
    }

    public void setPubaddress(List<IP> pubaddress) {
        this.pubaddress = pubaddress;
    }

    public List<IP> getPriaddress() {
        return priaddress;
    }

    public void setPriaddress(List<IP> priaddress) {
        this.priaddress = priaddress;
    }
}
