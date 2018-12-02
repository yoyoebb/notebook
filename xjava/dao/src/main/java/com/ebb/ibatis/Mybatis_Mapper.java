package com.ebb.ibatis;

import com.ebb.domain.Test;
import com.ebb.ibatis.mapper.DMLMapper;
import com.ebb.util.LoggerUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 *  1. get mapper from SqlSession
 *  2. execute sql using mapper
 */
public class Mybatis_Mapper {

    public void showDML(SqlSession session){
        DMLMapper mapper = session.getMapper(DMLMapper.class);

        Test test = new Test();
        test.setName("name1");
        int result = mapper.insert(test);
        LoggerUtils.LOGGER.debug("result: {}",result);

        test.setName("name2");
        result = mapper.insertReturnId(test);
        LoggerUtils.LOGGER.debug("result={};id={}",result,test.getId());

        Map<String,Object> map = new HashMap<>();
        map.put("id",test.getId());
        map.put("name","nameById");
        result = mapper.update(map);
        LoggerUtils.LOGGER.debug("result: {}",result);

        map.clear();
        map.put("name","nameByAll");
        result = mapper.update(map);
        LoggerUtils.LOGGER.debug("result: {}",result);

        map.clear();
        map.put("id",test.getId());
        result = mapper.delete(map);
        LoggerUtils.LOGGER.debug("result: {}",result);

        map.clear();
        result = mapper.delete(map);
        LoggerUtils.LOGGER.debug("result: {}",result);

        session.commit();
    }
}
