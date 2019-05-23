package com.yuanhy.library_tools.http.okhttp3;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;


import com.yuanhy.library_tools.http.Http;
import com.yuanhy.library_tools.http.ProgressListener;
import com.yuanhy.library_tools.file.FileUtil;
import com.yuanhy.library_tools.util.LogCatUtil;
import com.yuanhy.library_tools.util.SharedPreferencesUtil;
import com.yuanhy.library_tools.util.StringUtil;
import com.yuanhy.library_tools.util.YCallBack;
import com.yuanhy.library_tools.app.AppFramentUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by yuanhy on 2018/1/22.
 */

public class AndroidOkHttp3 implements Http {


    private static String TAG = "AndroidOkHttp3";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");


    private static OkHttpClient client;
    private static OkHttpClient mFileClient;
    private static OkHttpClient msslClient;
    private static Context mContext;
    static OkHttpClient.Builder builder;

    public static AndroidOkHttp3 getInstance(Context context) {
        if (androidOkHttp3 == null) {
            synchronized (AndroidOkHttp3.class) {
                androidOkHttp3 = new AndroidOkHttp3(context);
            }
        }
        return androidOkHttp3;
    }

    public static AndroidOkHttp3 getInstance() {
        if (androidOkHttp3 == null) {
            synchronized (AndroidOkHttp3.class) {
                androidOkHttp3 = new AndroidOkHttp3();
            }
        }
        return androidOkHttp3;
    }

    private static AndroidOkHttp3 androidOkHttp3;

    public OkHttpClient initFileClient() {
        if (mFileClient == null) {
            mFileClient = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(60 * 2, TimeUnit.SECONDS).build();
        }
        return mFileClient;
    }

    public OkHttpClient getSSLOkhttp() {
        return msslClient;
    }

    private AndroidOkHttp3(Context ctx) {
        mContext = ctx;
        LogCatUtil.getInstance(ctx).i(TAG, "init AndroidOkHttp3()");
        // 指定缓存路径,缓存大小 50Mb
        Cache cache = new Cache(new File(ctx.getApplicationContext().getFilesDir(), "HttpCache"),
                1024 * 1024 * 50);
        mContext = ctx;
        //================
        builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                AppFramentUtil.logCatUtil.i(TAG, "retrofitBack = " + message);
                FileUtil.writeString("httpConnect.txt", message, true);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request builder = chain.request().newBuilder()
//                        .addHeader("token",你的ToKen)
                        .build();
                return chain.proceed(builder);
            }
        };
        builder.addInterceptor(loggingInterceptor);
        builder.addInterceptor(interceptor);
//==========================
        client = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(55, TimeUnit.SECONDS).build();
//        client.interceptors().add(new ReceivedCookiesInterceptor()); //你定义的cookie接收监听器

        mFileClient = new OkHttpClient.Builder()
//                .cache(cache)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(60 * 2, TimeUnit.SECONDS).build();


        msslClient = builder.cache(cache)

                .sslSocketFactory(createSSLSocketFactory(), mMyTrustManager)
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(60 * 2, TimeUnit.SECONDS).build();

    }

    private AndroidOkHttp3() {
        AppFramentUtil.logCatUtil.i(TAG, "init AndroidOkHttp3()");
        builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                AppFramentUtil.logCatUtil.i(TAG, "retrofitBack = " + message);
                FileUtil.writeString("httpConnect.txt", message, true);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request builder = chain.request().newBuilder()
//                        .addHeader("token",你的ToKen)
                        .build();
                return chain.proceed(builder);
            }
        };
        builder.addInterceptor(loggingInterceptor);
        builder.addInterceptor(interceptor);
