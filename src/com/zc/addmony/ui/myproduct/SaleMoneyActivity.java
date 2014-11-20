package com.zc.addmony.ui.myproduct;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;

/** 提现赎回 */
public class SaleMoneyActivity extends BaseActivity {
	private MApplication app;
	private TextView tvFundName, tvSaleMoney, tvHaveMoney, tvBankCode;
	private EditText edtMinSaleMoney, edtSalePwd;
	private Button btnOk;
	private String fundName, saleMoney, haveMoney, bankName, bankCode,
			minMoney, salePwd;
	private UserSharedData userShare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_sale_money_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		app = (MApplication) getApplication();
		userShare = UserSharedData.getInstance(getApplicationContext());
	}

	@Override
	protected void setTitleViews() {
		titleText.setText("赎回");

	}

	@Override
	protected void setViews() {
		tvBankCode = (TextView) findViewById(R.id.activity_sale_money_tv_bank_code);
		tvFundName = (TextView) findViewById(R.id.activity_sale_money_tv_fund_name);
		tvHaveMoney = (TextView) findViewById(R.id.activity_sale_money_tv_have_money);
		tvSaleMoney = (TextView) findViewById(R.id.activity_sale_money_tv_have_sale);

		edtMinSaleMoney = (EditText) findViewById(R.id.activity_sale_money_edt_minmoney);
		edtSalePwd = (EditText) findViewById(R.id.activity_sale_money_edt_pwd);
		btnOk = (Button) findViewById(R.id.activity_sale_money_btn_ok);
		btnOk.setOnClickListener(this);
		getUserFundInfoRequest();

	}

	private void getUserFundInfoRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("fundcode", "820002");
		// params.put("fundcode", app.fundBean.getFundcode());
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GET_USER_FUND_INFO, params, callBack, 0);

	}

	private void getSaleMoneyRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("fundcode", "820002");
		// params.put("fundcode", app.fundBean.getFundcode());
		params.put("sharetype", app.fundBean.getSharetype());
		params.put("applysum", minMoney);
		params.put("tradeacco", "");
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.SALE_MONEY, params, callBack, 1);

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
				if(Integer.valueOf(minMoney) <1000){
					showToast("最低赎回金额为1000");
				}else{
					getSaleMoneyRequest();
				}
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
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			this.setResult(101);
			finish();
			AnimUtil.pushRightInAndOut(this);
			showToast("赎回成功");
			break;

		default:
			break;
		}
	}

}
