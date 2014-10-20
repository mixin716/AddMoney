package com.zc.addmony.ui.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.jky.struct2.http.FinalHttp;
import com.jky.struct2.http.core.AjaxCallBack;
import com.jky.struct2.http.core.AjaxParams;
import com.jky.struct2.http.entityhandle.HttpExceptionResult;
import com.jky.struct2.http.entityhandle.HttpResult;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.adapter.activities.AddressAdapter;
import com.zc.addmony.adapter.activities.CityAddressAdapter;
import com.zc.addmony.adapter.activities.PhoneFragmenAdapter;
import com.zc.addmony.bean.BaseBean;
import com.zc.addmony.bean.activities.AddressBean;
import com.zc.addmony.bean.activities.ShowPhoneBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicActivities;
import com.zc.addmony.logic.LogicBase;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;

public class SelectPhoneActivity extends FragmentActivity implements
		OnClickListener, ListViewPassValuetoActivityListener {

	protected ImageView titleIvLeft;
	protected ImageView titleIvRight;
	protected TextView titleText;
	private TextView tvAddress;
	protected ViewGroup titleLayout;
	protected ViewGroup loadingLayout;
	private static int width, height;
	private MarginLayoutParams mp;
	private LinearLayout.LayoutParams lp;
	protected Toast toast;
	protected FinalHttp httpRequest;

	private ViewPager viewPager;
	private List<PhonesFragment> fragments;
	private List<List<ShowPhoneBean>> phones;
	private PhoneFragmenAdapter adapter;
	private int pageNum = 0;// 页数
	private LinearLayout llPhoneAddress;
	private Button btnSendOrder;
	private PopupWindow popWindow;
	private List<AddressBean> list;
	private List<AddressBean> citylist;
	private List<ShowPhoneBean> phoneAllList;
	private Intent intent;
	private boolean isFrist = true;
	private MApplication app;

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("refresh_phone")) {
				phones.clear();
				int yu = phoneAllList.size() % 20;
				int zheng = phoneAllList.size() / 20;
				int len = 0;
				if (yu == 0) {
					len = zheng;
				} else {
					len = zheng + 1;
				}
				for (int i = 0; i < phoneAllList.size(); i++) {
					phoneAllList.get(i).setFlag(0);
				}
				for (int i = 0; i < len; i++) {
					List<ShowPhoneBean> list = new ArrayList<ShowPhoneBean>();
					if (phoneAllList.size() > (i + 1) * 20) {
						for (int j = 0; j < 20; j++) {
							ShowPhoneBean bean = new ShowPhoneBean();
							bean.setFlag(0);
							bean.setPhone(phoneAllList.get(i * 20 + j)
									.getPhone());
							list.add(bean);
						}
					} else {
						list.addAll(phoneAllList.subList(i * 20,
								phoneAllList.size()));
					}
					phones.add(list);
				}
				int position = intent.getIntExtra("position", 0);
				phones.get(pageNum).get(position).setFlag(1);
				fragments.get(pageNum).setPhones(phones.get(pageNum),
						getApplicationContext(), pageNum,phones.size());
				adapter.notifyDataSetChanged();
				Log.e("refresh_phone", "pageNum-->" + pageNum
						+ "  mposition-->" + position);
				app.apBean.setPhoneNum(phoneAllList
						.get(20 * pageNum + position).getPhone());
			}else if (intent.getAction().equals("click_page")) {//上一页 下一页
				int position = intent.getIntExtra("position", 0);//获取页数
				int clickFlag = intent.getIntExtra("clickFlag", 0);//1上一页 2下一页
				Log.e("", "click_page-->"+position+"   "+clickFlag);
				if(clickFlag == 1){
					viewPager.arrowScroll(1);
				}else if(clickFlag == 2){
					viewPager.arrowScroll(2);
				}
			}
		}

	};

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_get_phone_address_layout);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("refresh_phone");
		intentFilter.addAction("click_page");
		registerReceiver(receiver, intentFilter);
		httpRequest = new FinalHttp(this);
		getWindowHW();
		initVariable();
		setViews();
	}

	private void initVariable() {
		app = (MApplication) getApplication();
		list = new ArrayList<AddressBean>();
		citylist = new ArrayList<AddressBean>();
		phones = new ArrayList<List<ShowPhoneBean>>();
		phoneAllList = new ArrayList<ShowPhoneBean>();
		fragments = new ArrayList<PhonesFragment>();
		adapter = new PhoneFragmenAdapter(getSupportFragmentManager(),
				fragments, phones, getApplicationContext());
	}

	/**
	 * 获取手机分辨率
	 */
	public void getWindowHW() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		width = dm.widthPixels;
		height = dm.heightPixels;
	}

	private void setViews() {

		titleLayout = (ViewGroup) findViewById(R.id.page_title);
		tvAddress = (TextView) findViewById(R.id.activity_get_phone_tv_address);
		mp = new MarginLayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
				(int) (height * 0.0875));
		lp = new LinearLayout.LayoutParams(mp);
		titleLayout.setLayoutParams(lp);
		titleIvLeft = (ImageView) titleLayout.findViewById(R.id.title_iv_left);
		titleIvRight = (ImageView) titleLayout
				.findViewById(R.id.title_iv_right);
		titleText = (TextView) titleLayout.findViewById(R.id.title_tv_text);
		loadingLayout = (ViewGroup) findViewById(R.id.page_loading);
		loadingLayout.setVisibility(View.GONE);
		titleText.setText("天天增财");
		titleIvLeft.setOnClickListener(this);
		titleIvRight.setOnClickListener(this);

		llPhoneAddress = (LinearLayout) findViewById(R.id.activity_get_phone_ll_address);
		btnSendOrder = (Button) findViewById(R.id.activity_get_phone_btn_send_order);
		llPhoneAddress.setOnClickListener(this);
		btnSendOrder.setOnClickListener(this);

		viewPager = (ViewPager) findViewById(R.id.activity_get_phone_viewPager);
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(0);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Log.e("onPageSelected", arg0 + "");
				pageNum = arg0;
				fragments.get(arg0).setPhones(phones.get(arg0),
						getApplicationContext(), pageNum,phones.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * 获取号码归属地
	 */
	private void getAddressRequest() {
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
		httpRequest.get(Urls.GET_ADDRESS, params, callBack, 2);

	}

	/**
	 * 获取地方手机号
	 */
	private void getPhoneNumRequest(String aid) {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("aid", aid);
		params.put("page", 1 + "");
		params.put("meiye", 100000 + "");
		httpRequest.get(Urls.GET_PHONE_NUM, params, callBack, 1);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_get_phone_ll_address:// 号码归属地
			if (null != popWindow && popWindow.isShowing()) {
				popWindow.dismiss();
			}
			if (isFrist) {
				getAddressRequest();
			} else {
				setPopWindow(llPhoneAddress);
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

	/**
	 * 返回数据进行处理
	 */
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		dismissLoading();
		switch (reqeustCode) {
		case 0:
			try {
				list = LogicActivities.parseGetAddress(jsonString);
				setPopWindow(llPhoneAddress);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				phones.clear();
				fragments.clear();
				JSONObject obj = new JSONObject(jsonString);
				String listjson = obj.optString("list");
				if (TextUtils.isEmpty(listjson) || listjson.equals("null")) {
					showToast("该地区手机号为空");
					adapter = new PhoneFragmenAdapter(
							getSupportFragmentManager(), fragments, phones,
							getApplicationContext());
					viewPager.setAdapter(adapter);
					return;
				}
				JSONArray array = obj.getJSONArray("list");
				phoneAllList = LogicActivities.parsePhoneNum(array.toString());
				if (phoneAllList.size() == 0) {
					showToast("该地区手机号为空");
					return;
				}
				int yu = phoneAllList.size() % 20;
				int zheng = phoneAllList.size() / 20;
				int len = 0;
				if (yu == 0) {
					len = zheng;
				} else {
					len = zheng + 1;
				}
				for (int i = 0; i < len; i++) {
					List<ShowPhoneBean> list = new ArrayList<ShowPhoneBean>();
					if (phoneAllList.size() > (i + 1) * 20) {
						for (int j = 0; j < 20; j++) {
							ShowPhoneBean bean = new ShowPhoneBean();
							bean.setFlag(0);
							bean.setPhone(phoneAllList.get(i * 20 + j)
									.getPhone());
							list.add(bean);
						}
					} else {
						list.addAll(phoneAllList.subList(i * 20,
								phoneAllList.size()));
					}
					phones.add(list);
				}
				for (int i = 0; i < len; i++) {
					PhonesFragment pf = new PhonesFragment();
					pf.setPhones(phones.get(i), getApplicationContext(), i,phones.size());
					fragments.add(pf);
				}
				adapter.notifyDataSetChanged();

			} catch (JSONException e) {
				e.printStackTrace();
			}

			break;
		case 2:
			try {
				citylist = LogicActivities.parseGetAddress(jsonString);
				setCityPopWindow(llPhoneAddress);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 显示省份列表
	 */
	private void setPopWindow(LinearLayout v) {
		View contentView = getLayoutInflater().inflate(
				R.layout.get_phone_popupwindow_layout, null);
		ListView addressList = (ListView) contentView
				.findViewById(R.id.get_phone_popupwindow_lv);
		AddressAdapter adapter = new AddressAdapter(this, list);
		adapter.setListViewPassValuetoActivityListener(SelectPhoneActivity.this);
		addressList.setAdapter(adapter);
		addressList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// popWindow.dismiss();

			}
		});

		popWindow = new PopupWindow(contentView, v.getWidth(), height / 2);
		popWindow.setFocusable(true);
		popWindow.setOutsideTouchable(true);
		popWindow.setBackgroundDrawable(new BitmapDrawable());
		popWindow.showAsDropDown(v);
		// llPhoneAddress.setClickable(false);

	}

	/**
	 * 显示市区列表
	 */
	private void setCityPopWindow(LinearLayout v) {
		View contentView = getLayoutInflater().inflate(
				R.layout.get_phone_popupwindow_layout, null);
		ListView addressList = (ListView) contentView
				.findViewById(R.id.get_phone_popupwindow_lv);
		CityAddressAdapter adapter = new CityAddressAdapter(this, citylist);
		adapter.setListViewPassValuetoActivityListener(SelectPhoneActivity.this);
		addressList.setAdapter(adapter);
		addressList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// popWindow.dismiss();

			}
		});

		popWindow = new PopupWindow(contentView, v.getWidth(), height / 2);
		popWindow.setFocusable(true);
		popWindow.setOutsideTouchable(true);
		popWindow.setBackgroundDrawable(new BitmapDrawable());
		popWindow.showAsDropDown(v);
		// llPhoneAddress.setClickable(false);

	}

	@Override
	public void doPassActionListener(Object obj, int org1, int org2, String str) {
		if (str.equals("省份")) {
			if ((Boolean) obj) {
				isFrist = false;
				popWindow.dismiss();
				// llPhoneAddress.setClickable(true);
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setFlag(false);
				}
				list.get(3 * org1 + org2).setFlag(true);
				app.apBean.setPhoneAdd(list.get(3 * org1 + org2).getArea());
				tvAddress.setText(app.apBean.getPhoneAdd());
				String aid = list.get(3 * org1 + org2).getAid();
				getCityAddressRequest(aid);
			}
		} else if (str.equals("城市")) {
			if ((Boolean) obj) {
				popWindow.dismiss();
				// llPhoneAddress.setClickable(true);
				for (int i = 0; i < citylist.size(); i++) {
					citylist.get(i).setFlag(false);
				}
				citylist.get(3 * org1 + org2).setFlag(true);
				app.apBean.setPhoneAddCity(citylist.get(3 * org1 + org2)
						.getArea());
				tvAddress.setText(app.apBean.getPhoneAdd()+app.apBean.getPhoneAddCity());
				String aid = citylist.get(3 * org1 + org2).getAid();
				getPhoneNumRequest(aid);
			}

		}

	}

	protected void handleResult(int requestCode, HttpResult result) {
		String baseJson = result.baseJson;
		System.out.println("-----json:------" + baseJson);
		BaseBean baseBean = LogicBase.getInstance().parseData(baseJson);
		switch (baseBean.getStatus()) {
		case 1:
			handleJson(requestCode, baseBean.getContent(),
					baseBean.getMessage());
			break;
		default:// 请求失败
			showToast(baseBean.getMessage());
			break;
		}

	}

	protected AjaxCallBack<HttpResult> callBack = new AjaxCallBack<HttpResult>() {
		@Override
		public void onSuccess(HttpResult httpResult) {
			dismissLoading();
			handleResult(httpResult.which, httpResult);
		};

		@Override
		public void onFailure(int which, HttpExceptionResult result) {
			dismissLoading();
			// handleNetErr(which, result.code);
		};
	};

	/**
	 * 显示等待dialog
	 */
	protected void showLoading() {
		if (loadingLayout != null) {
			loadingLayout.bringToFront();
			loadingLayout.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 取消等待dialog
	 */
	protected void dismissLoading() {
		if (loadingLayout != null) {
			loadingLayout.setVisibility(View.GONE);
		}

	}

	/**
	 * toast 通知
	 * 
	 * @param msg
	 */
	protected void showToast(String msg) {
		if (toast == null) {
			toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
			toast.setDuration(2200);
			toast.show();
		} else {
			toast.setText(msg);
			toast.setDuration(2200);
			toast.show();
		}
	}

}
