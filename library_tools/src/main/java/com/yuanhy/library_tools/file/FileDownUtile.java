package com.yuanhy.library_tools.file;

import android.content.Context;

import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.http.ProgressListener;
import com.yuanhy.library_tools.http.okhttp3.AndroidOkHttp3;
import com.yuanhy.library_tools.util.SharedPreferencesUtil;
import com.yuanhy.library_tools.util.YCallBack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class FileDownUtile {
    /**
     * 断点下载的文件
     *
     * @param context          上下文
     * @param url              网络url
     * @param fileDownPath     文件的父目录
     * @param progressListener
     */
    public static void fileDownBreakpoint(Context context, String url, String fileDownPath, String fileName,
                                          ProgressListener progressListener) {
        AndroidOkHttp3 androidOkHttp3 = AndroidOkHttp3.getInstance(context);
        androidOkHttp3.initFileClient();
        androidOkHttp3.downFileBreakpoint(url, fileDownPath,
                fileName, progressListener);
    }

    public static void fileDown(Context context, String url, String fileDownPath, String fileName, ProgressListener progressListener) {

        AndroidOkHttp3 androidOkHttp3 = AndroidOkHttp3.getInstance(context);
        androidOkHttp3.initFileClient();
        androidOkHttp3.downFile(url, fileDownPath,
                fileName, progressListener);
    }

    /**
     * 断点下载的文件
     *
     * @param context          上下文
     * @param multithreadnubs  线程数量 （最大10条）
     * @param url              网络url
     * @param fileDownPath     文件的父目录
     * @param progressListener
     */
    public static void fileDownMultithreadBreakpoint(Context context, String url, String fileDownPath,
                                                     String fileName, int multithreadnubs, ProgressListener progressListener) {

        AndroidOkHttp3 androidOkHttp3 = AndroidOkHttp3.getInstance(context);
        androidOkHttp3.initFileClient();
        //获取文件的长度
        androidOkHttp3.getContentLength(url, new YCallBack() {
            @Override
            public void onError(Object o) {
                progressListener.onError("");
            }

            @Override
            public void onOk(Object o) {
                HashMap<String, Long> hashMap = new HashMap<>();
                long contentlength = (long) o;
                long length = (contentlength / multithreadnubs);
                if (contentlength > 0) {//获取到正确的文件长度
                    for (int i = 0; i < multithreadnubs; i++) {//线程分割
                        long start = length * i;
                        long end = length * i + length - 1;
                        if (i == multithreadnubs - 1) {//最后一条线程要特别处理
                            start = length * i;
                            end = contentlength - 1;
                        }
                        String dataKey = fileName + start;//多点下载保存本节点的key
                        long them = SharedPreferencesUtil.getSharedPreferencesUtil(context).getLong(dataKey);
                        hashMap.put(dataKey, them);
                        AppFramentUtil.logCatUtil.i("总长度：" + contentlength +
                                " 线程 ：" + i + "起始位置：" + start + "  结束位置：" + end);
                        FileDownThred fileDownThred = new FileDownThred(context, start, end, url, fileDownPath,
                                fileName, i, new ProgressListener() {

                            @Override
                            public void onProgress(long fileSizeBytes, long remainingBytes, String dataKey, String thredId, boolean done) {
                                long downFileSize = remainingBytes;
                                hashMap.put(dataKey, downFileSize);
                                long lenth = 0;
                                for (long value : hashMap.values()) {
                                    lenth = lenth + value;
                                }
                                int progress = (int) (lenth * 100 / contentlength);

                                AppFramentUtil.logCatUtil.i("线程数量：" + multithreadnubs +
                                        " 当前线程：" + thredId +
                                        "  需要下载：" + fileSizeBytes + "  已下载：" + remainingBytes +
                                        " 总大小：" + contentlength + " 已下载总：" + lenth + " 总进度：" + progress);
                                progressListener.onProgress(progress, contentlength);
                                if (lenth == contentlength) {
                                    progressListener.onProgress(progress, contentlength, true);
                                }

                            }

                            @Override
                            public void onProgress(Object o, Object o2) {

                            }

                        });
                        AppFramentUtil.tasks.postRunnable(fileDownThred);
                    }
                } else {
                    progressListener.onError("");
                }
            }
        });


    }

}
