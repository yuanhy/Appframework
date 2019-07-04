package com.yuanhy.library_tools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义流式布局
 */
public class FlowLayoutViewTemp extends ViewGroup {
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
	int sumLinesHeith=0;//父总高度

	public FlowLayoutViewTemp(Context context) {
		super(context);

	}

	public FlowLayoutViewTemp(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayoutViewTemp(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	void init() {
		lineList = new ArrayList<>();
		if (lists == null)
			lists = new ArrayList<>();
		if (heithList == null)
			heithList = new ArrayList<>();
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
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int childCount = getChildCount();
		init();

		int leftMargin  ;
		int topMargin  ;
		int rightMargin  ;
		int bottomMargin  ;

		linsWidth = 0;
		for (int i = 0; i < childCount; i++) {
			View view = getChildAt(i);

					measureChild(view, widthMeasureSpec, heightMeasureSpec);
			int childWidth = view.getMeasuredWidth();
			int childHeight = view.getMeasuredHeight();
			MyMarginLayoutParams marginLayoutParams = new MyMarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

			  leftMargin = marginLayoutParams.leftMargin;
			  topMargin = marginLayoutParams.topMargin;
			  rightMargin = marginLayoutParams.rightMargin;
			  bottomMargin = marginLayoutParams.bottomMargin;

			  LayoutParams childLayoutParams=view.getLayoutParams();
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
				sumLinesHeith +=(childHeight +bottomMargin+topMargin);
			}
//			linsWidth += childWidth;
			linsWidth += sumChildWidth;//当前行的宽度 = 每次子view的宽度累加
//			mathWidth = Math.max(mathWidth, linsWidth);
			linesHeith = Math.max(linesHeith, childHeight);
			lineList.add(view);
			if (i==childCount-1){//对最后一个单独处理
				lists.add(lineList);//将当前行保存
				lineList = new ArrayList<>();
				mumLineHeith += linesHeith;
				heithList.add(mumLineHeith);
				linsWidth = 0;//初始化行的宽度
				linesHeith = 0;//初始化行高
				sumLinesHeith +=(childHeight +bottomMargin+topMargin);
			}
		}
//		remeasureChild(widthMeasureSpec,heightMeasureSpec);
		setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : mathWidth, heightMode == MeasureSpec.EXACTLY ? heightSize : mumLineHeith);
	}

	private void remeasureChild(int widthMeasureSpec, int heightMeasureSpec) {

		int linesSizi = lists.size();
		if (linesSizi == 0) {
			return;
		}
		for (int i = 0; i < linesSizi; i++) {
			List<View> viewList = lists.get(i);
//			View childView;
			for (int j = 0; j < viewList.size(); j++) {
				View childView = viewList.get(j);
				MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
				if (layoutParams.height == LayoutParams.MATCH_PARENT) {
					int heithMath = heithList.get(i);
					int widthSpec = getChildMeasureSpec(widthMeasureSpec, childView.getPaddingLeft() + childView.getPaddingRight(), layoutParams.width);
					int heithSpec = getChildMeasureSpec(heightMeasureSpec, childView.getPaddingTop() + childView.getPaddingBottom(), heithMath);
					childView.measure(widthSpec, heithSpec);
				}
			}
		}

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
				MyMarginLayoutParams marginLayoutParams = new MyMarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

				int leftMargin =  marginLayoutParams.leftMargin;
				int topMargin =  marginLayoutParams.topMargin;
				int rightMargin =  marginLayoutParams.rightMargin;
				int bottomMargin =  marginLayoutParams.bottomMargin;

				left+=leftMargin;


				right = left + view.getMeasuredWidth();
				bottom = top +topMargin+ view.getMeasuredHeight();

				Log.v(getClass().getName(), "left:" + left + " top:" + top  +topMargin+ "  right:" + right + "  bottom:" + bottom);
				view.layout(left, top+topMargin, right, bottom);
				left += view.getMeasuredWidth()+rightMargin;





			}
			top = heithList.get(i);
			Log.v(getClass().getName(), "indexd:" + i + " heith:" + heithList.get(i));
			left = 0;

		}

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
