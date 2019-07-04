package com.yuanhy.library_tools.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.util.LogCatUtil;

public class WebViewUtilActivity extends BaseWebView {
    private String TAG = "WebViewUtilActivity";
    String url = "";
    String title;
    BridgeWebView bdwebview;

    public static void openWebViewUtilActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewUtilActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    public static void openWebViewUtilActivityForResult(Activity context, String url, String title, int code) {
        Intent intent = new Intent(context, WebViewUtilActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivityForResult(intent, code);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_util);

        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");

//        setTitleName(title);
//        initBack();
//        setRight_tvVisibility(View.GONE);
        bdwebview = findViewById(R.id.bdwebview);
        initWebView(bdwebview, url);
        bdwebview.setWebChromeClient(new MyWebCromeClient());
        progressBar = findViewById(R.id.progressBarHorizontal);


        // 通过addJavascriptInterface()将Java对象映射到JS对象 //参数1：Javascript对象名 //参数2：Java对象名
        bdwebview.addJavascriptInterface(new AndroidtoJs(), "test");//AndroidtoJS类对象映射到js的test对象
        /*test.hello("js调用了android中的hello方法");*/
    }

    ProgressBar progressBar;

    private class MyWebCromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) { //加载完毕进度条消失
                progressBar.setVisibility(View.GONE);
            } else {
                //更新进度
                if (progressBar.getVisibility() != View.VISIBLE) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }


    // 继承自Object类
    public class AndroidtoJs extends Object {
        // 定义JS需要调用的方法 // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void hello(String msg) {
            LogCatUtil.getInstance(WebViewUtilActivity.this).d(TAG,"JS调用了Android的hello方法");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bdwebview != null)
            bdwebview.destroy();
    }


}

