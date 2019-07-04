package com.yuanhy.appframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanhy.appframework.swipecard.SwipeCardBean;
import com.yuanhy.library_tools.adapter.UniversalAdapter;
import com.yuanhy.library_tools.adapter.ViewHolder;
import com.yuanhy.library_tools.callback.SwipeCardCallback;
import com.yuanhy.library_tools.image.GlideUtil;
import com.yuanhy.library_tools.layoutmanager.CardConfig;
import com.yuanhy.library_tools.layoutmanager.SwipeCarLayoutManager;

import java.util.List;

/**
 * 探探滑动效果
 */
public class SwipeCardActivity extends AppCompatActivity {

	private RecyclerView rv;
	private UniversalAdapter<SwipeCardBean> adapter;
	private List<SwipeCardBean> mDatas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe_card);
		rv = findViewById(R.id.rv);
		rv.setLayoutManager( new SwipeCarLayoutManager());

		mDatas = SwipeCardBean.initDatas();

		adapter= new UniversalAdapter<SwipeCardBean>(this,mDatas,R.layout.item_swipe_card) {
			@Override
			public void convert(ViewHolder viewHolder, SwipeCardBean swipeCardBean) {
				TextView tvName=viewHolder.itemView.findViewById(R.id.tvName);
				tvName.setText(swipeCardBean.getName());
				ImageView iv=viewHolder.itemView.findViewById(R.id.iv);
				GlideUtil.setImage(SwipeCardActivity.this,swipeCardBean.getUrl(),iv);

			}
		};
		rv.setAdapter(adapter);
		CardConfig.initConfig(this);

		ItemTouchHelper.Callback callback = new SwipeCardCallback (rv, adapter, mDatas);
		ItemTouchHelper helper = new ItemTouchHelper(callback);
		helper.attachToRecyclerView(rv);
	}
}
