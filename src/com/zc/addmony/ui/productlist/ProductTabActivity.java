package com.zc.addmony.ui.productlist;

import java.lang.reflect.Field;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.zc.addmony.R;
import com.zc.addmony.common.UserSharedData;

@SuppressWarnings("deprecation")
public class ProductTabActivity extends TabActivity implements OnClickListener {
	private String TAG = "HomeTabActivity";
	private String str = "";
	private RadioGroup rg;
	private RadioButton rbAll, rbMoney, rbStock, rbBond;
	private Intent mAllIntent, mMoneyIntent, mStockIntent, mBondIntent, intent;
	private final String TAB_ALL = "AllProductListActivity";
	private final String TAB_MONEY = "ProductListActivity";
	private final String TAB_STOCK = "StockProductListActivity";
	private final String TAB_BOND = "BondProductListActivity";
	private TabHost tabHost;
	private int intoSelect = 1;//
	private UserSharedData userShare;
	private int width,height;
	protected ImageView titleIvLeft;
	protected TextView titleText;
	protected ViewGroup titleLayout;
	private MarginLayoutParams mp;
	private LinearLayout.LayoutParams lp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_tab_layout);
		this.tabHost = getTabHost();
		intoSelect = this.getIntent().getIntExtra("into_tab", 0);
		userShare = UserSharedData.getInstance(getApplicationContext());
		getWindowHW();
		InitViews();
		InitIntent();
		setupIntent();
		setCurrentActivity(intoSelect);
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

	private void InitViews() {
		titleLayout = (ViewGroup) findViewById(R.id.page_title);
		mp = new MarginLayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
				(int) (height * 0.0875));
		lp = new LinearLayout.LayoutParams(mp);
		titleLayout.setLayoutParams(lp);
		titleText = (TextView) titleLayout.findViewById(R.id.title_tv_text);
		titleIvLeft = (ImageView) titleLayout.findViewById(R.id.title_iv_left);
		titleIvLeft.setVisibility(View.INVISIBLE);
		titleText.setText("产品列表");
		
		
		rg = (RadioGroup) findViewById(R.id.buttom_rgroup);
		rbAll = (RadioButton) findViewById(R.id.activity_product_rb_all);
		rbMoney = (RadioButton) findViewById(R.id.activity_product_rb_money);
		rbStock = (RadioButton) findViewById(R.id.activity_product_rb_stock);
		rbBond = (RadioButton) findViewById(R.id.activity_product_rb_bond);

		rbAll.setSelected(true);
		rbAll.setOnClickListener(this);
		rbMoney.setOnClickListener(this);
		rbStock.setOnClickListener(this);
		rbBond.setOnClickListener(this);
	}

	private void InitIntent() {
		mAllIntent = new Intent(ProductTabActivity.this,
				AllProductListActivity.class);
		mAllIntent.putExtra("type", 1);
		mMoneyIntent = new Intent(ProductTabActivity.this,
				ProductListActivity.class);
		mStockIntent = new Intent(ProductTabActivity.this,
				StockProductListActivity.class);
		mAllIntent.putExtra("type", 2);
		mBondIntent = new Intent(ProductTabActivity.this,
				BondProductListActivity.class);
		mAllIntent.putExtra("type", 3);
	}

	private void setupIntent() {
		TabHost localTabHost = this.tabHost;
		try {
			Field current = tabHost.getClass().getDeclaredField("mCurrentTab");
			current.setAccessible(true);
			current.setInt(tabHost, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		localTabHost.addTab(buildTabSpec(TAB_ALL, TAB_ALL, mAllIntent));
		localTabHost.addTab(buildTabSpec(TAB_MONEY, TAB_MONEY, mMoneyIntent));
		localTabHost.addTab(buildTabSpec(TAB_STOCK, TAB_STOCK, mStockIntent));
		localTabHost.addTab(buildTabSpec(TAB_BOND, TAB_BOND, mBondIntent));

		try {
			Field current = tabHost.getClass().getDeclaredField("mCurrentTab");
			current.setAccessible(true);
			current.set(tabHost, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private TabHost.TabSpec buildTabSpec(String tag, String resLabel,
			final Intent content) {
		return this.tabHost.newTabSpec(tag).setIndicator(resLabel)
				.setContent(content);
	}

	public void setCurrentActivity(int index) {
		switch (index) {
		case 1:
			onCheckedChanged(rg, R.id.activity_product_rb_all);
			break;
		case 2:
			onCheckedChanged(rg, R.id.activity_product_rb_money);
			break;
		case 3:
			onCheckedChanged(rg, R.id.activity_product_rb_stock);
			break;
		case 4:
			onCheckedChanged(rg, R.id.activity_product_rb_bond);
			break;
		}
	}

	private void onCheckedChanged(RadioGroup rg2, int id) {
		switch (id) {
		case R.id.activity_product_rb_all:
			this.tabHost.setCurrentTabByTag(TAB_ALL);
			break;
		case R.id.activity_product_rb_money:
			this.tabHost.setCurrentTabByTag(TAB_MONEY);
			break;
		case R.id.activity_product_rb_stock:
			this.tabHost.setCurrentTabByTag(TAB_STOCK);
			break;
		case R.id.activity_product_rb_bond:
			this.tabHost.setCurrentTabByTag(TAB_BOND);
			break;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		onCheckedChanged(rg, v.getId());
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent("refresh_tab");
			intent.putExtra("intoSelect", 1);
			this.sendBroadcast(intent);
			return true;
		}
		return false;
	}
}
