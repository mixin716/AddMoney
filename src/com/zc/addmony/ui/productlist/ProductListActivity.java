package com.zc.addmony.ui.productlist;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.adapter.productlist.ProductListAdapter;
import com.zc.addmony.bean.productlist.ProductListBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicProductList;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.views.XListView;
import com.zc.addmony.views.XListView.IXListViewListener;

public class ProductListActivity extends BaseActivity implements
		IXListViewListener, OnItemClickListener {

	private MApplication app;
	private String TAG = "ProductListActivity";
	private XListView lv;
	private LinearLayout llPerson, llWf, llRate;
	private TextView tvPerson, tvWf, tvRate;
	private ImageView imgPerson, imgWf, imgRate;
	private List<ProductListBean> allList;
	private List<ProductListBean> list;
	private ProductListAdapter adapter;
	private int pages = 1, pageSize;
	private String key = "DailyProfit";// g 购买人数 DailyProfit 万分收益
										// LatestWeeklyYield
	private String orderby = "asc";// 倒序还是正序，asc 正；desc 倒
	private int desc = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_product_list_layout);
		dismissTop();
		setViews();
		showLoading();
		requestData();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		app = (MApplication) this.getApplication();
		list = new ArrayList<ProductListBean>();
		allList = new ArrayList<ProductListBean>();
		adapter = new ProductListAdapter(getApplicationContext(), allList);
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleIvLeft.setVisibility(View.INVISIBLE);
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("产品列表");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		tvPerson = (TextView) findViewById(R.id.activity_product_list_tv_person);
		tvWf = (TextView) findViewById(R.id.activity_product_list_tv_wan);
		tvRate = (TextView) findViewById(R.id.activity_product_list_tv_rate);
		imgPerson = (ImageView) findViewById(R.id.activity_product_list_img_person);
		imgWf = (ImageView) findViewById(R.id.activity_product_list_img_wan);
		imgRate = (ImageView) findViewById(R.id.activity_product_list_img_rate);
		llPerson = (LinearLayout) findViewById(R.id.activity_product_list_ll_person);
		llWf = (LinearLayout) findViewById(R.id.activity_product_list_ll_wf);
		llRate = (LinearLayout) findViewById(R.id.activity_product_list_ll_rate);
		lv = (XListView) findViewById(R.id.activity_product_list_lv);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);

		llPerson.setOnClickListener(this);
		llRate.setOnClickListener(this);
		llWf.setOnClickListener(this);
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		showLoading();
		switch (viewId) {
		case R.id.activity_product_list_ll_person:// 购买人数
			clearTv();
			allList.clear();
			pages = 0;
			tvPerson.setTextColor(this.getResources().getColor(
					R.color.normal_purple));
			if ("DailyProfit".equals(key)) {
				if (desc == 0) {
					imgPerson
							.setImageResource(R.drawable.ic_product_list_arrow_bttom_press);
					desc = 1;
				} else if (desc == 1) {
					imgPerson
							.setImageResource(R.drawable.ic_product_list_arrow_top_press);
					desc = 0;
				}
			} else {
				imgPerson
						.setImageResource(R.drawable.ic_product_list_arrow_bttom_press);
				key = "DailyProfit";
				desc = 1;
			}
			requestData();
			break;
		case R.id.activity_product_list_ll_wf:// 万份收益
			clearTv();
			allList.clear();
			pages = 0;
			tvWf.setTextColor(this.getResources().getColor(
					R.color.normal_purple));
			if ("DailyProfit".equals(key)) {
				if (desc == 0) {
					imgWf.setImageResource(R.drawable.ic_product_list_arrow_bttom_press);
					desc = 1;
				} else if (desc == 1) {
					imgWf.setImageResource(R.drawable.ic_product_list_arrow_top_press);
					desc = 0;
				}
			} else {
				imgWf.setImageResource(R.drawable.ic_product_list_arrow_bttom_press);
				key = "DailyProfit";
				desc = 1;
			}
			requestData();
			break;
		case R.id.activity_product_list_ll_rate:// 收益率
			clearTv();
			allList.clear();
			pages = 0;
			tvRate.setTextColor(this.getResources().getColor(
					R.color.normal_purple));
			if ("DailyProfit".equals(key) || "DailyProfit".equals(key)) {
				imgRate.setImageResource(R.drawable.ic_product_list_arrow_bttom_press);
				desc = 1;
			} else {
				if (desc == 0) {
					imgRate.setImageResource(R.drawable.ic_product_list_arrow_bttom_press);
					desc = 1;
				} else if (desc == 1) {
					imgRate.setImageResource(R.drawable.ic_product_list_arrow_top_press);
					desc = 0;
				}
			}
			key = "LatestWeeklyYield";
			requestData();
			break;
		default:
			break;
		}
	}

	/** 标题重置 */
	public void clearTv() {
		tvPerson.setTextColor(this.getResources().getColor(R.color.text_deep));
		tvRate.setTextColor(this.getResources().getColor(R.color.text_deep));
		tvWf.setTextColor(this.getResources().getColor(R.color.text_deep));
		imgPerson
				.setImageResource(R.drawable.ic_product_list_arrow_bttom_normal);
		imgWf.setImageResource(R.drawable.ic_product_list_arrow_bttom_normal);
		imgRate.setImageResource(R.drawable.ic_product_list_arrow_bttom_normal);
	}

	@Override
	public void onRefresh() {// 下拉刷新
		// TODO Auto-generated method stub
		Log.e(TAG, "onRefresh");
		// allList.clear();
		// requestData();
		stop();
	}

	@Override
	public void onLoadMore() {// 上滑加载
		// TODO Auto-generated method stub
		// stop();
		++pages;
		requestData();
	}

	/** 请求数据 */
	public void requestData() {
		AjaxParams params = new AjaxParams();
		if (desc == 0) {
			params.put("orderby", "desc");// 倒序还是正序，asc 正；desc 倒
		} else {
			params.put("orderby", "asc");// 倒序还是正序，asc 正；desc 倒
		}
		params.put("key", key);// 排序参照的字段
		params.put("page", pages + "");// 页码 1开始
		params.put("listRows", 10 + "");// 每页条数
		params.put("fundType", "1109");// 基金类型
		httpRequest.get(Urls.PRODUCT_LIST_TWO, params, callBack, 0);
	}

	public void stop() {
		lv.stopRefresh();
		lv.stopLoadMore();
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		list.clear();
		list = LogicProductList.parseProductListElse(jsonString);
		for (ProductListBean bean : list) {
			allList.add(bean);
		}
		adapter.notifyDataSetChanged();
		stop();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ProductDetailActivity.class);
		if (TextUtils.isEmpty(allList.get(arg2 - 1).getSharetype())
				|| "null".equals(allList.get(arg2 - 1).getSharetype())) {
			app.fundBean.setSharetype("A");
		} else {
			app.fundBean.setSharetype(allList.get(arg2 - 1).getSharetype());
		}
		app.fundBean.setFundname(allList.get(arg2 - 1).getFundname());
		app.fundBean.setFundcode(allList.get(arg2 - 1).getFundcode());
		startActivity(intent);
		AnimUtil.pushLeftInAndOut(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent("refresh_tab");
			intent.putExtra("intoSelect", 1);
			this.sendBroadcast(intent);
			return true;
		}
		return false;
	}
}
