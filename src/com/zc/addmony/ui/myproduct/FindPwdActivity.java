package com.zc.addmony.ui.myproduct;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.bean.buyproduct.BanksBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicBuyProduct;
import com.zc.addmony.ui.buyproduct.SelectActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;
import com.zc.addmony.utils.PatternUtil;

/** 找回密码 */
public class FindPwdActivity extends BaseActivity {

	private EditText edtName, edtIdCard, edtBankNum, edtPhone, edtCode;
	private TextView tvBanks;
	private LinearLayout llBanks;
	private Button btnNext, btnCode;
	private ArrayList<String> banksList;
	private List<BanksBean> bean;
	private String accoreqserial = "";// 获取验证码时返回的字段
	private String mPhone;
	public String banks, province, city, branch, bankCode, branchCode;
	private String name, idCard, userName, bankNum, phone, checkCode;
	private Intent intent;
	private int position;
	private int times;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_find_pwd_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		banksList = new ArrayList<String>();
		bean = new ArrayList<BanksBean>();
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("找回密码");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		edtName = (EditText) findViewById(R.id.activity_find_pwd_et_name);
		edtBankNum = (EditText) findViewById(R.id.activity_find_pwd_edt_bank_num);
		edtIdCard = (EditText) findViewById(R.id.activity_find_pwd_et_idcard);
		edtPhone = (EditText) findViewById(R.id.activity_find_pwd_edt_phone);
		edtCode = (EditText) findViewById(R.id.activity_find_pwd_edt_checkcode);

		tvBanks = (TextView) findViewById(R.id.activity_find_pwd_tv_bank);
		llBanks = (LinearLayout) findViewById(R.id.activity_find_pwd_ll_banks);
		btnNext = (Button) findViewById(R.id.activity_find_pwd_bt_next);
		btnCode = (Button) findViewById(R.id.activity_find_pwd_btn_code);

