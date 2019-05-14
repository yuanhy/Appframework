package com.yuanhy.library_tools.util.presenter;

import android.view.View;

import com.yuanhy.library_tools.util.activity.BaseActivity;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<T> {
    public WeakReference<T> weakReference;
   public void onBindView(T view){
       weakReference = new WeakReference<T>(view);
   }
    public void onDestroy(){
       if (weakReference!=null){
           weakReference.clear();
       }
    }
}
