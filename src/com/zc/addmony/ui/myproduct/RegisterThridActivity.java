package com.zc.addmony.ui.myproduct;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.myproduct.LoginBean;
import com.zc.addmony.bean.myproduct.OpenBankBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicPerson;
import com.zc.addmony.ui.buyproduct.PerfectInformationActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;

/** 注册第三步 设置密码 */
public class RegisterThridActivity extends BaseActivity {

	private MApplication mApplication;
	private OpenBankBean obBean;
	private EditText etPwd, etPwdSecond;
	private Button btn;
	private String strPwd, strPwdSec;
	private UserSharedData userShare;
	private LoginBean bean;

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
		mApplication = (MApplication) this.getApplication();
		obBean = mApplication.getObBean();
		userShare = UserSharedData.getInstance(getApplicationContext());
		bean = new LoginBean();
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
				sendCheck();
			}
			break;
		}
	}

	/** 开户验证*/
	public void sendCheck() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("password", strPwd);
		params.put("name", obBean.getName());
		params.put("idcard", obBean.getIdcard());
		params.put("branchcode", obBean.getBranchcode());
		params.put("bnum", obBean.getBnum());
		params.put("bankcode", obBean.getBankcode());
		params.put("branch", obBean.getBranch());
		params.put("phone", obBean.getPhone());
		params.put("province", obBean.getProvince());
		params.put("city", obBean.getCity());
		httpRequest.get(Urls.OPEN_CHECK, params, callBack, 0);
	}
	
	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		switch (reqeustCode) {
		case 0:
			showToast("开户成功");
			bean = LogicPerson.parseLogin(jsonString);
			userShare.SaveFlag(true);
			userShare.SavePhone(bean.getPhone());
			userShare.SaveId(bean.getUser_id());
			userShare.SaveName(bean.getUser_name());
			userShare.SaveOpenFlag(bean.getOpenflag());
			userShare.SaveBuyPwd(bean.getTrade_pwd());
			userShare.SaveToken(bean.getToken());
			userShare.SaveRealname(bean.getRealname());
			userShare.SaveIdcard(bean.getIdcard());
			Intent intent = new Intent(this,RegisterSuccessActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(this);
			this.finish();
			break;

		default:
			break;
		}
		
	}
}
