package com.zc.addmony.ui.myproduct;

import java.util.ArrayList;
import java.util.List;
import javax.crypto.spec.OAEPParameterSpec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.jky.struct2.http.core.AjaxParams;
import com.jky.struct2.http.entityhandle.HttpResult;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.adapter.myproduct.ManageBankAdapter;
import com.zc.addmony.bean.BaseBean;
import com.zc.addmony.bean.myproduct.ManageBankBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicBase;
import com.zc.addmony.logic.LogicBuyProduct;
import com.zc.addmony.ui.buyproduct.CheckBankActivity;
import com.zc.addmony.ui.buyproduct.PerfectInformationActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.DialogUtil;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;

/** 管理银行卡 */
public class ManageBankActivity extends BaseActivity implements
		ListViewPassValuetoActivityListener {

	private MApplication app;
	private ListView lv;
	private Button btAdd;
	private ManageBankAdapter adapter;
	private UserSharedData userShare;
	private List<ManageBankBean> banks;
	private int position;// 删除的posi

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if("refresh_banks".equals(intent.getAction())){
				getBankList();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_manage_bank_layout);
		setViews();
		getBankList();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		app = (MApplication) this.getApplication();
		app.addAllActivity(this);
		userShare = UserSharedData.getInstance(getApplicationContext());
		banks = new ArrayList<ManageBankBean>();
		adapter = new ManageBankAdapter(getApplicationContext(), banks);
		adapter.setListViewPassValuetoActivityListener(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction("refresh_banks");
		registerReceiver(receiver, filter);
		
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("银行卡管理");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		btAdd = (Button) findViewById(R.id.activity_manage_bank_bt_add);
		btAdd.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.activity_manage_bank_lv);
		SetListViewHeight();
		lv.setAdapter(adapter);
	}

	/** 设置listview高度 */
	public void SetListViewHeight() {
		if (banks.size() > 0) {
			View listitem = adapter.getView(0, null, lv);
			listitem.measure(0, 0);
			ViewGroup.LayoutParams params = lv.getLayoutParams();
			params.height = (listitem.getMeasuredHeight()) * 10;
			lv.setLayoutParams(params);
		}
	}

	/** 获取银行卡列表 */
	public void getBankList() {
		AjaxParams params = new AjaxParams();
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GET_BANK_LIST, params, callBack, 0);
	}

	/** 获取银行卡限额*/
	public void getBankCode(){
		AjaxParams params = new AjaxParams();
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.GET_BANK_CODE, params, callBack, 2);
	}
	
	/** 解绑银行卡 */
	public void requestUnbindBank(String id) {
		showLoading();
		AjaxParams params = new AjaxParams();
		params.put("tradeacco", id);
		params.put("tradepwd", userShare.GetPwd());
		httpRequest.addHeader("Cookie", "PHPSESSID=" + userShare.GetSession());
		httpRequest.get(Urls.DELETE_BANK, params, callBack, 1);
	}

	@Override
	protected void handleResult(int requestCode, HttpResult result) {
		// TODO Auto-generated method stub
		super.handleResult(requestCode, result);
		ManageBankBean bean;
		String baseJson = result.baseJson;
		System.out.println("-----json:------" + baseJson);
		BaseBean baseBean = LogicBase.getInstance().parseData(baseJson);
		if (requestCode == 0) {//获取银行卡列表
			switch (baseBean.getStatus()) {
			case 1:
				System.out.println("-----baseBean.getData():------"
						+ baseBean.getContent());
				banks = LogicBuyProduct.parseMyBanks(baseBean.getContent());
//				bean = new ManageBankBean();
//				bean.setFlag(0);
//				banks.add(bean);
				adapter = new ManageBankAdapter(getApplicationContext(), banks);
				adapter.setListViewPassValuetoActivityListener(this);
				lv.setAdapter(adapter);
				setListViewHeightBasedOnChildren(lv);
				
				break;
			default:// 请求失败
				showToast(baseBean.getMessage());
				banks.clear();
				bean = new ManageBankBean();
				bean.setFlag(0);
				banks.add(bean);
				adapter.notifyDataSetChanged();
				break;
			}
		}
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		if (reqeustCode == 1) {// 删除成功
			showToast("删除成功");
			banks.remove(position);
			adapter.notifyDataSetChanged();
		}else if(reqeustCode == 2){
			try {
				JSONArray array = new JSONArray(jsonString);
				for(int i =0; i < array.length(); i++){
					JSONObject obj = array.getJSONObject(i);
					String str1 = obj.optString("default");
					Log.e("", str1+"");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void doPassActionListener(Object obj, int org1, final int org2,
			String str) {
		// TODO Auto-generated method stub
		switch (org1) {
		case 1:// 打款验证

			break;

		case 2:// 解除绑定
			DialogUtil.showDialog(ManageBankActivity.this, "要删除该银行卡吗？      ",
					"删除", "取消", new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (v.getId() == R.id.dialog_prompt_btn_ok) {
								position = org2;
								requestUnbindBank(banks.get(org2)
										.getTradeacco());
							}
						}
					});
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
			AnimUtil.pushRightInAndOut(ManageBankActivity.this);
			break;
		case R.id.activity_manage_bank_bt_add:
			Intent intent = new Intent(this, PerfectInformationActivity.class);
			intent.putExtra("addOrRegister", 1);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 动态设置ListView的高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		if (listView == null)
			return;

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
