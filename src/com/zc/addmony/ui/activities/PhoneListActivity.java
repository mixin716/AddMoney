package com.zc.addmony.ui.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.bean.activities.PhoneBean;
import com.zc.addmony.bean.activities.PhoneTCBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicActivities;
import com.zc.addmony.utils.AnimUtil;

/** 活动手机列表 */
public class PhoneListActivity extends BaseActivity {

	private ListView lv;
	private List<PhoneBean> requestPhones,showPhones;
	
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
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("天天増财");
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
			for(PhoneBean bean : requestPhones){
				Log.e("", bean.getPhoneTC().size()+"   ----");
				if(bean.getPhoneTC().size() == 1){
					showPhones.add(bean);
				}else{
					for(PhoneTCBean tcBean: bean.getPhoneTC()){
						List<PhoneTCBean> tcList = new ArrayList<PhoneTCBean>();
						tcList.add(tcBean);
						bean.setPhoneTC(tcList);
						showPhones.add(bean);
						Log.e("", showPhones.size()+"   .....");
					}
				}
			}
			Log.e("", showPhones.size()+"");
			break;

		default:
			break;
		}
	}
}
