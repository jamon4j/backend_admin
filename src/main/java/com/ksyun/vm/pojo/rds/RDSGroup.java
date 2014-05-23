package com.ksyun.vm.pojo.rds;

import java.util.LinkedHashSet;

/**
 * Created by ZHANGNAN4 on 2014-5-21.
 * Email: zn.share@gmail.com
 */
public class RDSGroup {
    private String group;
    private LinkedHashSet<RDSInstance> rdsInstances = new LinkedHashSet<>();


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public LinkedHashSet<RDSInstance> getRdsInstances() {
        return rdsInstances;
    }

    public void setRdsInstances(LinkedHashSet<RDSInstance> rdsInstances) {
        this.rdsInstances = rdsInstances;
    }
}
