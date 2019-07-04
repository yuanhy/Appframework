package com.yuanhy.library_tools.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义流式布局  不带回滚
 */
public class FlowLayoutView2 extends ViewGroup {
	/**
	 * 当前行的view数组
	 */
	List<View> lineList;
	/**
	 * 所有的所有行的数组
	 */
	List<List<View>> lists;
	List<Integer> heithList;
	int mathWidth = 0;//所有行中最大的宽度
	int linsWidth = 0;//当前行的宽度
	int linesHeith = 0;//F当前行的最大高度
	int mumLineHeith = 0;//总高度
	int sumLinesHeith = 0;//父总高度
	int flowLayoutHeith = 0;
	boolean isScroller;//是否可以滑动
	int distanceY = 0;
	OverScroller scroller;
	int mTouchSlop;//默认的移动距离 误差

	int mMinimumVelocity;
	int mMaximumVelocity;
	private VelocityTracker mVelocityTracker;//惯性
	public FlowLayoutView2(Context context) {
		this(context, null);
	}

	public FlowLayoutView2(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlowLayoutView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		final ViewConfiguration configuration = ViewConfiguration.get(context);
		mTouchSlop = configuration.getScaledTouchSlop();
		mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();


		scroller = new OverScroller(context);
		intVelocityTracker();
	}

	private void 	intVelocityTracker(){
	if (mVelocityTracker == null) {
		mVelocityTracker = VelocityTracker.obtain();
	} else {
		mVelocityTracker.clear();
	}
}
	private void recycleVelocityTracker() {
		if (mVelocityTracker != null) {
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}
	void init() {
		lineList = new ArrayList<>();
		if (lists == null)
			lists = new ArrayList<>();
		if (heithList == null)
			heithList = new ArrayList<>();
	}

	float startX, startY;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return  super.onInterceptTouchEvent(ev);
//		boolean isIntercept = false;//是否拦截手势
//		float themX = ev.getX();
//		float themY = ev.getY();
//		switch (ev.getAction()) {
//			case MotionEvent.ACTION_DOWN:
//				Log.v(getClass().getName(),"MotionEvent.ACTION_DOWN:");
//				startX = themX;
//				startY = themY;
//				isIntercept = false;
//				break;
//			case MotionEvent.ACTION_MOVE:
//				isIntercept = true;
////				float moveXThem = themX - startX;
////				float moveYThem = themY - startY;
////				Log.v(getClass().getName(),"MotionEvent.ACTION_MOVE:");
////				if (Math.abs(moveXThem) < Math.abs(moveYThem) && mTouchSlop < Math.abs(moveYThem)) {
////					isIntercept = true;
////				} else {
////					isIntercept = false;
////				}
//				break;
//			case MotionEvent.ACTION_UP:
//				Log.v(getClass().getName(),"MotionEvent.ACTION_UP:");
//				isIntercept = false;
//				break;
//		}
//		startX = themX;
//		startY = themY;
//		Log.v(getClass().getName(),"isIntercept:"+isIntercept);
//		if (!isIntercept){
//			super.onInterceptTouchEvent(ev);
//		}
//		return isIntercept;
	}

	float mLastY = 0;//记录一次手势滑动的起点

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isScroller) {
			return super.onTouchEvent(event);
		}
		intVelocityTracker();
		mVelocityTracker.addMovement(event);
		float curry = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
