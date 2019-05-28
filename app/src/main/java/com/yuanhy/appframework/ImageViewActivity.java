package com.yuanhy.appframework;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.app.AppAcitivityUtile;
import com.yuanhy.library_tools.image.GlideUtil;
import com.yuanhy.library_tools.image.ImageUtil;
import com.yuanhy.library_tools.presenter.BasePresenter;

public class ImageViewActivity extends BaseActivity {
    ImageView imageView;
    TextView title_view;
    View tag_view;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //方式二：这句代码必须写在setContentView()方法的前面
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_view);
        //隐藏标题栏
//          actionBar = getSupportActionBar();
//        actionBar.hide();
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        title_view = findViewById(R.id.title_view);
        String path = "http://img4.imgtn.bdimg.com/it/u=1183057007,4270556535&fm=26&gp=0.jpg";
        Glide.with(this) // 不使用内存缓存
                .load(path).into(imageView);
        tag_view = findViewById(R.id.tag_view);
        setActivityTittle();
    }

    public void setActivityTittle() {
        int h = AppAcitivityUtile.getTitlebarHith(context);
        tag_view.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, h));// 设置布局);
        tag_view.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.imageView:
                if (title_view.getVisibility() == View.GONE) {
                    title_view.setVisibility(View.VISIBLE);
                    tag_view.setVisibility(View.VISIBLE);
                    setStatusBarColorWhite();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//显示状态栏
                } else {
                    title_view.setVisibility(View.GONE);
                    tag_view.setVisibility(View.GONE);
                    setStatusBarColorBlack();
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏


                }
                break;
        }
    }

    @Override
    public void setTransparent() {
        setTransparent(true);
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}
