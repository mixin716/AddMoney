package com.zc.addmony.ui.myproduct;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.myproduct.BuyProductsBean;
import com.zc.addmony.bean.myproduct.SaleBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicMyProduct;
import com.zc.addmony.ui.buyproduct.SelectActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;

/** 提现赎回 */
public class SaleMoneyActivity extends BaseActivity {
	private MApplication app;
	private LinearLayout llNo;
	private TextView tvFundName, tvSaleMoney, tvSaleTitle, tvHaveMoney,tvHaveMoneyTitle,
			tvBankCode;
	private EditText edtMinSaleMoney, edtSalePwd;
	private Button btnOk;
	private ImageView imgBank;
	private String fundName, saleMoney, haveMoney, bankName, bankCode,
			minMoney, salePwd, marketvalue,sharetype;
	private UserSharedData userShare;
	private List<SaleBean> list;
	private ArrayList<String> names;
	private int position = 0;
	private String fundcode;
	private BuyProductsBean bpBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_sale_money_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		fundcode = this.getIntent().getStringExtra("fundcode");
		app = (MApplication) getApplication();
		app.addAllActivity(this);
		userShare = UserSharedData.getInstance(getApplicationContext());
		list = new ArrayList<SaleBean>();
		names = new ArrayList<String>();
	}

	@Override
	protected void setTitleViews() {
		titleText.setText("赎回");

	}

	@Override
	protected void setViews() {
		llNo = (LinearLayout) findViewById(R.id.activity_sale_money_ll_no);
		tvBankCode = (TextView) findViewById(R.id.activity_sale_money_tv_bank_code);
		tvFundName = (TextView) findViewById(R.id.activity_sale_money_tv_fund_name);
		tvHaveMoney = (TextView) findViewById(R.id.activity_sale_money_tv_have_money);
		tvHaveMoneyTitle = (TextView) findViewById(R.id.activity_sale_money_tv_have_money_title);
		tvSaleMoney = (TextView) findViewById(R.id.activity_sale_money_tv_have_sale);
		tvSaleTitle = (TextView) findViewById(R.id.activity_sale_money_tv_have_sale_title);

		imgBank = (ImageView) findViewById(R.id.activity_sale_money_img_bank);

		edtMinSaleMoney = (EditText) findViewById(R.id.activity_sale_money_edt_minmoney);
		edtSalePwd = (EditText) findViewById(R.id.activity_sale_money_edt_pwd);
		btnOk = (Button) findViewById(R.id.activity_sale_money_btn_ok);
		btnOk.setOnClickListener(this);
		tvBankCode.setOnClickListener(this);

		if (app.zcbCode.equals(fundcode)) {
			imgBank.setVisibility(View.VISIBLE);
			getFundSharelist();
			getUserFundInfoRequest();
		} else {
			imgBank.setVisibility(View.GONE);
			bpBean = app.bpBean;
			fundcode = bpBean.getmFundcode();
			sharetype = bpBean.getmSharetype();
			fundName = bpBean.getmFundname();
			tvFundName.setText(bpBean.getmFundname());
			String code = bpBean.getmBank().substring(0, 6)
					+ "***"
					+ bpBean.getmBank().substring(bpBean.getmBank().length() - 4,
							bpBean.getmBank().length());
			tvBankCode.setText(bpBean.getmBankName() + "(" + code + ")");
			tvSaleMoney.setText("￥" + bpBean.getmHave());
//			if ("1109".equals(bpBean.getmFundTypeCode())) {
//				tvSaleTitle.setText("可赎回金额:");
//				tvHaveMoneyTitle.setText("未结算收益：");
//				tvHaveMoney.setText("￥" + bpBean.getmNot());
//			} else {
				tvSaleTitle.setText("可赎回份额:");
				//基金市值
				tvHaveMoneyTitle.setText("基金市值：");
				tvHaveMoney.setText("￥" + bpBean.getMarketvalue());
//			}
			
		}

	}

	private void getUserFundInfoRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("fundcode", fundcode);
		// params.put("fundcode", app.fundBean.getFundcode());
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GET_USER_FUND_INFO, params, callBack, 0);

	}

	private void getSaleMoneyRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("fundcode", fundcode);
		params.put("sharetype", sharetype);
		params.put("applysum", minMoney);
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.SALE_MONEY, params, callBack, 1);
	}

	/** 可赎回金额 */
	private void getFundSharelist() {
		AjaxParams params = new AjaxParams();
		params.put("fundcode", app.zcbCode);
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GETFundSharelist, params, callBack, 2);
	}

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_sale_money_btn_ok:
			KeyBoard.demissKeyBoard(getApplicationContext(), edtMinSaleMoney);
			minMoney = edtMinSaleMoney.getText().toString();
			salePwd = edtSalePwd.getText().toString();
			if (TextUtils.isEmpty(minMoney)) {
				showToast("请输入赎回金额");
				// } else if (TextUtils.isEmpty(salePwd)) {
				// showToast("请输入交易密码");
				// } else {
			} else {
				if (Integer.valueOf(minMoney) < 100) {
					showToast("最低赎回金额为100");
				} else {
					getSaleMoneyRequest();
				}
			}
			break;
		case R.id.activity_sale_money_tv_bank_code:
			if (app.zcbCode.equals(fundcode)) {
				Intent intent = new Intent(this, SelectActivity.class);
				intent.putStringArrayListExtra("nameList", names);
				startActivityForResult(intent, 101);
				AnimUtil.pushLeftInAndOut(this);
			}
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
					JSONObject obj = new JSONObject(jsonString);
					fundName = obj.optString("fundname");
					saleMoney = obj.optString("usableremainshare");
					haveMoney = obj.optString("unpaidincome");
					bankName = obj.optString("bankname");
					bankCode = obj.optString("bankacco");
					sharetype = obj.optString("sharetype");
					marketvalue = obj.optString("marketvalue");
					if (!TextUtils.isEmpty(bankCode)) {
						String code = bankCode.substring(0, 6)
								+ "***"
								+ bankCode.substring(bankCode.length() - 4,
										bankCode.length());
						tvBankCode.setText(bankName + "(" + code + ")");
					} else {
						tvBankCode.setText(bankName);
					}

					tvFundName.setText(fundName);
					tvSaleMoney.setText("￥" + saleMoney);

					JSONObject fundObj = new JSONObject(
							obj.optString("fundinfo"));
