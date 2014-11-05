package com.zc.addmony.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.utils.AnimUtil;

public class SelectPhoneNewActivity extends BaseActivity {
	private LinearLayout llSelectAddress, llSelectPhone;
	private TextView tvAddress, tvPhone;
	private Intent intent;
	private String aid;//市id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_select_phone_new_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setTitleViews() {
		titleText.setText("天天增财");

	}

	@Override
	protected void setViews() {
		llSelectAddress = (LinearLayout) findViewById(R.id.activity_select_phone_new_ll_address);
		llSelectPhone = (LinearLayout) findViewById(R.id.activity_select_phone_new_ll_phone);
		tvAddress = (TextView) findViewById(R.id.activity_select_phone_new_tv_address);
		tvPhone = (TextView) findViewById(R.id.activity_select_phone_new_tv_phone);
		llSelectAddress.setOnClickListener(this);
		llSelectPhone.setOnClickListener(this);

	}
	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_select_phone_new_ll_address://选择归属地
			intent = new Intent(this,SelectAddressActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.activity_select_phone_new_ll_phone://选择归属地手机号
			
			break;

		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 0:
			if(resultCode == RESULT_OK){
				tvAddress.setText(data.getStringExtra("address"));
				aid = data.getStringExtra("aid");
			}
			break;

		default:
			break;
		}
	}

}
