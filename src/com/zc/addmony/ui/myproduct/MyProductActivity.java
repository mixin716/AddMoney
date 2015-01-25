package com.zc.addmony.ui.myproduct;

import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class MyProductActivity extends BaseActivity {

	private LinearLayout llIsLogin, llNoLogin;
	private ImageView imgHead;//头像
	private RelativeLayout rlInfo;//顶部姓名身份证号
	private TextView tvName, tvPhone, tvMoneyAll, tvMoneyYes, tvBuy, tvDeal;
	private LinearLayout llTop, llYesterday, llBuy, llDeal;
	private UserSharedData userShare;
	// 登录控件及数据
	private EditText etPhone, etPwd;
	private TextView tvForget;
	private Button btRegister, btLogin;
	private String mPhone, mPwd;
	private LoginBean bean;
	private String PHPSESSID = null;

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if("refresh_my_product".equals(intent.getAction())){
				requestUserInfo();
			}else if("login_my_product".equals(intent.getAction())){
				requestLogin();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userShare = UserSharedData.getInstance(getApplicationContext());
		setContentViewRes(R.layout.activity_my_product_layout);
		setViews();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (userShare.GetFlag()) {
			if (llNoLogin != null) {
				llNoLogin.setVisibility(View.GONE);
				llIsLogin.setVisibility(View.VISIBLE);
			}
			
		} else {
			if (llNoLogin != null) {
				llNoLogin.setVisibility(View.VISIBLE);
				llIsLogin.setVisibility(View.GONE);
			}
		}
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		bean = new LoginBean();
		IntentFilter filter = new IntentFilter();
		filter.addAction("refresh_my_product");
		filter.addAction("login_my_product");
		registerReceiver(receiver, filter);
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleIvLeft.setVisibility(View.INVISIBLE);
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("我的増财");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		llIsLogin = (LinearLayout) findViewById(R.id.activity_my_product_ll_islogin);
		llNoLogin = (LinearLayout) findViewById(R.id.activity_my_product_ll_nologin);
		tvName = (TextView) findViewById(R.id.activity_my_product_tv_open);
		tvPhone = (TextView) findViewById(R.id.activity_my_product_tv_phone);
		tvMoneyAll = (TextView) findViewById(R.id.activity_my_product_tv_moneyAll);
		tvMoneyYes = (TextView) findViewById(R.id.activity_my_product_tv_moneyYes);
		tvBuy = (TextView) findViewById(R.id.activity_my_product_tv_already);
		tvDeal = (TextView) findViewById(R.id.activity_my_product_tv_deal);
		llTop = (LinearLayout) findViewById(R.id.activity_my_product_ll_top);
		llYesterday = (LinearLayout) findViewById(R.id.activity_my_product_ll_yestoday);
		llBuy = (LinearLayout) findViewById(R.id.activity_my_product_ll_buy);
		llDeal = (LinearLayout) findViewById(R.id.activity_my_product_ll_deal);
		imgHead = (ImageView) findViewById(R.id.activity_my_product_img_head);
		rlInfo = (RelativeLayout) findViewById(R.id.activity_my_product_rl_info);

		llTop.setOnClickListener(this);
		llYesterday.setOnClickListener(this);
		llBuy.setOnClickListener(this);
		llDeal.setOnClickListener(this);
		imgHead.setOnClickListener(this);
		rlInfo.setOnClickListener(this);
		etPhone = (EditText) findViewById(R.id.activity_login_et_phone);
		etPwd = (EditText) findViewById(R.id.activity_login_et_pwd);
		tvForget = (TextView) findViewById(R.id.activity_login_tv_forget);
		btRegister = (Button) findViewById(R.id.activity_login_bt_register);
		btLogin = (Button) findViewById(R.id.activity_login_bt_login);

		btRegister.setOnClickListener(this);
		btLogin.setOnClickListener(this);
		tvForget.setOnClickListener(this);

		if (userShare.GetFlag()) {
			if (userShare.GetOpenFlag() == 1) {// 已开户
				requestUserInfo();
			} else {
				tvName.setText("未开户");
				tvPhone.setText(userShare.GetName());
				tvMoneyAll.setText("0元");
				tvMoneyYes.setText("0元");
				tvBuy.setText("共0个基金产品");
			}
			llNoLogin.setVisibility(View.GONE);
			llIsLogin.setVisibility(View.VISIBLE);
		} else {
			llNoLogin.setVisibility(View.VISIBLE);
			llIsLogin.setVisibility(View.GONE);
		}
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		Intent intent;
		switch (viewId) {
//		case R.id.activity_my_product_ll_top:
		case R.id.activity_my_product_img_head:
		case R.id.activity_my_product_rl_info:
			intent = new Intent(this, ProductSetActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(MyProductActivity.this);
			break;
		case R.id.activity_my_product_ll_yestoday:// 增财宝
			intent = new Intent(this, IncreaseWealthActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(MyProductActivity.this);
			break;
		case R.id.activity_my_product_ll_buy:// 进入基金列表界面
			if ("共0个基金产品".equals(tvBuy.getText().toString().trim())) {
				showToast("您暂未购买基金");
			} else {
				intent = new Intent(this, BuyProductsActivity.class);
				startActivity(intent);
				AnimUtil.pushLeftInAndOut(MyProductActivity.this);
			}
			break;
		case R.id.activity_my_product_ll_deal:// 进入当日交易申请界面
			if("共0条交易申请".equals(tvDeal.getText().toString())){
				showToast("您没有交易申请");
			}else{
			intent = new Intent(this, TodayDealActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(MyProductActivity.this);
			}
			break;
		case R.id.activity_login_bt_register:
			intent = new Intent(this, RegisterSecondActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(MyProductActivity.this);
			break;
		case R.id.activity_login_bt_login:
			mPhone = etPhone.getText().toString().trim();
			mPwd = etPwd.getText().toString().trim();
			KeyBoard.demissKeyBoard(getApplicationContext(), etPhone);
			KeyBoard.demissKeyBoard(getApplicationContext(), etPwd);
			if (TextUtils.isEmpty(mPhone)) {
				showToast("请输入手机号或身份证号");
			} else if (TextUtils.isEmpty(mPwd)) {
				showToast("请输入密码");
			} else {
				requestLogin();
			}
			break;
		case R.id.activity_login_tv_forget:// 忘记密码
			intent = new Intent(this, FindPwdActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(MyProductActivity.this);
			break;
		}
	}

	/** 请求用户信息 */
	public void requestUserInfo() {
		showLoading();
		AjaxParams params = new AjaxParams();
		Log.e("", userShare.GetSession() + "  ");
		if (TextUtils.isEmpty(PHPSESSID)) {
			httpRequest.addHeader("Cookie",
					"PHPSESSID=" + userShare.GetSession());
		}
		httpRequest.get(Urls.GET_USER_INRO, params, callBack, 0);
	}

	/** 登录 */
	public void requestLogin() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("username", mPhone);
		params.put("password", mPwd);
		httpRequest.get(Urls.LOGIN, params, callBack, 1);
	}

	private Handler cookieHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			DefaultHttpClient httpClient;
			httpClient = (DefaultHttpClient) httpRequest.getHttpClient();
			CookieStore mCookieStore = httpClient.getCookieStore();
			List<Cookie> cookies = mCookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				// 这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
				if ("PHPSESSID".equals(cookies.get(i).getName())) {
					PHPSESSID = cookies.get(i).getValue();
					userShare.SaveSession(PHPSESSID);
					break;
				}
			}
			requestUserInfo();
		};
	};

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		switch (reqeustCode) {
		case 0:
			try {
				llNoLogin.setVisibility(View.GONE);
				llIsLogin.setVisibility(View.VISIBLE);
				JSONObject obj = new JSONObject(jsonString);
				tvMoneyAll.setText(obj.optInt("sum") + "元");
				tvMoneyYes.setText(obj.optString("zcbsum") + "元");
				tvBuy.setText("共" + obj.optInt("count") + "个基金产品");
				tvName.setText(obj.optString("realname"));
				tvPhone.setText(obj.optString("phone"));
				userShare.SaveBankSum(obj.optString("banksum"));
				tvDeal.setText("共" + obj.optString("todayTransaction")
						+ "条交易申请");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:
			cookieHandler.sendEmptyMessageDelayed(0, 200);
			showToast("登录成功");
			bean = LogicPerson.parseLogin(jsonString);
			userShare.SaveFlag(true);
			userShare.SaveLoginAccount(mPhone);
			userShare.SavePhone(etPhone.getText().toString().trim());
			userShare.SavePwd(etPwd.getText().toString().trim());
			userShare.SavePhone(bean.getPhone());
			userShare.SaveId(bean.getUser_id());
			userShare.SaveName(bean.getUser_name());
			userShare.SaveOpenFlag(bean.getOpenflag());
			userShare.SaveBuyPwd(bean.getTrade_pwd());
			userShare.SaveToken(bean.getToken());
			userShare.SaveRealname(bean.getRealname());
			userShare.SaveIdcard(bean.getIdcard());
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent("refresh_tab");
			intent.putExtra("intoSelect", 1);
			this.sendBroadcast(intent);
			return true;
		}
		return false;
	}
}
