package com.wktx.www.subjects.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yyj on 2017/9/7.
 * 时间转换类
 * N:年、Y:月、R:日、S:时、F:分、 M:秒
 * 2:to 如geta2b，代表获取a类型转为b类型的结果
 */

public class DateUtil {
    /**
     * ------------------获取当前日期&时间格式-----------------------------
     */
    //获取当前日期（"06月14日"）
    public static String getCurrentDateYR() {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        return format.format(new Date());
    }
    //获取当前日期("2014-06-14"）
    public static String getCurrentDateNYR() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    // 获取当前日期时间("2014-06-14 16:09"）
    public static String getCurrentDateNYRSF() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }
    //获取当前日期时间（"2014-06-14-16-09-00"）
    public static String getCurrentDateNYRSFM1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date());
    }
    //获取当前日期时间（"2014-06-14 16:09:00"）
    public static String getCurrentDateNYRSFM2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        return format.format(new Date());
    }


    /**
     * --------------------输入所要转换的日期&时间格式 返回 时间戳-----------------------
     */
    //输入日期&时间如（"2014-06-14-16-09-00"），自定义日期&时间格式如（"yyyy-MM-dd-HH-mm-ss"） 返回时间戳如（1402733340）
    public static String getCustomType2Timestamp(String time, String type) {
        SimpleDateFormat sdr = new SimpleDateFormat(type, Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
    //输入日期&时间格式如（"2014年06月14日16时09分00秒"） 返回时间戳如（1402733340）
    public static String getNYRSFM2Timestamp1(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
    //输入日期&时间格式如（"2014-06-14-16-09-00"） 返回时间戳如（1402733340）
    public static String getNYRSFM2Timestamp2(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }


    /**
     * ------------------------输入所要转换的时间戳 返回 日期&时间格式------------------------------------
     */
    //输入时间戳如（1402733340），自定义日期&时间格式如（"yyyy-MM-dd-HH-mm-ss"） 返回对应格式的日期&时间结果如（"2014-06-14-16-09-00"）
    public static String getTimestamp2CustomType(String str, String type) {
        Date date = new Date(Long.valueOf(str)* 1000L);
        SimpleDateFormat format = new SimpleDateFormat(type);
        String time = format.format(date);
        return time;
    }
    //输入时间戳如（1402733340） 返回格式如（"16:09"）
    public static String getTimestamp2SF(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    //输入时间戳如（1402733340） 返回格式如（"2014/06/14"）
    public static String getTimestamp2NYR1(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy/MM/dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
//      int i = Integer.parseInt(time);
        String times = sdr.format(new Date(lcc * 1000L));
        return times;
    }
    //输入时间戳如（1402733340） 返回格式如（"2014-06-14"）
    public static String getTimestamp2NYR2(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    //输入时间戳如（1402733340） 返回格式如（"2014年06月14日"）
    //TODO timeNYR
    public static String getTimestamp2NYR3(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    //输入时间戳如（1402733340） 返回格式如（"20140614 16:09"）
    public static String getTimestamp2NYRSF1(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyyMMdd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    //输入时间戳如（1402733340） 返回格式如（"2014/06/14,16:09"）
    public static String getTimestamp2NYRSF2(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy/MM/dd,HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    //输入时间戳如（1402733340） 返回格式如（"2014-06-14 16:09"）
    public static String getTimestamp2NYRSF3(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    //输入时间戳如（1402733340） 返回格式如（"2014年06月14日 16:09"）
    public static String getTimestamp2NYRSF4(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    //输入时间戳如（1402733340） 返回格式如（"2014-06-14 16:09:00"）
    public static String getTimestamp2NYRSFM1(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    //输入时间戳如（1402733340） 返回格式如（"2014-06-14-16-09-00"）
    public String getTimestamp2NYRSFM2(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    //输入时间戳如（1402733340） 返回格式如（"2014年06月14日16时09分00秒"）
    public static String getTimestamp2NYRSFM3(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
    //输入时间戳如（1402733340） 返回格式如（"06月14日  周六 16:09"）
    public static String getTimestamp2YRSFW(long timeStamp) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日  #  HH:mm");
        return sdr.format(new Date(timeStamp)).replaceAll("#", getTimestamp2Week1(timeStamp));
    }


    /**
     * --------------------输入日期&时间格式如（2014年06月14日16时09分00秒）-----------------------------
     * --------------分割符（年月日时分秒）把时间分成时间数组["2014","06","14","16","09","00"]---------------
     */
    public String[] getNYRSFM2Array(String time) {
        String[] fenge = time.split("[年月日时分秒]");
        return fenge;
    }

    /**
     * ---------------------输入时间戳如（1402733340） 返回格式如（"2014年06月14日16时09分00秒"）------------------------
     * ----------------并使用分割符（年月日时分秒）把时间分成时间数组["2014","06","14","16","09","00"]--------------------
     */
    public static String[] getTimestamp2NYRSFM2Array(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        String[] fenge = times.split("[年月日时分秒]");
        return fenge;
    }


    /**
     * ------------------------输入所要转换的 时间戳 or 日期&时间格式  返回星期数-------------------------------------
     */
    //输入时间戳如（1402733340） 返回星期数
    private static String getTimestamp2Week1(long timeStamp) {
        int mydate = 0;
        String week = null;
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(timeStamp));
        mydate = cd.get(Calendar.DAY_OF_WEEK);
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "周日";
        } else if (mydate == 2) {
            week = "周一";
        } else if (mydate == 3) {
            week = "周二";
        } else if (mydate == 4) {
            week = "周三";
        } else if (mydate == 5) {
            week = "周四";
        } else if (mydate == 6) {
            week = "周五";
        } else if (mydate == 7) {
            week = "周六";
        }
        return week;
    }
    //输入时间戳如（1402733340） 转为日期&时间格式如（"2014-06-14-16-09-00"） 再返回星期数
    public static String getTimestamp2Week2(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;
    }
    //输入时间戳如（1402733340） 转为日期&时间格式如（"2014年06月14日16时09分00秒"） 再返回星期数
    public static String getTimestamp2Week3(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;
    }
    //输入日期&时间格式如（"2014-06-14-16-09-00"） 返回星期数
    public String getNYRSFM2Week1(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(time);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;
    }
    //输入日期&时间格式如（2014年06月14日16时09分00秒） 返回星期数
    public String getNYRSFM2Week2(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(time);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;
    }


    /**
     * ---------------输入时间戳如（1402733340），自定义日期&时间格式如（"yyyy-MM-dd HH：mm"）---------------------
     * -----------------返回对应格式的日期&时间结果，星期数如（例如：2014－06－14　16:09　星期六）------------------------
     * 获取日期和星期　
     */
    public static String getTsType2DateAndWeek(String time, String type) {
        return getTimestamp2CustomType(time + "000", type) + "  " + getTimestamp2Week2(time);
    }

    /**
     * -------------------输入两个日期如（2017-09-19，2017-10-19 ）返回（间隔天数）-----------------
     *-----------------------（开始时间与结束时间算在一起）----------------------
     */
    public static int getGapDays(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date_start = null;
        Date date_end = null;
        try {
            date_start = sdf.parse(startDate);
            date_end = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(date_start);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(date_end);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        return  (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24))+1;
    }

    /**
     * ------------------------输入时间格式如（14:55）返回（Calendar）-------------------------
     */
    public static Calendar getSF2Calendar(String timeSting) {
        SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date =sdf.parse(timeSting);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * ------------------------输入日期格式如（2017-09-19）返回（Calendar）--------------------------
     */
    public static Calendar getNYR2Calendar(String dateSting) {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date =sdf.parse(dateSting);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * ------------------------输入日期格式如（yyyy-MM-dd）返回（Calendar）--------------------------
     */
    public static Calendar getCustomType2Calendar(String dateSting, String type) {
        SimpleDateFormat sdf= new SimpleDateFormat(type);
        Date date = null;
        try {
            date =sdf.parse(dateSting);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * ------------------------比较两个日期的大小，日期格式为yyyy-MM-dd-----------------------------
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean compareDate2Bigger1(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() >= dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }

    /**
     * ------------------------比较两个日期的大小，日期格式为自定义，如：yyyy-MM-dd-----------------------------
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean compareDate2Bigger3(String str1, String str2,String type) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() >= dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }

    /**
     * --------------------------比较两个日期的大小，日期格式为yyyy-MM-dd----------------------------
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean compareDate2Bigger2(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }
}
