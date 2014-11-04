package com.zc.addmony.ui.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.adapter.activities.PhoneListAdapter;
import com.zc.addmony.bean.activities.PhoneBean;
import com.zc.addmony.bean.activities.PhoneTCBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicActivities;
import com.zc.addmony.utils.AnimUtil;

/** 活动手机列表 */
public class PhoneListActivity extends BaseActivity implements
		OnItemClickListener {

	private ListView lv;
	private List<PhoneBean> requestPhones, showPhones;
	private PhoneListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_phone_list_layout);
		setViews();
		requestPhone();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		requestPhones = new ArrayList<PhoneBean>();
		showPhones = new ArrayList<PhoneBean>();
		adapter = new PhoneListAdapter(showPhones, getApplicationContext());
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("天天増财");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		lv = (ListView) findViewById(R.id.lv_activity_phone_list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
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

	/** 获取手机信息 */
	public void requestPhone() {
		showLoading();
		AjaxParams params = new AjaxParams();
		httpRequest.get(Urls.GET_PHONES, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		switch (reqeustCode) {
		case 0:
			requestPhones = LogicActivities.parsePhoneList(jsonString);
			for (PhoneBean bean : requestPhones) {
				if (bean.getPhoneTC().size() == 1) {
					showPhones.add(bean);
				} else {
					for (PhoneTCBean tcBean : bean.getPhoneTC()) {
						PhoneBean phoneBean = new PhoneBean();
						List<PhoneTCBean> tcList = new ArrayList<PhoneTCBean>();
						tcList.add(tcBean);
						phoneBean.setListUrl(bean.getListUrl());
						phoneBean.setPhoneColors(bean.getPhoneColors());
						phoneBean.setPhoneId(bean.getPhoneId());
						phoneBean.setPhoneName(bean.getPhoneName());
						phoneBean.setPhoneUrl(bean.getPhoneUrl());
						phoneBean.setPhoneTC(tcList);
						showPhones.add(phoneBean);
						adapter.notifyDataSetChanged();
					}
				}
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PhoneListActivity.this,
				ShoppingActivity.class);
		startActivity(intent);
	}
}