//				if(!scroller.isFinished()){
//					scroller.abortAnimation();
//				}

				mLastY = curry;
				break;
			case MotionEvent.ACTION_MOVE:
				// scroller.getFinalY() 已经滑动的距离
				Log.v(getClass().getName(),"onTouchEvent--> MotionEvent.ACTION_MOVE: scroller.getFinalY():"+ scroller.getFinalY()   +" distanceY:"+distanceY);
				float dy = mLastY - curry;//本次收拾滑动了多大的距离
				int curryMoveDy= scroller.getFinalY()+(int)dy;
				if (curryMoveDy > distanceY) {//上啦滑动，距离超过了 画布，修改要移动的距离，
					dy=distanceY-scroller.getFinalY();
				}
				if (curryMoveDy < 0) {//下啦滑动，距离超过了 画布，修改要移动的距离
					dy=0-scroller.getFinalY();
				}
				scroller.startScroll(0, scroller.getFinalY(), 0, (int) dy);
				invalidate();
				mLastY = curry;
				postInvalidateOnAnimation();
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		Log.v(getClass().getName(),"computeScroll"+ scroller.getFinalY()   +" getCurrY:"+scroller.getCurrY());
		if (scroller.computeScrollOffset()) {
			int curry = scroller.getCurrY();//要滑动的总得距离
//			if (curry < 0) {
//				curry = 0;
//			}
//			if (curry > distanceY) {
//				curry = distanceY;
//			}
			scrollTo(0, curry);
			postInvalidate();

		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);\
		Log.e(getClass().getName(), "onMeasure()");
		if (lists != null)
			lists.clear();
		if (heithList != null)
			heithList.clear();
		mumLineHeith = 0;
		distanceY = 0;
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int childCount = getChildCount();
		init();

		int leftMargin;
		int topMargin;
		int rightMargin;
		int bottomMargin;

		linsWidth = 0;
		for (int i = 0; i < childCount; i++) {
			View view = getChildAt(i);

			measureChild(view, widthMeasureSpec, heightMeasureSpec);
			int childWidth = view.getMeasuredWidth();
			int childHeight = view.getMeasuredHeight();
			MyMarginLayoutParams marginLayoutParams = new MyMarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			leftMargin = marginLayoutParams.leftMargin;
			topMargin = marginLayoutParams.topMargin;
			rightMargin = marginLayoutParams.rightMargin;
			bottomMargin = marginLayoutParams.bottomMargin;

			LayoutParams childLayoutParams = view.getLayoutParams();
			if (childLayoutParams.height == LayoutParams.MATCH_PARENT) {//高度属性为match_parent重新设置为 WRAP_CONTENT
				childLayoutParams.height = LayoutParams.WRAP_CONTENT;
				view.setLayoutParams(childLayoutParams);
				i--;
				continue;
			}
			int sumChildWidth = childWidth + (leftMargin + rightMargin);

			if ((sumChildWidth + linsWidth) > widthSize) {//如果当前已存在的子view的宽度加上下一个要布局的view大于父view的宽度换行布局
//				if ((childWidth + linsWidth) > widthSize) {//如果当前已存在的子view的宽度加上下一个要布局的view大于父view的宽度换行布局

				if (lineList.size() == 0) {//只有一个元素，这个元素的宽度大于父view的宽度
					linsWidth += sumChildWidth;
//					mathWidth = Math.max(mathWidth, linsWidth);
					linesHeith = Math.max(linesHeith, childHeight);
					lineList.add(view);
				}
				lists.add(lineList);//将当前行保存
				lineList = new ArrayList<>();
				mumLineHeith += linesHeith;
				heithList.add(mumLineHeith);
				linsWidth = 0;//初始化行的宽度
				linesHeith = 0;//初始化行高
				sumLinesHeith += (childHeight + bottomMargin + topMargin);
			}
//			linsWidth += childWidth;
			linsWidth += sumChildWidth;//当前行的宽度 = 每次子view的宽度累加
//			mathWidth = Math.max(mathWidth, linsWidth);
			linesHeith = Math.max(linesHeith, childHeight);
			lineList.add(view);
			if (i == childCount - 1) {//对最后一个单独处理
				lists.add(lineList);//将当前行保存
				lineList = new ArrayList<>();
				mumLineHeith += linesHeith;
				heithList.add(mumLineHeith);
				linsWidth = 0;//初始化行的宽度
				linesHeith = 0;//初始化行高
				sumLinesHeith += (childHeight + bottomMargin + topMargin);
			}
		}
		flowLayoutHeith = (heightMode == MeasureSpec.EXACTLY ? heightSize : mumLineHeith);
		isScroller = mumLineHeith > flowLayoutHeith;
		if (isScroller) {
			distanceY = mumLineHeith - flowLayoutHeith;
		}
		setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : mathWidth, flowLayoutHeith);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.e(getClass().getName(), "onLayout()");
		int linsCount = lists.size();
		int left = 0;
		int top = 0;
		int right = 0;
		int bottom = 0;
		for (int i = 0; i < linsCount; i++) {
			List<View> viewList = lists.get(i);
			for (int j = 0; j < viewList.size(); j++) {
				View view = viewList.get(j);
//				MyMarginLayoutParams marginLayoutParams = getLayoutParams();//new MyMarginLayoutParams(widthMeasureSpec,heightMeasureSpec);
//				final MyMarginLayoutParams marginLayoutParams = (MyMarginLayoutParams) view.getLayoutParams();
//				MarginLayoutParams marginLayoutParams =  view.getLayoutParams();
				MyMarginLayoutParams marginLayoutParams = new MyMarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				int leftMargin = marginLayoutParams.leftMargin;
				int topMargin = marginLayoutParams.topMargin;
				int rightMargin = marginLayoutParams.rightMargin;
				int bottomMargin = marginLayoutParams.bottomMargin;

				left += leftMargin;


				right = left + view.getMeasuredWidth();
				bottom = top + topMargin + view.getMeasuredHeight();

				Log.v(getClass().getName(), "left:" + left + " top:" + top + topMargin + "  right:" + right + "  bottom:" + bottom);
				view.layout(left, top + topMargin, right, bottom);
				left += view.getMeasuredWidth() + rightMargin;

			}
			top = heithList.get(i);
			Log.v(getClass().getName(), "indexd:" + i + " heith:" + heithList.get(i));
			left = 0;

		}

	}
	@Override
	public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
		if (disallowIntercept) {
			recycleVelocityTracker();
		}
		super.requestDisallowInterceptTouchEvent(disallowIntercept);
	}

	@Override
	public void setLayoutParams(LayoutParams params) {
		super.setLayoutParams(params);
	}

	static class MyMarginLayoutParams extends MarginLayoutParams {
		public MyMarginLayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);
		}

		public MyMarginLayoutParams(int width, int height) {
			super(width, height);
		}

		public MyMarginLayoutParams(MarginLayoutParams source) {
			super(source);
		}

		public MyMarginLayoutParams(LayoutParams source) {
			super(source);
		}
	}
}
