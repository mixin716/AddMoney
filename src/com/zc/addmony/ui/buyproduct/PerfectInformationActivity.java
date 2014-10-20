package com.zc.addmony.ui.buyproduct;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.bean.buyproduct.BanksBean;
import com.zc.addmony.bean.buyproduct.BranchBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicBuyProduct;
import com.zc.addmony.ui.myproduct.RegisterSuccessActivity;
import com.zc.addmony.utils.AnimUtil;

public class PerfectInformationActivity extends BaseActivity {
	private EditText edtName, edtIdCard, edtBankNum;
	private TextView tvBanks, tvProvince, tvCity, tvBranch;
	private LinearLayout llBanks, llProvince, llCity, llBranch;
	private Button btnNext;
	private ArrayList<String> banksList, branchList, provinceList, cityList;
	private String banks, province, city, branch, bankCode, branchCode;
	private List<BanksBean> bean;
	private Intent intent;
	private String name, idCard, userName, bankNum;
	private int position;
	private List<BranchBean> branchBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_perfect_information_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		branchBean = new ArrayList<BranchBean>();
		bean = new ArrayList<BanksBean>();
		banksList = new ArrayList<String>();
		provinceList = new ArrayList<String>();
		branchList = new ArrayList<String>();
		cityList = new ArrayList<String>();

	}

	@Override
	protected void setTitleViews() {
		titleText.setText("注册");

	}

	@Override
	protected void setViews() {
		edtName = (EditText) findViewById(R.id.activity_perfect_information_edt_name);
		edtBankNum = (EditText) findViewById(R.id.activity_perfect_information_edt_bank_num);
		edtIdCard = (EditText) findViewById(R.id.activity_perfect_information_edt_idcard);

		tvBanks = (TextView) findViewById(R.id.activity_perfect_information_tv_bank);
		tvBranch = (TextView) findViewById(R.id.activity_perfect_information_tv_branch);
		tvCity = (TextView) findViewById(R.id.activity_perfect_information_tv_city);
		tvProvince = (TextView) findViewById(R.id.activity_perfect_information_tv_province);

		llBanks = (LinearLayout) findViewById(R.id.activity_perfect_information_ll_banks);
		llBranch = (LinearLayout) findViewById(R.id.activity_perfect_information_ll_branch);
		llCity = (LinearLayout) findViewById(R.id.activity_perfect_information_ll_city);
		llProvince = (LinearLayout) findViewById(R.id.activity_perfect_information_ll_province);
		btnNext = (Button) findViewById(R.id.activity_perfect_information_btn_next);

		btnNext.setOnClickListener(this);
		llBanks.setOnClickListener(this);
		llBranch.setOnClickListener(this);
		llCity.setOnClickListener(this);
		llProvince.setOnClickListener(this);
	}

	private void getBanksRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("key", "banks");
		httpRequest.get(Urls.PERFECT_INFORMATION, params, callBack, 0);

	}

	private void getProvinceRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("key", "province");
		httpRequest.get(Urls.PERFECT_INFORMATION, params, callBack, 1);

	}

	private void getCityRequest(String province) {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("key", "city");
		params.put("province", province);
		httpRequest.get(Urls.PERFECT_INFORMATION, params, callBack, 2);

	}

	private void getBranchRequest(String city) {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("key", "branch");
		params.put("city", city);
		params.put("bankcode", bankCode);
		httpRequest.post(Urls.PERFECT_INFORMATION, params, callBack, 3);
	}

	private void sendPerfectRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
//		params.put("idcard", idCard);
//		params.put("name", userName);
		params.put("bankcode", bankCode);
		params.put("bnum", bankNum);
		params.put("province", province);
		params.put("branch", branch);
		params.put("branchcode", branchCode);
		params.put("city", city);
		httpRequest.post(Urls.PERFECT_INFORMATION_NEXT, params, callBack, 4);
	}

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_perfect_information_ll_banks:// 选择银行
			if (banksList.size() == 0) {
				getBanksRequest();
			} else {
				intent = new Intent(this, SelectActivity.class);
				intent.putStringArrayListExtra("nameList", banksList);
				startActivityForResult(intent, 0);
				AnimUtil.pushLeftInAndOut(this);
			}
			break;
		case R.id.activity_perfect_information_ll_branch:// 选择支行
			banks = tvBanks.getText().toString();
			city = tvCity.getText().toString();
			if (TextUtils.isEmpty(banks)) {
				showToast("请选择银行");
			} else if (TextUtils.isEmpty(city)) {
				showToast("请选择城市");
			} else {
				getBranchRequest(city);
			}
			break;
		case R.id.activity_perfect_information_ll_city:// 选择城市
			province = tvProvince.getText().toString();
			if (TextUtils.isEmpty(province)) {
				showToast("请选择省份");
			} else {
				getCityRequest(province);
			}

			break;
		case R.id.activity_perfect_information_ll_province:// 选择省份
			if (provinceList.size() == 0) {
				getProvinceRequest();
			} else {
				intent = new Intent(this, SelectActivity.class);
				intent.putStringArrayListExtra("nameList", provinceList);
				startActivityForResult(intent, 1);
				AnimUtil.pushLeftInAndOut(this);
			}
			break;
		case R.id.activity_perfect_information_btn_next:// 下一步
