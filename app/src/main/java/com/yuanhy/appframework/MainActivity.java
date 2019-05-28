package com.yuanhy.appframework;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.appframework.rxjavademo.RetrofitAndRxjavaDemoActivity;
import com.yuanhy.library_tools.presenter.BasePresenter;


public class MainActivity extends BaseActivity {
//    @BindView( R.id.tv2 )
//     TextView  tv2;
//    @BindView( R.id.tv1 )
//    TextView tv1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv1).setOnClickListener(this);
        findViewById(R.id.tv2).setOnClickListener(this);
        findViewById(R.id.tv3).setOnClickListener(this);
        findViewById(R.id.tv4).setOnClickListener(this);

    }

    /**
     * 不调用 白色字体(默认)
     * 调用
     * setTransparent(boolean transparent)
     */
    @Override
    public void setTransparent() {
        setTransparent(false);
    }

    @Override
    public void onClick(View view){
        int id=view.getId();
        Intent intent;
        switch (id){
            case R.id.tv1:
                  intent = new Intent(this,RetrofitAndRxjavaDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv2:
                   intent = new Intent(this,FileDownActivity.class);
                startActivity(intent);
                break;
            case R.id.tv3:
                intent = new Intent(this,AppUpdataActivity.class);
                startActivity(intent);
                break;
            case R.id.tv4:
                intent = new Intent(this,ImageViewActivity.class);
                startActivity(intent);
                break;

        }
    }



    @Override
    public BasePresenter createPresenter() {
        return null;
    }
    static {
        System.loadLibrary("native-lib");
    }
}
