package com.zc.addmony.ui.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.adapter.activities.AddressAdapter;
import com.zc.addmony.bean.activities.AddressBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicActivities;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;

public class GetPhoneAddressActivity extends BaseActivity implements
		ListViewPassValuetoActivityListener {
	private PopupWindow popWindow;
	private static int width, height;// 屏幕分辨率
	private LinearLayout llGetAddress;
	private List<AddressBean> list;
	private Button btnSenOrder;
	private Intent intent;
	private boolean isFrist = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_get_phone_address_layout);
		getWindowHW();
		setViews();
	}

	@Override
	protected void initVariable() {
		list = new ArrayList<AddressBean>();

	}

	@Override
	protected void setTitleViews() {
		titleText.setText("天天增财");

	}

	/**
	 * 获得手机分辨率
	 */
	public void getWindowHW() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		width = dm.widthPixels;
		height = dm.heightPixels;
	}

	@Override
	protected void setViews() {
		llGetAddress = (LinearLayout) findViewById(R.id.activity_get_phone_ll_address);
		btnSenOrder = (Button) findViewById(R.id.activity_get_phone_btn_send_order);
		llGetAddress.setOnClickListener(this);
		btnSenOrder.setOnClickListener(this);
	}

	private void getAddressRequest() {
		AjaxParams params = new AjaxParams();
		httpRequest.get(Urls.GET_ADDRESS, params, callBack, 0);

	}

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_get_phone_ll_address:
			if (isFrist) {
				getAddressRequest();
			} else {
				setPopWindow(llGetAddress);
			}
			break;
		case R.id.activity_get_phone_btn_send_order:// 提交订单
			intent = new Intent(this, ConfirmationOrderActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(this);
			break;

		default:
			break;
		}
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		switch (reqeustCode) {
		case 0:
			try {
				list = LogicActivities.parseGetAddress(jsonString);
				setPopWindow(llGetAddress);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 显示分享列表
	 */
	private void setPopWindow(LinearLayout v) {
		View contentView = getLayoutInflater().inflate(
				R.layout.get_phone_popupwindow_layout, null);
		ListView addressList = (ListView) contentView
				.findViewById(R.id.get_phone_popupwindow_lv);
		AddressAdapter adapter = new AddressAdapter(this, list);
		adapter.setListViewPassValuetoActivityListener(GetPhoneAddressActivity.this);
		addressList.setAdapter(adapter);
		addressList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// popWindow.dismiss();

			}
		});

		popWindow = new PopupWindow(contentView, v.getWidth(),
				height/2);
		popWindow.setFocusable(true);
		popWindow.setOutsideTouchable(true);
		// popWindow.setBackgroundDrawable(new BitmapDrawable());
		popWindow.showAsDropDown(v);
		llGetAddress.setClickable(false);

	}

	@Override
	public void doPassActionListener(Object obj, int org1, int org2, String str) {
		if ((Boolean) obj) {
			isFrist = false;
			popWindow.dismiss();
			llGetAddress.setClickable(true);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setFlag(false);
			}
			list.get(3 * org1 + org2).setFlag(true);
		}

	}

}
