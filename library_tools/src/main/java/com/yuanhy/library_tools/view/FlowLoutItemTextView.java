package com.yuanhy.library_tools.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class FlowLoutItemTextView extends Button {
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.v("onTouchEvent","Action:"+ event.getAction());
//		getParent().requestDisallowInterceptTouchEvent(true);
//		boolean bl=super.onTouchEvent(event);
		if (event.getAction()==MotionEvent.ACTION_MOVE){//当滑动的时候，将事件往下传低。让子空间去处理，此处不作拦截

			return false;
		}
		return super.onTouchEvent(event);
	}

	public FlowLoutItemTextView(Context context) {
		this(context, null);
	}

	public FlowLoutItemTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlowLoutItemTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	//onInterceptTouchEvent


}
