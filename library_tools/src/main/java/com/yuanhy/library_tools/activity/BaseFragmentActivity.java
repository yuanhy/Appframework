package com.yuanhy.library_tools.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanhy.library_tools.util.StatusBarUtil;


public abstract class BaseFragmentActivity extends android.support.v4.app.FragmentActivity
        implements View.OnClickListener {
    private int color = Color.WHITE;
    public Context context;
   StatusBarUtil statusBarUtil;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        statusBarUtil = new StatusBarUtil();
        setStatusBar();
        context = this;
        ActivityManager.getInstance().addActivity(this);
    }

    public void setStatusBar() {
        statusBarUtil.setStatusBar(this, transparent);
    }

    ;


    public abstract void setTransparent();

    /**
     * true 黑色字体，白色背景
     * false 白色字体
     *
     * @param transparent
     */
    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
    }

    public void initView() {
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().remove(this);
        super.onDestroy();
    }

    boolean transparent = false;
}
