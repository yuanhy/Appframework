package com.yuanhy.library_tools.http.retrofit;

import android.content.Context;

import com.yuanhy.library_tools.file.FileUtil;
import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.http.AppUrl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * yuanhy
 * 2019.4.30
 */
public class RetrofitUtile {
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit mRetrofit;
    private Retrofit specialCaseRetrofit;

    private static   RetrofitUtile retrofitUtile =null;
    private static   RetrofitUtile specialCaseRetrofitUtile =null;
    private  Context context;
    String TAG="RetrofitUtile";

    private RetrofitUtile() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder;
         builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                AppFramentUtil.logCatUtil.i(TAG, "retrofitBack = " + message);
                FileUtil.writeString("httpConnect.txt",message,true);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptor=new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request builder = chain.request().newBuilder()
//                        .addHeader("token",你的ToKen)
                        .build();
                return chain.proceed(builder);
            }
        };
        builder .addInterceptor(loggingInterceptor);
        builder .addInterceptor(interceptor);
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //配置支持RJava2的 Observable
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器,会将返回的数据自动转换为 对应的 class
                .baseUrl(AppUrl.BASE_URL)
                .build();
    }
    private RetrofitUtile(String baseUrl) {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder;
        builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                AppFramentUtil.logCatUtil.i(TAG, "retrofitBack = " + message);
                FileUtil.writeString("httpConnect.txt",message,true);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptor=new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request builder = chain.request().newBuilder()
//                        .addHeader("token",你的ToKen)
                        .build();
                return chain.proceed(builder);
            }
        };
        builder .addInterceptor(loggingInterceptor);
        builder .addInterceptor(interceptor);
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
        // 创建Retrofit
        specialCaseRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //配置支持RJava2的 Observable
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器,会将返回的数据自动转换为 对应的 class
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return
     */
    public static RetrofitUtile getInstance() {
        if (retrofitUtile==null){
            retrofitUtile = new RetrofitUtile();
        }
        return retrofitUtile;
    }

    public static RetrofitUtile getSpecialCaseInstance(String  baseUrl) {
        if (specialCaseRetrofitUtile==null){
            specialCaseRetrofitUtile = new RetrofitUtile(baseUrl);
        }
        return specialCaseRetrofitUtile;
    }

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>     使用方法:   mMovieService = RetrofitServiceManager.getInstance().create(MovieService.class);
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    public <T> T createSpecialCaseRetrofit(Class<T> service) {
        return specialCaseRetrofit.create(service);
    }
    public <T> T create(Class<T> service,boolean isSSL) {
        return mRetrofit.create(service);
    }
}
