package com.yuanhy.appframework;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.yuanhy.appframework.R;

import java.util.Calendar;
import java.util.Date;

public class MyViewActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_view);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

//		使用Calendar的add(int field, int amount)方法
		Calendar ca = null;//得到一个Calendar的实例
			ca = Calendar.getInstance();
			ca.setTime(new Date()); //设置时间为当前时间
			ca.add(Calendar.YEAR, -1); //年份减1
			Date lastMonth = ca.getTime(); //结果
		Log.v("time",lastMonth.toString());

	}

}
