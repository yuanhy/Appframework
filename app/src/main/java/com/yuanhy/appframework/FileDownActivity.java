package com.yuanhy.appframework;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.file.FileDownUtile;
import com.yuanhy.library_tools.http.ProgressListener;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.util.LogCatUtil;
import com.yuanhy.library_tools.util.SdCardUtil;

public class FileDownActivity extends BaseActivity {
    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_down);
        findViewById(R.id.bt1).setOnClickListener(this);
        textView = findViewById(R.id.tv1);
    }

    @Override
    public void setTransparent() {
        setTransparent(true);
    }

    String url = "你自己的文件网络路径";
    String fileDownPath = SdCardUtil.getExternalSdCardPath();

    @Override
    public void onClick(View v) {
        super.onClick(v);

        FileDownUtile.fileDown(this, url, fileDownPath, new ProgressListener() {
            @Override
            public void onProgress(long totalBytes, long remainingBytes, boolean done) {

            }


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
