package com.ksyun.vm.pojo.rds;

import com.ksyun.vm.pojo.vm.VmValidationPo;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ZHANGNAN4 on 2014-5-20.
 * Email: zn.share@gmail.com
 */
public class RDSGroupDTO {
    private int instanceSize;
    private LinkedHashSet<RDSGroup> rdsGroups = new LinkedHashSet<>();

    private RDSGroup containsGroup(String group) {
        for (RDSGroup rdsGroup : rdsGroups) {
            if (rdsGroup.getGroup().equalsIgnoreCase(group)) {
                return rdsGroup;
            }
        }
        return null;
    }

    public void addRDSGroup(RDSInstance rdsInstance) {
        if (rdsInstance != null) {
            RDSGroup rdsGroup = containsGroup(rdsInstance.getGroup());
            if (rdsGroup == null) {
                rdsGroup = new RDSGroup();
                rdsGroup.setGroup(rdsInstance.getGroup());
                rdsGroup.getRdsInstances().add(rdsInstance);
                rdsGroups.add(rdsGroup);
            } else {
                rdsGroup.getRdsInstances().add(rdsInstance);
            }
            instanceSize++;
        }
    }

    public int getInstanceSize() {
        return instanceSize;
    }

    public void setInstanceSize(int instanceSize) {
        this.instanceSize = instanceSize;
    }

    public LinkedHashSet<RDSGroup> getRdsGroups() {
        return rdsGroups;
    }

    public void setRdsGroups(LinkedHashSet<RDSGroup> rdsGroups) {
        this.rdsGroups = rdsGroups;
    }
}
