package com.yuanhy.library_tools.util.http;

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
void  downFile(String url, String downFilePath, String fileName, ProgressListener progressListener);
}
