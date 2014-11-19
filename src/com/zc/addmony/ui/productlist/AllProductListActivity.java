package com.zc.addmony.ui.productlist;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.R.layout;
import com.zc.addmony.adapter.productlist.ProductNameAdapter;
import com.zc.addmony.adapter.productlist.ProductRateAdapter;

public class AllProductListActivity extends BaseActivity {
	private ListView lvName, lvRate;
	private ProductNameAdapter nameAdapter;
	private ProductRateAdapter rataAdapter;
	private View nameHeader, rateHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_common_product_list_layout);
		dismissTop();
		setViews();
	}

	@Override
	protected void initVariable() {
		nameAdapter = new ProductNameAdapter(getApplicationContext());
		rataAdapter = new ProductRateAdapter(getApplicationContext());

	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViews() {
		lvName = (ListView) findViewById(R.id.activity_common_product_list_lv_name);
		lvRate = (ListView) findViewById(R.id.activity_common_product_list_lv_rate);
		nameHeader = View.inflate(getApplicationContext(),
				R.layout.view_name_header_layout, null);
		rateHeader = View.inflate(getApplicationContext(),
				R.layout.view_rate_header_layout, null);

		lvName.addHeaderView(nameHeader);
		lvRate.addHeaderView(rateHeader);

		lvName.setAdapter(nameAdapter);
		lvRate.setAdapter(rataAdapter);
		setListViewHeightBasedOnChildren(lvName);
		setListViewHeightBasedOnChildren(lvRate);

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

}
