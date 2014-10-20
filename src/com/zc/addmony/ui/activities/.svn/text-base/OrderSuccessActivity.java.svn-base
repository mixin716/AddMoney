package com.zc.addmony.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.utils.AnimUtil;

/** 订单提交成功 */
public class OrderSuccessActivity extends BaseActivity {

	private Button btInfor, btProducts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_order_success_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("天天增财");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		btInfor = (Button) findViewById(R.id.activity_order_success_bt_infor);
		btProducts = (Button) findViewById(R.id.activity_order_success_bt_other);

		btInfor.setOnClickListener(this);
		btProducts.setOnClickListener(this);
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		Intent intent;
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(OrderSuccessActivity.this);
			break;
		case R.id.activity_order_success_bt_infor:
			intent = new Intent(this, OrderInformationActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(OrderSuccessActivity.this);
			break;
		case R.id.activity_order_success_bt_other:

			break;
		}
	}

}
