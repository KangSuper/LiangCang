package com.xiekang.king.liangcang.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by King on 2016/9/7.
 *
 */
public class DateUtils {
    public static String getCurrentTime(){
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(currentTimeMillis);
        String format = simpleDateFormat.format(date);
        return format;
    }
}
