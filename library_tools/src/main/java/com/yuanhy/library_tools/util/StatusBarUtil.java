package com.yuanhy.library_tools.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarUtil {
    String titleCole="#1B8AD1";
    public void setTitleCole(String titleCole){
        this.titleCole=titleCole;
    };
    /**
     * true 黑色字体，白色背景
     * false 白色字体
     *
     * @param transparent
     */
    public void setStatusBar(Activity activity, boolean transparent) {
        int type = RomUtil.getLightStatusBarAvailableRomType();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            if (RomUtil.FLYME == type) {
                if (transparent) {
                    StatusbarColorUtils.setStatusBarDarkIcon(activity, true);  //参数 false 白色 true 黑色
                    activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                } else {
                    StatusbarColorUtils.setStatusBarDarkIcon(activity, false);  //参数 false 白色 true 黑色
                    activity.getWindow().setStatusBarColor(Color.parseColor(titleCole));
                }
            } else {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改

            boolean isxiaomi = OsUtls.isMIUI();
            if (RomUtil.MIUI == type || isxiaomi) {
                MIUISetStatusBarLightMode(activity, transparent);
            } else if (RomUtil.FLYME == type) {
                if (transparent) {
                    StatusbarColorUtils.setStatusBarDarkIcon(activity, true);  //参数 false 白色 true 黑色
                    activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                } else {
                    StatusbarColorUtils.setStatusBarDarkIcon(activity, false);  //参数 false 白色 true 黑色
                    activity.getWindow().setStatusBarColor(Color.parseColor(titleCole));
                }
            } else {
                if (transparent) { //黑色字体
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                } else {//白色字体
                    activity.getWindow().setStatusBarColor(Color.parseColor(titleCole));
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_VISIBLE);
                }
            }
        }
    }
    /**
     * true 黑色字体，白色背景
     * false 白色字体
     *
     * @param transparent
     */
    /**
     *黑色状态栏和黑色背景
     * @param activity
     */
    public void setStatusBarColorBlack(Activity activity) {
        String  typefaceColor = "#000000";
        int type = RomUtil.getLightStatusBarAvailableRomType();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            if (RomUtil.FLYME == type) {

                StatusbarColorUtils.setStatusBarDarkIcon(activity, false);  //参数 false 白色 true 黑色
                activity.getWindow().setStatusBarColor(Color.parseColor(typefaceColor));
            } else {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().setStatusBarColor(Color.parseColor(typefaceColor));
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改

            boolean isxiaomi = OsUtls.isMIUI();
            if (RomUtil.MIUI == type || isxiaomi) {
                MIUISetStatusBarLightMode(activity, typefaceColor);
            } else if (RomUtil.FLYME == type) {
                StatusbarColorUtils.setStatusBarDarkIcon(activity, true);  //参数 false 白色 true 黑色
                activity.getWindow().setStatusBarColor(Color.parseColor(typefaceColor));
            } else {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    activity.getWindow().setStatusBarColor(Color.parseColor(typefaceColor));
            }
        }
    }

    /**
     * 小米
     *
     * @param activity
     * @return
     */
    private boolean MIUISetStatusBarLightMode(Activity activity, boolean transparent) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (transparent) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
                    }
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && OsUtls.isMIUI()) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (transparent) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
                    } else {
                        activity.getWindow().setStatusBarColor(Color.parseColor(titleCole));
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }
    /**
     * 小米
     *
     * @param activity
     * @return
     */
    private boolean MIUISetStatusBarLightMode(Activity activity, String transparentColor) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.getWindow().setNavigationBarColor(Color.parseColor(transparentColor));
                    }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && OsUtls.isMIUI()) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        activity.getWindow().setNavigationBarColor(Color.parseColor(transparentColor));
                }
            } catch (Exception e) {

            }
        }
        return result;
    }
}
