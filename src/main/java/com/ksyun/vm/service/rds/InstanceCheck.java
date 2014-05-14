package com.ksyun.vm.service.rds;

import com.ksyun.vm.dao.rds.RDSValidationDao;
import com.ksyun.vm.pojo.rds.RDSValidationPo;
import com.ksyun.vm.service.CheckListener;
import com.ksyun.vm.service.InstanceUpdField;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 上午11:32
 * Func:
 */
@Service
public class InstanceCheck implements CheckListener {

    Logger log = LoggerFactory.getLogger(InstanceCheck.class);
    @Resource
    private RDSValidationDao rdsValidationDao;

    public boolean checkUpdate(Map<String, String> keyValues) {
        Iterator<Map.Entry<String, String>> it = keyValues.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.equals("id")) {
                if(!StringUtils.isBlank(value)){
                    RDSValidationPo po = (RDSValidationPo) rdsValidationDao.getValidTime(value);
                    if(po!=null){
                        if (po.getValidTime().getTime()<(Calendar.getInstance().getTime().getTime())) {
                            log.info("[ RDS--"+value+" 过期,不能操作]");
                            return true;
                        }
                    }
                }
                continue;
            }else if (key.equals(InstanceUpdField.RAM.getType())
                    || key.equals(InstanceUpdField.VCPUS.getType())
                    || key.equals(InstanceUpdField.AUTOBACKUP_AT.getType())
                    || key.equals(InstanceUpdField.PASSWORD.getType())
                    || key.equals(InstanceUpdField.DISK.getType())) {
                continue;
            }else{
                log.info("[ 非法字段 " + key + " ] 操作失败!");
                return true;
            }
        }
        return false;
    }
}
