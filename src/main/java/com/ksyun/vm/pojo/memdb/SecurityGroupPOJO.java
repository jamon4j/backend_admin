package com.ksyun.vm.pojo.memdb;

import com.ksyun.vm.pojo.BasePo;
import java.util.List;import java.util.List;
/**
 * Created by LiYang14 on 2014/9/16.
 */
public class SecurityGroupPOJO extends  BasePo {

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }



    public List<SeurityGroupRulePOJO> getRules() {
        return rules;
    }

    public void setRules(List<SeurityGroupRulePOJO> rules) {
        this.rules = rules;
    }

    private  String instance_id;
    private  String name;
    private  String created;
    private  String updated;
    private  int id;
    private  String description;

    private List<SeurityGroupRulePOJO>  rules;
}
