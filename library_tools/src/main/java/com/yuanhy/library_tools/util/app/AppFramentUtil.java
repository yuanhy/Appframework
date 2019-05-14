package com.yuanhy.library_tools.util.app;


import android.content.Context;

import com.yuanhy.library_tools.util.LogCatUtil;
import com.yuanhy.library_tools.util.task.ExecutorTasks;
import com.yuanhy.library_tools.util.task.Tasks;

/**
 * 工具頂級類
 * Created by yuanhy on 2018/11/8.
 */

public class AppFramentUtil {
    /**
     * 线程池
     */
    public static Tasks tasks = new ExecutorTasks();
public static LogCatUtil logCatUtil;

public static void  initAppFramentUtil(Context c){
    logCatUtil =LogCatUtil.getInstance(c.getApplicationContext());
}

}
