package com.zc.addmony.ui.myproduct;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.adapter.myproduct.TodayDealAdapter;
import com.zc.addmony.bean.productlist.ProductBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicProductList;
import com.zc.addmony.utils.AnimUtil;

/** 当日交易申请*/
public class TodayDealActivity extends BaseActivity {

	private MApplication app;
	private ListView lvContent;
	private TodayDealAdapter adapter;
	private List<ProductBean> list;
	private UserSharedData userShare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_today_deal_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		app = (MApplication) this.getApplication();
		app.addAllActivity(this);
		userShare = UserSharedData.getInstance(getApplicationContext());
		list = new ArrayList<ProductBean>();
		getTodayDealRequest();

	}

	@Override
	protected void setTitleViews() {
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("当日交易申请");

	}

	@Override
	protected void setViews() {
		lvContent = (ListView) findViewById(R.id.activity_today_deal_lv_content);
		
	}
	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break; 

		default:
			break;
		}
	}

	/**
	 * 请求当日交易申请列表
	 * 
	 */
	private void getTodayDealRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.TODAY_DEAL_LIST, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		super.handleJson(reqeustCode, jsonString, message);
		switch (reqeustCode) {
		case 0:
			list = LogicProductList.parseTodayDealList(jsonString);
			adapter = new TodayDealAdapter(getApplicationContext(), list);
			lvContent.setAdapter(adapter);
			break;

		default:
			break;
		}
	}
}
