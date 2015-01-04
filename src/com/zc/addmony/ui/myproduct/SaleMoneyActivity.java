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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
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
	private TextView tvFundName, tvSaleMoney, tvSaleTitle, tvHaveMoney,
			tvBankCode;
	private EditText edtMinSaleMoney, edtSalePwd;
	private Button btnOk;
	private String fundName, saleMoney, haveMoney, bankName, bankCode,
			minMoney, salePwd;
	private UserSharedData userShare;
	private List<SaleBean> list;
	private ArrayList<String> names;
	private int position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_sale_money_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
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
		tvSaleMoney = (TextView) findViewById(R.id.activity_sale_money_tv_have_sale);
		tvSaleTitle = (TextView) findViewById(R.id.activity_sale_money_tv_have_sale_title);

		edtMinSaleMoney = (EditText) findViewById(R.id.activity_sale_money_edt_minmoney);
		edtSalePwd = (EditText) findViewById(R.id.activity_sale_money_edt_pwd);
		btnOk = (Button) findViewById(R.id.activity_sale_money_btn_ok);
		btnOk.setOnClickListener(this);
		tvBankCode.setOnClickListener(this);
		// getUserFundInfoRequest();
		getFundSharelist();
	}

	private void getUserFundInfoRequest(String fundcode) {
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
		params.put("fundcode", list.get(position).getFundcode());
		params.put("sharetype", list.get(position).getSharetype());
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
			} else if (TextUtils.isEmpty(salePwd)) {
				showToast("请输入交易密码");
			} else {
				if (Integer.valueOf(minMoney) < 100) {
					showToast("最低赎回金额为100");
				} else {
					getSaleMoneyRequest();
				}
			}
			break;
		case R.id.activity_sale_money_tv_bank_code:
			if (list.size() > 1) {

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
					if (!TextUtils.isEmpty(bankCode)) {
						String code = "***"
								+ bankCode.substring(bankCode.length() - 4,
										bankCode.length());
						tvBankCode.setText(bankName + "(" + code + ")");
					} else {
						tvBankCode.setText(bankName);
					}

					tvFundName.setText(fundName);
					tvHaveMoney.setText("￥" + haveMoney);
					tvSaleMoney.setText("￥" + saleMoney);

					JSONObject fundObj = new JSONObject(
							obj.optString("fundinfo"));
					if (!TextUtils.isEmpty(fundObj.optString("FundTypeCode"))) {
						if ("1109".equals(fundObj.optString("FundTypeCode"))) {
							tvSaleTitle.setText("可赎回金额:");
						} else {
							tvSaleTitle.setText("可赎回份额:");
							llNo.setVisibility(View.GONE);
						}
					}
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
			setData(0);
			for (SaleBean bean : list) {
				if (!TextUtils.isEmpty(bean.getBankacco())) {
					String code = "***"
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
		getUserFundInfoRequest(list.get(position).getFundcode());
		if (!TextUtils.isEmpty(bean.getBankacco())) {
			String code = "***"
					+ bean.getBankacco().substring(
							bean.getBankacco().length() - 4,
							bean.getBankacco().length());
			tvBankCode.setText(bean.getBankname() + "(" + code + ")");
		} else {
			tvBankCode.setText(bean.getBankname());
		}

		tvFundName.setText(bean.getFundname());
		tvHaveMoney.setText("￥" + bean.getUnpaidincome());
		tvSaleMoney.setText("￥" + bean.getUsableremainshare());

		if (!TextUtils.isEmpty(bean.getFundTypeCode())) {
			if ("1109".equals(bean.getFundTypeCode())) {
				tvSaleTitle.setText("可赎回金额:");
			} else {
				tvSaleTitle.setText("可赎回份额:");
				llNo.setVisibility(View.GONE);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {

			String name = data.getStringExtra("name");
			position = data.getIntExtra("postion", 0);
			// tvBankCode.setText(name);
			setData(position);
		}
	}

}
