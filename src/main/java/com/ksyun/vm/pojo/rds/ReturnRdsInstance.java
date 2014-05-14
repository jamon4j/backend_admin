package com.ksyun.vm.pojo.rds;


import com.ksyun.vm.pojo.BasePo;
import com.ksyun.vm.pojo.UserPo;
import com.ksyun.vm.pojo.user.UserPojo;
import com.ksyun.vm.pojo.vm.VmValidationPo;
import com.ksyun.vm.utils.TimeUtils;

import java.util.List;

public class ReturnRdsInstance extends BasePo {
    private RDSInstance rdsInstance;
    private List<RDSInstance> rdsInstances;
    private VmValidationPo vmValidationPo;

    public List<RDSInstance> getRdsInstances() {
        return rdsInstances;
    }

    public void setRdsInstances(List<RDSInstance> rdsInstances) {
        this.rdsInstances = rdsInstances;
    }

    public VmValidationPo getVmValidationPo() {
        return vmValidationPo;
    }

    public void setVmValidationPo(VmValidationPo vmValidationPo) {
        this.vmValidationPo = vmValidationPo;
    }

    public RDSInstance getRdsInstance() {
        return rdsInstance;
    }

    public void setRdsInstance(RDSInstance rdsInstance) {
        this.rdsInstance = rdsInstance;
    }
}
