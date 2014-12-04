package com.zc.addmony.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.jky.struct2.http.entityhandle.HttpResult;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.BaseBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicBase;
import com.zc.addmony.ui.myproduct.LoginActivity;
import com.zc.addmony.utils.AnimUtil;

public class ActivitiesRuleActivity extends BaseActivity {
	
	private MApplication mApplication;
	private TextView tvMore;
	private LinearLayout llMoreContent;
	private Button btnOk;
	private UserSharedData userShare;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_activities_rule_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		mApplication= (MApplication) this.getApplication();
		mApplication.addActivitys(this);
		userShare = UserSharedData.getInstance(getApplicationContext());
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViews() {
		tvMore = (TextView) findViewById(R.id.activity_activities_rule_tv_more);
		llMoreContent = (LinearLayout) findViewById(R.id.activity_activities_rule_ll_more);
		btnOk = (Button) findViewById(R.id.activity_activities_rule_btn_ok);

		tvMore.setOnClickListener(this);
		btnOk.setOnClickListener(this);
	}

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_activities_rule_tv_more:
			tvMore.setVisibility(View.GONE);
			llMoreContent.setVisibility(View.VISIBLE);
			break;
		case R.id.activity_activities_rule_btn_ok:
			if (userShare.GetFlag()) {
				requestOrder();
			} else {
				intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				AnimUtil.pushLeftInAndOut(ActivitiesRuleActivity.this);
			}
			break;

		default:
			break;
		}
	}

	/** 请求订单 */
	public void requestOrder() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("idcard", userShare.GetIdcard());
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GET_ORDER, params, callBack, 1);
	}
	
	@Override
	protected void handleResult(int requestCode, HttpResult result) {
		// TODO Auto-generated method stub
		super.handleResult(requestCode, result);
		String baseJson = result.baseJson;
		System.out.println("-----json:------" + baseJson);
		BaseBean baseBean = LogicBase.getInstance().parseData(baseJson);
		switch (baseBean.getStatus()) {
		case 1:
			showToast("您已参加过该活动");
			userShare.SaveActivity(true);
			intent = new Intent(this, OrderInformationActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(ActivitiesRuleActivity.this);
			this.finish();
			break;
		default:// 请求失败
			userShare.SaveActivity(false);
			intent = new Intent(this, PhoneListActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(ActivitiesRuleActivity.this);
			break;
		}
	}

}
