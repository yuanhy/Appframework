package com.yuanhy.appframework;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.presenter.BasePresenter;

public class FlowLayoutActivity extends BaseActivity {

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flow_layout);
		findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v(getClass().getName(),"OnClickListener");
			}
		});
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
