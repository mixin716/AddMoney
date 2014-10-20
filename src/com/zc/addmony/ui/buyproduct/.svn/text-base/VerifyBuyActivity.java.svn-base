package com.zc.addmony.ui.buyproduct;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.common.Urls;
import com.zc.addmony.utils.AnimUtil;

public class VerifyBuyActivity extends BaseActivity{

	private TextView tvName,tvMoney,tvHand,tvBankName;
	private RelativeLayout rlBank;
	private EditText etPwd;
	private Button btBuy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_verify_buy_layout);
		setViews();
	}
	
	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("确认购买");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		tvName = (TextView) findViewById(R.id.activity_verify_buy_tv_name);
		tvMoney = (TextView) findViewById(R.id.activity_verify_buy_tv_money);
		tvHand = (TextView) findViewById(R.id.activity_verify_buy_tv_hand);//手续费
		tvBankName = (TextView) findViewById(R.id.activity_verify_buy_tv_bank_name);
		etPwd = (EditText) findViewById(R.id.activity_verify_buy_et_pwd);
		rlBank = (RelativeLayout) findViewById(R.id.activity_verify_buy_rl_select_bank);
		btBuy = (Button) findViewById(R.id.activity_verify_buy_bt_buy);
		
		rlBank.setOnClickListener(this);
		btBuy.setOnClickListener(this);
	}
	
	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(VerifyBuyActivity.this);
			break;
		case R.id.activity_verify_buy_rl_select_bank://选择银行
			
			break;
		case R.id.activity_verify_buy_bt_buy://购买
			
			break;
		}
	}
	
	/** 选择银行卡*/
	public void getFundBanks(){
		AjaxParams params = new AjaxParams();
		params.put("fundcode", "");//基金代码
		params.put("sharetype", "");//收费方式
		httpRequest.get(Urls.GET_FUND_BANKS, params, callBack, 0);
	}
	
	/** 购买基金*/
	public void requestBuy(){
		AjaxParams params = new AjaxParams();
		params.put("fundcode", "");//基金代码
		params.put("money", "");//钱
		params.put("tradeacco", "");//交易账号
		params.put("password", "");//交易秘密啊
		httpRequest.get(Urls.BUY_PRODUCT, params, callBack, 1);
	}
	
	/** 充值*/
	public void requestAdd(){
		AjaxParams params = new AjaxParams();
		params.put("tradeacco", "");//交易账号
		params.put("applysum", "");//交易金额
		params.put("tradepassword", "");//交易秘密
		httpRequest.get(Urls.TBUYFUND, params, callBack, 2);
	}
	
	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
	}

}
