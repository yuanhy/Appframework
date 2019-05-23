package com.yuanhy.appframework;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.file.FileDownUtile;
import com.yuanhy.library_tools.http.ProgressListener;
import com.yuanhy.library_tools.image.GlideUtil;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.util.LogCatUtil;
import com.yuanhy.library_tools.util.SdCardUtil;
import com.yuanhy.library_tools.util.YCallBack;

import java.io.FileNotFoundException;

public class FileDownActivity extends BaseActivity {
    TextView textView;
ImageView image4;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_down);
        findViewById(R.id.bt1).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
        findViewById(R.id.bt3).setOnClickListener(this);
        findViewById(R.id.bt4).setOnClickListener(this);
        textView = findViewById(R.id.tv1);
        image4= findViewById(R.id.image4);
    }

    @Override
    public void setTransparent() {
        setTransparent(true);
    }

    String url = "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/sample-debug.apk";
    String fileDownPath = SdCardUtil.getExternalSdCardPath()+"/";

    @Override
    public void onClick(View v) {
        super.onClick(v);
switch (v.getId()){
    case R.id.bt1:
        FileDownUtile.fileDown(this, url, fileDownPath, "filedown.apk",new ProgressListener() {
            @Override
            public void onProgress(Object o, Object o2) {
                int a = (int) o;
                long b = (long) o2;
                runOnUiThread(new Runnable() {
                    @SuppressLint("NewApi")
                    @Override
                    public void run() {
                        textView.setText(a + "%");
                    }
                });
            }
        });
        break;
    case R.id.bt2:
        FileDownUtile.fileDownBreakpoint(this, url, fileDownPath,"fileDown2.apk" ,new ProgressListener() {
            @Override
            public void onProgress(Object o, Object o2) {
                int a = (int) o;
                long b = (long) o2;
                runOnUiThread(() -> textView.setText(a + "%"));
            }
        });
        break;
    case R.id.bt3:
        FileDownUtile.fileDownMultithreadBreakpoint(this, url, fileDownPath,"fileDown33.apk",
                10 ,new ProgressListener() {
            @Override
            public void onProgress(Object o, Object o2) {
                int a = (int) o;
                long b = (long) o2;
                runOnUiThread(() -> textView.setText(a + "%"));
            }
        });
        break;
    case R.id.bt4:
        GlideUtil.savaImage(this, "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3384779388,3115607562&fm=26&gp=0.jpg",      "yuanhyImage.jpg",fileDownPath, new YCallBack() {
                    @Override
                    public void requestSuccessful(Object o) {
                        super.requestSuccessful(o);
                        boolean isSuccess = (boolean) o;
                        if (isSuccess){
                            Toast.makeText(FileDownActivity.this, "成功--->/storage/emulated/0/yuanhyImage.jpg", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
        break;
}

    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
