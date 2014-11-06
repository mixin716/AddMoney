package com.zc.addmony.ui.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.adapter.activities.EmailAddressAdapter;
import com.zc.addmony.adapter.activities.PhoneAddressAdapter;
import com.zc.addmony.bean.activities.AddressBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicActivities;
import com.zc.addmony.ui.buyproduct.SelectActivity;
import com.zc.addmony.utils.AnimUtil;

public class EmailAddressActivity extends BaseActivity implements
		OnItemClickListener {

	private ListView lvContent;
	private EmailAddressAdapter adapter;
	private List<String> list;
	private String address;
	private int type;// 1，请求省份，2，请求市
	private String province;// 省

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_email_address_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		list = new ArrayList<String>();
		type = getIntent().getIntExtra("type", 0);
	}

	@Override
	protected void setTitleViews() {
		titleText.setText("选择省份");

	}

	@Override
	protected void setViews() {
		lvContent = (ListView) findViewById(R.id.activity_email_address_lv_content);
		lvContent.setOnItemClickListener(this);
		if (type == 1) {
			getProvinceRequest();
		} else if (type == 2) {
			province = getIntent().getStringExtra("province");
			getCityRequest(province);
		}
	}

	/** 获取省份 */
	private void getProvinceRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("key", "province");
		httpRequest.get(Urls.PERFECT_INFORMATION, params, callBack, 1);

	}

	/** 获取城市 */
	private void getCityRequest(String province) {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("key", "city");
		params.put("province", province);
		httpRequest.get(Urls.PERFECT_INFORMATION, params, callBack, 2);

	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		dismissLoading();
		switch (reqeustCode) {
		case 1:
			try {
				if (TextUtils.isEmpty(jsonString) || jsonString.equals("null")) {
					showToast("省份数据为空");
				} else {
					JSONArray array = new JSONArray(jsonString);
					int len = array.length();
					list = new ArrayList<String>();
					for (int i = 0; i < len; i++) {
						JSONObject obj = array.getJSONObject(i);
						list.add(obj.optString("VC_PROVINCENAME"));
					}
					adapter = new EmailAddressAdapter(getApplicationContext(),
							list);
					lvContent.setAdapter(adapter);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			break;
		case 2:
			try {
				if (TextUtils.isEmpty(jsonString) || jsonString.equals("null")) {
					showToast("城市数据为空");
				} else {
					JSONArray array = new JSONArray(jsonString);
					int len = array.length();
					list = new ArrayList<String>();
					for (int i = 0; i < len; i++) {
						JSONObject obj = array.getJSONObject(i);
						list.add(obj.optString("VC_CITYNAME"));
					}
					adapter = new EmailAddressAdapter(getApplicationContext(),
							list);
					lvContent.setAdapter(adapter);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		address = list.get(arg2);
		Intent intent = new Intent();
		intent.putExtra("address", address);
		setResult(RESULT_OK, intent);
		finish();

	}

}
