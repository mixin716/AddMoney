package com.zc.addmony.ui.buyproduct;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.ui.myproduct.LoginActivity;

public class BuyProductActivity extends BaseActivity {
	private TextView tvFundName, tvMinPrice;
	private EditText edtPrice;
	private Button btnNext;
	private String fundName, minPrice;
	private UserSharedData User;
	private Intent intent;
	private MApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_buy_product_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		app = (MApplication) getApplication();
		User = UserSharedData.getInstance(getApplicationContext());
		fundName = app.fundBean.getFundname();
		minPrice = getIntent().getStringExtra("minPrice");

	}

	@Override
	protected void setTitleViews() {
		titleText.setText("增财基金");

	}

	@Override
	protected void setViews() {
		tvFundName = (TextView) findViewById(R.id.activity_buy_product_tv_fundname);
		tvMinPrice = (TextView) findViewById(R.id.activity_buy_product_btn_minprice);
		edtPrice = (EditText) findViewById(R.id.activity_buy_product_edt_price);
		btnNext = (Button) findViewById(R.id.activity_buy_product_btn_next);
		btnNext.setOnClickListener(this);
		tvFundName.setText(fundName);
		tvMinPrice.setText("￥" + minPrice + "起购");
		edtPrice.setHint(">" + minPrice);

	}

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			break;
		case R.id.activity_buy_product_btn_next:// 下一步
			if(User.GetFlag()){//已登录
				//未绑定银行卡
				intent = new Intent(this,PerfectInformationActivity.class);
			}else{
				intent = new Intent(this,LoginActivity.class);
			}
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
