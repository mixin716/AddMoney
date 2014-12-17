package com.zc.addmony.ui.buyproduct;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

/** 设置交易密码*/
public class SetBuyPwdActivity extends BaseActivity{

	private EditText etPwd;
	private Button btSure;
	private UserSharedData userShare;
	private String pwd;
	private MApplication app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_set_buy_pwd_layout);
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
		titleText.setText("完善信息");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		etPwd = (EditText) findViewById(R.id.activity_set_buy_pwd_et_pwd);
		btSure = (Button) findViewById(R.id.activity_set_buy_pwd_bt_next);
		btSure.setOnClickListener(this);
	}
	
	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(SetBuyPwdActivity.this);
			break;
		case R.id.activity_set_buy_pwd_bt_next:
			KeyBoard.demissKeyBoard(getApplicationContext(), etPwd);
			pwd = etPwd.getText().toString().trim();
			if(TextUtils.isEmpty(pwd)){
				showToast("请输入交易密码");
			}else{
				Intent intent = new Intent(this,VerifyBuyActivity.class);
				startActivity(intent);
			}
			break;
		}
	}
	
	/** 设置交易密码*/
	public void requestSetPwd(){
		AjaxParams params = new AjaxParams();
		params.put("password", pwd);
		httpRequest.get(Urls.SET_BUY_PWD, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		
	}
	
}
