package com.zc.addmony.ui.buyproduct;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.jky.struct2.http.entityhandle.HttpResult;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.BaseBean;
import com.zc.addmony.bean.myproduct.FundBankListBean;
import com.zc.addmony.bean.myproduct.ManageBankBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicBase;
import com.zc.addmony.logic.LogicBuyProduct;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;

/** 购买基金 */
public class VerifyBuyActivity extends BaseActivity {

	private TextView tvName, tvMoney, tvHand, tvBankName;
	private RelativeLayout rlBank;
	private EditText etPwd;
	private Button btBuy;
	private String money;// 金额
	private MApplication app;
	private UserSharedData userShare;
	private List<FundBankListBean> banks;
	private ArrayList<String> bankList;// 银行卡名字
	private Intent intent;
	private String name, bankName, password;// 选择的银行名字 获取的tv银行名字 交易密码
	private int position;// 选择银行的位置

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
		money = this.getIntent().getStringExtra("money");
		app = (MApplication) getApplication();
		userShare = UserSharedData.getInstance(getApplicationContext());
		banks = new ArrayList<FundBankListBean>();
		bankList = new ArrayList<String>();
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
		tvHand = (TextView) findViewById(R.id.activity_verify_buy_tv_hand);// 手续费
		tvBankName = (TextView) findViewById(R.id.activity_verify_buy_tv_bank_name);
		etPwd = (EditText) findViewById(R.id.activity_verify_buy_et_pwd);
		rlBank = (RelativeLayout) findViewById(R.id.activity_verify_buy_rl_select_bank);
		btBuy = (Button) findViewById(R.id.activity_verify_buy_bt_buy);

		rlBank.setOnClickListener(this);
		btBuy.setOnClickListener(this);

		if (!TextUtils.isEmpty(app.fundBean.getFundname())
				&& !"null".equals(app.fundBean.getFundname())) {
			tvName.setText(app.fundBean.getFundname());
		}
		// tvName.setText(app.fundBean.getFundname());
		tvMoney.setText(money);
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
		case R.id.activity_verify_buy_rl_select_bank:// 选择银行
			bankList.clear();
			banks.clear();
			getBankList();
			break;
		case R.id.activity_verify_buy_bt_buy:// 购买
			KeyBoard.demissKeyBoard(getApplicationContext(), etPwd);
			bankName = tvBankName.getText().toString();
			password = etPwd.getText().toString().trim();
			if ("请选择银行卡".equals(bankName)) {
				showToast("请选择银行卡");
			} else if (TextUtils.isEmpty(password)) {
				showToast("请输入密码");
			} else {
				requestBuy();
			}
			break;
		}
	}

	/** 获取银行卡列表 */
	public void getBankList() {
		AjaxParams params = new AjaxParams();
		params.put("fundcode", app.fundBean.getFundcode());// 基金代码
		params.put("sharetype", app.fundBean.getSharetype());
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GET_FUND_BANKS, params, callBack, 0);
	}

	/** 购买基金 */
	public void requestBuy() {
		showLoading();
		AjaxParams params = new AjaxParams();
		// params.put("fundcode", "820002");// 基金代码
		params.put("fundcode", app.fundBean.getFundcode());// 基金代码
		params.put("money", money);// 钱
		params.put("tradeacco", banks.get(position).getTradeacco());// 交易账号
		params.put("password", password);// 交易密码
		httpRequest.get(Urls.BUY_PRODUCT, params, callBack, 1);
	}

	@Override
	protected void handleResult(int requestCode, HttpResult result) {
		// TODO Auto-generated method stub
		super.handleResult(requestCode, result);
		switch (requestCode) {
		case 0:
			String baseJson = result.baseJson;
			System.out.println("-----json:------" + baseJson);
			BaseBean baseBean = LogicBase.getInstance().parseData(baseJson);
			switch (baseBean.getStatus()) {
			case 1:
				System.out.println("-----baseBean.getData():------"
						+ baseBean.getContent());
				
				banks = LogicBuyProduct.parseFundBankList(baseBean.getContent());
				for (FundBankListBean bean : banks) {
					bankList.add(bean.getBankname()
							+ "（***"
							+ bean.getBankacco().substring(
									bean.getBankacco().length() - 4,
									bean.getBankacco().length()) + "）");
				}
				intent = new Intent(this, SelectActivity.class);
				intent.putStringArrayListExtra("nameList", bankList);
				startActivityForResult(intent, 0);
				AnimUtil.pushLeftInAndOut(this);
				break;
			default:// 请求失败
				showToast(baseBean.getMessage());
				break;
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
		if (reqeustCode == 1) {
			sendBroadcast(new Intent("refresh_products"));
			sendBroadcast(new Intent("refresh_judgment"));
			showToast("购买成功");
			this.setResult(101);
			this.finish();
			AnimUtil.pushRightInAndOut(this);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				name = data.getStringExtra("name");
				position = data.getIntExtra("postion", 0);
				tvBankName.setText(name);
				break;
			default:
				break;
			}
		}
	}

}
