package com.yuanhy.library_tools.util.demo.rxjavademo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 冷的发射器
 */
public class RxJavaText {
    public static void main(String[] strings) {
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                Observable.interval(10, TimeUnit.MILLISECONDS,
                        Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(emitter::onNext);//
            }

        });
 observable.subscribe(new io.reactivex.functions.Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("111:--->"+aLong);
            }
        });
 observable.subscribe(new io.reactivex.functions.Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("2222:--->"+aLong);
            }
        });
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        observable.subscribe(new io.reactivex.functions.Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("333:--->"+aLong);
            }
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
