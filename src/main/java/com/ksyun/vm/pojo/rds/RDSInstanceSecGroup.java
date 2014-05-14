package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 14-4-9
 * Time: 下午7:48
 * Func:
 */
public class RDSInstanceSecGroup extends BasePo {
    private String updated;
    private String name;
    private List<Links> links;
    private String created;
    private List<RDSInstanceRule> rules;
    private String instance_id;
    private String id;
    private String description;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Links> getLinks() {
        return links;
    }

    public void setLinks(List<Links> links) {
        this.links = links;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<RDSInstanceRule> getRules() {
        return rules;
    }

    public void setRules(List<RDSInstanceRule> rules) {
        this.rules = rules;
    }

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
