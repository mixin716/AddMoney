package com.zc.addmony.ui.buyproduct;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.ui.myproduct.LoginActivity;
import com.zc.addmony.utils.AnimUtil;

/** 购买基金输入金额 */
public class BuyProductActivity extends BaseActivity {
	private TextView tvFundName, tvMinPrice;
	private EditText edtPrice;
	private Button btnNext;
	private String fundName, minPrice;
	private UserSharedData User;
	private Intent intent;
	private MApplication app;
	private int intoFlag = 0;// 进入标示 0从増财宝进入 1从基金列表进入 1需要登录0已经登录
	private String price;// 输入金额

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
		intoFlag = this.getIntent().getIntExtra("intoFlag", 0);
		if (TextUtils.isEmpty(this.getIntent().getStringExtra("fundName"))) {
			fundName = app.fundBean.getFundname();
		} else {
			fundName = this.getIntent().getStringExtra("fundName");
		}
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
		if(!TextUtils.isEmpty(fundName) && !"null".equals(fundName)){
			tvFundName.setText(fundName);
		}
		tvMinPrice.setText("￥" + minPrice + "元起购");
		edtPrice.setHint(">" + minPrice + "元");

	}

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_buy_product_btn_next:// 下一步
			price = edtPrice.getText().toString().trim();
			if (TextUtils.isEmpty(price)) {
				showToast("请输入购买金额");
			} else if (Integer.valueOf(price) < Integer.valueOf(minPrice)) {
				showToast("当前购买金额小于起购金额");
			} else {
				if (User.GetFlag()) {// 已登录
					// 未绑定银行卡
					intent = new Intent(this, VerifyBuyActivity.class);
					intent.putExtra("money", price);
					startActivityForResult(intent, 101);
				} else {
					showToast("请先登录");
					intent = new Intent(this, LoginActivity.class);
					startActivity(intent);
				}
				AnimUtil.pushLeftInAndOut(this);
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 101){
			this.setResult(101);
			this.finish();
		}
	}

}
