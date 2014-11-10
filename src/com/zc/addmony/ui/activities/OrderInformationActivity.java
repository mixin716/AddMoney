package com.zc.addmony.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.bean.activities.OrderDetailBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicActivities;
import com.zc.addmony.utils.AnimUtil;

public class OrderInformationActivity extends BaseActivity {

	private TextView tvName, tvIdcard, tvConnectPhone, tvAdress, tvPhoneName,
			tvPhoneColor, tvPhoneTc, tvSelectPhone,tvRemark;
	private OrderDetailBean bean;
	private UserSharedData userShare;

	@Override
 	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_order_information_layout);
		setViews();
		requestOrder();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		bean = new OrderDetailBean();
		userShare = UserSharedData.getInstance(getApplicationContext());
	}

	@Override
	protected void setTitleViews() {
		titleText.setText("订单详情");

	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		tvName = (TextView) findViewById(R.id.activity_order_detail_tv_name);
		tvIdcard = (TextView) findViewById(R.id.activity_order_detail_tv_idcard);
		tvConnectPhone = (TextView) findViewById(R.id.activity_order_detail_tv_conect_phone);
		tvAdress = (TextView) findViewById(R.id.activity_order_detail_tv_address);
		tvPhoneName = (TextView) findViewById(R.id.activity_order_detail_tv_phone_name);
		tvPhoneColor = (TextView) findViewById(R.id.activity_order_detail_tv_phone_color);
		tvPhoneTc = (TextView) findViewById(R.id.activity_order_detail_tv_tc);
		tvSelectPhone = (TextView) findViewById(R.id.activity_order_detail_tv_select_phone);
		tvRemark = (TextView) findViewById(R.id.activity_order_detail_tv_remark);
	}
	
	/** 请求订单*/
	public void requestOrder(){
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("idcard", userShare.GetIdcard());
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GET_ORDER, params, callBack, 0);
	}
	
	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		bean = LogicActivities.parseOrderDetail(jsonString);
		tvName.setText(bean.getO_name());
		tvIdcard.setText(bean.getO_card());
		tvConnectPhone.setText(bean.getO_phone());
		tvAdress.setText(bean.getO_contact());
		tvPhoneName.setText(bean.getName());
		tvPhoneColor.setText(bean.getColor());
		tvPhoneTc.setText(bean.getP_name());
		tvSelectPhone.setText(bean.getPhone());
		tvRemark.setText(bean.getO_remarks());
	}
	
	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(OrderInformationActivity.this);
			break;

		default:
			break;
		}
	}

}
