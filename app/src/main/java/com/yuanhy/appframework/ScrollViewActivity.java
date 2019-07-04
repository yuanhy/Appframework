package com.yuanhy.appframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;

/**
 *自定义ScrollView 解决滑动冲突
 * 外部拦截
 */
public class ScrollViewActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scokview);
	}
}
