package com.yuanhy.library_tools.util.rxjava;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.yuanhy.library_tools.util.LogCatUtil;
import com.yuanhy.library_tools.util.app.AppFramentUtil;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * yuanhy
 */
public abstract class ObServerBean<T> implements Observer<T> {
    String TAG = "ObServerBean";
    Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(T t) {
        onSuccees(t);
    }

    @Override
    public void onError(Throwable e) {
        try {
            e.printStackTrace();
            AppFramentUtil.logCatUtil.e(TAG, e.getStackTrace().toString());
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);  //网络错误
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccees(T t);

    /**
     * 返回失败,外部类要继承实现的
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    /**
     * 请求结束  如果外部类要继承实现,就加上修饰符  abstract
     */
    public  void onRequestEnd() {
        //取消订阅
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
