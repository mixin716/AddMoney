package com.zc.addmony.adapter.activities;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.zc.addmony.bean.activities.ShowPhoneBean;
import com.zc.addmony.ui.activities.PhonesFragment;

/** viewpage adapter */
public class PhoneFragmenAdapter extends FragmentStatePagerAdapter {

	private List<PhonesFragment> fragments;
	private List<List<ShowPhoneBean>> phones;
	private Context context;

	public PhoneFragmenAdapter(FragmentManager fm,
			List<PhonesFragment> fragments, List<List<ShowPhoneBean>> phones,
			Context context) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fragments = fragments;
		this.phones = phones;
		this.context = context;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		fragments.get(arg0).setPhones(phones.get(arg0), context,arg0,phones.size());
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

	// @Override
	// public void destroyItem(ViewGroup container, int position, Object object)
	// {
	// System.out.println("position Destory" + position);
	// super.destroyItem(container, position, object);
	// }

}
