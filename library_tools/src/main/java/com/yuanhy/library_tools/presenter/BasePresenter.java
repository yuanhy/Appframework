package com.yuanhy.library_tools.presenter;



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
