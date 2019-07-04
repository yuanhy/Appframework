package com.yuanhy.library_tools.util;

/**
 * Created by Administrator on 2019/4/1.
 */

public  abstract  class YCallBack {
    public     void requestSuccessful(Object o){};
    public    void  requestFail(Object o){};

    public   void onOk(Object o){};
    public   void onError(Object o){};
    public void onProgress(Object o){};
}
