package com.zc.addmony;

import java.text.DecimalFormat;

import org.json.JSONException;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.bean.HomeBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicHome;
import com.zc.addmony.ui.activities.ActivitiesRuleActivity;
import com.zc.addmony.ui.activities.ConfirmationOrderActivity;
import com.zc.addmony.ui.activities.ShoppingActivity;
import com.zc.addmony.ui.activities.ShowPhonesA;
import com.zc.addmony.ui.lock.GestureActivity;
import com.zc.addmony.ui.myproduct.IncreaseWealthActivity;
import com.zc.addmony.ui.myproduct.LoginActivity;
import com.zc.addmony.ui.productlist.ProductDetailActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.view.lockview.LockPatternUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeActivity extends BaseActivity {
	private TextView tvTimes, tvRate, tvBank, tvInstitution, tvName,
			tvMinPrice, tvBought;
	private TextView tvFristPhone, tvFristMoney, tvSecondPhone, tvSecondMoney,
			tvThirdPhone, tvThirdMoney;
	private HomeBean bean;
	private RelativeLayout rlTodayContent, rlIncreaseWealth;
	private Intent intent;
	private MApplication app;
	private UserSharedData User;
	private ImageView ivTopActivity;

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
 
		if (!TextUtils.isEmpty(LockPatternUtils.getInstance(this)
				.getLockPaternString("user_key"))) {
			app.startVerify();
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), GestureActivity.class);
			intent.putExtra(GestureActivity.INTENT_MODE,
					GestureActivity.GESTURE_MODE_VERIFY);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
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
		rlIncreaseWealth = (RelativeLayout) findViewById(R.id.activity_home_rl_increase_wealth);
		rlTodayContent = (RelativeLayout) findViewById(R.id.activity_home_rl_today_content);
		rlIncreaseWealth.setOnClickListener(this);
		rlTodayContent.setOnClickListener(this);
		ivTopActivity.setOnClickListener(this);
		getInformationRequest();

	}

	private void getInformationRequest() {
		AjaxParams params = new AjaxParams();
		httpRequest.get(Urls.HOME, params, callBack, 0);
	}

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.activity_home_iv_activity:// 活动
			intent = new Intent(this, ActivitiesRuleActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(this);
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
				if (TextUtils.isEmpty(jsonString) || jsonString.equals("null")) {
					showToast("数据为空");
				} else {
					bean = LogicHome.pareseHome(jsonString);
					tvTimes.setText(bean.getBeishu() + "倍");
					DecimalFormat df = new DecimalFormat("0.00");
					tvRate.setText(df.format(Double.valueOf(bean.getTuijian()
							.getIncomeratio())) + "%");
					tvFristPhone
							.setText(bean.getPaiming().get(0).getUsername());
					tvFristMoney.setText("￥"
							+ bean.getPaiming().get(0).getIncome());
					tvSecondPhone.setText(bean.getPaiming().get(1)
							.getUsername());
					tvSecondMoney.setText("￥"
							+ bean.getPaiming().get(1).getIncome());
					tvThirdPhone
							.setText(bean.getPaiming().get(2).getUsername());
					tvThirdMoney.setText("￥"
							+ bean.getPaiming().get(2).getIncome());
					tvName.setText(bean.getTuijian().getFundname());
					tvMinPrice.setText(bean.getTuijian().getMinprice() + "元起购");
					tvBought.setText(bean.getTuijian().getBought() + "人已购");

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

}