		btnNext.setOnClickListener(this);
		btnCode.setOnClickListener(this);
		llBanks.setOnClickListener(this);
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(FindPwdActivity.this);
			break;
		case R.id.activity_find_pwd_ll_banks:// 获取银行卡列表
			if (banksList.size() == 0) {
				getBanksRequest();
			} else {
				intent = new Intent(this, SelectActivity.class);
				intent.putStringArrayListExtra("nameList", banksList);
				startActivityForResult(intent, 0);
				AnimUtil.pushLeftInAndOut(this);
			}
			break;
		case R.id.activity_find_pwd_bt_next:// 验证手机号
			KeyBoard.demissKeyBoard(getApplicationContext(), edtIdCard);
			userName = edtName.getText().toString();
			idCard = edtIdCard.getText().toString().trim();
			bankNum = edtBankNum.getText().toString();
			phone = edtPhone.getText().toString().trim();
			checkCode = edtCode.getText().toString().trim();
			banks = tvBanks.getText().toString();
			if (TextUtils.isEmpty(userName)) {
				showToast("请输入姓名");
			} else if (TextUtils.isEmpty(idCard)) {
				showToast("请输入身份证号");
			} else if (!PatternUtil.patternIdCard(idCard)) {
				showToast("请输入正确地身份证号");
			} else if (TextUtils.isEmpty(bankNum)) {
				showToast("请输入银行卡号");
			} else if (TextUtils.isEmpty(phone)) {
				showToast("请输入手机号");
			} else if (phone.length() != 11) {
				showToast("请输入正确的手机号");
			} else if (TextUtils.isEmpty(checkCode)) {
				showToast("请输入收到的验证码");
			} else if (checkCode.length() != 6) {
				showToast("请输入6位验证码");
			} else {
				times = 0;
				btnCode.setClickable(true);
				btnCode.setText("发送验证码");
				sendCheckMessage();
			}
			break;
		case R.id.activity_find_pwd_btn_code:// 获取短信
			KeyBoard.demissKeyBoard(getApplicationContext(), edtIdCard);
			userName = edtName.getText().toString();
			idCard = edtIdCard.getText().toString().trim();
			bankNum = edtBankNum.getText().toString();
			phone = edtPhone.getText().toString().trim();
			checkCode = edtCode.getText().toString().trim();
			if (TextUtils.isEmpty(userName)) {
				showToast("请输入姓名");
			} else if (TextUtils.isEmpty(idCard)) {
				showToast("请输入身份证号");
			} else if (!PatternUtil.patternIdCard(idCard)) {
				showToast("请输入正确地身份证号");
			} else if (TextUtils.isEmpty(bankNum)) {
				showToast("请输入银行卡号");
			} else if (TextUtils.isEmpty(phone)) {
				showToast("请输入手机号");
			} else if (phone.length() != 11) {
				showToast("请输入正确的手机号");
			} else {
				getMessage();
			}
			break;
		default:
			break;
		}
	}

	/** 获取银行列表 */
	private void getBanksRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		// params.put("key", "banks");
		httpRequest.get(Urls.GET_SUPPORT_BANKS, params, callBack, 0);
	}

	/** 获取手机短信 */
	private void getMessage() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("phone", phone);// 手机号
		params.put("banknum", bankNum);// 银行卡号
		params.put("name", userName);// 姓名
		params.put("idcard", idCard);// 身份证号
		httpRequest.get(Urls.OPEN_BANK_CODE, params, callBack, 5);
	}

	/** 验证信息 */
	private void sendCheckMessage() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("accoreqserial", accoreqserial);
		params.put("mobileauthcode", checkCode);
		params.put("phone", phone);// 手机号
		params.put("banknum", bankNum);// 银行卡号
		params.put("name", userName);// 姓名
		params.put("idcard", idCard);// 身份证号
		httpRequest.get(Urls.OPEN_BANK_CHECK, params, callBack, 6);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		switch (reqeustCode) {
		case 0:
			if (TextUtils.isEmpty(jsonString) || jsonString.equals("null")) {
				showToast("没有银行");
			} else {
				bean = new ArrayList<BanksBean>();
				bean = LogicBuyProduct.parseBanksList(jsonString);
				int len = bean.size();
				banksList = new ArrayList<String>();
				for (int i = 0; i < len; i++) {
					banksList.add(bean.get(i).getBank_name());
				}
				intent = new Intent(this, SelectActivity.class);
				intent.putStringArrayListExtra("nameList", banksList);
				startActivityForResult(intent, 0);
				AnimUtil.pushLeftInAndOut(this);
			}
			break;
		case 5:// 获取短信
			showToast("信息发送成功，请注意查收");
			btnCode.setClickable(false);
			times = 59;
			timeHandler.sendEmptyMessageDelayed(0, 1000);
			try {
				JSONObject obj = new JSONObject(jsonString);
				accoreqserial = obj.getString("accoreqserial");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 6:// 验证短信
			intent = new Intent(this,SetNewPwdActivity.class);
			intent.putExtra("banknum", bankNum);
			intent.putExtra("idcard", idCard);
			intent.putExtra("phone", phone);
			intent.putExtra("name", userName);
			startActivityForResult(intent, 101);
			AnimUtil.pushLeftInAndOut(this);
			break;
		}
	}
	
	public Handler timeHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (times != 0 && times != 60) {
				btnCode.setText(times + "秒后重发");
				--times;
				timeHandler.sendEmptyMessageDelayed(0, 1000);
			} else {
				times = 60;
				btnCode.setClickable(true);
				btnCode.setText("发送验证码");
			}

		};
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				name = data.getStringExtra("name");
				position = data.getIntExtra("postion", 0);
				tvBanks.setText(name);
				bankCode = bean.get(position).getBank_num();
				break;
			default:
				break;
			}
		}else if(resultCode == 101){
			this.finish();
		}
	}

}
