package com.zc.addmony.ui.buyproduct;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.ui.myproduct.LoginActivity;
import com.zc.addmony.utils.AnimUtil;

/** 购买基金输入金额 */
public class BuyProductActivity extends BaseActivity {
	private TextView tvFundName, tvMinPrice, tvSxf;// 手续费
	private EditText edtPrice;
	private Button btnNext;
	private String fundName, minPrice="    ";
	private UserSharedData User;
	private Intent intent;
	private MApplication app;
	private int intoFlag = 0;// 进入标示 0从増财宝进入 1从基金列表进入 1需要登录0已经登录
	private String price;// 输入金额
	private String FundTypeCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_buy_product_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		app = (MApplication) getApplication();
		app.addAllActivity(this);
		FundTypeCode = this.getIntent().getStringExtra("FundTypeCode");
		User = UserSharedData.getInstance(getApplicationContext());
		intoFlag = this.getIntent().getIntExtra("intoFlag", 0);
		if (TextUtils.isEmpty(this.getIntent().getStringExtra("fundName"))) {
			fundName = app.fundBean.getFundname();
		} else {
			fundName = this.getIntent().getStringExtra("fundName");
		}
		// minPrice = getIntent().getStringExtra("minPrice");

	}

	@Override
	protected void setTitleViews() {
		titleText.setText("增财基金");

	}

	@Override
	protected void setViews() {
		tvFundName = (TextView) findViewById(R.id.activity_buy_product_tv_fundname);
		tvMinPrice = (TextView) findViewById(R.id.activity_buy_product_btn_minprice);
		tvSxf = (TextView) findViewById(R.id.activity_buy_product_tv_sxf);
		edtPrice = (EditText) findViewById(R.id.activity_buy_product_edt_price);
		btnNext = (Button) findViewById(R.id.activity_buy_product_btn_next);
		btnNext.setOnClickListener(this);
		if (!TextUtils.isEmpty(fundName) && !"null".equals(fundName)) {
			tvFundName.setText(fundName);
		}
		tvMinPrice.setText("起购金额：￥" + minPrice);
		edtPrice.setHint(">" + minPrice + "元");
		if(!TextUtils.isEmpty(FundTypeCode)){
			if("1109".equals(FundTypeCode)){
				tvSxf.setText("手续费：0元");
			}else{
				tvSxf.setText("费率折扣：4折");
			}
		}else{
			tvSxf.setText("费率折扣：4折");
		}
		getNumberDetail();
	}

	/** 请求获取最低购买金额 */
	private void getNumberDetail() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("fundcode", app.fundBean.getFundcode());
		httpRequest.get(Urls.GET_PRODUCT_NUMBER, params, callBack, 1);
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
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		switch (reqeustCode) {
		case 1:
			edtPrice.setHint(">" + jsonString + "元");
			tvMinPrice.setText("起购金额：￥" + jsonString);
			minPrice = jsonString;
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 101) {
			this.setResult(101);
			this.finish();
		}
	}

}
