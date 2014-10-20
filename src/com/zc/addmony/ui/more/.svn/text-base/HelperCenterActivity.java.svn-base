package com.zc.addmony.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.adapter.more.HelperCenterAdapter;
import com.zc.addmony.utils.AnimUtil;

public class HelperCenterActivity extends BaseActivity implements
		OnItemClickListener {
	private ListView lvContent;
	private HelperCenterAdapter adapter;
	private String[] urls = { "http://115.28.20.187/zcapi/help/1.html",
			"http://115.28.20.187/zcapi/help/2.html",
			"http://115.28.20.187/zcapi/help/3.html",
			"http://115.28.20.187/zcapi/help/4.html",
			"http://115.28.20.187/zcapi/help/5.html",
			"http://115.28.20.187/zcapi/help/6.html",
			"http://115.28.20.187/zcapi/help/7.html" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_helper_center_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		adapter = new HelperCenterAdapter(getApplicationContext());

	}

	@Override
	protected void setTitleViews() {
		titleText.setText("帮助中心");

	}

	@Override
	protected void setViews() {
		lvContent = (ListView) findViewById(R.id.activity_helper_center_lv);
		lvContent.setAdapter(adapter);
		lvContent.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this,HelperDetailActivity.class);
		intent.putExtra("url", urls[arg2]);
		startActivity(intent);
		AnimUtil.pushLeftInAndOut(this);
	}
	
	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(HelperCenterActivity.this);
			break;

		default:
			break;
		}
	}

}
