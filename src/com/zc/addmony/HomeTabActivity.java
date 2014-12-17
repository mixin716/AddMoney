package com.zc.addmony;

import java.lang.reflect.Field;

import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.ui.more.MoreMainActivity;
import com.zc.addmony.ui.myproduct.LoginActivity;
import com.zc.addmony.ui.myproduct.MyProductActivity;
import com.zc.addmony.ui.productlist.ProductListActivity;
import com.zc.addmony.ui.productlist.ProductTabActivity;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class HomeTabActivity extends TabActivity implements
		OnCheckedChangeListener, OnClickListener {
  
	private String TAG = "HomeTabActivity"	;
	private String str ="";
	private RadioGroup rg;
	private RadioButton rbHome, rbProductList, rbMyProduct, rbMore;
	private Intent mHomeIntent, mProductListIntent, mMyProductIntent,
			mMoreIntent, intent;
	private final String TAB_TESTSUGAR = "TestSugarActivity";
	private final String TAB_MANAGESUGAR = "ManageSugarActivity";
	private final String TAB_LOOKDOCTOR = "DoctorMainActivity";
	private final String TAB_MANAGEDRUG = "ManageMedicineActivity";
	private final String TAB_PRODUCESERVICE = "TestSugarActivity";
	private TabHost tabHost;
	private int intoSelect = 1;//
	private UserSharedData userShare;
	
	private BroadcastReceiver receive = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.e(TAG, "onReceive");
			intoSelect = intent.getIntExtra("intoSelect", 1);
			setCurrentActivity(intoSelect);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_tab_layout);
		Log.e(TAG, "onCreate");
		this.tabHost = getTabHost();
		intoSelect = this.getIntent().getIntExtra("into_tab", 0);
		userShare = UserSharedData.getInstance(getApplicationContext());
		InitViews();
		InitIntent();
		setupIntent();
		setCurrentActivity(intoSelect);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("refresh_tab");
		registerReceiver(receive, intentFilter);
	}

	private void InitViews() {
		rg = (RadioGroup) findViewById(R.id.buttom_rgroup);
		rbHome = (RadioButton) findViewById(R.id.activity_home_rb_home);
		rbProductList = (RadioButton) findViewById(R.id.activity_home_rb_productlist);
		rbMyProduct = (RadioButton) findViewById(R.id.activity_home_rb_myproduct);
		rbMore = (RadioButton) findViewById(R.id.activity_home_rb_more);

		rbHome.setSelected(true);
		rbHome.setOnClickListener(this);
		rbProductList.setOnClickListener(this);
		rbMyProduct.setOnClickListener(this);
		rbMore.setOnClickListener(this);
	}

	private void InitIntent() {
		mHomeIntent = new Intent(HomeTabActivity.this, HomeActivity.class);
		mProductListIntent = new Intent(HomeTabActivity.this,
				ProductTabActivity.class);
		mMyProductIntent = new Intent(HomeTabActivity.this,
				MyProductActivity.class);
		mMoreIntent = new Intent(HomeTabActivity.this, MoreMainActivity.class);
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

		localTabHost.addTab(buildTabSpec(TAB_TESTSUGAR, TAB_TESTSUGAR,
				mHomeIntent));
		localTabHost.addTab(buildTabSpec(TAB_MANAGESUGAR, TAB_MANAGESUGAR,
				mProductListIntent));
		localTabHost.addTab(buildTabSpec(TAB_LOOKDOCTOR, TAB_LOOKDOCTOR,
				mMyProductIntent));
		localTabHost.addTab(buildTabSpec(TAB_MANAGEDRUG, TAB_MANAGEDRUG,
				mMoreIntent));

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
			onCheckedChanged(rg, R.id.activity_home_rb_home);
			break;
		case 2:
			onCheckedChanged(rg, R.id.activity_home_rb_productlist);
			break;
		case 3:
			onCheckedChanged(rg, R.id.activity_home_rb_myproduct);
			break;
		case 4:
			onCheckedChanged(rg, R.id.activity_home_rb_more);
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		rbHome.setBackgroundResource(R.drawable.ic_tab_home_normal);
		rbProductList
				.setBackgroundResource(R.drawable.ic_tab_product_list_normal);
		rbMyProduct.setBackgroundResource(R.drawable.ic_tab_my_product_normal);
		rbMore.setBackgroundResource(R.drawable.ic_tab_more_normal);
		switch (arg1) {
		case R.id.activity_home_rb_home:
			this.tabHost.setCurrentTabByTag(TAB_TESTSUGAR);
			rbHome.setBackgroundResource(R.drawable.ic_tab_home_press);
			break;
		case R.id.activity_home_rb_productlist:
			this.tabHost.setCurrentTabByTag(TAB_MANAGESUGAR);
			rbProductList
					.setBackgroundResource(R.drawable.ic_tab_product_list_press);
			break;
		case R.id.activity_home_rb_myproduct:
			this.tabHost.setCurrentTabByTag(TAB_LOOKDOCTOR);
			rbMyProduct
					.setBackgroundResource(R.drawable.ic_tab_my_product_press);
			break;
		case R.id.activity_home_rb_more:
			this.tabHost.setCurrentTabByTag(TAB_MANAGEDRUG);
			rbMore.setBackgroundResource(R.drawable.ic_tab_more_press);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		onCheckedChanged(rg, v.getId());
	}

}
