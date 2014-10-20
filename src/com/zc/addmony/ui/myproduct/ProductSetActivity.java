package com.zc.addmony.ui.myproduct;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.R;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.ui.lock.GestureActivity;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.view.lockview.LockPatternUtils;

public class ProductSetActivity extends BaseActivity {

	private LinearLayout llBank, llPwd, llGes;
	private TextView tvBankSum;
	private UserSharedData userShare;
	private String user_key = "user_key";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_product_set_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		userShare = UserSharedData.getInstance(getApplicationContext());
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("设置");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		tvBankSum = (TextView) findViewById(R.id.activity_product_set_tv_bank);
		llBank = (LinearLayout) findViewById(R.id.activity_product_set_ll_bank);
		llPwd = (LinearLayout) findViewById(R.id.activity_product_set_ll_pwd);
		llGes = (LinearLayout) findViewById(R.id.activity_product_set_ll_ges);

		llBank.setOnClickListener(this);
		llPwd.setOnClickListener(this);
		llGes.setOnClickListener(this);

		if (userShare.GetBankSum() == null) {
			tvBankSum.setText("共0张银行卡");
		} else {
			tvBankSum.setText("共" + userShare.GetBankSum() + "张银行卡");
		}
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		Intent intent;
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(ProductSetActivity.this);
			break;
		case R.id.activity_product_set_ll_bank:
			intent = new Intent(this, ManageBankActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(ProductSetActivity.this);
			break;
		case R.id.activity_product_set_ll_pwd:
			intent = new Intent(this, ChangePwdActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(ProductSetActivity.this);
			break;
		case R.id.activity_product_set_ll_ges:
			intent = new Intent(this, GestureActivity.class);
			if (TextUtils.isEmpty(LockPatternUtils.getInstance(this)
					.getLockPaternString(user_key))) {
				intent.putExtra(GestureActivity.INTENT_MODE,
						GestureActivity.GESTURE_MODE_SET);
			}else{
				intent.putExtra(GestureActivity.INTENT_MODE,
						GestureActivity.GESTURE_MODE_CHANGE);
			}
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(ProductSetActivity.this);
			break;
		}
	}

}
