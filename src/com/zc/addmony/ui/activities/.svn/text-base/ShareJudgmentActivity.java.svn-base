package com.zc.addmony.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.utils.AnimUtil;

public class ShareJudgmentActivity extends BaseActivity{

	private Button btFull,btEmpty;//份额充足 份额不足
	private TextView tvName,tvNumber;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_share_judgment_layout);
		setViews();
		requestProduct();
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
		// TODO Auto-generated method stub
		tvName = (TextView) findViewById(R.id.activity_share_judgment_tv_name);
		tvNumber = (TextView) findViewById(R.id.activity_share_judgment_tv_number);
		btFull = (Button) findViewById(R.id.activity_share_judgment_bt_full);
		btEmpty = (Button) findViewById(R.id.activity_share_judgment_bt_empty);
		
		btFull.setOnClickListener(this);
		btEmpty.setOnClickListener(this);
	}
	
	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(ShareJudgmentActivity.this);
			break;
		case R.id.activity_share_judgment_bt_full:
			intent = new Intent(this,SelectPhoneActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(ShareJudgmentActivity.this);
			break;
		case R.id.activity_share_judgment_bt_empty:
			
			break;
		}
	}
	
	/** 请求基金*/
	public void requestProduct(){
		
	}
	
	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
	}

}
