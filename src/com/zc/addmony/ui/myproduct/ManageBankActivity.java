package com.zc.addmony.ui.myproduct;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.jky.struct2.http.core.AjaxParams;
import com.jky.struct2.http.entityhandle.HttpResult;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.adapter.myproduct.ManageBankAdapter;
import com.zc.addmony.bean.BaseBean;
import com.zc.addmony.bean.myproduct.ManageBankBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.logic.LogicBase;
import com.zc.addmony.logic.LogicBuyProduct;
import com.zc.addmony.ui.buyproduct.CheckBankActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;

/** 管理银行卡 */
public class ManageBankActivity extends BaseActivity implements
		ListViewPassValuetoActivityListener {

	private ListView lv;
	private Button btAdd;
	private ManageBankAdapter adapter;
	private UserSharedData userShare;
	private List<ManageBankBean> banks;

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
		userShare = UserSharedData.getInstance(getApplicationContext());
		banks = new ArrayList<ManageBankBean>();
		adapter = new ManageBankAdapter(getApplicationContext(), banks);
		adapter.setListViewPassValuetoActivityListener(this);
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

	@Override
	protected void handleResult(int requestCode, HttpResult result) {
		// TODO Auto-generated method stub
		super.handleResult(requestCode, result);
		ManageBankBean bean;
		String baseJson = result.baseJson;
		System.out.println("-----json:------" + baseJson);
		BaseBean baseBean = LogicBase.getInstance().parseData(baseJson);
		switch (baseBean.getStatus()) {
		case 1:
			System.out.println("-----baseBean.getData():------"
					+ baseBean.getContent());
			banks = LogicBuyProduct.parseMyBanks(baseBean.getContent());
			bean = new ManageBankBean();
			bean.setFlag(0);
			banks.add(bean);
			adapter = new ManageBankAdapter(getApplicationContext(), banks);
			lv.setAdapter(adapter);
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

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
	}

	@Override
	public void doPassActionListener(Object obj, int org1, int org2, String str) {
		// TODO Auto-generated method stub
		switch (org1) {
		case 1:// 打款验证

			break;

		case 2:// 解除绑定

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
			Intent intent = new Intent(this, CheckBankActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
