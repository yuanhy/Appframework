package com.yuanhy.appframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yuanhy.library_tools.app.AppAcitivityUtile;
import com.yuanhy.library_tools.app.AppUpdataUtil;

public class AppUpdataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_updata);
        String mUpdateUrl = "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/json/json.txt";
        AppUpdataUtil.updateApk(this,mUpdateUrl,true,false);
    }
}
