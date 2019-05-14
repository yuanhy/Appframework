package com.yuanhy.library_tools.util;

import java.text.SimpleDateFormat;
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
}
