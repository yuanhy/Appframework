package com.yuanhy.library_tools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ScrollViewAndScrollView2 extends ScrollView {
	public ScrollViewAndScrollView2(Context context) {
		super(context);
	}

	public ScrollViewAndScrollView2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollViewAndScrollView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		Log.v("onTouchEvent","Action:"+ ev.getAction());
//		if (MotionEvent.ACTION_MOVE==ev.getAction()){
//			getParent().requestDisallowInterceptTouchEvent(false);
////			return true;
//		}
//		else 	if (MotionEvent.ACTION_DOWN==ev.getAction()){
//			getParent().requestDisallowInterceptTouchEvent(true);
////			return true;
//		}
//		return super.dispatchTouchEvent(ev);
//	}
	private int mLastX, mLastY;

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent event) {
//		int x = (int) event.getX();
//		int y = (int) event.getY();
//
//		switch (event.getAction()) {
//			case MotionEvent.ACTION_DOWN: {
//				getParent().requestDisallowInterceptTouchEvent(true);
//				break;
//			}
//			case MotionEvent.ACTION_MOVE: {
//				int deltaX = x - mLastX;
//				int deltaY = y - mLastY;
//				if (Math.abs(deltaX) > Math.abs(deltaY)) {
//					getParent().requestDisallowInterceptTouchEvent(false);
//				}
//				break;
//			}
//			case MotionEvent.ACTION_UP: {
//				break;
//
//			}
//			default:
//				break;
//		}
//
//		mLastX = x;
//		mLastY = y;
//		return super.dispatchTouchEvent(event);
//	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		boolean bl=super.onInterceptTouchEvent(ev);
		Log.v("onTouchEvent","Action:"+ ev.getAction()+bl);
		return bl;
	}
}
