package com.yuanhy.library_tools.file;

import android.content.Context;

import com.yuanhy.library_tools.http.ProgressListener;
import com.yuanhy.library_tools.http.okhttp3.AndroidOkHttp3;

public class FileDownThred implements Runnable {
   private AndroidOkHttp3 androidOkHttp3;
    private long start;
    private   long end;
    private  String url;
    private   String fileDownPath;
    private   String fileName;
    private   ProgressListener progressListener;
    private   int index;
    protected FileDownThred(Context context, long start, long end, String url, String fileDownPath,
                         String fileName,int index, ProgressListener progressListener) {
        androidOkHttp3 = AndroidOkHttp3.getInstance(context);
        androidOkHttp3.initFileClient();
        this.start = start;
        this.end = end;
        this.url = url;
        this.fileDownPath = fileDownPath;
        this.fileName = fileName;
        this.progressListener = progressListener;
        this.index = index;
    }

    @Override
    public void run() {
        androidOkHttp3.downFileMultithreadBreakpoint(start, end, url, fileDownPath,
                fileName, progressListener);
    }
}
