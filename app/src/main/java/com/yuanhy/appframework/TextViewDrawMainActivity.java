package com.yuanhy.appframework;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.presenter.BasePresenter;

public class TextViewDrawMainActivity extends BaseActivity {

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_view_draw_main);
		findViewById(R.id.bt1).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()){
			case R.id.bt1:



				break;
		}
	}

	@Override
	public void setTransparent() {

	}

	@Override
	public BasePresenter createPresenter() {
		return null;
	}
}
