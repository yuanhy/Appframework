package com.yuanhy.library_tools.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yuanhy.library_tools.app.AppAcitivityUtile;
import com.yuanhy.library_tools.util.PermissionUtil;
import com.yuanhy.library_tools.util.StatusBarUtil;

/**
 * Created by yuanhy on 2018/1/23.
 */

public abstract class BaseActicity2 extends Activity implements View.OnClickListener {
    private int color = Color.WHITE;
    public Context context;
    StatusBarUtil statusBarUtil;
    //加载view
    protected ProgressDialog mProgressDialog;
    public String TAG=this.getClass().getName();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        statusBarUtil = new StatusBarUtil();
        setTransparent();
        setStatusBar();
        context = this;
        ActivityManager.getInstance().addActivity(this);
    }

    /**
     * 设置成黑色状态栏和黑色的字体颜色
     */
    public void  setStatusBarColorBlack(){
        statusBarUtil.setStatusBarColorBlack(this);
    }
    /**
     * 设置成白色状态栏和黑色的字体颜色
     */
    public void  setStatusBarColorWhite(){
        statusBarUtil.setStatusBar(this, true);
    }
    public void setStatusBar() {
        statusBarUtil.setStatusBar(this, transparent);
    }

    /**
     * true 黑色字体，白色背景
     * false 白色字体
     *
     * @param transparent
     */
    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    /**
     * 不调用 白色字体(默认)
     * true 黑色字体，白色背景
     *  false 白色字体
     *
     * 具体使用 ：setTransparent(boolean transparent)
     */
    public abstract void setTransparent();

    boolean transparent = false;

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
    }
    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().remove(this);
        super.onDestroy();
    }

    public void callPhoneDialog( ){
        PermissionUtil.requestPermissions(this, 0x02, new String[]{Manifest.permission.CALL_PHONE},
                new PermissionUtil.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        //请求成功
                        AppAcitivityUtile.callPhone(context, "10086");
                    }

                    @Override
                    public void onPermissionDenied() {
                        PermissionUtil.showTipsDialog(context);
                    }
                });

    }
}
