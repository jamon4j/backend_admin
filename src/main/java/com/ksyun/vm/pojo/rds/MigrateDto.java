package com.ksyun.vm.pojo.rds;

/**
 * Created by ZHANGNAN4 on 2014-5-27.
 * Email: zn.share@gmail.com
 */
public class MigrateDto {
    private String ram;
    private String vcpus;
    private String disk;
    private String host;
    private String backup_id;

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getVcpus() {
        return vcpus;
    }

    public void setVcpus(String vcpus) {
        this.vcpus = vcpus;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBackup_id() {
        return backup_id;
    }

    public void setBackup_id(String backup_id) {
        this.backup_id = backup_id;
    }
}
