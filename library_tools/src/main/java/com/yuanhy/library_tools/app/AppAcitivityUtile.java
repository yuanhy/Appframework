package com.yuanhy.library_tools.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;
import com.yuanhy.library_tools.http.okgo.OkGoUpdateHttpUtil;
import com.yuanhy.library_tools.util.AppCommonTool;
import com.yuanhy.library_tools.util.HttpResultUtil;
import com.yuanhy.library_tools.util.JsonOrEntyTools;
import com.yuanhy.library_tools.util.SharedPreferencesUtil;
import com.yuanhy.library_tools.util.StringUtil;

/**
 * Created by yuanhy on 2018/1/22.
 */

public class AppAcitivityUtile {
    /**
     * 获取系統通知欄高度
     *
     * @param context
     * @return
     */
    public static int getTitlebarHith(Context context) {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint("MissingPermission")
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }


}
