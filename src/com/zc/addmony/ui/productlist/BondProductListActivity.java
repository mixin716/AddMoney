package com.zc.addmony.ui.productlist;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.adapter.productlist.ProductNameAdapter;
import com.zc.addmony.adapter.productlist.ProductRateAdapter;
import com.zc.addmony.bean.productlist.ProductListBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicProductList;
import com.zc.addmony.utils.AnimUtil;

public class BondProductListActivity extends BaseActivity implements
		OnItemClickListener, OnTouchListener {
	private MApplication app;
	private ListView lvName, lvRate;
	private ProductNameAdapter nameAdapter;
	private ProductRateAdapter rataAdapter;
	private View nameHeader, rateHeader;
	private TextView tvUnit, tvWeek, tvMonth, tvThreeMonth, tvSixMonth,
			tvRecentYear;
	private List<ProductListBean> allList;
	private List<ProductListBean> list;
	private ScrollView svContent;
	private int page = 1;
	private String order = "desc", key = "RRInSingleWeek";
	private boolean isWeek = false, isMonth = false, isThreeMonth = false,
			isSixMonth = false, isYear = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_common_product_list_layout);
		dismissTop();
		setViews();
	}

	@Override
	protected void initVariable() {
		app = (MApplication) this.getApplication();
		allList = new ArrayList<ProductListBean>();
		list = new ArrayList<ProductListBean>();

	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViews() {
		svContent = (ScrollView) findViewById(R.id.activity_common_product_list_sv);
		lvName = (ListView) findViewById(R.id.activity_common_product_list_lv_name);
		lvRate = (ListView) findViewById(R.id.activity_common_product_list_lv_rate);
		nameHeader = View.inflate(getApplicationContext(),
				R.layout.view_name_header_layout, null);
		rateHeader = View.inflate(getApplicationContext(),
				R.layout.view_rate_header_layout, null);
		tvMonth = (TextView) rateHeader
				.findViewById(R.id.view_rate_header_tv_month);
		tvRecentYear = (TextView) rateHeader
				.findViewById(R.id.view_rate_header_tv_recent_year);
		tvThreeMonth = (TextView) rateHeader
				.findViewById(R.id.view_rate_header_tv_three);
		tvUnit = (TextView) rateHeader
				.findViewById(R.id.view_rate_header_tv_unit);
		tvWeek = (TextView) rateHeader
				.findViewById(R.id.view_rate_header_tv_week);
		tvSixMonth = (TextView) rateHeader
				.findViewById(R.id.view_rate_header_tv_six_month);
		lvName.setOnItemClickListener(this);
		lvRate.setOnItemClickListener(this);
		tvMonth.setOnClickListener(this);
		tvRecentYear.setOnClickListener(this);
		tvSixMonth.setOnClickListener(this);
		tvThreeMonth.setOnClickListener(this);
		tvUnit.setOnClickListener(this);
		tvWeek.setOnClickListener(this);
		svContent.setOnTouchListener(this);
		nameAdapter = new ProductNameAdapter(getApplicationContext(), allList);
		rataAdapter = new ProductRateAdapter(getApplicationContext(), allList);
		lvName.addHeaderView(nameHeader);
		lvRate.addHeaderView(rateHeader);
		getProductListRequest();
	}

	/**
	 * 获取产品列表
	 */
	private void getProductListRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("orderby", order);// 倒序还是正序，asc 正；desc 倒
		params.put("key", key);// 排序参照的字段
		params.put("page", page + "");// 页码 1开始
		params.put("listRows", 10 + "");// 每页条数
		params.put("fundType", "1105");// 基金类型 
		httpRequest.get(Urls.PRODUCT_LIST_TWO, params, callBack, 0);

	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		dismissLoading();
		switch (reqeustCode) {
		case 0:
			if (jsonString.equals("null")) {
				showToast("没有更多数据");
			} else {
				list = new ArrayList<ProductListBean>();
				list = LogicProductList.parseProductListElse(jsonString);
				allList.addAll(list);
				lvName.setAdapter(nameAdapter);
				lvRate.setAdapter(rataAdapter);
				setListViewHeightBasedOnChildren(lvName);
				setListViewHeightBasedOnChildren(lvRate);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 动态设置ListView的高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		if (listView == null)
			return;

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (position > 0) {
			if (TextUtils.isEmpty(allList.get(position - 1).getSharetype())
					|| "null".equals(allList.get(position - 1).getSharetype())) {
				app.fundBean.setSharetype("A");
			} else {
				app.fundBean.setSharetype(allList.get(position - 1)
						.getSharetype());
			}
			app.fundBean.setFundname(allList.get(position - 1).getFundname());
			app.fundBean.setFundcode(allList.get(position - 1).getFundcode());
			app.setPdBean(allList.get(position - 1));
			Intent intent;
			if("1109".equals(allList.get(position-1).getFundTypeCode())){
				intent = new Intent(this, ProductDetailActivity.class);
			}else{
				intent = new Intent(this, StockDetailActivity.class);
			}
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(this);
		}
	}

	@Override
	protected void doClickAction(int viewId) {
		if(viewId == R.id.view_rate_header_tv_unit){
			Log.e("", "doclick");
			return;
		}
		page = 1;
		allList.clear();
		switch (viewId) {
		case R.id.view_rate_header_tv_week:// 近一周
			if (!isWeek) {
				tvWeek.setText("近1周↑");
				isWeek = true;
				order = "asc";
				key = "RRInSingleWeek";
			} else {
				tvWeek.setText("近1周↓");
				isWeek = false;
				order = "desc";
				key = "RRInSingleWeek";
			}
			tvMonth.setText("近1月");
			tvThreeMonth.setText("近3月");
			tvSixMonth.setText("近6月");
			tvRecentYear.setText("近1年");
			getProductListRequest();
			break;
		case R.id.view_rate_header_tv_month:// 近一月
			if (!isMonth) {
				tvMonth.setText("近1月↑");
				isMonth = true;
				order = "asc";
				key = "RRInSingleMonth";
			} else {
				tvMonth.setText("近1月↓");
				isMonth = false;
				order = "desc";
				key = "RRInSingleMonth";
			}
			tvWeek.setText("近1周");
			tvThreeMonth.setText("近3月");
			tvSixMonth.setText("近6月");
			tvRecentYear.setText("近1年");
			getProductListRequest();
			break;
		case R.id.view_rate_header_tv_three:// 近3月
			if (!isThreeMonth) {
				tvThreeMonth.setText("近3月↑");
				isThreeMonth = true;
				order = "asc";
				key = "RRInThreeMonth";
			} else {
				tvThreeMonth.setText("近3月↓");
				isThreeMonth = false;
				order = "desc";
				key = "RRInThreeMonth";
			}
			tvWeek.setText("近1周");
			tvMonth.setText("近1月");
			tvSixMonth.setText("近6月");
			tvRecentYear.setText("近1年");
			getProductListRequest();
			break;
		case R.id.view_rate_header_tv_six_month:// 近半年
			if (!isSixMonth) {
				tvSixMonth.setText("近6月↑");
				isSixMonth = true;
				order = "asc";
				key = "RRInSixMonth";
			} else {
				tvSixMonth.setText("近6月↓");
				isSixMonth = false;
				order = "desc";
				key = "RRInSixMonth";
			}
			tvWeek.setText("近1周");
			tvMonth.setText("近1月");
			tvThreeMonth.setText("近3月");
			tvRecentYear.setText("近1年");
			getProductListRequest();
			break;
		case R.id.view_rate_header_tv_recent_year:// 近1年
			if (!isYear) {
				tvRecentYear.setText("近1年↑");
				isYear = true;
				order = "asc";
				key = "RRInSingleYear";
			} else {
				tvRecentYear.setText("近1年↓");
				isYear = false;
				order = "desc";
				key = "RRInSingleYear";
			}
			tvWeek.setText("近1周");
			tvMonth.setText("近1月");
			tvThreeMonth.setText("近3月");
			tvSixMonth.setText("近6月");
			getProductListRequest();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent arg1) {
		switch (arg1.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			if (v.getScrollY() <= 0) {// 顶部

			} else if (svContent.getChildAt(0).getMeasuredHeight() <= v
					.getHeight() + v.getScrollY()) {// 底部
				if(list.size()<10){
					showToast("没有更多数据");
				}else{
					page++;
					getProductListRequest();
				}

			}
			break;
		default:
			break;
		}
		return false;
	}
}