//			idCard = edtIdCard.getText().toString();
//			userName = edtName.getText().toString();
			bankNum = edtBankNum.getText().toString();
			if (checkEmpty()) {
				sendPerfectRequest();
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
			if (TextUtils.isEmpty(jsonString) || jsonString.equals("null")) {
				showToast("没有银行");
			} else {
				bean = new ArrayList<BanksBean>();
				bean = LogicBuyProduct.parseBanksList(jsonString);
				int len = bean.size();
				banksList = new ArrayList<String>();
				for (int i = 0; i < len; i++) {
					banksList.add(bean.get(i).getBank_name());
				}
				intent = new Intent(this, SelectActivity.class);
				intent.putStringArrayListExtra("nameList", banksList);
				startActivityForResult(intent, 0);
				AnimUtil.pushLeftInAndOut(this);
			}
			break;
		case 1:
			try {
				if (TextUtils.isEmpty(jsonString) || jsonString.equals("null")) {
					showToast("省份数据为空");
				} else {
					JSONArray array = new JSONArray(jsonString);
					int len = array.length();
					provinceList = new ArrayList<String>();
					for (int i = 0; i < len; i++) {
						JSONObject obj = array.getJSONObject(i);
						provinceList.add(obj.optString("VC_PROVINCENAME"));
					}
					intent = new Intent(this, SelectActivity.class);
					intent.putStringArrayListExtra("nameList", provinceList);
					startActivityForResult(intent, 1);
					AnimUtil.pushLeftInAndOut(this);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			break;
		case 2:
			try {
				if (TextUtils.isEmpty(jsonString) || jsonString.equals("null")) {
					showToast("城市数据为空");
				} else {
					JSONArray array = new JSONArray(jsonString);
					int len = array.length();
					cityList = new ArrayList<String>();
					for (int i = 0; i < len; i++) {
						JSONObject obj = array.getJSONObject(i);
						cityList.add(obj.optString("VC_CITYNAME"));
					}
					intent = new Intent(this, SelectActivity.class);
					intent.putStringArrayListExtra("nameList", cityList);
					startActivityForResult(intent, 2);
					AnimUtil.pushLeftInAndOut(this);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			if (TextUtils.isEmpty(jsonString) || jsonString.equals("null")) {
				showToast("支行数据为空");
			} else {
				branchBean = new ArrayList<BranchBean>();
				branchBean = LogicBuyProduct.parseBranchList(jsonString);
				int len = branchBean.size();
				for (int i = 0; i < len; i++) {
					branchList.add(branchBean.get(i).getVC_BANKNAME());
				}
				intent = new Intent(this, SelectActivity.class);
				intent.putStringArrayListExtra("nameList", branchList);
				startActivityForResult(intent, 3);
				AnimUtil.pushLeftInAndOut(this);

			}
			break;
		case 4:
//			showToast("提交成功！");
			finish();
			Intent intent = new Intent(this,RegisterSuccessActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(this);
			break;

		default:
			break;
		}
	}

	private boolean checkEmpty() {
		if (TextUtils.isEmpty(banks)) {
			showToast("请选择银行");
			return false;
		} else if (TextUtils.isEmpty(bankNum)) {
			showToast("请输入银行卡号");
			return false;
		} else if (TextUtils.isEmpty(province)) {
			showToast("请选择省份");
			return false;
		} else if (TextUtils.isEmpty(city)) {
			showToast("请选择城市");
			return false;
		} else if (TextUtils.isEmpty(branch)) {
			showToast("请选择支行");
			return false;
		}
		return true;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				name = data.getStringExtra("name");
				position = data.getIntExtra("postion", 0);
				tvBanks.setText(name);
				bankCode = bean.get(position).getBank_num();
				break;
			case 1:
				name = data.getStringExtra("name");
				tvProvince.setText(name);
				break;
			case 2:
				name = data.getStringExtra("name");
				tvCity.setText(name);
				break;
			case 3:
				name = data.getStringExtra("name");
				position = data.getIntExtra("postion", 0);
				branchCode = branchBean.get(position).getVC_BRANCHBANK();
				branch = name;
				tvBranch.setText(name);
				break;

			default:
				break;
			}
		}
	}

}
