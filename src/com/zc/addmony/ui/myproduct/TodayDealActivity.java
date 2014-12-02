package com.zc.addmony.ui.myproduct;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;

public class TodayDealActivity extends BaseActivity {
	
	private ListView lvContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_today_deal_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setTitleViews() {
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("当日交易申请");

	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub

	}

}
