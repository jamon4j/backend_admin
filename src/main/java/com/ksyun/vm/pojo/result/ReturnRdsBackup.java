package com.ksyun.vm.pojo.result;

import com.ksyun.vm.pojo.BasePo;
import com.ksyun.vm.utils.TimeUtils;

/**
 * User: liuchuandong
 * Date: 14-4-9
 * Time: 下午6:42
 * Func:
 */
public class ReturnRdsBackup extends BasePo {
    private String status;
    private String created;
    private String db_type;
    private String name;
    private String instance_id;
    private String type;
    private String id;
    private String size;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        try{
            return TimeUtils.makeYMDStringFormat(TimeUtils.parseISODateFormat(created));
        }catch (Exception e){
            return created;
        }
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDb_type() {
        return db_type;
    }

    public void setDb_type(String db_type) {
        this.db_type = db_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
