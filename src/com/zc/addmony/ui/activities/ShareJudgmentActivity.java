package com.zc.addmony.ui.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.jky.struct2.http.entityhandle.HttpResult;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.BaseBean;
import com.zc.addmony.bean.activities.PhoneBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicBase;
import com.zc.addmony.ui.buyproduct.BuyProductActivity;
import com.zc.addmony.utils.AnimUtil;

/** 判断套餐份额*/
public class ShareJudgmentActivity extends BaseActivity {

	private String TAG = "ShareJudgmentActivity";
	private MApplication mApplication;
	public PhoneBean pBean;// 所选手机
	private Button btFull, btEmpty;// 份额充足 份额不足
	private TextView tvName, tvNumber;
	private Intent intent;
	private UserSharedData userShare;
	private String fundcode, fundname;// 基金id
	private String total = "0";// 基金份额
	private boolean requestFlag = false;// 网络请求成功标示

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if ("refresh_judgment".equals(intent.getAction())) {
				handler.sendEmptyMessageDelayed(0, 2000);
			}
		}
	};

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Log.e(TAG, "充值成功之后需要刷新界面");
			requestProduct();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_share_judgment_layout);
		setViews();
		requestProduct();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		userShare = UserSharedData.getInstance(getApplicationContext());
		mApplication = (MApplication) this.getApplication();
		mApplication.addActivitys(this);
		pBean = mApplication.getpBean();
		IntentFilter filter = new IntentFilter();
		filter.addAction("refresh_judgment");
		registerReceiver(receiver, filter);
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		tvName = (TextView) findViewById(R.id.activity_share_judgment_tv_name);
		tvNumber = (TextView) findViewById(R.id.activity_share_judgment_tv_number);
		btFull = (Button) findViewById(R.id.activity_share_judgment_bt_full);
		btEmpty = (Button) findViewById(R.id.activity_share_judgment_bt_empty);

		btFull.setOnClickListener(this);
		btEmpty.setOnClickListener(this);
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(ShareJudgmentActivity.this);
			break;
		case R.id.activity_share_judgment_bt_full:
			if (Integer.valueOf(pBean.getPhoneTC().get(0).getShare()) <= Integer
					.valueOf(total)) {
				if (!requestFlag) {
					showToast("网络请求失败，正在刷新");
					requestProduct();
				} else {
					intent = new Intent(this, SelectPhoneNewActivity.class);
					startActivity(intent);
					AnimUtil.pushLeftInAndOut(ShareJudgmentActivity.this);
				}
			} else {
				showToast("份额不足，请购买基金");
				mApplication.fundBean.setFundcode(fundcode);
				mApplication.fundBean.setFundname(fundname);
				mApplication.fundBean.setSharetype("A");
				intent = new Intent(this, BuyProductActivity.class);
				intent.putExtra("minPrice", "1000");
				startActivityForResult(intent, 101);
				AnimUtil.pushLeftInAndOut(ShareJudgmentActivity.this);
			}
			break;
		case R.id.activity_share_judgment_bt_empty:
			mApplication.fundBean.setFundcode(fundcode);
			mApplication.fundBean.setFundname(fundname);
			mApplication.fundBean.setSharetype("A");
			intent = new Intent(this, BuyProductActivity.class);
			intent.putExtra("minPrice", "1000");
			startActivityForResult(intent, 101);
			AnimUtil.pushLeftInAndOut(ShareJudgmentActivity.this);
			break;
		}
	}

	/** 请求基金 */
	public void requestProduct() {
		showLoading();
		AjaxParams params = new AjaxParams();
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.CHECK_TOTALS, params, callBack, 1);
	}

	@Override
	protected void handleResult(int requestCode, HttpResult result) {
		// TODO Auto-generated method stub
		super.handleResult(requestCode, result);
		String baseJson = result.baseJson;
		System.out.println("-----json:------" + baseJson);
		BaseBean baseBean = LogicBase.getInstance().parseData(baseJson);
		switch (baseBean.getStatus()) {
		case 1:
			try {
				requestFlag = true;
				JSONObject obj = new JSONObject(baseBean.getContent());
				fundcode = obj.optString("fundcode");
				fundname = obj.optString("fundname");
				total = obj.optString("total");
				tvName.setText(fundname + "基金");
				tvNumber.setText(obj.optString("total"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			try {
				showToast("请求失败，请重试");
				requestFlag = false;
				JSONObject obj = new JSONObject(baseBean.getContent());
				fundcode = obj.optString("fundcode");
				fundname = obj.optString("fundname");
				tvName.setText(fundname + "基金");
				tvNumber.setText(obj.optString("total"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 101) {// 充值成功之后需要刷新界面
		}
	}
}
