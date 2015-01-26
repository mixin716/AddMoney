package com.zc.addmony.ui.myproduct;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.adapter.myproduct.BuyProductsAdapter;
import com.zc.addmony.bean.myproduct.BuyProductsBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicMyProduct;
import com.zc.addmony.ui.buyproduct.BuyProductActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;
import com.zc.addmony.views.XListView;

/** 已购买的基金列表 */
public class BuyProductsActivity extends BaseActivity implements
		ListViewPassValuetoActivityListener {

	private MApplication app;
	private XListView lv;
	private BuyProductsAdapter adapter;
	private List<BuyProductsBean> list;
	private UserSharedData userShare;

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if ("refresh_products".equals(intent.getAction())) {
				requestData();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_buy_products_layout);
		setViews();
		requestData();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		app = (MApplication) this.getApplication();
		app.addAllActivity(this);
		list = new ArrayList<BuyProductsBean>();
		userShare = UserSharedData.getInstance(getApplicationContext());
		adapter = new BuyProductsAdapter(getApplicationContext(), list);
		adapter.setListViewPassValuetoActivityListener(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction("refresh_products");
		registerReceiver(receiver, filter);
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("购买的基金");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		lv = (XListView) findViewById(R.id.activity_buy_products_lv_content);
		lv.setAdapter(adapter);
		lv.setPullLoadEnable(false);
		lv.setPullRefreshEnable(false);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BuyProductsActivity.this,
						FundHistoryActivity.class);
				intent.putExtra("fundcode", list.get(position - 1)
						.getmFundcode());
				startActivity(intent);
				AnimUtil.pushLeftInAndOut(BuyProductsActivity.this);
			}
		});
	}

	@Override
	public void doPassActionListener(Object obj, int org1, int position,
			String str) {
		// TODO Auto-generated method stub
		app.fundBean.setFundcode(list.get(position).getmFundcode());
		app.fundBean.setFundname(list.get(position).getmFundname());
		app.fundBean.setSharetype(list.get(position).getmSharetype());
		Intent intent = null;
		if (org1 == 1) {// 购买
			intent = new Intent(this, BuyProductActivity.class);
			intent.putExtra("getFundTypeCode", list.get(position).getmFundTypeCode());
			intent.putExtra("minPrice", "1000");
		} else if (org1 == 2) {// 赎回
			app.setBpBean(list.get(position));
			if (TextUtils.isEmpty(list.get(position).getmHave())) {
				intent = new Intent(this, SaleMoneyActivity.class);
				intent.putExtra("fundcode", list.get(position).getmFundcode());
				startActivity(intent);
				AnimUtil.pushLeftInAndOut(BuyProductsActivity.this);
			} else {
				if (Float.valueOf(list.get(position).getmHave()) > 0) {
					intent = new Intent(this, SaleMoneyActivity.class);
					intent.putExtra("fundcode", list.get(position).getmFundcode());
					startActivity(intent);
					AnimUtil.pushLeftInAndOut(BuyProductsActivity.this);
				} else {
					showToast("您当前持有金额为0，不能赎回");
				}
			}
		}
		
	}

	/** 请求购买的基金列表 */
	public void requestData() {
		showLoading();
		AjaxParams params = new AjaxParams();
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GET_MY_BUY_ORDERS, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		switch (reqeustCode) {
		case 0:// 基金列表
			list = LogicMyProduct.parseBuyProducts(jsonString);
			adapter = new BuyProductsAdapter(getApplicationContext(), list);
			adapter.setListViewPassValuetoActivityListener(this);
			lv.setAdapter(adapter);
			break;

		default:
			break;
		}
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(this);
			break;

		default:
			break;
		}
	}

}
