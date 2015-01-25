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
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.adapter.myproduct.RecordDetailsAdapter;
import com.zc.addmony.bean.myproduct.MoneyChangeBean;
import com.zc.addmony.bean.productlist.ProductListBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicProductList;
import com.zc.addmony.ui.buyproduct.BuyProductActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.views.XListView;

/** 增财宝 */
public class IncreaseWealthActivity extends BaseActivity {
	private XListView lvContent;
	private View view;
	private RecordDetailsAdapter adapter;
	private Button btnMention, btnRecharge;
	private TextView tvYesterdayMoney, tvAllMoney;
	private Intent intent;
	private List<MoneyChangeBean> list;
	private MApplication app;
	private ProductListBean bean;
	private UserSharedData userShare;
	private String zcbsum = "0", zrsy;

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
		app.addAllActivity(this);
		userShare = UserSharedData.getInstance(getApplicationContext());
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
		// getZCBInfo();

	}

	private void getgetUserInfo() {
		showLoading();
		AjaxParams params = new AjaxParams();
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GET_USER_INRO, params, callBack, 0);
	}

	private void getZCBInfo() {
		showLoading();
		AjaxParams params = new AjaxParams();
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.ZCBINFO, params, callBack, 2);
	}

	private void getMoneyChageRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		// params.put("fundcode", "000380");
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GET_ZCB_BUTTOM_INFO, params, callBack, 1);

	}

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.view_increase_wealth_list_btn_mention:// 提现/赎回
			if (Float.valueOf(zcbsum) == 0) {
				showToast("当前可用余额为0");
				return;
			}
			intent = new Intent(this, SaleMoneyActivity.class);
			intent.putExtra("fundcode", app.zcbCode);
			app.fundBean.setFundcode(app.zcbCode);
			app.fundBean.setFundname("増财宝");
			app.fundBean.setSharetype(app.zcbShareType);
			startActivityForResult(intent, 101);
			AnimUtil.pushLeftInAndOut(this);
			break;
		case R.id.view_increase_wealth_list_btn_recharge:// 充值
			intent = new Intent(this, BuyProductActivity.class);
			app.fundBean.setFundcode(app.zcbCode);
			app.fundBean.setFundname("増财宝");
			app.fundBean.setSharetype(app.zcbShareType);
			intent.putExtra("minPrice", "1000");
			intent.putExtra("FundTypeCode", app.zcbFundtypecode);
			startActivityForResult(intent, 101);
			AnimUtil.pushLeftInAndOut(this);
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
				zrsy = obj.optString("zrsy");
				zcbsum = obj.optString("zcbsum");
				tvYesterdayMoney.setText("￥" + zcbsum);
//				tvYesterdayMoney.setText("￥" + zrsy);
//				tvAllMoney.setText("￥" + zcbsum);
				tvAllMoney.setText("￥" + zrsy);
				getMoneyChageRequest();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				dismissLoading();
				// JSONObject ojb = new JSONObject(jsonString);
				// JSONObject resultObj = new
				// JSONObject(ojb.optString("results"));
				JSONArray array = new JSONArray(jsonString);
				int len = array.length();
				list.clear();
				for (int i = 0; i < len; i++) {
					MoneyChangeBean bean = new MoneyChangeBean();
					JSONObject obj = array.getJSONObject(i);
					bean.setFundname(obj.optString("fundname"));
					bean.setHappeningsum(obj.optString("happeningsum"));
					bean.setApplydate(obj.optString("applydate"));
					bean.setApplyshare(obj.optString("applyshare"));
					bean.setApplysum(obj.optString("applysum"));
					bean.setCallingcode(obj.optString("callingcode"));
					// bean.setBusinflagStr(obj.optString("businflagStr"));
					// String time = obj.optString("date");
					// bean.setDate(time.substring(0, 4) + "-"
					// + time.substring(4, 6) + "-" + time.substring(6, 8));
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
		case 2:
			bean = LogicProductList.parseFundDetail(jsonString);
			app.fundBean.setFundcode(bean.getFundcode());
			app.fundBean.setFundname(bean.getFundname());
			app.fundBean.setSharetype(bean.getSharetype());
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 101) {// 从充值或赎回界面返回，需要刷新界面
			getgetUserInfo();
			getMoneyChageRequest();
		}
	}

}
