package com.zc.addmony;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jky.struct2.bitmap.FinalBitmap;
import com.jky.struct2.bitmap.FinalBitmapManager;
import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.bean.HomeBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicHome;
import com.zc.addmony.ui.activities.ActivitiesRuleActivity;
import com.zc.addmony.ui.myproduct.IncreaseWealthActivity;
import com.zc.addmony.ui.myproduct.LoginActivity;
import com.zc.addmony.ui.productlist.ProductDetailActivity;
import com.zc.addmony.utils.AnimUtil;

public class HomeActivity extends BaseActivity {
	private TextView tvTimes, tvRate, tvBank, tvInstitution, tvName,
			tvMinPrice, tvBought, tvSmsm;
	private TextView tvFristPhone, tvFristMoney, tvSecondPhone, tvSecondMoney,
			tvThirdPhone, tvThirdMoney;
	private ImageView imgFund;
	private HomeBean bean;
	private RelativeLayout rlTodayContent, rlIncreaseWealth;
	private Intent intent;
	private MApplication app;
	private UserSharedData User;
	private ImageView ivTopActivity;
	private FinalBitmap finalBitmap;
	/** 用于判断退出 */
	private boolean isExit = false;
	private boolean activitysFlag = false;// 活动标示
	private String PHPSESSID = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_home_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		app = (MApplication) getApplication();
		User = UserSharedData.getInstance(getApplicationContext());
		bean = new HomeBean();
		finalBitmap = FinalBitmapManager.getFinalBitmapManager(
				getApplicationContext()).getFinalBitmap(
				FinalBitmapManager.IMG_SMALL);
		// if (!TextUtils.isEmpty(LockPatternUtils.getInstance(this)
		// .getLockPaternString("user_key"))) {
		// app.startVerify();
		// Intent intent = new Intent();
		// intent.setClass(getApplicationContext(), GestureActivity.class);
		// intent.putExtra(GestureActivity.INTENT_MODE,
		// GestureActivity.GESTURE_MODE_VERIFY);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		// startActivity(intent);
		// }
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleIvLeft.setVisibility(View.INVISIBLE);
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("首页");
	}

	@Override
	protected void setViews() {
		imgFund = (ImageView) findViewById(R.id.activity_home_iv_today_icon);
		ivTopActivity = (ImageView) findViewById(R.id.activity_home_iv_activity);
		tvBank = (TextView) findViewById(R.id.activity_home_tv_supervise_bank);
		tvFristMoney = (TextView) findViewById(R.id.activity_home_income_tv_frist_money);
		tvFristPhone = (TextView) findViewById(R.id.activity_home_income_tv_frist_phone);
		tvInstitution = (TextView) findViewById(R.id.activity_home_tv_supervise_institution);
		tvRate = (TextView) findViewById(R.id.activity_home_today_tv_rate);
		tvSecondMoney = (TextView) findViewById(R.id.activity_home_income_tv_second_money);
		tvSecondPhone = (TextView) findViewById(R.id.activity_home_income_tv_second_phone);
		tvThirdMoney = (TextView) findViewById(R.id.activity_home_income_tv_third_money);
		tvThirdPhone = (TextView) findViewById(R.id.activity_home_income_tv_third_phone);
		tvTimes = (TextView) findViewById(R.id.activity_home_money_tv_times);
		tvName = (TextView) findViewById(R.id.activity_home_today_tv_name);
		tvMinPrice = (TextView) findViewById(R.id.activity_home_today_tv_minprice);
		tvBought = (TextView) findViewById(R.id.activity_home_today_tv_bought);
		tvSmsm = (TextView) findViewById(R.id.activity_home_today_tv_smsm);
		rlIncreaseWealth = (RelativeLayout) findViewById(R.id.activity_home_rl_increase_wealth);
		rlTodayContent = (RelativeLayout) findViewById(R.id.activity_home_rl_today_content);
		rlIncreaseWealth.setOnClickListener(this);
		rlTodayContent.setOnClickListener(this);
		ivTopActivity.setOnClickListener(this);
		getInformationRequest();
		if (User.GetFlag()) {
			// requestOrder();
		}
	}

	private void getInformationRequest() {
		AjaxParams params = new AjaxParams();
		httpRequest.get(Urls.HOME, params, callBack, 0);
	}

	/** 请求订单 */
	public void requestOrder() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("idcard", User.GetIdcard());
		httpRequest.addHeader("Cookie", "PHPSESSID=" + User.GetSession());
		httpRequest.get(Urls.GET_ORDER, params, callBack, 1);
	}

	/** 登录 */
	public void requestLogin() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("username", User.getAccount());
		params.put("password", User.GetPwd());
		httpRequest.get(Urls.LOGIN, params, callBack, 2);
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
					User.SaveSession(PHPSESSID);
					break;
				}
			}
		};
	};

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.activity_home_iv_activity:// 活动
			if ("0".equals(bean.getActivitysType())) {
				showToast(bean.getActivitysMessage());
			} else {
				intent = new Intent(this, ActivitiesRuleActivity.class);
				startActivity(intent);
				AnimUtil.pushLeftInAndOut(this);
			}
			break;
		case R.id.activity_home_rl_increase_wealth:// 增财宝
			if (User.GetFlag()) {
				intent = new Intent(this, IncreaseWealthActivity.class);
			} else {
				intent = new Intent(this, LoginActivity.class);
			}
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(this);
			break;
		case R.id.activity_home_rl_today_content:// 今日推荐
			intent = new Intent(this, ProductDetailActivity.class);
			if (bean != null) {
				app.fundBean.setSharetype(bean.getTuijian().getFundtype());
				app.fundBean.setFundname(bean.getTuijian().getFundname());
				app.fundBean.setFundcode(bean.getTuijian().getFundcode());

			}
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(this);
			break;

		default:
			break;
		}
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		switch (reqeustCode) {
		case 0:
			try {
				if (User.GetFlag()) {
					requestLogin();
				}
				if (TextUtils.isEmpty(jsonString) || jsonString.equals("null")) {
					showToast("数据为空");
				} else {
					bean = LogicHome.pareseHome(jsonString);
					app.zcbCode = bean.getZcbCode();
					app.zcbShareType = bean.getZcbShareType();
					app.zcbFundtypecode = bean.getZcbFundtypecode();
					tvTimes.setText(bean.getBeishu() + "倍");
					DecimalFormat df = new DecimalFormat("0.00");
					// tvFristPhone
					// .setText(bean.getPaiming().get(0).getUsername());
					// tvFristMoney.setText("￥"
					// + bean.getPaiming().get(0).getIncome());
					// tvSecondPhone.setText(bean.getPaiming().get(1)
					// .getUsername());
					// tvSecondMoney.setText("￥"
					// + bean.getPaiming().get(1).getIncome());
					// tvThirdPhone
					// .setText(bean.getPaiming().get(2).getUsername());
					// tvThirdMoney.setText("￥"
					// + bean.getPaiming().get(2).getIncome());

					tvName.setText(bean.getTuijian().getFundname());// 推荐基金名
					tvRate.setText(df.format(Double.valueOf(bean.getTuijian()
							.getIncomeratio())) + "%");// 推进基金年化效益
					tvMinPrice.setText(bean.getTuijian().getMinprice() + "起购");
					tvBought.setText(bean.getTuijian().getBought() + "人已购");
					tvSmsm.setText(bean.getTuijian().getBuyinfo());
					finalBitmap.display(imgFund, bean.getImgUrl(),
							R.drawable.ic_home_today_icon);
					finalBitmap.display(ivTopActivity, bean.getActivitysImg(),
							R.drawable.ic_home_activity_top_icon);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			activitysFlag = true;
			User.SaveActivity(true);
			break;
		case 2:
			cookieHandler.sendEmptyMessageDelayed(0, 200);
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ExitProgram();
			return true;
		}
		return false;
	}

	public void ExitProgram() {
		if (!isExit) {
			isExit = true;
			showToast("再按一次\"退出\"程序");
			exitHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			this.finish();
		}
	}

	public Handler exitHandler = new Handler() {
		public void handleMessage(Message msg) {
			isExit = false;
		};
	};
}
