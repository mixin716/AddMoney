package com.zc.addmony.ui.myproduct;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.jky.struct2.http.entityhandle.HttpResult;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.BaseBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicBase;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;
import com.zc.addmony.utils.PatternUtil;

public class RegisterActivity extends BaseActivity {

	private EditText etPhone, etPwd, etPwdSecond, etCheck;
	private Button btCheck, btRegister;
	private TextView tvCheck;
	private MApplication mApp;
	private int seconds = 60;
	private String mPhone, mPwd, mPwdSecond, mCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_register_layout);
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
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("注册");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		etPhone = (EditText) findViewById(R.id.activity_register_et_phone);
		etPwd = (EditText) findViewById(R.id.activity_register_et_pwd);
		etPwdSecond = (EditText) findViewById(R.id.activity_register_et_pwdsecond);
		etCheck = (EditText) findViewById(R.id.activity_register_et_check);
		tvCheck = (TextView) findViewById(R.id.activity_register_tv_check);
		btCheck = (Button) findViewById(R.id.activity_register_bt_send);
		btRegister = (Button) findViewById(R.id.activity_register_bt_register);

		btCheck.setOnClickListener(this);
		btRegister.setOnClickListener(this);

	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(RegisterActivity.this);
			break;
		case R.id.activity_register_bt_send:// 获取验证码
			if (checkInput()) {
				btCheck.setVisibility(View.GONE);
				tvCheck.setVisibility(View.VISIBLE);
				seconds = 60;
				--seconds;
				tvCheck.setText("(59秒)之后重发");
				timeHandler.sendEmptyMessageDelayed(0, 1000);
				requestCheckCode();
			}
			break;
		case R.id.activity_register_bt_register:// 注册
			Intent intent = new Intent(this,RegisterSecondActivity.class);
			startActivity(intent);
			this.finish();
			if (checkInput()) {
				mCheck = etCheck.getText().toString().trim();
				if (TextUtils.isEmpty(mCheck)) {
					showToast("请输入验证码");
				} else {
					requestRegister();
				}
			}
			break;
		default:
			break;
		}
	}

	/** 判断是输入情况 */
	public boolean checkInput() {
		KeyBoard.demissKeyBoard(getApplicationContext(), etPhone);
		KeyBoard.demissKeyBoard(getApplicationContext(), etPwd);
		KeyBoard.demissKeyBoard(getApplicationContext(), etPwdSecond);
		mPhone = etPhone.getText().toString().trim();
		mPwd = etPwd.getText().toString().trim();
		mPwdSecond = etPwdSecond.getText().toString().trim();
		if (TextUtils.isEmpty(mPhone)) {
			showToast("请输入手机号");
			return false;
		} else if (TextUtils.isEmpty(mPwd)) {
			showToast("请输入登录密码");
			return false;
		} else if (TextUtils.isEmpty(mPwdSecond)) {
			showToast("请输入确认密码");
			return false;
		} else if (!mPwd.equals(mPwdSecond)) {
			showToast("两次密码不一致");
			return false;
		} else if (!PatternUtil.patternPhoneNumber(mPhone)) {
			showToast("请输入正确的手机号");
			return false;
		} else {
			return true;
		}

	}

	public Handler timeHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (seconds != 0) {
				--seconds;
				tvCheck.setText("(" + seconds + "秒)之后重发");
				timeHandler.sendEmptyMessageDelayed(0, 1000);
			} else {
				btCheck.setVisibility(View.VISIBLE);
				tvCheck.setVisibility(View.GONE);
				seconds = 60;
			}
		};
	};

	/** 获取验证码 */
	public void requestCheckCode() {
		AjaxParams params = new AjaxParams();
		params.put("phone", mPhone);
		httpRequest.get(Urls.CHECK_CODE, params, callBack, 0);
	}

	/** 注册 */
	public void requestRegister() {
		AjaxParams params = new AjaxParams();
		params.put("username", mPhone);
		params.put("password", mPwd);
		params.put("returnNum", mCheck);
		httpRequest.get(Urls.REGISTER, params, callBack, 1);
	}

	@Override
	protected void handleResult(int requestCode, HttpResult result) {
		// TODO Auto-generated method stub
		super.handleResult(requestCode, result);
		String baseJson = result.baseJson;
		System.out.println("-----json:------" + baseJson);
		BaseBean baseBean = LogicBase.getInstance().parseData(baseJson);
		switch (baseBean.getStatus()) {
		case 1:
			handleJson(requestCode, baseBean.getContent(),
					baseBean.getMessage());
			System.out.println("-----baseBean.getData():------"
					+ baseBean.getContent());
			break;
		default:// 请求失败
			showToast(baseBean.getMessage());
			btCheck.setVisibility(View.VISIBLE);
			tvCheck.setVisibility(View.GONE);
			seconds = 0;
			break;
		}
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		switch (reqeustCode) {
		case 0:
			showToast(message);
			break;
		case 1:
			showToast(message);
			break;
		default:
			break;
		}
	}
}
