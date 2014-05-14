package com.ksyun.vm.pojo.rds;

/**
 * User: liuchuandong
 * Date: 14-4-8
 * Time: 下午1:51
 * Func:
 */
public class InstancePo {

    private String name;
    private String type;
    private Extend extend;
    private CreateFlavor flavor;
    private String service_type;
    private RestorePoint restorePoint;
    private String instance_id;
    private String is_req_body = "true";

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public RestorePoint getRestorePoint() {
        return restorePoint;
    }

    public void setRestorePoint(RestorePoint restorePoint) {
        this.restorePoint = restorePoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Extend getExtend() {
        return extend;
    }

    public void setExtend(Extend extend) {
        this.extend = extend;
    }

    public CreateFlavor getFlavor() {
        return flavor;
    }

    public void setFlavor(CreateFlavor flavor) {
        this.flavor = flavor;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getIs_req_body() {
        return is_req_body;
    }

    public void setIs_req_body(String is_req_body) {
        this.is_req_body = is_req_body;
    }
}
