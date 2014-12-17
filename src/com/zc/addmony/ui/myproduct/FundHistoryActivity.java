package com.zc.addmony.ui.myproduct;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.adapter.myproduct.RecordDetailsAdapter;
import com.zc.addmony.bean.myproduct.MoneyChangeBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.views.XListView;

/** 基金历史 */
public class FundHistoryActivity extends BaseActivity {

	private MApplication app;
	private XListView lv;
	private RecordDetailsAdapter adapter;
	private List<MoneyChangeBean> list;
	private UserSharedData userShare;
	private String fundcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_fund_history_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		app = (MApplication) this.getApplication();
		app.addAllActivity(this);
		fundcode = this.getIntent().getStringExtra("fundcode");
		list = new ArrayList<MoneyChangeBean>();
		adapter = new RecordDetailsAdapter(getApplicationContext(), list);
		userShare = UserSharedData.getInstance(getApplicationContext());
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("历史记录");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		lv = (XListView) findViewById(R.id.activity_fund_history_lv_content);
		lv.setPullRefreshEnable(false);
		lv.setPullLoadEnable(false);
		lv.setAdapter(adapter);
		getMoneyChageRequest();
	}

	private void getMoneyChageRequest() {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("fundcode", fundcode);
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GET_ZCB_BUTTOM_INFO, params, callBack, 1);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		switch (reqeustCode) {
		case 1:
			try {
				dismissLoading();
				JSONArray array = new JSONArray(jsonString);
				int len = array.length();
				if(len == 0){
					showToast("当前没有历史记录");
					return;
				}
				for (int i = 0; i < len; i++) {
					MoneyChangeBean bean = new MoneyChangeBean();
					JSONObject obj = array.getJSONObject(i);
					bean.setFundname(obj.optString("fundname"));
					bean.setHappeningsum(obj.optString("happeningsum"));
					bean.setApplydate(obj.optString("applydate"));
					bean.setApplyshare(obj.optString("applyshare"));
					bean.setApplysum(obj.optString("applysum"));
					bean.setCallingcode(obj.optString("callingcode"));
					bean.setBusinflagStr(obj.optString("businflagStr"));
					list.add(bean);
				}
				adapter.notifyDataSetChanged();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
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
