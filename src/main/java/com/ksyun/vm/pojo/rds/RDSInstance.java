package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.BasePo;

import java.util.List;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 上午10:45
 * Func:
 */
public class RDSInstance extends BasePo{

    private String status;
    private String updated;
    private String group;
    private String name;
    private String created;
    private String server_status;
    private String service_status;
    private String vip;
    private Flavor flavor;
    private String task_status;
    private String type;
    private String id;
    private String validTime;
    private String admin_user;
    private List<String> ip;
    private String db_type;
    private String port;
    private String storage_used;
    private String security_group;
    private RDSInstanceSecGroup rDSInstanceSecGroup;
    private List<Backup> backups;
    private BackupConfig backup_config;

    public List<Backup> getBackups() {
        return backups;
    }

    public void setBackups(List<Backup> backups) {
        this.backups = backups;
    }

    public RDSInstanceSecGroup getrDSInstanceSecGroup() {
        return rDSInstanceSecGroup;
    }

    public void setrDSInstanceSecGroup(RDSInstanceSecGroup rDSInstanceSecGroup) {
        this.rDSInstanceSecGroup = rDSInstanceSecGroup;
    }


    public BackupConfig getBackup_config() {
        return backup_config;
    }

    public void setBackup_config(BackupConfig backup_config) {
        this.backup_config = backup_config;
    }

    public String getService_status() {
        return service_status;
    }

    public void setService_status(String service_status) {
        this.service_status = service_status;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getAdmin_user() {
        return admin_user;
    }

    public void setAdmin_user(String admin_user) {
        this.admin_user = admin_user;
    }

    public List<String> getIp() {
        return ip;
    }

    public void setIp(List<String> ip) {
        this.ip = ip;
    }

    public String getDb_type() {
        return db_type;
    }

    public void setDb_type(String db_type) {
        this.db_type = db_type;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getStorage_used() {
        return storage_used;
    }

    public void setStorage_used(String storage_used) {
        this.storage_used = storage_used;
    }

    public String getSecurity_group() {
        return security_group;
    }

    public void setSecurity_group(String security_group) {
        this.security_group = security_group;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public String getServer_status() {
        return server_status;
    }

    public void setServer_status(String server_status) {
        this.server_status = server_status;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
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
}
