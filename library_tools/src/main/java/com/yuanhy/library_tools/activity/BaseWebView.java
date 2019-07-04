package com.yuanhy.library_tools.activity;

import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.util.SharedPreferencesUtil;

import java.net.URL;

public class BaseWebView extends BaseActivity {

    BridgeWebView mBdwebview;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void setTransparent() {
    }

    public void initWebView(BridgeWebView mBdwebview, String url){





        mBdwebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mBdwebview.getSettings().setAllowFileAccess(true); // 允许访问文件


        mBdwebview.getSettings().setBuiltInZoomControls(true); // 设置显示缩放按钮
        mBdwebview.getSettings().setSupportZoom(true); // 支持缩放




        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            mBdwebview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            mBdwebview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            mBdwebview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            mBdwebview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            mBdwebview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else{
            mBdwebview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
        // 设置与Js交互的权限
        mBdwebview.getSettings().setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        mBdwebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mBdwebview.getSettings().setAppCacheEnabled(true);
        mBdwebview.getSettings().setDomStorageEnabled(true);
        mBdwebview.getSettings().supportMultipleWindows();
        mBdwebview.getSettings().setAllowContentAccess(true);
//        mBdwebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mBdwebview.getSettings().setSavePassword(true);
        mBdwebview.getSettings().setSaveFormData(true);
        mBdwebview.getSettings().setLoadsImagesAutomatically(true);

        mBdwebview.getSettings().setUseWideViewPort(true);
        mBdwebview.getSettings().setLoadWithOverviewMode(true);
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        mBdwebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        mBdwebview.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉
        mBdwebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.v("webview","url:"+url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

        });

        mBdwebview.loadUrl(url);

    }
    private void syncCookie(Context context, String url){
        try{
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            String oldCookie = cookieManager.getCookie(url);

            URL aURL = new URL(url);
            String cookie = SharedPreferencesUtil.getSharedPreferencesUtil(this).getString(  "HC360.SSOUser=");
            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(String.format("HC360.SSOUser=" + cookie));
            //webview在使用cookie前会前判断保存cookie的domain和当前要请求的domain是否相同，相同才会发送cookie
            sbCookie.append(String.format(";domain=%s",aURL.getHost())); //注意，是getHost()，不是getAuthority(),
            sbCookie.append(String.format(";path=%s","/"));

            String cookieValue = sbCookie.toString();
            cookieManager.setCookie(url, cookieValue);
            CookieSyncManager.getInstance().sync();

            String newCookie = cookieManager.getCookie(url);
        }catch(Exception e){
        }
    }


}
