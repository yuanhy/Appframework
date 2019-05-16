package com.yuanhy.appframework;

import android.app.Application;

import com.yuanhy.library_tools.app.AppFramentUtil;

public class MyAppAcontent extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppFramentUtil.initAppFramentUtil(this);
    }
}
