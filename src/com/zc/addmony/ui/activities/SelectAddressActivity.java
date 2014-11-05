package com.zc.addmony.ui.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.adapter.activities.PhoneAddressAdapter;
import com.zc.addmony.bean.activities.AddressBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicActivities;

public class SelectAddressActivity extends BaseActivity implements
		OnItemClickListener {

	private ListView lvProvince, lvCity;
	private PhoneAddressAdapter adapter;
	private List<AddressBean> list;
	private String address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_select_address_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		list = new ArrayList<AddressBean>();

	}

	@Override
	protected void setTitleViews() {
		titleText.setText("选择省份");

	}

	@Override
	protected void setViews() {
		lvProvince = (ListView) findViewById(R.id.activity_select_address_lv_province);
		lvCity = (ListView) findViewById(R.id.activity_select_address_lv_city);
		lvProvince.setOnItemClickListener(this);
		lvCity.setOnItemClickListener(this);
		getProvinceAddressRequest();
	}

	/**
	 * 获取号码省归属地
	 */
	private void getProvinceAddressRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		httpRequest.get(Urls.GET_ADDRESS, params, callBack, 0);

	}

	/**
	 * 获取城市
	 */
	private void getCityAddressRequest(String aid) {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("aid", aid);
		httpRequest.get(Urls.GET_ADDRESS, params, callBack, 1);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		dismissLoading();
		switch (reqeustCode) {
		case 0:
			try {
				list = LogicActivities.parseGetAddress(jsonString);
				adapter = new PhoneAddressAdapter(this, list);
				lvProvince.setAdapter(adapter);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				list = new ArrayList<AddressBean>();
				list = LogicActivities.parseGetAddress(jsonString);
				adapter = new PhoneAddressAdapter(this, list);
				lvCity.setAdapter(adapter);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg0.getId()) {
		case R.id.activity_select_address_lv_province:// 省份
			getCityAddressRequest(list.get(arg2).getAid());
			address = list.get(arg2).getArea();
			lvProvince.setVisibility(View.GONE);
			lvCity.setVisibility(View.VISIBLE);
			break;
		case R.id.activity_select_address_lv_city:// 市
			address = address+list.get(arg2).getArea();
			Intent intent = new Intent();
			intent.putExtra("address", address);
			intent.putExtra("aid", list.get(arg2).getAid());
			setResult(RESULT_OK, intent);
			finish();
			break;

		default:
			break;
		}

	}

}
