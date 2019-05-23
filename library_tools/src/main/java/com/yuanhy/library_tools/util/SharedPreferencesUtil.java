package com.yuanhy.library_tools.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private String appSavaData = "yuanhy.lib_tolls.appsavadata";
    private String AppUpData = "yuanhy.lib_tolls.appupdata";
    public static String SIMILAR_THRESHOLD = "SIMILAR_THRESHOLD";
    public static String DETECTFACEORIENTPRIORITY = "DETECTFACEORIENTPRIORITY";

    /**
     * 非强制更新保存dekey
     */
    public static String AppDataData = "APPDATADATA";
    public static SharedPreferencesUtil sharedPreferencesUtil;
    private static Context context;

    private SharedPreferencesUtil(Context context) {
        this.context = context.getApplicationContext();
    }

    public static SharedPreferencesUtil getSharedPreferencesUtil(Context context) {
        if (sharedPreferencesUtil == null) {
            sharedPreferencesUtil = new SharedPreferencesUtil(context);
        }
        return sharedPreferencesUtil;
    }

    public void putFloat(String key, float v) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(appSavaData, Context.MODE_PRIVATE);
        //获取editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        //存储键值对
        editor.putFloat(key, v);
        //提交
        editor.commit();//提交修改

    }

    public float getFloat(String key) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(appSavaData, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 0.8f);
    }
    public void putInt(String key, int v) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(appSavaData, Context.MODE_PRIVATE);
        //获取editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        //存储键值对
        editor.putInt(key, v);
        //提交
        editor.commit();//提交修改

    }

    public int getInt(String key) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(appSavaData, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 16);
    }



    public void putLong(String key, long v) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(appSavaData, Context.MODE_PRIVATE);
        //获取editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        //存储键值对
        editor.putLong(key, v);
        //提交
        editor.commit();//提交修改

    }

    public long getLong(String key) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(appSavaData, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    public void putString(String key, String v) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(appSavaData, Context.MODE_PRIVATE);
        //获取editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        //存储键值对
        editor.putString(key, v);
        //提交
        editor.commit();//提交修改

    }

    public String getString(String key) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(appSavaData, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "true");
    }


    public void putBoolean(String key, boolean v) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(appSavaData, Context.MODE_PRIVATE);
        //获取editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        //存储键值对
        editor.putBoolean(key, v);
        //提交
        editor.commit();//提交修改

    }

    public boolean getBoolean(String key) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(appSavaData, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public void putAppUpDataString(String v) {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppUpData, Context.MODE_PRIVATE);
        //获取editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        //存储键值对
        editor.putString(AppUpData, v);
        //提交
        editor.commit();//提交修改

    }
    public String getAppUpDataString() {
        //获取sharedPreferences对象
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppUpData, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AppUpData, "");
    }
}
