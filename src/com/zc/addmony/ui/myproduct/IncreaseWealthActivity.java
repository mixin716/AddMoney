package com.zc.addmony.ui.myproduct;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.adapter.myproduct.RecordDetailsAdapter;
import com.zc.addmony.bean.myproduct.MoneyChangeBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.ui.buyproduct.BuyProductActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.views.XListView;

public class IncreaseWealthActivity extends BaseActivity {
	private XListView lvContent;
	private View view;
	private RecordDetailsAdapter adapter;
	private Button btnMention, btnRecharge;
	private TextView tvYesterdayMoney, tvAllMoney;
	private Intent intent;
	private List<MoneyChangeBean> list;
	private MApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_increase_wealth_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		list = new ArrayList<MoneyChangeBean>();
		adapter = new RecordDetailsAdapter(getApplicationContext(), list);
		app = (MApplication) getApplication();
	}

	@Override
	protected void setTitleViews() {
		titleText.setText("增财宝");

	}

	@Override
	protected void setViews() {
		view = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.view_increase_wealth_list_header_layout, null);
		lvContent = (XListView) findViewById(R.id.activity_increase_wealth_lv_content);
		lvContent.setPullRefreshEnable(false);
		lvContent.setPullLoadEnable(false);
		lvContent.addHeaderView(view);
		lvContent.setAdapter(adapter);
		tvYesterdayMoney = (TextView) view
				.findViewById(R.id.view_increase_wealth_list_tv_yesterday_money);
		tvAllMoney = (TextView) view
				.findViewById(R.id.view_increase_wealth_list_tv_all_money);
		btnMention = (Button) view
				.findViewById(R.id.view_increase_wealth_list_btn_mention);
		btnRecharge = (Button) view
				.findViewById(R.id.view_increase_wealth_list_btn_recharge);
		btnMention.setOnClickListener(this);
		btnRecharge.setOnClickListener(this);
		getgetUserInfo();

	}

	private void getgetUserInfo() {
		showLoading();
		AjaxParams params = new AjaxParams();
		httpRequest.get(Urls.GET_USER_INRO, params, callBack, 0);

	}

	private void getMoneyChageRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("fundcode", "320002");
		httpRequest.get(Urls.GET_MONEY_CHANGE, params, callBack, 1);

	}

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.view_increase_wealth_list_btn_mention:// 提现/赎回
			intent = new Intent(this, SaleMoneyActivity.class);
			app.fundBean.setFundcode("320002");
			app.fundBean.setFundname("诺安货币A");
			app.fundBean.setSharetype("A");
			startActivity(intent);
			break;
		case R.id.view_increase_wealth_list_btn_recharge:// 充值
			intent = new Intent(this, BuyProductActivity.class);
			app.fundBean.setFundcode("320002");
			app.fundBean.setFundname("诺安货币A");
			app.fundBean.setSharetype("A");
			intent.putExtra("minPrice", "1元");
			startActivity(intent);
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
				JSONObject obj = new JSONObject(jsonString);
				String zrsy = obj.optString("zrsy");
				String zcbsum = obj.optString("zcbsum");
				tvYesterdayMoney.setText("￥" + zrsy);
				tvAllMoney.setText("￥" + zcbsum);
				getMoneyChageRequest();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				dismissLoading();
				JSONArray array = new JSONArray(jsonString);
				int len = array.length();
				for (int i = 0; i < len; i++) {
					MoneyChangeBean bean = new MoneyChangeBean();
					JSONObject obj = array.getJSONObject(i);
					bean.setFundname(obj.optString("fundname"));
					bean.setHappeningsum(obj.optString("happeningsum"));
					list.add(bean);
				}
				// adapter = new RecordDetailsAdapter(getApplicationContext(),
				// list);
				adapter.notifyDataSetChanged();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

}
