package com.zc.addmony.ui.myproduct;

import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;

public class ChangePwdActivity extends BaseActivity {

	private EditText etPwdOld, etPwdNew, etPwdSecond;
	private Button btChange;
	private String pwdOld, pwdNew, pwdSecond;
	private UserSharedData userShare;
	private MApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_change_pwd_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		app = (MApplication) this.getApplication();
		app.addAllActivity(this);
		userShare = UserSharedData.getInstance(getApplicationContext());
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("修改登录密码");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		etPwdOld = (EditText) findViewById(R.id.activity_change_pwd_et_pwdold);
		etPwdNew = (EditText) findViewById(R.id.activity_change_pwd_et_pwdnew);
		etPwdSecond = (EditText) findViewById(R.id.activity_change_pwd_et_pwdsecond);
		btChange = (Button) findViewById(R.id.activity_change_pad_bt_change);
		btChange.setOnClickListener(this);
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(ChangePwdActivity.this);
			break;
		case R.id.activity_change_pad_bt_change:
			KeyBoard.demissKeyBoard(getApplicationContext(), etPwdOld);
			KeyBoard.demissKeyBoard(getApplicationContext(), etPwdNew);
			KeyBoard.demissKeyBoard(getApplicationContext(), etPwdSecond);
			pwdOld = etPwdOld.getText().toString().trim();
			pwdNew = etPwdNew.getText().toString().trim();
			pwdSecond = etPwdSecond.getText().toString().trim();
			if (TextUtils.isEmpty(pwdOld)) {
				showToast("请输入原密码");
			} else if (TextUtils.isEmpty(pwdNew)) {
				showToast("请输入新密码");
			} else if (TextUtils.isEmpty(pwdSecond)) {
				showToast("两次密码不一致");
			} else if (!pwdNew.equals(pwdSecond)) {
				showToast("两次密码不一致");
			} else {
				requestLogin();
			}
			break;

		default:
			break;
		}
	}

	/** 登录 */
	public void requestLogin() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("username", userShare.getAccount());
		params.put("password", userShare.GetPwd());
		httpRequest.get(Urls.LOGIN, params, callBack, 0);
	}

	/** 更改密码 */
	public void requestChange() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("orderpwd", pwdOld);
		params.put("newpwd", pwdNew);
		params.put("token", userShare.GetToken());
		httpRequest.get(Urls.CHANGE_PWD, params, callBack, 1);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		switch (reqeustCode) {
		case 0:
			requestChange();
			break;
		case 1:
			showToast(message);
			userShare.SavePwd(pwdNew);
			this.finish();
			break;
		}

	}
}
