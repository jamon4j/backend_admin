package com.ksyun.vm.exception;

import com.ksyun.vm.pojo.OpenStackResult;

/**
 * User: liuchuandong
 * Date: 13-9-6
 * Time: 下午6:06
 * Func: 定义openstack代码异常
 */
public class ErrorCodeException extends Exception {

    private OpenStackResult result;
    public ErrorCodeException(OpenStackResult result){
        this.result=result;
    }

    public OpenStackResult getResult() {
        return result;
    }

    public void setResult(OpenStackResult result) {
        this.result = result;
    }
}
