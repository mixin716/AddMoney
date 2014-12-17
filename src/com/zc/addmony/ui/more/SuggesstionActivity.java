package com.zc.addmony.ui.more;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.utils.AnimUtil;
import com.zc.addmony.utils.KeyBoard;

/** 意见反馈 */
public class SuggesstionActivity extends BaseActivity {

	private EditText etSuggestion;
	private Button btSub;
	private String strSugges;
	private MApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_suggesstion_layout);
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
		titleText.setText("意见反馈");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		etSuggestion = (EditText) findViewById(R.id.activity_suggesstion_et);
		btSub = (Button) findViewById(R.id.activity_suggesstion_bt);
		btSub.setOnClickListener(this);
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(SuggesstionActivity.this);
			break;
		case R.id.activity_suggesstion_bt:
			KeyBoard.demissKeyBoard(getApplicationContext(), etSuggestion);
			strSugges = etSuggestion.getText().toString().trim();
			if (TextUtils.isEmpty(strSugges)) {
				showToast("请输入建议内容！");
			} else {
				
			}
			break;
		default:
			break;
		}
	}

}
