package com.yuanhy.library_tools.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.yuanhy.library_tools.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BasePresenter> extends BaseActicity2 implements View.OnClickListener {
    T basePresenter;
    private Unbinder mUnbinder;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        basePresenter = createPresenter();
        if (basePresenter != null)
            basePresenter.onBindView(this);

        mUnbinder = ButterKnife.bind(this);

    }

    public abstract T createPresenter();

    @Override
    protected void onDestroy() {

        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
            this.mUnbinder = null;
        }
        if (basePresenter != null) {
            basePresenter.onDestroy();
            this.basePresenter = null;
        }

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}
