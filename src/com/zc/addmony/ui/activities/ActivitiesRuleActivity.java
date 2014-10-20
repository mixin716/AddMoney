package com.zc.addmony.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.utils.AnimUtil;

public class ActivitiesRuleActivity extends BaseActivity {
	private TextView tvMore;
	private LinearLayout llMoreContent;
	private Button btnOk;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_activities_rule_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViews() {
		tvMore = (TextView) findViewById(R.id.activity_activities_rule_tv_more);
		llMoreContent = (LinearLayout) findViewById(R.id.activity_activities_rule_ll_more);
		btnOk = (Button) findViewById(R.id.activity_activities_rule_btn_ok);
		
		tvMore.setOnClickListener(this);
		btnOk.setOnClickListener(this);
	}
	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_activities_rule_tv_more:
			tvMore.setVisibility(View.GONE);
			llMoreContent.setVisibility(View.VISIBLE);
			break;
		case R.id.activity_activities_rule_btn_ok:
			Intent intent = new Intent(this,ShoppingActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(this);
			break;

		default:
			break;
		}
	}

}
