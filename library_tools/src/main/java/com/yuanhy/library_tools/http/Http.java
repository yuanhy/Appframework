package com.yuanhy.library_tools.http;

import com.yuanhy.library_tools.util.YCallBack;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by yuanhy on 2018/1/22.
 */

public interface Http {
    String post(String url, String json) throws IOException;

    byte[] postAsHeader(String url, String data, Map<String, String> addHeader) throws IOException;

    String postJSON(String url, String json) throws IOException;

    byte[] postJSON2(String url, String json) throws IOException;

    byte[] postJSON2SetUserAgent(String url, String json) throws IOException;

    String postFile(String url, File file) throws Exception;

    String postString(String url, String string) throws Exception;

    String get(String url) throws IOException;

    byte[] getAsBytes(String url) throws IOException;

    byte[] getHeaderAsBytes(String url, Map<String, String> _header) throws IOException;

    String postForm(String url, Map<String, String> addHeader, Map<String, String> forms) throws IOException;

    String postFormLogin(String url, Map<String, String> addHeader, Map<String, String> forms) throws IOException;


    String postFileParms(String url, File file, Map<String, Object> paramsMap) throws Exception;

    void postFileParmsProgress(String url, File file, Map<String, Object> paramsMap, YCallBack callBack) throws Exception;

    /**
     * 普通下载文件
     *
     * @param url
     * @param downFilePath
     * @param fileName
     * @param progressListener
     */
    void downFile(String url, String downFilePath, String fileName, ProgressListener progressListener);

    /**
     * 断点下载文件
     *
     * @param url
     * @param downFilePath
     * @param fileName
     * @param progressListener
     */
    void downFileBreakpoint(String url, String downFilePath, String fileName, ProgressListener progressListener);

    /**
     * 多线程断点下载文件
     * @param  multithreadNubm 线程数量
     * @param url 网络地址
     * @param downFilePath 本地文件父路径
     * @param fileName 文件名字
     * @param progressListener
     */
    void downFileMultithreadBreakpoint(long downloadLength,long contentLength,String url, String downFilePath, String fileName, ProgressListener progressListener);

    /**
     * 获取文件的长度
     * @param url
     * @return
     */
    long getContentLength (String url,YCallBack callBack);
}
