package com.yuanhy.library_tools.file;

import android.content.Context;

import com.yuanhy.library_tools.http.ProgressListener;
import com.yuanhy.library_tools.http.okhttp3.AndroidOkHttp3;
import com.yuanhy.library_tools.util.YCallBack;

import okhttp3.OkHttpClient;

public class FileDownUtile {
    /**
     * 断点下载的文件
     * @param context  上下文
     * @param url 网络url
     * @param fileDownPath 文件的父目录
     * @param progressListener
     */
    public static void fileDownBreakpoint(Context context,String url, String fileDownPath, ProgressListener progressListener){
        AndroidOkHttp3 androidOkHttp3 = AndroidOkHttp3.getInstance(context);
        androidOkHttp3.initFileClient();
        androidOkHttp3.downFileBreakpoint(url,fileDownPath,
                "donw1.apk",progressListener);
    }

    public  static void fileDown(Context context,String url, String fileDownPath, ProgressListener progressListener){

        AndroidOkHttp3 androidOkHttp3 = AndroidOkHttp3.getInstance(context);
        androidOkHttp3.initFileClient();
        androidOkHttp3.downFile(url,fileDownPath,
                "donw2.apk",progressListener);
    }
}
