package com.ebb;

import com.ebb.util.LoggerUtils;
import org.junit.Test;

import java.util.Date;

public class ShowDataType {

    @Test
    public void testStringLength(){
        LoggerUtils.LOGGER.debug("hello's length is {};","hello".length());
        LoggerUtils.LOGGER.debug("中文's length is {};","中文".length());

        LoggerUtils.LOGGER.debug("hello's codePointCount is {};","hello".codePointCount(0,5));
        LoggerUtils.LOGGER.debug("中文's codePointCount is {};","中文".codePointCount(0,2));
    }

    @Test
    public void testOldDate(){
        // 题外话，System类中提供了两个获取时间的方法
        long currentTime = System.currentTimeMillis();    // 返回当前时间，精度到毫秒
        long currentNanoTime = System.nanoTime();         // 多了6位纳秒

        // 获取当前时间，底层调用的 new Date(System.currentTimeMillis())
        Date currentDate = new Date();
        // 通过Long类型数字设置指定时间，基于1970年1月1号00时00分00秒 (UTC)
        Date assignedDate = new Date(-1000);

        // toString方法，基于内部的Calendar实现，返回的是对应当前时区的字符串，格式：dow mon dd hh:mm:ss zzz yyyy
        // dow 星期几           mon  月份              dd  当月第几天(01-31)
        // hh  小时(00-23)      mm   分钟(00-59)       ss  秒(00-61)
        // zzz 时区zone         yyyy 4位数字的年份
        LoggerUtils.LOGGER.debug("Date.toString() : {}",currentDate);    //Thu Aug 23 11:54:29 CST 2018
        LoggerUtils.LOGGER.debug("Date.toString() : {}",assignedDate);   //Thu Jan 01 07:59:59 CST 1970
    }
}
