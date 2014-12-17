package com.zc.addmony.ui.more;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.utils.AnimUtil;

public class AboutAsActivity extends BaseActivity {

	private RelativeLayout rlWelcom;
	private LinearLayout llPhone, llEmail, llWeb;
	private MApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_about_as_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		app = (MApplication) this.getApplication();
		app.addAllActivity(this);
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("关于我们");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		rlWelcom = (RelativeLayout) findViewById(R.id.activity_about_rl_welcom);
		llPhone = (LinearLayout) findViewById(R.id.activity_about_ll_phone);
		llEmail = (LinearLayout) findViewById(R.id.activity_about_ll_email);
		llWeb = (LinearLayout) findViewById(R.id.activity_about_ll_web);

		rlWelcom.setOnClickListener(this);
		llPhone.setOnClickListener(this);
		llEmail.setOnClickListener(this);
		llWeb.setOnClickListener(this);
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		Intent intent;
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(AboutAsActivity.this);
			break;
		case R.id.activity_about_rl_welcom:

			break;
		case R.id.activity_about_ll_phone:
			intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ "4000018811"));
			this.startActivity(intent);
			break;
		case R.id.activity_about_ll_email:

			break;
		case R.id.activity_about_ll_web:
			intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse("http://www.zcvc.com.cn");
			intent.setData(content_url);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
