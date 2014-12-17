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
import com.zc.addmony.adapter.activities.PhoneListNewAdapter;
import com.zc.addmony.bean.activities.ShowPhoneBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicActivities;
import com.zc.addmony.utils.AnimUtil;

public class PhoneListNewActivity extends BaseActivity implements OnItemClickListener {
	private ListView lvPhone;
	private List<ShowPhoneBean> phoneAllList;
	private String aid;
	private PhoneListNewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_phone_list_new_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		phoneAllList = new ArrayList<ShowPhoneBean>();
		aid = getIntent().getStringExtra("aid");
		

	}

	@Override
	protected void setTitleViews() {
		titleText.setText("选择号码");

	}

	@Override
	protected void setViews() {
		lvPhone = (ListView) findViewById(R.id.activity_phone_list_new_lv);
		lvPhone.setOnItemClickListener(this);
		getPhoneNumRequest();

	}

	/**
	 * 获取地方手机号
	 */
	private void getPhoneNumRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("aid", aid);
		params.put("page", 1 + "");
		params.put("meiye", 100000 + "");
		httpRequest.get(Urls.GET_PHONE_NUM, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		switch (reqeustCode) {
		case 0:
			dismissLoading();
			try {
				JSONObject obj = new JSONObject(jsonString);
				String listjson = obj.optString("list");
				if (TextUtils.isEmpty(listjson) || listjson.equals("null")) {
					showToast("该地区手机号为空");
					return;
				}
				JSONArray array = obj.getJSONArray("list");
				phoneAllList = LogicActivities.parsePhoneNum(array.toString());
				if (phoneAllList.size() == 0) {
					showToast("该地区手机号为空");
					return;
				}
				adapter = new PhoneListNewAdapter(this,phoneAllList);
				lvPhone.setAdapter(adapter);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent();
		intent.putExtra("phone", phoneAllList.get(arg2).getPhone());
		setResult(RESULT_OK, intent);
		finish();
		
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
