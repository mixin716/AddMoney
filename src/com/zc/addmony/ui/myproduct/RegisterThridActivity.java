package com.zc.addmony.ui.myproduct;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.ui.buyproduct.PerfectInformationActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;

/** 注册第三步 设置密码 */
public class RegisterThridActivity extends BaseActivity {

	private EditText etPwd, etPwdSecond;
	private Button btn;
	private String strPwd, strPwdSec;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_register_third_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("注册");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		etPwd = (EditText) findViewById(R.id.activity_register_third_et_pwd);
		etPwdSecond = (EditText) findViewById(R.id.activity_register_third_et_pwdsecond);
		btn = (Button) findViewById(R.id.activity_register_third_btn);

		btn.setOnClickListener(this);
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
		case R.id.activity_register_third_btn:
			KeyBoard.demissKeyBoard(getApplicationContext(), etPwd);
			strPwd = etPwd.getText().toString().trim();
			strPwdSec = etPwdSecond.getText().toString().trim();
			if (TextUtils.isEmpty(strPwd)) {
				showToast("请输入密码");
			} else if (strPwd.length() < 6 || strPwd.length() > 8) {
				showToast("请输入6~8位密码");
			} else if (TextUtils.isEmpty(strPwdSec)) {
				showToast("请再次输入密码");
			} else if (!strPwd.equals(strPwdSec)) {
				showToast("两次输入密码不一致");
			} else {
				
			}
			Intent intent = new Intent(this,PerfectInformationActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(this);
			break;
		}
	}
}
