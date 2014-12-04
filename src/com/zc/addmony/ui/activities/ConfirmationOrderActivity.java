package com.zc.addmony.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.activities.ActivitiesPhoneBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;
import com.zc.addmony.utils.PatternUtil;

/** 确认订单 */
public class ConfirmationOrderActivity extends BaseActivity {

	private MApplication mApplication;
	private TextView tvPhoneName, tvColor, tvPackage, tvPhoneAddress,
			tvPhoneSelect;
	private EditText  etAddress, etPhone, etRemarks;
	private TextView tvProvince,tvCity;
	private CheckBox cbAgree;
	private Button btNext;
	private ActivitiesPhoneBean apBean;
	private UserSharedData userShare;
	private String strProvince, strCity, strAddress, strPhone, strRemark;
	private String province;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_confirmation_order_layout);
		mApplication = (MApplication) this.getApplication();
		mApplication.addActivitys(this);
		apBean = mApplication.getApBean();
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		userShare = UserSharedData.getInstance(getApplicationContext());
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("天天增财");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		tvPhoneName = (TextView) findViewById(R.id.activity_confirmation_order_tv_phone_name);
		tvColor = (TextView) findViewById(R.id.activity_confirmation_order_tv_phonecolor);
		tvPackage = (TextView) findViewById(R.id.activity_confirmation_order_tv_package);
		tvPhoneAddress = (TextView) findViewById(R.id.activity_confirmation_order_tv_phone_address);
		tvPhoneSelect = (TextView) findViewById(R.id.activity_confirmation_order_tv_phone_select);
		tvProvince = (TextView) findViewById(R.id.activity_confirmation_order_tv_province);
		tvCity = (TextView) findViewById(R.id.activity_confirmation_order_tv_city);
		etAddress = (EditText) findViewById(R.id.activity_confirmation_order_et_address);
		etPhone = (EditText) findViewById(R.id.activity_confirmation_order_et_phone);
		etRemarks = (EditText) findViewById(R.id.activity_confirmation_order_et_remark);
		cbAgree = (CheckBox) findViewById(R.id.activity_confirmation_order_cb_agree);
		btNext = (Button) findViewById(R.id.activity_confirmation_order_bt_next);
		tvProvince.setOnClickListener(this);
		tvCity.setOnClickListener(this);
		btNext.setOnClickListener(this);

		if (!TextUtils.isEmpty(apBean.getPhoneName())) {
			tvPhoneName.setText(apBean.getPhoneName());
		}
		if (!TextUtils.isEmpty(apBean.getPhoneColor())) {
			tvColor.setText(apBean.getPhoneColor());
		}
		if (!TextUtils.isEmpty(apBean.getPhoneTc())) {
			tvPackage.setText(apBean.getPhoneTc()+"套餐");
		}
		if(!TextUtils.isDigitsOnly(apBean.getPhoneAdd())){
			tvPhoneAddress.setText(apBean.getPhoneAdd());
		}
		if(!TextUtils.isEmpty(apBean.getPhoneNum())){
			tvPhoneSelect.setText(apBean.getPhoneNum());
		}
		etPhone.setText(userShare.GetPhone());
	}


	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		Intent intent;
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(ConfirmationOrderActivity.this);
			break;
		case R.id.activity_confirmation_order_tv_province://选择省份
			intent = new Intent(this,EmailAddressActivity.class);
			intent.putExtra("type", 1);
			startActivityForResult(intent, 0);
			AnimUtil.pushLeftInAndOut(ConfirmationOrderActivity.this);
			break;
		case R.id.activity_confirmation_order_tv_city://选择市
			if(TextUtils.isEmpty(tvProvince.getText().toString())){
				showToast("请选择省份");
			}else{
				intent = new Intent(this,EmailAddressActivity.class);
				intent.putExtra("type", 2);
				intent.putExtra("province", province);
				startActivityForResult(intent, 1);
				AnimUtil.pushLeftInAndOut(ConfirmationOrderActivity.this);
			}
			break;
		case R.id.activity_confirmation_order_bt_next:
			// intent = new Intent(this, OrderSuccessActivity.class);
			// startActivity(intent);
			AnimUtil.pushLeftInAndOut(ConfirmationOrderActivity.this);
//			KeyBoard.demissKeyBoard(getApplicationContext(), etProvince);
//			KeyBoard.demissKeyBoard(getApplicationContext(), etCity);
			KeyBoard.demissKeyBoard(getApplicationContext(), etAddress);
			KeyBoard.demissKeyBoard(getApplicationContext(), etPhone);
			KeyBoard.demissKeyBoard(getApplicationContext(), etRemarks);
			strProvince = tvProvince.getText().toString().trim();
			strCity = tvCity.getText().toString().trim();
			strAddress = etAddress.getText().toString().trim();
			strPhone = etPhone.getText().toString().trim();
			strRemark = etRemarks.getText().toString().trim();
			if (TextUtils.isEmpty(strProvince)) {
				showToast("请输入配送省份");
			} else if (TextUtils.isEmpty(strCity)) {
				showToast("请输入配送城市");
			} else if (TextUtils.isEmpty(strAddress)) {
				showToast("请输入详细地址");
			} else if (TextUtils.isEmpty(strPhone)) {
				showToast("请输入手机号");
			} else if (!PatternUtil.patternPhoneNumber(strPhone)) {
				showToast("请输入正确的手机号");
			} else {
				strAddress = strProvince + strCity + strAddress;
				updateActivities();
			}
			break;
		default:
			break;
		}
	}

	/** 上传活动信息 */
	public void updateActivities() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("o_name", userShare.Getrealname());// 姓名
		params.put("o_phone", strPhone);// 联系电话
		params.put("o_contact", strAddress);// 送货地址
		params.put("o_card", userShare.GetIdcard());// 身份证号
		params.put("o_remarks", strRemark);// 买家备注
		params.put("C_id", apBean.getPhoneColorId());// 颜色id
		params.put("M_id", apBean.getPhoneId());// 手机型号id
		params.put("phone", apBean.getPhoneNum());// 选择的手机号
		params.put("P_id", apBean.getPhoneTcId());// 套餐id
		httpRequest.get(Urls.UPDATE_ACTIVITIES, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		showToast("订单提交成功");
		Intent intent = new Intent(this, OrderSuccessActivity.class);
		startActivity(intent);
		AnimUtil.pushLeftInAndOut(ConfirmationOrderActivity.this);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			if(resultCode == RESULT_OK){
				province = data.getStringExtra("address");
				tvProvince.setText(province);
			}
			break;
		case 1:
			if(resultCode == RESULT_OK){
				tvCity.setText(data.getStringExtra("address"));
			}
			break;

		default:
			break;
		}
	}

}
