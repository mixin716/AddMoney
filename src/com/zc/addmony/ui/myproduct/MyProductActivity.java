package com.zc.addmony.ui.myproduct;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.utils.AnimUtil;

public class MyProductActivity extends BaseActivity {

	private TextView tvName, tvPhone, tvMoneyAll, tvMoneyYes, tvBuy;
	private LinearLayout llTop, llYesterday, llBuy;
	private UserSharedData userShare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_my_product_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		userShare = UserSharedData.getInstance(getApplicationContext());
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
		tvName = (TextView) findViewById(R.id.activity_my_product_tv_open);
		tvPhone = (TextView) findViewById(R.id.activity_my_product_tv_phone);
		tvMoneyAll = (TextView) findViewById(R.id.activity_my_product_tv_moneyAll);
		tvMoneyYes = (TextView) findViewById(R.id.activity_my_product_tv_moneyYes);
		tvBuy = (TextView) findViewById(R.id.activity_my_product_tv_already);
		llTop = (LinearLayout) findViewById(R.id.activity_my_product_ll_top);
		llYesterday = (LinearLayout) findViewById(R.id.activity_my_product_ll_yestoday);
		llBuy = (LinearLayout) findViewById(R.id.activity_my_product_ll_buy);

		llTop.setOnClickListener(this);
		llYesterday.setOnClickListener(this);
		llBuy.setOnClickListener(this);

		if (userShare.GetOpenFlag() == 1) {// 已开户
			requestUserInfo();
		} else {
			tvName.setText("未开户");
			tvPhone.setText(userShare.GetName());
			tvMoneyAll.setText("0元");
			tvMoneyYes.setText("0元");
			tvBuy.setText("共0个基金产品");
		}
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		Intent intent;
		switch (viewId) {
		case R.id.activity_my_product_ll_top:
			intent = new Intent(this, ProductSetActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(MyProductActivity.this);
			break;
		case R.id.activity_my_product_ll_yestoday:
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(MyProductActivity.this);
			break;
		case R.id.activity_my_product_ll_buy:
			// if ("共0个基金产品".equals(tvBuy.getText().toString().trim())) {
			// showToast("您暂未购买基金");
			// }
			intent = new Intent(this, BuyProductsActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(MyProductActivity.this);
			break;
		}
	}

	/** 请求用户信息 */
	public void requestUserInfo() {
		AjaxParams params = new AjaxParams();
		httpRequest.get(Urls.GET_USER_INRO, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		try {
			JSONObject obj = new JSONObject(jsonString);
			tvMoneyAll.setText(obj.optInt("sum") + "");
			tvMoneyYes.setText(obj.optInt("content") + "");
			tvBuy.setText("共0个基金产品");
			tvName.setText(obj.optString("realname"));
			tvPhone.setText(obj.optString("phone"));
			userShare.SaveBankSum(obj.optString("banksum"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
