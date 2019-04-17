package com.dianxiaoer.dutillibrary.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    public static final String pattern = "yyyy-MM-dd HH:mm:ss";
    public static final String pattern_ymd = "yyyy-MM-dd";
    public static final String pattern_ym = "yyyy-MM";
    public static final String pattern_ymdhm = "yyyy-MM-dd HH:mm";
    public static final String pattern_hms = "HH:mm:ss";
    public static final String pattern_hm = "HH:mm";
    public static final String pattern_h = "HH";

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long time) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
        Date date = new Date(time);
        String res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
        Date date = new Date(System.currentTimeMillis());
        String res = simpleDateFormat.format(date);

        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String pattern) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
        Date date = new Date(System.currentTimeMillis());
        String res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String pattern, long time) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
        Date date = new Date(time);
        String res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        String res = String.valueOf(ts);
        return res;
    }

    // https://blog.csdn.net/weixin_37539378/article/details/78889992
    public static String getTime() {
        Calendar cas = Calendar.getInstance();
        int year  = cas.get(Calendar.YEAR);
        int month = cas.get(Calendar.MONTH) +1;
        int date  = cas.get(Calendar.DATE);
        int hour  = cas.get(Calendar.HOUR_OF_DAY);
        int minu  = cas.get(Calendar.MINUTE);
        int sec   = cas.get(Calendar.SECOND);
        return year + "年" + month + "月" + date + "日" + hour + "时" + minu + "分" + sec + "秒";
    }
}
