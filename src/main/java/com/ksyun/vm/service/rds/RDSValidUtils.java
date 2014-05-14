package com.ksyun.vm.service.rds;


import com.ksyun.vm.dao.rds.RDSValidationDao;
import com.ksyun.vm.pojo.rds.RDSInstance;
import com.ksyun.vm.pojo.rds.RDSValidationPo;
import com.ksyun.vm.utils.TimeUtils;

import java.util.Date;
import java.util.List;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 上午11:14
 * Func:
 */
public class RDSValidUtils {

    public static List<RDSInstance> addValidTime(List<RDSInstance> list,RDSValidationDao lbsValidationDao) {
        if (list != null&&!list.isEmpty()) {
            for (RDSInstance t : list) {
                RDSValidationPo rdsValidationPo = (RDSValidationPo) lbsValidationDao.getValidTime(t.getId());
                if (rdsValidationPo != null) {
                    t.setValidTime(TimeUtils.makeYMDHMSStringFormat(rdsValidationPo.getValidTime()));
                }
                if (t.getValidTime() != null && TimeUtils.makeYMDHMSDateFormat(t.getValidTime()).compareTo(new Date()) < 0) {
                    t.setStatus("invalid");
                }
            }
            return list;
        }
        return null;
    }
    public static RDSInstance addValidTime(RDSInstance t,RDSValidationDao lbsValidationDao) {
        if (t != null) {
            RDSValidationPo ebsValidationPo = (RDSValidationPo) lbsValidationDao.getValidTime(t.getId());
            if (ebsValidationPo != null) {
                t.setValidTime(TimeUtils.makeYMDHMSStringFormat(ebsValidationPo.getValidTime()));
            }
            if (t.getValidTime() != null && TimeUtils.makeYMDHMSDateFormat(t.getValidTime()).compareTo(new Date()) < 0) {
                t.setStatus("invalid");
            }
            return t;
        }
        return null;
    }
}
