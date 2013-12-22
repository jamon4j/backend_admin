package com.ksyun.vm.pojo.zone;

import com.ksyun.vm.pojo.BasePo;
import com.ksyun.vm.pojo.stat.IpStat;
import com.ksyun.vm.pojo.stat.StatZone;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 13-10-10
 * Time: 上午11:10
 * Func:
 */
public class ZonePojo extends BasePo {

    private String name;
    private String availability_zone;
    private String deleted;
    private String created_at;
    private String updated_at;
    private List hosts;
    private String deleted_at;
    private String id;
    private Metadata metadata;
    private StatZone statZone;
    private IpStat ipStat;

    public IpStat getIpStat() {
        return ipStat;
    }

    public void setIpStat(IpStat ipStat) {
        this.ipStat = ipStat;
    }

    public StatZone getStatZone() {
        return statZone;
    }

    public void setStatZone(StatZone statZone) {
        this.statZone = statZone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailability_zone() {
        return availability_zone;
    }

    public void setAvailability_zone(String availability_zone) {
        this.availability_zone = availability_zone;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List getHosts() {
        return hosts;
    }

    public void setHosts(List hosts) {
        this.hosts = hosts;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
class Metadata{
    private String wan;
    private String lan;

    String getWan() {
        return wan;
    }

    void setWan(String wan) {
        this.wan = wan;
    }

    String getLan() {
        return lan;
    }

    void setLan(String lan) {
        this.lan = lan;
    }
}
