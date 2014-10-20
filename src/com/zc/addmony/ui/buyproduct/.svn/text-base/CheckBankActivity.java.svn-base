package com.zc.addmony.ui.buyproduct;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.common.Urls;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;

/** 打款验证 */
public class CheckBankActivity extends BaseActivity {

	private TextView tvBank;// 银行卡号
	private EditText etMoney;
	private Button btNext;
	private String strMoney, strBank;
	private String orderno;//小额打款编号

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_check_bank_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		strBank = this.getIntent().getStringExtra("");// 获取银行卡号
		orderno = this.getIntent().getStringExtra("orderno");
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("完善信息");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		tvBank = (TextView) findViewById(R.id.activity_check_bank_tv_bank);
		etMoney = (EditText) findViewById(R.id.activity_check_bank_et_money);
		btNext = (Button) findViewById(R.id.activity_check_bank_bt_next);
		btNext.setOnClickListener(this);

		tvBank.setText("已向您" + strBank + "的银行卡汇入一笔金额，请确认具体的汇款金额");
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(CheckBankActivity.this);
			break;
		case R.id.activity_check_bank_bt_next:
			KeyBoard.demissKeyBoard(getApplicationContext(), etMoney);
			strMoney = etMoney.getText().toString().trim();
			if (TextUtils.isEmpty(strMoney)) {
				showToast("请输入汇款金额");
			} else {
				if (Float.valueOf(strMoney) >= 1) {
					showToast("请输入合理的金额范围");
				} else {
					Intent intent = new Intent(this,SetBuyPwdActivity.class);
					startActivity(intent);
				}
			}
			break;
		}
	}

	public void requestCheck() {
		AjaxParams params = new AjaxParams();
		params.put("orderno", "");// 小额打款编号
		params.put("money", strMoney + "");
		httpRequest.get(Urls.CHECK_TINY_PAY, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
	}

}
