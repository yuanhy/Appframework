package com.yuanhy.library_tools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 外部拦截法处理
 */
public class ScrollViewAndScrollView extends ScrollView {
	public ScrollViewAndScrollView(Context context) {
		super(context);
	}

	public ScrollViewAndScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollViewAndScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.v("onTouchEvent","Action:"+ ev.getAction());
		if (ev.getAction()==MotionEvent.ACTION_MOVE){//当滑动的时候，将事件往下传低。让子空间去处理，此处不作拦截

			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}
}
