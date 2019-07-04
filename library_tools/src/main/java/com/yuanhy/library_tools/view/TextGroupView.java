package com.yuanhy.library_tools.view;

import android.content.Context;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class TextGroupView extends ViewGroup {
	public TextGroupView(Context context) {
		super(context);
	}

	public TextGroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TextGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public TextGroupView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int childWidthMode = MeasureSpec.getMode(widthMeasureSpec);
		int childHeightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);


		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = getChildAt(i);
			LayoutParams layoutParams = view.getLayoutParams();
			int widthPading = view.getPaddingLeft() + view.getPaddingRight();
			int heightPading = view.getPaddingBottom() + view.getPaddingTop();
			int width = getChildMeasureSpec(widthMeasureSpec, widthPading, layoutParams.width);
			int height = getChildMeasureSpec(heightMeasureSpec, heightPading, layoutParams.height);
			view.measure(width, height);
		}
		int width = 0;
		int heigt = 0;

		switch (childWidthMode) {
			case MeasureSpec.EXACTLY:
				width = widthSize;
				break;
			case MeasureSpec.AT_MOST:
			case MeasureSpec.UNSPECIFIED:
				for (int i = 0; i < childCount; i++) {
					View view = getChildAt(i);
					int childWidth = view.getMeasuredWidth();
					width = Math.max(width, childWidth);
				}
				break;
			default:
				break;
		}
		switch (childHeightMode) {
			case MeasureSpec.EXACTLY:
				heigt = heightSize;
				break;
			case MeasureSpec.AT_MOST:
			case MeasureSpec.UNSPECIFIED:
				for (int i = 0; i < childCount; i++) {
					View view = getChildAt(i);
					int childHeigt = view.getMeasuredHeight();
					heigt = Math.max(heigt, childHeigt);
				}
				break;
			default:
				break;
		}
		setMeasuredDimension(width, heigt);
	}

	int standard = 100;

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int left = 0;
		int right = 0;
		int top = 0;
		int bottom = 0;

		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = getChildAt(i);
			left = standard * i ;
			right = left+view.getMeasuredWidth();
			bottom=top+view.getMeasuredHeight();
			view.layout(left, top, right, bottom);
			top=top+view.getMeasuredHeight();
		}
	}
}
