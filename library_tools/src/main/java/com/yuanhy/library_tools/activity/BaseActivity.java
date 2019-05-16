package com.yuanhy.library_tools.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yuanhy.library_tools.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BasePresenter> extends Activity {
    T basePresenter;
    private Unbinder mUnbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        basePresenter = createPresenter();
        basePresenter.onBindView(this);

        mUnbinder = ButterKnife.bind(this);

    }

    public abstract T createPresenter();

    @Override
    protected void onDestroy() {

        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if(basePresenter!=null){
            basePresenter.onDestroy();
        }
        this.  basePresenter = null;
        this.mUnbinder = null;
        super.onDestroy();
    }
}
