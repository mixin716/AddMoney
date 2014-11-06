package com.zc.addmony.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.activities.ActivitiesPhoneBean;
import com.zc.addmony.utils.AnimUtil;

public class SelectPhoneNewActivity extends BaseActivity {
	private LinearLayout llSelectAddress, llSelectPhone;
	private TextView tvAddress, tvPhone;
	private Intent intent;
	private String aid;// 市id
	private String phoneNum,address;//号码归属地 包括省和市
	private ActivitiesPhoneBean apBean;
	private Button btnOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_select_phone_new_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		mApplication = (MApplication) this.getApplication();
		apBean = mApplication.getApBean();

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
		btnOk = (Button) findViewById(R.id.activity_select_phone_new_btn_ok);

		btnOk.setOnClickListener(this);
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
		case R.id.activity_select_phone_new_ll_address:// 选择归属地
			intent = new Intent(this, SelectAddressActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.activity_select_phone_new_ll_phone:// 选择归属地手机号
			if (TextUtils.isEmpty(tvAddress.getText().toString())) {
				showToast("请选择号码归属地");
			} else {
				intent = new Intent(this, PhoneListNewActivity.class);
				intent.putExtra("aid", aid);
				startActivityForResult(intent, 1);
			}
			break;
		case R.id.activity_select_phone_new_btn_ok:// 确定
			if(TextUtils.isEmpty(tvAddress.getText().toString())){
				showToast("请选择号码归属地");
			}else if(TextUtils.isEmpty(tvPhone.getText().toString())){
				showToast("请选择归属地号码");
			}else{
				apBean.setPhoneAdd(address);
				apBean.setPhoneNum(phoneNum);
				intent = new Intent(this, ConfirmationOrderActivity.class);
				startActivity(intent);
			}
			
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("----requestCode::--->"+requestCode);
		switch (requestCode) {
		case 0:
			if (resultCode == RESULT_OK) {
				address = data.getStringExtra("address");
				tvAddress.setText(address);
				aid = data.getStringExtra("aid");
				System.out.println("----address::--->"+address);
			}
			break;
		case 1:
			if (resultCode == RESULT_OK) {
				phoneNum = data.getStringExtra("phone");
				tvPhone.setText(phoneNum);
				System.out.println("----phoneNum::--->"+phoneNum);
			}
			break;

		default:
			break;
		}
	}

}
