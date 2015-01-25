package com.zc.addmony.ui.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.zc.addmony.R;
import com.zc.addmony.adapter.activities.PhoneFragmenAdapter;

public class ShowPhonesA extends FragmentActivity {

	private ViewPager viewPager;
	private List<PhonesFragment> fragments;
	private PhoneFragmenAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_phones_layout);
		viewPager = (ViewPager) findViewById(R.id.activity_show_viewPager);
		fragments = new ArrayList<PhonesFragment>();
		for (int i = 0; i < 5; i++) {
			PhonesFragment pf = new PhonesFragment();
			fragments.add(pf);
		}
		// adapter = new PhoneFragmenAdapter(getSupportFragmentManager(),
		// fragments);
		// viewPager.setAdapter(adapter);
	}

}
