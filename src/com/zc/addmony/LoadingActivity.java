package com.zc.addmony;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoadingActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading_layout);
		Intent intent = new Intent(this,HomeTabActivity.class);
		startActivity(intent);
		this.finish();
	}
}
