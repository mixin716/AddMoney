package com.zc.addmony.ui.myproduct;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.utils.AnimUtil;

/** 注册成功 */
public class RegisterSuccessActivity extends BaseActivity {

	private LinearLayout llLook,llInput;
	private MApplication mApp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_register_success_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		mApp = (MApplication) this.getApplication();
		mApp.addAllActivity(this);
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		llLook = (LinearLayout) findViewById(R.id.activity_register_success_ll_look);
		llInput = (LinearLayout) findViewById(R.id.activity_register_success_ll_into);
		
		llInput.setOnClickListener(this);
		llLook.setOnClickListener(this);
	}
	
	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(this);
			break;
			
		case R.id.activity_register_success_ll_look:
			Intent intent = new Intent("refresh_tab");
			intent.putExtra("intoSelect", 2);
			sendBroadcast(intent);
			mApp.clearAllActivity();
			break;
		case R.id.activity_register_success_ll_into:
			Intent intoIntent = new Intent("refresh_tab");
			intoIntent.putExtra("intoSelect", 3);
			sendBroadcast(intoIntent);
			sendBroadcast(new Intent("login_my_product"));
			mApp.clearAllActivity();
			break;
		}
	}

}
