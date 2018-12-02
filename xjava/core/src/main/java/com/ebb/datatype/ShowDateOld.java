package com.ebb.datatype;

import com.ebb.util.DateUtil;
import com.ebb.util.LoggerUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 *   Java SE 8之前的时间API
 *   java.util.Date                      精确到毫秒的UST时间
 *        java.sql.Date                  写入db，或者从db读回来时只包含年月日(由jdbc driver保证)
 *        java.sql.Time                  写入db，或者从db读回来时只包含年月日(由jdbc driver保证)
 *        java.sql.Timestamp             精确到毫秒，也支持纳秒(需要作为构造方法参数传进去)
 *   java.util.Calendar                  抽象类，通过Date获取日历坐标，进行日历操作
 *        java.util.GregorianCalendar    代表格列高利历(即通用的标准日历)
 *   java.util.TimeZone
 *   java.text.DateFormat                抽象类，对Date进行format
 *        java.text.SimpleDateFormat
 */
public class ShowDateOld {

    /**
     *  java.util.Date : The class Date represents a specific instant in time, with millisecond precision
     *  Date底层其实就存储了一个long类型的毫秒值，这个毫秒表示距离1970年1月1号00时00分00秒 (UTC) 的毫秒差
     *  long代表的毫秒数并不考虑时区，只有当把date格式化输出，或者toString()时，系统才会调用os对应时区，格式化为当前时区的时间
     *
     *  标准库的时间类设计的非常失败，被广为诟病。这也是为什么Joda Time在Java日期/时间需求中扮演了高质量替换的重要角色
     *   1. 所有的时间类都是mutable，没有线程安全，作为参数传递时也需要防御式编程，传递副本
     *   2. java.sql.Date唯一需要用到的地方就是PreparedStatement.setDate()，其他时候都可以被java.util.Date取代
     *   3. java.util.Date及其子类都是标准化时间，没有考虑国际化和时区
     *
     *   由于这些原因，java.util.Date及其子类的很多直接获取年/月/日/时/分/秒的方法都被废弃，
     *   需要使用Calendar来代替，可惜的是，Calendar同样是个奇葩
     */
    public static void showDate(){
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

    /**
     *  SimpleDateFormat : 业务系统很少用Date默认字符串来表示时间，可以用此类来按需格式化Date
     *  1. 任意组织 yyyy:MM:dd, hh:mm:ss:SSS的格式
     *  2. 按指定Zone输出
     *
     *  也可以将指定格式的时间字符串转为Date
     */
    public static void showDateFormat(){
        Date date = new Date();

        // default pattern : e.g '8/23/18 5:05 PM'
        SimpleDateFormat format = new SimpleDateFormat();
        LoggerUtils.LOGGER.debug("default pattern : {}", format.format(date));

        // special pattern : e.g '2018-08-23 17:05:00'
        // 具体参考API
        format.applyPattern("yyyy-MM-dd HH:mm:ss:SSS z");
        LoggerUtils.LOGGER.debug("'yyyy-MM-dd HH:mm:ss' pattern : {}", format.format(date));

        // 指定时区
        format.applyPattern("yyyy-MM-dd HH:mm:ss z");
        format.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        LoggerUtils.LOGGER.debug("with London timezone : {}", format.format(date));

        // 从字符串转为成Date
        format.applyPattern("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateFromString = format.parse("2018-01-01 00:00:00");
            LoggerUtils.LOGGER.debug("String to Date : {}", format.format(dateFromString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Calendar，定义了时间的日历操作
     *  1. 提供了工厂方法返回当前时刻的日历实例(需要提供Locale和TimeZone，不指定则使用系统默认)
     *  2. 指定/获取 对应的Date对象
     *  3. get/set/clear 获取指定单位的值:年/月/日/时/分/秒/毫秒， 还可以获取星期几等额外信息。 需要注意的地方：
     *     a MONTH的范围是0-11， 12小时制的HOUR范围也是0-11
     *     b DAY_OF_WEEK的范围是1-7，但从星期天算起
     *     c clear 对 HOUR_OF_DAY、HOUR、AM_PM不起作用，清除只能用set(HOUR_OF_DAY, 0)
     *  4. add/roll 根据指定单位移动指定值，区别在于，add会关联调整，roll只会影响单个属性
     */
    public static void showCalendar(){
        /** 日历单位的常量
         *  YEAR - 年       MONTH - 月(0-11)     DAY_OF_MONTH | DATE  - 日
         *  HOUR_OF_DAY - 小时(0-23)    MINUTE - 分钟    SECOND - 秒
         *
         *  WEEK_OF_YEAR - 日历周期的本年第几周
         *  WEEK_OF_MONTH - 日历周期的本月第几周      DAY_OF_WEEK_IN_MONTH - 每月1-7号固定为第1周，以此类推
         *
         *  DAY_OF_YEAR - 本年第几天                  DAY_OF_WEEK - 星期几(1-7，从周日算起)
         *
         *  AM_PM - 12小时制上午/下午                 HOUR - 12小时制的小时(0-11)
         */
        // 1. 返回日历实例，使用当前时刻、默认Locale和TimeZeon的GregorianCalendar
        Calendar calendar = Calendar.getInstance();
        LoggerUtils.LOGGER.debug("default calendar : {}", calendar.toString());

        // 2. 指定/获取特定时刻
        Date dateFromString = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateFromString = format.parse("2018-01-01 00:00:00");   //星期一
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(dateFromString);
        LoggerUtils.LOGGER.debug("calendar' time : {}", calendar.getTime());

        // 3.1 获取 年/月/日/时/分/秒/毫秒， 还可以获取星期几等额外信息
        LoggerUtils.LOGGER.debug("calendar year : {}", calendar.get(Calendar.YEAR));
        LoggerUtils.LOGGER.debug("calendar month : {}", calendar.get(Calendar.MONTH));         //0-11，对应现实月份要+1
        LoggerUtils.LOGGER.debug("calendar day : {}", calendar.get(Calendar.DAY_OF_MONTH));
        LoggerUtils.LOGGER.debug("calendar hour : {}", calendar.get(Calendar.HOUR_OF_DAY));    //HOUR是12小时制...
        LoggerUtils.LOGGER.debug("calendar minute : {}", calendar.get(Calendar.MINUTE));
        LoggerUtils.LOGGER.debug("calendar second : {}", calendar.get(Calendar.SECOND));
        // 通过日历获取星期几,
        LoggerUtils.LOGGER.debug("calendar day of week : {}", calendar.get(Calendar.DAY_OF_WEEK));  // 1-7，从星期天开始算

        // 3.2 设置 年/月/日/时/分/秒/毫秒
        // 可以单独指定一项，也可以同时设置年月日，或者年月日时分秒等
        calendar.set(Calendar.YEAR, 2000);
        LoggerUtils.LOGGER.debug("changed year : {}", calendar.get(Calendar.YEAR));
        calendar.set(2010,0,1);
        LoggerUtils.LOGGER.debug("changed year, month, day : {}, {}, {}",
                                  calendar.get(Calendar.YEAR),
                                  calendar.get(Calendar.MONTH),
                                  calendar.get(Calendar.DAY_OF_MONTH));

        // 4 add/roll
        LoggerUtils.LOGGER.debug("add/roll start---,current date : {}",format.format(calendar.getTime()));
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        LoggerUtils.LOGGER.debug("minus 24 hours, new date : {}",format.format(calendar.getTime()));
        calendar.roll(Calendar.HOUR_OF_DAY, 24);
        LoggerUtils.LOGGER.debug("roll 24 hours, new date : {}",format.format(calendar.getTime()));
    }

    /**
     *   判断两个date之间相差多少时间间隔，单位可以是(天、小时、分钟、秒、毫秒)
     *   原理都是计算两个date之间的毫秒差，/1000 ->秒  /60 ->分钟  /60 -> 小时  /24 ->天
     *
     *   计算间隔天数时，存在特殊需求：不是以24小时为分割，而是以自然日作为分割
     *   这时可以把时、分、秒、毫秒都置为零后再进行计算
     */
    public static void showDateComputeDiff(Date date1, Date date2){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LoggerUtils.LOGGER.debug("first date : {}", format.format(date1));
        LoggerUtils.LOGGER.debug("second date : {}", format.format(date2));

        // 利用工具类(对TimeUnit的封装)，进行计算
        Map<TimeUnit,Long> result = DateUtil.computeDiff(date1,date2);
        long diffDays = result.get(TimeUnit.DAYS);
        LoggerUtils.LOGGER.debug("different days between them : {}", diffDays);


        // 特殊情况，计算天数以自然日分割
        date1 = DateUtil.toDBDate(date1);
        date2 = DateUtil.toDBDate(date2);
        LoggerUtils.LOGGER.debug("first date : {}", format.format(date1));
        LoggerUtils.LOGGER.debug("second date : {}", format.format(date2));
        result = DateUtil.computeDiff(date1,date2);
        diffDays = result.get(TimeUnit.DAYS);
        LoggerUtils.LOGGER.debug("different days between them : {}", diffDays);
    }


    /**
     *  Date操作中还有一些常见的需求：
     *  1. 获取当月天数
     *  2. 获取当月最后一天， 判断指定Date是不是当月最后一天
     *
     */
    public static void showDateSpecial(Date date){
        // 当前时间所在月份有多少天
        Calendar calendar = Calendar.getInstance();
        LoggerUtils.LOGGER.debug("year-{},month-{} has total {} days",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                DateUtil.getDaysOfCurrentMonth());

        // 入参所在月份有多少天
        calendar.setTime(date);
        LoggerUtils.LOGGER.debug("year-{},month-{} has total {} days",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                DateUtil.getDaysOfMonth(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1));

    }
}
