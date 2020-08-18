package com.ebb.ibatis;

import com.ebb.domain.Test;
import com.ebb.util.LoggerUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *  显示原始的mybatis集成
 *  1. build SqlSessionFactory from config
 *  2. get SqlSession from factory
 *  3. execute sql with SqlSession
 */
public class Mybatis_Pure {
    public String getNameSpace(){return "com.ebb.t_orm.ibatis.mapper.DMLMapper.";}

    /** 1. build SqlSessionFactory from config
     */
    public SqlSessionFactory buildSqlSessionFactoryWithConfig(){
        String resouce="mybatis/mybatis_config.xml";
        SqlSessionFactory factory=null;
        try (InputStream inputStream = Resources.getResourceAsStream(resouce)){
            factory = new SqlSessionFactoryBuilder().build(inputStream);
            LoggerUtils.LOGGER.debug("SqlSessionFactory from config : {}", factory.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return factory;
    }

    /** 2. get SqlSession from factory
     */
    public void showGetSqlSession(){
        SqlSession session = null;
        SqlSessionFactory factory = buildSqlSessionFactoryWithConfig();
        if(Objects.nonNull(factory)){
            session = factory.openSession();
            LoggerUtils.LOGGER.debug("SqlSession : {}", session);
        }
        session.close();
    }

    /** 3. execute simple dml
     *     int insert(String statement, Object parameter);
     *     返回插入操作成功与否:0-插入失败，1-插入成功
     *     int update(String statement, Object parameter);
     *     int delete(String statement, Object parameter);
     *     返回操作更新/删除的记录行数: 0 or n
     */
    public void showDML(SqlSession session){
        Test test = new Test();
        test.setName("name1");
        int result = session.insert(getNameSpace()+"insert",test);
        LoggerUtils.LOGGER.debug("result: {}",result);

        test.setName("name2");
        result = session.insert(getNameSpace()+"insertReturnId",test);
        LoggerUtils.LOGGER.debug("result={};id={}",result,test.getId());

        Map<String,Object> map = new HashMap<>();
        map.put("id",test.getId());
        map.put("name","nameById");
        result = session.update(getNameSpace()+"update",test);
        LoggerUtils.LOGGER.debug("result: {}",result);

        map.clear();
        map.put("name","nameByAll");
        result = session.update(getNameSpace()+"update",map);
        LoggerUtils.LOGGER.debug("result: {}",result);

        map.clear();
        map.put("id",test.getId());
        result = session.delete(getNameSpace()+"delete",map);
        LoggerUtils.LOGGER.debug("result: {}",result);

        map.clear();
        result = session.delete(getNameSpace()+"delete",map);
        LoggerUtils.LOGGER.debug("result: {}",result);

        session.commit();
    }
}