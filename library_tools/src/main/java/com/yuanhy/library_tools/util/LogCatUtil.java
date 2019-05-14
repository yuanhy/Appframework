package com.yuanhy.library_tools.util;

import android.content.Context;
import android.util.Log;

/**
 * Created by yuanhy on 2018/1/22.
 */

public class LogCatUtil {
    private static LogCatUtil logCatUtil;
    String TAG = "LogCatUtil";
    static boolean isShowLog = true;

    private LogCatUtil(Context context) {
        isShowLog = AppCommonTool.isApkInDebug(context);
    }

    public static LogCatUtil getInstance(Context context) {
        if (logCatUtil == null) {
            logCatUtil = new LogCatUtil(context.getApplicationContext());
        }
        return logCatUtil;
    }

    public void v(String tag, String logContent) {
        if (isShowLog) {
            Log.v(tag, logContent);
        }
    }

    public void d(String tag, String logContent) {
        if (isShowLog) {
            Log.d(tag, logContent);
        }
    }

    public void i(String tag, String logContent) {
        if (isShowLog) {
            Log.i(tag, logContent);
        }
    }
    public void e(String tag, String logContent) {
        if (isShowLog) {
            Log.e(tag, logContent);
        }
    }
}