//					if (!TextUtils.isEmpty(fundObj.optString("FundTypeCode"))) {
//						if ("1109".equals(fundObj.optString("FundTypeCode"))) {
//							tvSaleTitle.setText("可赎回金额:");
//							tvHaveMoneyTitle.setText("未结算金额：");
//							tvHaveMoney.setText("￥" + haveMoney);
//						} else {
							tvSaleTitle.setText("可赎回份额:");
							tvHaveMoneyTitle.setText("基金市值：");
							tvHaveMoney.setText("￥" + marketvalue);
//						}
//					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			sendBroadcast(new Intent("refresh_products"));
			sendBroadcast(new Intent("refresh_my_product"));
			this.setResult(101);
			finish();
			AnimUtil.pushRightInAndOut(this);
			showToast("赎回成功");
			break;
		case 2:
			list = LogicMyProduct.parseSale(jsonString);
//			 setData(0);
			for (SaleBean bean : list) {
				if (!TextUtils.isEmpty(bean.getBankacco())) {
					String code = bean.getBankacco().substring(0, 6)
							+ "***"
							+ bean.getBankacco().substring(
									bean.getBankacco().length() - 4,
									bean.getBankacco().length());
					names.add(bean.getBankname() + "(" + code + ")");
				} else {
					names.add(bean.getBankname());
				}
			}
			break;
		}
	}

	public void setData(int pos) {
		position = pos;
		SaleBean bean = list.get(pos);
		// getUserFundInfoRequest(list.get(position).getFundcode());//每次赎回份额是否一定如果一定就不用请求了
		if (!TextUtils.isEmpty(bean.getBankacco())) {
			String code = bean.getBankacco().substring(0, 6)
					+ "***"
					+ bean.getBankacco().substring(
							bean.getBankacco().length() - 4,
							bean.getBankacco().length());
			tvBankCode.setText(bean.getBankname() + "(" + code + ")");
		} else {
			tvBankCode.setText(bean.getBankname());
		}

		tvFundName.setText(bean.getFundname());
		tvSaleMoney.setText("￥" + bean.getUsableremainshare());

		if (!TextUtils.isEmpty(bean.getFundTypeCode())) {
			
//			if ("1109".equals(bean.getFundTypeCode())) {
//				tvSaleTitle.setText("可赎回金额:");
//				tvHaveMoneyTitle.setText("未结算收益：");
//				tvHaveMoney.setText("￥" + bean.getUnpaidincome());
//			} else {
				tvSaleTitle.setText("可赎回份额:");
				//基金市值
				tvHaveMoneyTitle.setText("基金市值：");
				tvHaveMoney.setText("￥" + bean.getMarketvalue());
//			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {

			String name = data.getStringExtra("name");
			position = data.getIntExtra("postion", 0);
			SaleBean bean = list.get(position);
			String code = bean.getBankacco().substring(0, 6)
					+ "***"
					+ bean.getBankacco().substring(
							bean.getBankacco().length() - 4,
							bean.getBankacco().length());
			tvBankCode.setText(bean.getBankname() + "(" + code + ")");
			setData(position);
		}
	}

}
