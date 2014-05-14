package com.ksyun.vm.dao.rds;

import com.ksyun.vm.pojo.BasePo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * User: liuchuandong
 * Date: 14-4-1
 * Time: 上午10:32
 * Func:
 */
@Repository
public class RDSValidationDao<RDSValidationPo extends BasePo> extends BaseDao<RDSValidationPo,Long> {

    public RDSValidationPo getValidTime(String rdsId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rdsId", rdsId);
        RDSValidationPo result = sqlSession.selectOne(nameSpace + ".getValidTime",map);
        return result;
    }

    public RDSValidationPo getByRDSId(String rdsId) {
        Map<String, String> map = new HashMap<>();
        map.put("rdsId", rdsId);
        RDSValidationPo po= sqlSession.selectOne(nameSpace + ".findByRdsId",map);
        return po;
    }
}
