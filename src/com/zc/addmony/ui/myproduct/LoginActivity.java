package com.zc.addmony.ui.myproduct;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.bean.myproduct.LoginBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicPerson;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;
import com.zc.addmony.utils.PatternUtil;

public class LoginActivity extends BaseActivity {

	private EditText etPhone, etPwd;
	private TextView tvForget;
	private Button btRegister, btLogin;
	private String mPhone, mPwd;
	private UserSharedData userShare;
	private LoginBean bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_login_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		userShare = UserSharedData.getInstance(getApplicationContext());
		bean = new LoginBean();
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleIvRight.setVisibility(View.INVISIBLE);
		setTitle("登录増财基金");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		etPhone = (EditText) findViewById(R.id.activity_login_et_phone);
		etPwd = (EditText) findViewById(R.id.activity_login_et_pwd);
		tvForget = (TextView) findViewById(R.id.activity_login_tv_forget);
		btRegister = (Button) findViewById(R.id.activity_login_bt_register);
		btLogin = (Button) findViewById(R.id.activity_login_bt_login);

		btRegister.setOnClickListener(this);
		btLogin.setOnClickListener(this);
		tvForget.setOnClickListener(this);
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		Intent intent;
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(LoginActivity.this);
			break;
		case R.id.activity_login_bt_register:
			intent = new Intent(this, RegisterSecondActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(LoginActivity.this);
			break;
		case R.id.activity_login_bt_login:
			mPhone = etPhone.getText().toString().trim();
			mPwd = etPwd.getText().toString().trim();
			KeyBoard.demissKeyBoard(getApplicationContext(), etPhone);
			KeyBoard.demissKeyBoard(getApplicationContext(), etPwd);
			if (TextUtils.isEmpty(mPhone)) {
				showToast("请输入手机号");
			} else if (TextUtils.isEmpty(mPwd)) {
				showToast("请输入密码");
			} else if (!PatternUtil.patternPhoneNumber(mPhone)) {
				showToast("请输入正确的手机号");
			} else {
				requestLogin();
			}
			break;
		case R.id.activity_login_tv_forget:// 忘记密码
			intent = new Intent(this, FindPwdActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(LoginActivity.this);
			break;
		default:
			break;
		}
	}

	/** 登录 */
	public void requestLogin() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("username", mPhone);
		params.put("password", mPwd);
		httpRequest.get(Urls.LOGIN, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		showToast("登录成功");
		bean = LogicPerson.parseLogin(jsonString);
		userShare.SaveFlag(true);
		userShare.SavePhone(etPhone.getText().toString().trim());
		userShare.SavePwd(etPwd.getText().toString().trim());
		userShare.SaveId(bean.getUser_id());
		userShare.SaveName(bean.getUser_name());
		userShare.SaveOpenFlag(bean.getOpenflag());
		userShare.SaveBuyPwd(bean.getTrade_pwd());
		userShare.SaveToken(bean.getToken());
		userShare.SaveRealname(bean.getRealname());
		userShare.SaveIdcard(bean.getIdcard());
		this.finish();
	}
}
