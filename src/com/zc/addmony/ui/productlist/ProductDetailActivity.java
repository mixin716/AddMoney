package com.zc.addmony.ui.productlist;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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
import com.zc.addmony.bean.productlist.ProductListBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicProductList;
import com.zc.addmony.ui.buyproduct.BuyProductActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;

public class ProductDetailActivity extends BaseActivity implements
		OnCheckedChangeListener {
	private RadioGroup rgContent;
	private LinearLayout llIncome, llInformation;
	private TextView tvFundName, tvMinPrice, tvIncomeRate, tvFundCode,
			tvBankRate, tvRiskLevel;
	private Button btnBuy, btnCount;
	private EditText edtMoney, edtDay;
	private TextView tvIncome, tvBankIncome, tvYearRate, tvWeekRate,
			tvMonthRate, tvMillionRate, tvFundCompany, tvJjjl, tvFundType;
	private String fundcode;
	private ProductListBean bean;
	private MApplication app;
	private String minPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_product_detail_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		app = (MApplication) getApplication();
		app.addAllActivity(this);
		fundcode = app.fundBean.getFundcode();
		bean = new ProductListBean();
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
		tvBankRate = (TextView) findViewById(R.id.activity_product_detail_tv_rate_bank);
		tvRiskLevel = (TextView) findViewById(R.id.activity_product_detail_tv_risklevel);
		tvFundName = (TextView) findViewById(R.id.activity_product_detail_tv_fundname);
		tvFundCompany = (TextView) findViewById(R.id.activity_product_detail_tv_fund_company);
		tvIncome = (TextView) findViewById(R.id.activity_product_detail_tv_input);
		tvIncomeRate = (TextView) findViewById(R.id.activity_product_detail_tv_rate);
		tvMillionRate = (TextView) findViewById(R.id.activity_product_detail_tv_million_rate);
		tvJjjl = (TextView) findViewById(R.id.activity_product_detail_tv_fund_jjjl);
		tvFundType = (TextView) findViewById(R.id.activity_product_detail_tv_fund_fundtype);
		tvMinPrice = (TextView) findViewById(R.id.activity_product_detail_tv_minprice);
		tvMonthRate = (TextView) findViewById(R.id.activity_product_detail_tv_month_rate);
		tvFundCode = (TextView) findViewById(R.id.activity_product_detail_tv_fundcode);
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
		getNumberDetail();
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

	/** 请求获取最低购买金额 */
	private void getNumberDetail() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("fundcode", fundcode);
		httpRequest.get(Urls.GET_PRODUCT_NUMBER, params, callBack, 1);
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
			mApplication.fundBean.setFundcode(fundcode);
			if (TextUtils.isEmpty(minPrice) || "0".equals(minPrice)) {
				minPrice = "1000";
			}
			intent.putExtra("FundTypeCode", bean.getFundTypeCode());
			intent.putExtra("minPrice", minPrice);
			startActivity(intent);
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_product_detail_btn_ok:// 计算
			KeyBoard.demissKeyBoard(getApplicationContext(), edtDay);
			String day = edtDay.getText().toString();
			String everyprice = edtMoney.getText().toString();
			int dayNum,
			priceNum;// 价格 天数
			float bankRate = (float) 0.35;// 银行获取利率
			if (TextUtils.isEmpty(everyprice)) {
				showToast("请输入购买金额");
			} else if (TextUtils.isEmpty(day)) {
				showToast("请输入理财期限");
			} else {
				dayNum = Integer.valueOf(day);
				priceNum = Integer.valueOf(everyprice);
				double product = dayNum * priceNum
						* Double.valueOf(bean.getLatestWeeklyYield());

				double bankIncome = dayNum * priceNum * bankRate;
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
			bean = LogicProductList.parseFundDetail(jsonString);
			// DecimalFormat df = new DecimalFormat("0.00");
			if (TextUtils.isEmpty(bean.getIncomeratio())) {
				tvIncomeRate.setText("0.00%");
			} else {
				BigDecimal b = new BigDecimal(bean.getIncomeratio());
				tvIncomeRate.setText(b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue() + "%");// 近期收益率
			}
			tvBankRate.setText("0.35%");// 银行活期利益
			if (TextUtils.isEmpty(bean.getRRInSingleYear())) {
				tvYearRate.setText("0.00%");
			} else {
				BigDecimal b = new BigDecimal(bean.getRRInSingleYear());
				tvYearRate.setText(b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue() + "%");
			}
			if (TextUtils.isEmpty(bean.getLatestWeeklyYield())) {
				tvWeekRate.setText("0.00%");
			} else {
				BigDecimal b = new BigDecimal(bean.getLatestWeeklyYield());
				tvWeekRate.setText(b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue() + "%");
			}
			if (TextUtils.isEmpty(bean.getRRInSixMonth())) {
				tvMonthRate.setText("0.00%");
			} else {
				BigDecimal b = new BigDecimal(bean.getRRInSixMonth());
				tvMonthRate.setText(b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue() + "%");
			}
			if (TextUtils.isEmpty(bean.getHfincomeratio())) {
				tvMillionRate.setText("0.00%");
			} else {
				BigDecimal b = new BigDecimal(bean.getHfincomeratio());
				tvMillionRate.setText(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue() + "%");
			}
			tvJjjl.setText(bean.getManager());// 基金经理

			tvFundCompany.setText(bean.getInvestAdvisorName());// 公司
			// 基金名称
			if (!TextUtils.isEmpty(bean.getFundname())) {
				tvFundName.setText(bean.getFundname());
			}
			if (!TextUtils.isEmpty(bean.getFundcode())) {
				tvFundCode.setText("基金代码：" + bean.getFundcode());
			}
			// 设置风险水平
			if (TextUtils.isEmpty(bean.getFundrisklevel())) {
				tvRiskLevel.setText("风险水平：低风险");
			} else {
				switch (Integer.valueOf(bean.getFundrisklevel())) {
				case 0:
					tvRiskLevel.setText("风险水平：低风险");
					break;
				case 1:
					tvRiskLevel.setText("风险水平：中风险");
					break;
				case 2:
					tvRiskLevel.setText("风险水平：高风险");
					break;
				case 3:
					tvRiskLevel.setText("风险水平：中低风险");
					break;
				case 4:
					tvRiskLevel.setText("风险水平：中高风险");
					break;
				}
			}
			// 设置基金类型
			if (TextUtils.isEmpty(bean.getFundTypeCode())) {
				tvFundType.setText("");
			} else {
				switch (Integer.valueOf(bean.getFundTypeCode())) {
				case 1101:
					tvFundType.setText("股票型");
					break;
				case 1103:
					tvFundType.setText("混合型");
					break;
				case 1105:
					tvFundType.setText("债券型");
					break;
				case 1107:
					tvFundType.setText("保本型");
					break;
				case 1109:
					tvFundType.setText("货币型");
					break;
				case 1199:
					tvFundType.setText("其他型");
					break;
				}
			}
			break;
		case 1:
			tvMinPrice.setText("起购金额：￥" + jsonString);
			minPrice = jsonString;
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
