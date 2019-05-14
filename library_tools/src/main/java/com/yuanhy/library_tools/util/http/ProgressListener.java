package com.yuanhy.library_tools.util.http;

/**
 * Created by yuanhy on 2018/1/22.
 */

public interface ProgressListener {
    void onProgress(long totalBytes, long remainingBytes, boolean done);
    public void onOk(Object o);
    public void onError(Object o);
    /**
     * 进度
     *
     * @param o 进度0.00 - 0.50  - 1.00
     * @param o2    文件总大小 单位字节
     */
    public void onProgress(Object o, Object o2);
}
