package com.zc.addmony.ui.buyproduct;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.adapter.buyproduct.SelectAdapter;
import com.zc.addmony.utils.AnimUtil;

public class SelectActivity extends BaseActivity implements OnItemClickListener {
	private ListView lvContent;
	private SelectAdapter adapter;
	private List<String> list;
	private MApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_select_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		app = (MApplication) this.getApplication();
		app.addAllActivity(this);
		list = new ArrayList<String>();
		list = getIntent().getStringArrayListExtra("nameList");
		adapter = new SelectAdapter(getApplicationContext(), list);

	}

	@Override
	protected void setTitleViews() {
		titleText.setText("选择列表");

	}

	@Override
	protected void setViews() {
		lvContent = (ListView) findViewById(R.id.activity_select_lv_content);
		lvContent.setAdapter(adapter);
		lvContent.setOnItemClickListener(this);
	}
	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent();
		intent.putExtra("name", list.get(arg2));
		intent.putExtra("postion", arg2);
		setResult(RESULT_OK, intent);
		finish();
		AnimUtil.pushRightInAndOut(this);
	}

}
