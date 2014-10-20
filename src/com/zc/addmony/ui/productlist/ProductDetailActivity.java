package com.zc.addmony.ui.productlist;

import java.text.DecimalFormat;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.TuijianBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicProductList;
import com.zc.addmony.ui.buyproduct.BuyProductActivity;
import com.zc.addmony.utils.AnimUtil;

public class ProductDetailActivity extends BaseActivity implements
		OnCheckedChangeListener {
	private RadioGroup rgContent;
	private LinearLayout llIncome, llInformation;
	private TextView tvFundName, tvMinPrice, tvIncomeRate, tvPeople;
	private Button btnBuy, btnCount;
	private EditText edtMoney, edtDay;
	private TextView tvIncome, tvBankIncome, tvYearRate, tvWeekRate,
			tvMonthRate, tvMillionRate, tvFundCompany;
	private String fundcode;
	private TuijianBean bean;
	private MApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_product_detail_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		app = (MApplication) getApplication();
		fundcode = app.fundBean.getFundcode();

	}

	@Override
	protected void setTitleViews() {
		titleText.setText("增财基金");

	}

	@Override
	protected void setViews() {
		rgContent = (RadioGroup) findViewById(R.id.activity_product_detail_rg);
		llIncome = (LinearLayout) findViewById(R.id.activity_product_detail_ll_income);
		llInformation = (LinearLayout) findViewById(R.id.activity_product_detail_ll_information);
		btnBuy = (Button) findViewById(R.id.activity_product_detail_btn_buy);
		btnCount = (Button) findViewById(R.id.activity_product_detail_btn_ok);

		tvBankIncome = (TextView) findViewById(R.id.activity_product_detail_tv_bank_input);
		tvFundName = (TextView) findViewById(R.id.activity_product_detail_tv_fundname);
		tvFundCompany = (TextView) findViewById(R.id.activity_product_detail_tv_fund_company);
		tvIncome = (TextView) findViewById(R.id.activity_product_detail_tv_input);
		tvIncomeRate = (TextView) findViewById(R.id.activity_product_detail_tv_rate);
		tvMillionRate = (TextView) findViewById(R.id.activity_product_detail_tv_million_rate);
		tvMinPrice = (TextView) findViewById(R.id.activity_product_detail_tv_minprice);
		tvMonthRate = (TextView) findViewById(R.id.activity_product_detail_tv_month_rate);
		tvPeople = (TextView) findViewById(R.id.activity_product_detail_tv_people);
		tvWeekRate = (TextView) findViewById(R.id.activity_product_detail_tv_week_rate);
		tvYearRate = (TextView) findViewById(R.id.activity_product_detail_tv_year_rate);
		edtDay = (EditText) findViewById(R.id.activity_product_detail_edt_day);
		edtMoney = (EditText) findViewById(R.id.activity_product_detail_edt_money);

		rgContent.setOnCheckedChangeListener(this);
		btnBuy.setOnClickListener(this);
		btnCount.setOnClickListener(this);
		rgContent.setOnCheckedChangeListener(this);
		llIncome.setVisibility(View.VISIBLE);
		llInformation.setVisibility(View.GONE);
		getProductDetail();

	}

	/**
	 * 获取产品详情
	 */
	private void getProductDetail() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("fundcode", fundcode);
		httpRequest.get(Urls.PRODUCT_DETAIL, params, callBack, 0);

	}

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_product_detail_btn_buy:// 购买
			Intent intent = new Intent(this, BuyProductActivity.class);
			intent.putExtra("minPrice", bean.getMinprice());
			startActivity(intent);

			break;
		case R.id.activity_product_detail_btn_ok:// 计算
			String day = edtDay.getText().toString();
			String everyprice = edtMoney.getText().toString();
			int dayNum,
			priceNum;
			if (TextUtils.isEmpty(everyprice)) {
				showToast("请输入购买金额");
			} else if (TextUtils.isEmpty(day)) {
				showToast("请输入理财期限");
			} else {
				dayNum = Integer.valueOf(day);
				priceNum = Integer.valueOf(everyprice);
				double product = dayNum * priceNum
						* Double.valueOf(bean.getIncomeratio());
				double bankIncome = dayNum * priceNum * 0.35;
				DecimalFormat df = new DecimalFormat("0.00");
				tvIncome.setText("￥" + df.format(product / (100 * 365)) + "元");
				tvBankIncome.setText("￥" + df.format(bankIncome / (100 * 365))
						+ "元");

			}

			break;

		default:
			break;
		}
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		dismissLoading();
		switch (reqeustCode) {
		case 0:
			try {
				bean = LogicProductList.parseProductDetail(jsonString);
				tvMinPrice.setText("起购金币：￥" + bean.getMinprice());
				DecimalFormat df = new DecimalFormat("0.00");
				tvIncomeRate.setText(df.format(Double.valueOf(bean
						.getIncomeratio())) + "%");
				tvPeople.setText("购买人数：" + bean.getBought() + "人");
				tvYearRate.setText(df.format(Double.valueOf(bean.getYnzf()))
						+ "%");
				tvWeekRate.setText(df.format(Double.valueOf(bean.getQrnh()))
						+ "%");
				tvMonthRate.setText(df.format(Double.valueOf(bean.getLyzf()))
						+ "%");
				tvMillionRate.setText(df.format(Double.valueOf(bean
						.getHf_incomeratio())) + "%");
				tvFundCompany.setText(bean.getFundCompany());
				tvFundName.setText(bean.getFundname());

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.activity_product_detail_rb_income:// 计算收益
			llIncome.setVisibility(View.VISIBLE);
			llInformation.setVisibility(View.GONE);
			break;
		case R.id.activity_product_detail_rb_information:// 基本信息
			llIncome.setVisibility(View.GONE);
			llInformation.setVisibility(View.VISIBLE);

			break;

		default:
			break;
		}

	}

}
