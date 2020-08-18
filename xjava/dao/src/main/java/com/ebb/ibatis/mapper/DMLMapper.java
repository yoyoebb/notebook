package com.ebb.ibatis.mapper;

import com.ebb.domain.Test;

import java.util.Map;

public interface DMLMapper {
    int insert(Test test);

    int insertReturnId(Test test);

    int update(Map<String, Object> map);

    int delete(Map<String, Object> map);
}