//==========================
        msslClient = builder
                .sslSocketFactory(createSSLSocketFactory(), mMyTrustManager)
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
    }


    @Override
    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body).addHeader("charset", "UTF-8")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public byte[] postAsHeader(String url, String data, Map<String, String> addHeader) throws IOException {
        Log.v(TAG, " url: " + url + " data:" + data);
        RequestBody body = null;
        body = RequestBody.create(JSON, data);
        Request request = null;
        Request.Builder _builder = new Request.Builder()
                .url(url).post(body).addHeader("charset", "UTF-8");
        if (addHeader != null) {
            Set<String> _set = addHeader.keySet();
            for (String _key : _set) {
                _builder.addHeader(_key, addHeader.get(_key));
            }
        }
        request = _builder.build();
        Response response = client.newCall(request).execute();
        return response.body().bytes();
    }

    @Override
    public String postJSON(String url, String json) throws IOException {
        Log.v(TAG, " url: " + url + " json:" + json);
        String cookie = SharedPreferencesUtil.getSharedPreferencesUtil(mContext).getString("HC360.SSOUser=");

        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body).addHeader("charset", "UTF-8")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String jsonString = new String(response.body().bytes(), "UTF-8");
            Log.v(TAG, " jsonString: " + jsonString);
            return jsonString;
        } catch (NullPointerException e) {
            e.getStackTrace();
        } catch (Exception ex) {
        }
        return "";
    }

    @Override
    public byte[] postJSON2(String url, String json) throws IOException {
        AppFramentUtil.logCatUtil.i(TAG, " url: " + url + " json:" + json);
        try {

            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(url).post(body)
                    .addHeader("charset", "UTF-8")
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            List<String> headers = response.headers("Set-Cookie");
            if (headers != null) {
                for (String str : headers) {
                    if (str.startsWith("HC360.SSOUser=")) {
                        AppFramentUtil.logCatUtil.i(TAG, "set-cookie:" + str);
                        String[] strings = str.split("\\;");
                        AppFramentUtil.logCatUtil.i(TAG, "set-cookie:" + strings[0]);
                        SharedPreferencesUtil.getSharedPreferencesUtil(mContext).putString("HC360.SSOUser=", strings[0]);
                        break;
                    }
                }
            }
            return response.body().bytes();
        } catch (Exception e) {
            AppFramentUtil.logCatUtil.i(TAG, e.getMessage());
            return null;
        }

    }


    @Override
    public byte[] postJSON2SetUserAgent(String url, String json) throws IOException {
        AppFramentUtil.logCatUtil.i(TAG, " url: " + url + " json:" + json);
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body).addHeader("charset", "UTF-8")
                    .addHeader("Content-Type", "application/json")
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", getUserAgent(mContext))

                    .build();

            Response response = client.newCall(request).execute();
            return response.body().bytes();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取User-Agent并设置给Okhttp
     *
     * @param context
     * @return
     */
    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    @Override
    public String postFile(String url, File file) throws Exception {


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("subjectID", "123")
                .addFormDataPart("operType", "upload")
                .addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        Response response = mFileClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        String _reuslt = response.body().string();
        return _reuslt;
    }

    @Override
    public void postFileParmsProgress(String url, File file, Map<String, Object> paramsMap,
                                      final YCallBack callBack) throws Exception {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设置类型
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("uploadfile", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
        //追加参数
        for (String key : paramsMap.keySet()) {
            Object object = paramsMap.get(key);
            builder.addFormDataPart(key, object.toString());
        }
        //创建RequestBody
        RequestBody body = builder.build();
        //创建Request
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        /*Response response =*/
        mFileClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    callBack.onOk(response.body().string());
                } else {
                    callBack.onError("Unexpected code " + response);
                }
            }
        });
    }

    @Override
    public void downFile(String url, final String downFilePath, final String fileName, final ProgressListener callBack) {

        Request request = new Request.Builder().url(url).build();
        mFileClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                callBack.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean isOk = false;
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                File dir = new File(downFilePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File file = new File(dir, fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中更新进度条
                        callBack.onProgress(progress, total * 100);
                        AppFramentUtil.logCatUtil.i(TAG, "down file:" + progress);
                    }
                    fos.flush();
                    isOk = true;
                    AppFramentUtil.logCatUtil.i(TAG, file.getAbsolutePath());
                } catch (Exception e) {
                    AppFramentUtil.logCatUtil.i(TAG, e.getMessage());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                    if (isOk) {
                        // 下载完成

                        callBack.onOk(file.getAbsolutePath());
                    } else {
                        callBack.onError("");
                    }

                }
            }
        });
    }

    /**
     * 断点下载文件
     *
     * @param url
     * @param downFilePath
     * @param fileName         文件名字带后缀
     * @param progressListener
     */
    @Override
    public void downFileBreakpoint(final String url, final String downFilePath, final String fileName,
                                   final ProgressListener progressListener) {
        Request request = new Request.Builder().url(url).build();
        mFileClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                progressListener.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean isOk = false;
                InputStream inputStream = null;
                byte[] buf = new byte[2048];
                int len = 0;
                RandomAccessFile randomAccessFile = null;
                // 储存下载文件的目录
                File dir = new File(downFilePath);
                /**
                 * 本地文件已下载的大小
                 */
                long localFileSize = 0;
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                inputStream = response.body().byteStream();
                long total = response.body().contentLength();
                if (total <= 0) {
                    progressListener.onError("");
                    return;
                }
                File file = new File(dir, fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                randomAccessFile = new RandomAccessFile(file, "rw");
                localFileSize = randomAccessFile.length();
                try {
                    randomAccessFile.seek(localFileSize);//指定文件写入的位置 。例如上次下载了100个，这次送101开始

                    progressListener.onFileSize(total);
                    long sum = localFileSize;//将本地文件已经下载数量复制给临时变量吗，提供下载进度使用
                    inputStream.skip(localFileSize);//指定读取流读取的位置，例如上次数据保存了100，这次从101位置开始读取
                    while ((len = inputStream.read(buf)) != -1) {
                        randomAccessFile.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中更新进度条
                        AppFramentUtil.logCatUtil.i(TAG, "down file:" + progress);
                        progressListener.onProgress(progress, total);
                        progressListener.onProgress(total, sum, isOk);
                    }
                    isOk = true;
                    progressListener.onProgress(total, sum, isOk);
                    AppFramentUtil.logCatUtil.i(TAG, file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                    AppFramentUtil.logCatUtil.e(TAG, e.getStackTrace().toString());
                } finally {
                    try {
                        if (inputStream != null)
                            inputStream.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (randomAccessFile != null)
                            randomAccessFile.close();
                    } catch (IOException e) {
                    }
                    if (isOk) {
                        // 下载完成
                        progressListener.onOk(file.getAbsolutePath());
                    } else {
                        progressListener.onError("");
                    }

                }
            }
        });
    }

    /**
     * 断点多线程下载文件
     *
     * @param url
     * @param downFilePath
     * @param fileName         文件名字带后缀
     * @param progressListener
     */
    @Override
    public void downFileMultithreadBreakpoint(final long startIndex, final long contentLength,
                                              final String url, final String downFilePath, final String fileName,
                                              final ProgressListener progressListener) {

        Request request = new Request.Builder()
                .addHeader("RANGE", "bytes=" + startIndex + "-" + contentLength)
                .url(url).build();
        String threadId = String.valueOf(Thread.currentThread().getId());
        mFileClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                progressListener.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dataKey = fileName + startIndex;//多点下载保存本节点的key
                long downSizi = 0;//已经下载的长度
                boolean isOk = false;
                InputStream inputStream = null;
                byte[] buf = new byte[2048];
                int len = 0;
                RandomAccessFile randomAccessFile = null;
                // 储存下载文件的目录
                File dir = new File(downFilePath);

                if (!dir.exists()) {
                    dir.mkdirs();
                }
                inputStream = response.body().byteStream();
                long total = response.body().contentLength();
                if (total <= 0) {
                    progressListener.onError("");
                    return;
                }
                File file = new File(dir, fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                randomAccessFile = new RandomAccessFile(file, "rw");
                long seekStart = startIndex;
                /**
                 * 本地文件已下载的大小
                 */
                long them = SharedPreferencesUtil.getSharedPreferencesUtil(mContext).getLong(dataKey);
                AppFramentUtil.logCatUtil.e(TAG, "datakey:"+dataKey+"  downSiz:"+them);

                if (them ==contentLength-startIndex) {
                    progressListener.onProgress(total, them, dataKey, threadId ,isOk);
                    return;
                }else {
                    seekStart = seekStart + them;
                }

                try {
                    randomAccessFile.seek(seekStart);//指定文件写入的位置 。例如上次下载了100个，这次送101开始

                    long sum = them;//将本地文件已经下载数量复制给临时变量吗，提供下载进度使用
                    inputStream.skip(them);//指定读取流读取的位置，例如上次数据保存了100，这次从101位置开始读取
                    while ((len = inputStream.read(buf)) != -1) {
                        randomAccessFile.write(buf, 0, len);
                        sum += len;
                        downSizi = sum;
                        progressListener.onProgress(total, sum, isOk);
                        progressListener.onProgress(total, sum, dataKey,threadId ,isOk);
                    }
                    isOk = true;
                    progressListener.onProgress(total, sum, dataKey,threadId , isOk);
                    progressListener.onProgress(total, sum, isOk);
                    AppFramentUtil.logCatUtil.i(TAG, file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                    SharedPreferencesUtil.getSharedPreferencesUtil(mContext).putLong(dataKey, downSizi);
                    AppFramentUtil.logCatUtil.e(TAG, "datakey:"+dataKey+"  downSiz:"+downSizi+"/n/r"+e.getStackTrace().toString());
                } finally {
                    try {
                        if (inputStream != null)
                            inputStream.close();
                    } catch (IOException e) {
                    }
                    SharedPreferencesUtil.getSharedPreferencesUtil(mContext).putLong(dataKey, downSizi);
                    try {
                        if (randomAccessFile != null)
                            randomAccessFile.close();
                    } catch (IOException e) {
                    }
                    if (isOk) {
                        // 下载完成
                        progressListener.onOk(file.getAbsolutePath());
                    } else {
                        progressListener.onError("");
                    }
                }
            }
        });
    }

    @Override
    public long getContentLength(String url,YCallBack callBack) {
        Request request = new Request.Builder()
                .url(url).build();
        mFileClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack .onError(e.getStackTrace());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            long      lenth =response.body().contentLength();
                callBack .onOk(lenth );
            }
        });
        return 0;
    }

    @Override
    public String postFileParms(String url, File file, Map<String, Object> paramsMap) throws Exception {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设置类型
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("uploadfile", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));

        //追加参数
        if (paramsMap != null && paramsMap.size() > 0) {
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                builder.addFormDataPart(key, object.toString());
            }
        }


        //创建RequestBody
        RequestBody body = builder.build();

        //创建Request
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = mFileClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        String _reuslt = response.body().string();
        return _reuslt;
    }


    @Override
    public String postString(String url, String string) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, string))
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);


        String _result = response.body().string();
        Log.v(TAG, _result);
        return _result;
    }

    @Override
    public String get(String url) throws IOException {
        try {
            AppFramentUtil.logCatUtil.i(TAG, url);
//            String cookie = SharedPreferencesUtil.getSharedPreferencesUtil(mContext).getString("HC360.SSOUser=");
            Request request = new Request.Builder()
                    .get()
                    .url(url)
//                    .addHeader("Cookie", cookie)
                    .build();
            Response response = msslClient.newCall(request).execute();//enqueue
            String data = response.body().string();
            AppFramentUtil.logCatUtil.i(TAG, data);
            if (StringUtil.isServerExcepInfo(data)) {
                return "";
            }
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }

    }


    /**
     * 异步 get 请求
     *
     * @param url
     * @param callback
     */
    public void get(String url, Callback callback) {
        try {
//            String cookie = SharedPreferencesUtil.getSharedPreferencesUtil(mContext).getString("HC360.SSOUser=");
            Request request = new Request.Builder()
                    .get()
                    .url(url)
//                    .addHeader("Cookie", cookie)
                    .build();
            msslClient.newCall(request).enqueue(callback);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private MyTrustManager mMyTrustManager;

    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            mMyTrustManager = new MyTrustManager();
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{mMyTrustManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return ssfFactory;
    }

    /**
     * 实现X509TrustManager接口
     */
    public class MyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * 实现HostnameVerifier接口(信任所有的 https 证书。)
     */
    private class TrustAllHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    @Override
    public byte[] getAsBytes(String url) throws IOException {
        String cookie = SharedPreferencesUtil.getSharedPreferencesUtil(mContext).getString("HC360.SSOUser=");
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", cookie)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().bytes();
    }

    @Override
    public byte[] getHeaderAsBytes(String url, Map<String, String> _header) throws IOException {
        Request.Builder request = new Request.Builder().url(url);
        if (_header != null) {
            Set<String> _set = _header.keySet();
            for (String _key : _set) {
                request.addHeader(_key, _header.get(_key));
            }
        }
        Response response = client.newCall(request.build()).execute();
        return response.body().bytes();
    }

    @Override
    public String postForm(String url, Map<String, String> addHeader, Map<String, String> forms) throws IOException {
        AppFramentUtil.logCatUtil.i(TAG, " url: " + url);
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (forms != null) {
            Set keys = forms.keySet();
            Iterator iter = keys.iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                String value = forms.get(key);
                Log.v(TAG, " key: " + key + "   value:" + value);
                formBuilder.add(key, value);
            }
        }
        String cookie = SharedPreferencesUtil.getSharedPreferencesUtil(mContext).getString("HC360.SSOUser=");
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody).addHeader("charset", "UTF-8")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Cookie", cookie)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String jsonString = new String(response.body().bytes(), "UTF-8");
            AppFramentUtil.logCatUtil.i(TAG, " jsonString:" + jsonString);
            if (StringUtil.isServerExcepInfo(jsonString)) {
                return "";
            }

            return jsonString;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Override
    public String postFormLogin(String url, Map<String, String> addHeader, Map<String, String> forms) throws IOException {

        AppFramentUtil.logCatUtil.i(TAG, " url: " + url);
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (forms != null) {
            Set keys = forms.keySet();
            Iterator iter = keys.iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                String value = forms.get(key);
                AppFramentUtil.logCatUtil.i(TAG, " key: " + key + "   value:" + value);
                formBuilder.add(key, value);
            }
        }
        String cookie = SharedPreferencesUtil.getSharedPreferencesUtil(mContext).getString("HC360.SSOUser=");
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody).addHeader("charset", "UTF-8")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", cookie)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            List<String> headers = response.headers("Set-Cookie");
            if (headers != null) {
                String savecookie = "";
                String jsaonid = "";
                String ssou = "";
                for (String str : headers) {
                    if (str.startsWith("HC360.SSOUser=")) {
                        String[] strings = str.split("\\;");
                        ssou = strings[0];
                        AppFramentUtil.logCatUtil.i(TAG, "set-cookie:" + ssou);
                        continue;
                    }
                    if (str.startsWith("JSESSIONID=")) {//JSESSIONID=2294A37B3CAB5C757A120B3BA3D5CA74;path=/;HttpOnly
                        String[] strings = str.split("\\;");
                        jsaonid = strings[0] + ";";
                        AppFramentUtil.logCatUtil.i(TAG, "set-cookie:" + jsaonid);
                        continue;
                    }
                }
                savecookie = jsaonid + ssou;
                AppFramentUtil.logCatUtil.i(TAG, "savecookie:" + savecookie);
                SharedPreferencesUtil.getSharedPreferencesUtil(mContext).putString("HC360.SSOUser=", savecookie);
            }

            String jsonString = new String(response.body().bytes(), "UTF-8");
            AppFramentUtil.logCatUtil.i(TAG, " jsonString: " + jsonString);
            if (StringUtil.isServerExcepInfo(jsonString)) {
                return "";
            }
            return jsonString;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuffer params = new StringBuffer("?");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            params.append(entry.getKey());
            params.append("=");
            params.append(entry.getValue());
            params.append("&");
        }
        String str = params.toString();
        return str.substring(0, str.length() - 1);
    }


    //==========================================================================


}
