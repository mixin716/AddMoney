package com.zc.addmony.ui.productlist;

import android.os.Bundle;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.utils.AnimUtil;

public class StockDetailActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_stock_detail_layout);
		setViews();
	}
	
	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		
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
