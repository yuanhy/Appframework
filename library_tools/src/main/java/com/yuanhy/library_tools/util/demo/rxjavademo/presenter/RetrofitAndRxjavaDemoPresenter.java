package com.yuanhy.library_tools.util.demo.rxjavademo.presenter;

import android.content.Context;
import android.view.View;

import com.yuanhy.library_tools.util.YCallBack;
import com.yuanhy.library_tools.util.demo.rxjavademo.ListItemEnty;
import com.yuanhy.library_tools.util.demo.rxjavademo.PrBean;
import com.yuanhy.library_tools.util.demo.rxjavademo.RxjavaDemoApi;
import com.yuanhy.library_tools.util.demo.rxjavademo.SyncStudentInfoEnty2;
import com.yuanhy.library_tools.util.http.retrofit.RetrofitUtile;
import com.yuanhy.library_tools.util.presenter.BasePresenter;
import com.yuanhy.library_tools.util.rxjava.ObServerBean;
import com.yuanhy.library_tools.util.rxjava.RxJavaUtil;

import java.util.HashMap;
import java.util.Map;

public class RetrofitAndRxjavaDemoPresenter extends BasePresenter {
    RxjavaDemoApi rxjavaDemoApi;
    YCallBack yCallBack;
    public RetrofitAndRxjavaDemoPresenter( YCallBack yCallBack) {
        rxjavaDemoApi = RetrofitUtile.getInstance().create(RxjavaDemoApi.class);
        this.yCallBack = yCallBack;
    }

    public void chapters() {
         rxjavaDemoApi.banner().compose(RxJavaUtil.<PrBean>io_uiMain())
                .subscribe(new ObServerBean<PrBean>() {
                    @Override
                    public void onSuccees(PrBean prBean) {
                        if (weakReference.get() != null) {//避免泄露内存
                            yCallBack.onOk(prBean.toString());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });

    }

    public void getListItem() {
        rxjavaDemoApi.listItem(1, 294).compose(RxJavaUtil.<ListItemEnty>io_uiMain())
                .subscribe(new ObServerBean<ListItemEnty>() {

                    @Override
                    public void onSuccees(ListItemEnty listItemEnty) {
                        if (weakReference.get() != null) {//避免泄露内存
                            yCallBack.onOk(listItemEnty.toString());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (weakReference.get() != null) {//避免泄露内存
                            yCallBack.onError(e);
                        }
                    }
                });
    }

    public void user() {

        rxjavaDemoApi.user("123456", "7457845").
                compose(RxJavaUtil.<String>io_uiMain()).subscribe(new ObServerBean<String>() {
            @Override
            public void onSuccees(String o) {
                if (weakReference.get() != null) {//避免泄露内存
                    yCallBack.onOk(o.toString());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (weakReference.get() != null) {//避免泄露内存
                    yCallBack.onOk(e.toString());
                }
            }
        });
    }

    public void syncStudentInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("schoolId", "6");
        map.put("deviceCode", "androidCard");
        map.put("all", "true");
        map.put("ismatch", "true");
        rxjavaDemoApi.syncStudentInfo(map).compose(RxJavaUtil.<SyncStudentInfoEnty2>io_uiMain())
                .subscribe(new ObServerBean<SyncStudentInfoEnty2>() {
                    @Override
                    public void onSuccees(SyncStudentInfoEnty2 prBean) {
                        if (weakReference.get() != null) {//避免泄露内存
                            yCallBack.onOk(prBean.toString());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }


}
