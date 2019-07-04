package com.yuanhy.library_tools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2019/4/10.
 */

public class TimeUtil {
    /**
     * 获取日期
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static String getDate2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    /**
     * 获取日期
     *
     * @return
     */
    public static String getDate3() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    /**
     * 是否相差 numberDay 天
     *
     * @param day1      比较早的时间 2019-06-15 (标准) 或者 2019-06-15 18:22 在或者 2019-06-15 18:22:22
     * @param day2      比较晚的时间
     * @param numberDay 相差的天数
     * @return true >=numberDay
     */
    public static boolean isDifferNumberDays(String day1, String day2, int numberDay) {
        long longTime1;
        long longTime2;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        try {
            Date date1 =sdf.parse(day1);
            Date date2 =sdf.parse(day2);

            ca.setTime(date1);
            ca.add(Calendar.DAY_OF_MONTH, numberDay);
            Date lastMonth = ca.getTime(); //结果
            System.out.println("字符串 ：" + sdf.format(lastMonth) + "    :" + lastMonth.getTime());
            longTime1 = lastMonth.getTime();

            ca.setTime(date2);
            lastMonth = ca.getTime(); //结果
            lastMonth = sdf.parse(sdf.format(lastMonth));
            System.out.println("字符串2：" + sdf.format(lastMonth) + "    :" + lastMonth.getTime());
            longTime2 = lastMonth.getTime();
            if (longTime2 >= longTime1) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
