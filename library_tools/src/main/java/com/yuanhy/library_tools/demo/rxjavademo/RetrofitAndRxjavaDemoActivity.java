package com.yuanhy.library_tools.demo.rxjavademo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.demo.rxjavademo.presenter.RetrofitAndRxjavaDemoPresenter;
import com.yuanhy.library_tools.util.YCallBack;

import butterknife.BindView;

public class RetrofitAndRxjavaDemoActivity extends BaseActivity<RetrofitAndRxjavaDemoPresenter> implements View.OnClickListener {
    TextView infotv;
    Button bt1, bt2, bt3, bt4, bt5, bt6;
    RetrofitAndRxjavaDemoPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_and_rxjava_demo);
        infotv = findViewById(R.id.infotv);
        findViewById(R.id.bt1).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
        findViewById(R.id.bt3).setOnClickListener(this);
        findViewById(R.id.bt4).setOnClickListener(this);
        findViewById(R.id.bt5).setOnClickListener(this);
        findViewById(R.id.bt6).setOnClickListener(this);
//        RxView.attaches()
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt1) {
            presenter.chapters();
        } else if (id == R.id.bt2) {
            presenter.getListItem();
        } else if (id == R.id.bt3) {
            presenter.user();
        } else if (id == R.id.bt4) {
        } else if (id == R.id.bt5) {
        }
    }
    @Override
    public RetrofitAndRxjavaDemoPresenter createPresenter() {
        return presenter = new RetrofitAndRxjavaDemoPresenter(new YCallBack() {
            @Override
            public void onOk(Object o) {
                infotv.setText((String) o);
            }

            @Override
            public void requestSuccessful(Object o) {
            }

            @Override
            public void requestFail(Object o) {
            }
        });
    }
}
