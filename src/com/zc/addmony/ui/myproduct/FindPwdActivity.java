package com.zc.addmony.ui.myproduct;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.common.Urls;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;
import com.zc.addmony.utils.PatternUtil;

/** 找回密码 */
public class FindPwdActivity extends BaseActivity {

	private EditText etPhone;
	private Button button;
	private String mPhone;

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
		etPhone = (EditText) findViewById(R.id.activity_find_pwd_et_phone);
		button = (Button) findViewById(R.id.activity_find_pwd_bt);
		button.setOnClickListener(this);
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
		case R.id.activity_find_pwd_bt:
			KeyBoard.demissKeyBoard(getApplicationContext(), etPhone);
			mPhone = etPhone.getText().toString().trim();
			if (TextUtils.isEmpty(mPhone)) {
				showToast("请输入手机号");
			} else if (!PatternUtil.patternPhoneNumber(mPhone)) {
				showToast("请输入正确的手机号");
			} else {
				requestData();
			}
			break;
		default:
			break;
		}
	}

	/** 找回密码 */
	public void requestData() {
		AjaxParams params = new AjaxParams();
		params.put("phone", mPhone);
		httpRequest.get(Urls.FIND_PWD, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		showToast(message);
		this.finish();
	}
}
