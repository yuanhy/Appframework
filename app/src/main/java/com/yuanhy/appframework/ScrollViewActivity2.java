package com.yuanhy.appframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 *自定义ScrollView 解决滑动冲突
 * 内部拦截
 */
public class ScrollViewActivity2 extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll_view2);
	}
}
