package com.rjzd.commonlib.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2017/6/5.
 */

public class StockTimeUtils {
    //获取当天9:30
    public static long getnineMillis() {
        Calendar c1 = new GregorianCalendar();
        c1.set(Calendar.HOUR_OF_DAY, 9);
        c1.set(Calendar.MINUTE, 30);
     // Date nineDate=  c1.getTime();
       // DateUtils.formatDate(DateUtil.parser24NoSecond(nineDate), "HH:mm");
        return c1.getTimeInMillis()/1000;
    }
    //获取当天11:30
    public  static long getelevenMillis() {
        Calendar c1 = new GregorianCalendar();
        c1.set(Calendar.HOUR_OF_DAY, 11);
        c1.set(Calendar.MINUTE, 30);
        c1.set(Calendar.SECOND, 0);
        return c1.getTimeInMillis()/1000;
    }
    //获取当天13:00
    public static long getoneMillis() {
        Calendar c1 = new GregorianCalendar();
        c1.set(Calendar.HOUR_OF_DAY, 13);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        return c1.getTimeInMillis()/1000;
    }
    //获取当天8:45
    public static long getEightMillis() {
        Calendar c1 = new GregorianCalendar();
        c1.set(Calendar.HOUR_OF_DAY, 9);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        return c1.getTimeInMillis()/1000;
    }
    public static long  getThreeMillis(){
        Calendar c1 = new GregorianCalendar();
        c1.set(Calendar.HOUR_OF_DAY, 15);
        c1.set(Calendar.MINUTE, 0);

        c1.set(Calendar.SECOND, 0);
        return c1.getTimeInMillis()/1000;
    }
    public static Date getYesterdayTime() {
        Date dNow = new Date();   //当前时间
        Date dBefore;
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间
        return dBefore;
    }
}
