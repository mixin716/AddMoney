package com.zc.addmony.ui.myproduct;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.adapter.myproduct.BuyProductsAdapter;
import com.zc.addmony.bean.myproduct.BuyProductsBean;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;
import com.zc.addmony.views.XListView;

/** 已购买的基金列表 */
public class BuyProductsActivity extends BaseActivity implements
		ListViewPassValuetoActivityListener {

	private XListView lv;
	private BuyProductsAdapter adapter;
	private List<BuyProductsBean> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_buy_products_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		list = new ArrayList<BuyProductsBean>();
		adapter = new BuyProductsAdapter(getApplicationContext(), list);
		adapter.setListViewPassValuetoActivityListener(this);
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
	}

	@Override
	public void doPassActionListener(Object obj, int org1, int position,
			String str) {
		// TODO Auto-generated method stub
		if (org1 == 1) {// 购买

		} else if (org1 == 2) {// 赎回

		}
	}

}
