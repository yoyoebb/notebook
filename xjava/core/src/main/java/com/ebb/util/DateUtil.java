package com.ebb.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class DateUtil {

    /**
     *  compute the difference between two date
     *  正数表示dateTwo 大于 dateOne
     *  负数表示dateTwo 早与 dateOne
     *  0   表示两者相等
     *
     *  注意：两个date间隔24小时以内不算相差1天
     */
    public static Map<TimeUnit,Long> computeDiff(Date dateOne, Date dateTwo){
        long diffInMillies = dateTwo.getTime() - dateOne.getTime();
        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));

        Collections.reverse(units);

        Map<TimeUnit,Long> result = new LinkedHashMap<>();
        long milliesRest = diffInMillies;
        for(TimeUnit unit : units){
            long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest -= diffInMilliesForUnit;
            result.put(unit,diff);
        }
        return result;
    }

    /**
     *  把Date的Time部分擦除(时、分、秒、毫秒置为0)
     *  1. 不能通过转成java.sql.Date来实现，因为time擦除是靠DataBase Driver实现的
     *  2. 通过Calendar来转换
     */
    public static Date toDBDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);    // clear 对HOUR_OF_DAY、HOUR、AM_PM不起作用
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        return calendar.getTime();
    }


    public static int getDaysOfCurrentMonth(){
        Calendar calendar = Calendar.getInstance();
        return getDaysOfMonth(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
    }

    /**
     *  获取指定年、月对应的当月天数
     *  year  : 0-9999
     *  month : 1-12
     */
    public static int getDaysOfMonth(int year, int month){
        if (year < 0 || year > 9999){
            throw new RuntimeException("illegal parameter! year must between 0 and 9999");
        }
        if (month <1 || month > 12){
            throw new RuntimeException("illegal parameter! month must between 1 and 12");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, 1);
        calendar.roll(Calendar.DAY_OF_MONTH,-1); //回滚一天，溢出后变成了当月最后一天
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
