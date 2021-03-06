package com.zc.addmony.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jky.struct2.http.core.AjaxParams;
import com.jky.struct2.http.entityhandle.HttpExceptionResult;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.common.Urls;
import com.zc.addmony.common.UserSharedData;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.view.lockview.LockPatternUtils;

public class MoreMainActivity extends BaseActivity {

	private Button btLogout;
	private UserSharedData userSharedData;
	private LinearLayout llShare, llHelp, llOpinion, llUpdate, llAbout;
	private MApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_more_main_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		app = (MApplication) this.getApplication();
		userSharedData = UserSharedData.getInstance(getApplicationContext());
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleIvLeft.setVisibility(View.INVISIBLE);
		titleIvRight.setVisibility(View.INVISIBLE);
		titleText.setText("更多");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (btLogout != null) {
			if (userSharedData.GetFlag()) {
				btLogout.setVisibility(View.VISIBLE);
			} else {
				btLogout.setVisibility(View.GONE);
			}
		}
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		llShare = (LinearLayout) findViewById(R.id.activity_more_ll_share);
		llHelp = (LinearLayout) findViewById(R.id.activity_more_ll_help);
		llOpinion = (LinearLayout) findViewById(R.id.activity_more_ll_opinion);
		llUpdate = (LinearLayout) findViewById(R.id.activity_more_ll_update);
		llAbout = (LinearLayout) findViewById(R.id.activity_more_ll_about);
		btLogout = (Button) findViewById(R.id.activity_more_main_bt_logout);

		btLogout.setOnClickListener(this);
		llShare.setOnClickListener(this);
		llHelp.setOnClickListener(this);
		llOpinion.setOnClickListener(this);
		llUpdate.setOnClickListener(this);
		llAbout.setOnClickListener(this);

		if (userSharedData.GetFlag()) {
			btLogout.setVisibility(View.VISIBLE);
		} else {
			btLogout.setVisibility(View.GONE);
		}
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		Intent intent;
		switch (viewId) {
		case R.id.activity_more_main_bt_logout:// 退出
			requestLogout();
			break;
		case R.id.activity_more_ll_share:

			break;
		case R.id.activity_more_ll_help:
			intent = new Intent(this, HelperCenterActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(MoreMainActivity.this);
			break;
		case R.id.activity_more_ll_opinion:
			// intent = new Intent(this, SuggesstionActivity.class);
			// startActivity(intent);
			// AnimUtil.pushLeftInAndOut(MoreMainActivity.this);
			// Intent data = new Intent(Intent.ACTION_SENDTO);
			// data.setData(Uri.parse("97621232@qq.com"));
			// data.putExtra(Intent.EXTRA_SUBJECT, "増财意见反馈");
			// data.putExtra(Intent.EXTRA_TEXT, "欢迎您提出宝贵的建议：");
			// startActivity(data);
			sendMailByIntent();
			break;
		case R.id.activity_more_ll_update:
			showLoading();
			update.sendEmptyMessageDelayed(0, 2000);
			break;
		case R.id.activity_more_ll_about:
			intent = new Intent(this, AboutAsActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(MoreMainActivity.this);
			break;
		default:
			break;
		}
	}

	/** 退出登录 */
	public void requestLogout() {
		showLoading();
		AjaxParams params = new AjaxParams();
		httpRequest.get(Urls.LOGOUT, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		showToast(message);
		btLogout.setVisibility(View.GONE);
		userSharedData.clearUserInfomation();
		LockPatternUtils.getInstance(
				getApplicationContext()).saveLockPattern(null, "user_key");
	}

	@Override
	public void handleNetErr(int requestCode, int errorCode) {
		super.handleNetErr(requestCode, errorCode);
		if (errorCode == HttpExceptionResult.TASK_RESULT_NET_UNCONNECT) {
			showToast("请检查网络");
		} else {
			showToast("请求失败");
		}
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

	public int sendMailByIntent() {
		String[] reciver = new String[] { "service@zcvc.com.cn" };
		String[] mySbuject = new String[] { "増财意见反馈" };
		String myCc = "増财意见反馈";
		String mybody = "欢迎您提出宝贵的建议：";
		Intent myIntent = new Intent(android.content.Intent.ACTION_SEND);
		myIntent.setType("plain/text");
		myIntent.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);
		// myIntent.putExtra(android.content.Intent.EXTRA_CC, myCc);
		myIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mySbuject);
		myIntent.putExtra(android.content.Intent.EXTRA_TEXT, mybody);
		startActivity(Intent.createChooser(myIntent, "増财意见反馈"));
		return 1;
	}

	public Handler update = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dismissLoading();
			showToast("您当前是最新版本");
		};
	};
}
