package com.zc.addmony.ui.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zc.addmony.R;
import com.zc.addmony.adapter.activities.ShowPhoneAdapter;
import com.zc.addmony.bean.activities.ShowPhoneBean;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;

public class PhonesFragment extends ListFragment implements
		ListViewPassValuetoActivityListener, OnClickListener {

	private static List<ShowPhoneBean> phones;
	private ShowPhoneAdapter adapter;
	private FragmentActivity fragment;
	private TextView tvNowPage, tvAllPages, tvPrevious, tvNext;
	private int allPages, nowPage;
	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_show_phone_layout,
				container, false);
		tvNowPage = (TextView) view
				.findViewById(R.id.activity_fragment_show_phone_tv);
		tvAllPages = (TextView) view
				.findViewById(R.id.activity_fragment_tv_pages);
		tvPrevious = (TextView) view
				.findViewById(R.id.activity_fragment_tv_previous);
		tvNext = (TextView) view.findViewById(R.id.activity_fragment_tv_next);
		tvAllPages.setText("共" + allPages + "页");
		if (nowPage == 0) {
			tvPrevious.setVisibility(View.INVISIBLE);
		}
		tvPrevious.setOnClickListener(this);
		tvNext.setOnClickListener(this);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		phones = new ArrayList<ShowPhoneBean>();
		// adapter = new ShowPhoneAdapter(getActivity(), phones);
		// adapter.setListViewPassValuetoActivityListener(this);
		// this.setListAdapter(adapter);
		fragment = this.getActivity();
		// setPhones(phones);
	}

	/** 设置手机号 */
	public void setPhones(List<ShowPhoneBean> phones, Context context,
			int nowPage, int allPages) {
		Log.e("setPhones--------", phones.size() + "");
		this.context = context;
		this.phones = phones;
		this.allPages = allPages;
		this.nowPage = nowPage;
		adapter = new ShowPhoneAdapter(context, phones);
		adapter.setListViewPassValuetoActivityListener(this);
		this.setListAdapter(adapter);
		if (tvNowPage != null) {
			tvNowPage.setText("第" + (nowPage + 1) + "页");
			tvAllPages.setText("共" + allPages + "页");
			if (nowPage == 0) {
				tvPrevious.setVisibility(View.INVISIBLE);
			}
			if (nowPage + 1 == allPages) {
				tvNext.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public void doPassActionListener(Object obj, int org1, int org2, String str) {
		// TODO Auto-generated method stub
		Log.e("doPassActionListener--------", phones.size() + "");
		for (int i = 0; i < phones.size(); i++) {
			phones.get(i).setFlag(0);
		}
		phones.get(org2 * 2 + org1 - 1).setFlag(1);
		adapter = new ShowPhoneAdapter(getActivity(), phones);
		adapter.setListViewPassValuetoActivityListener(this);
		this.setListAdapter(adapter);
		// adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method
		Intent intent;
		switch (arg0.getId()) {
		case R.id.activity_fragment_tv_previous://
			intent = new Intent("click_page");
			intent.putExtra("position", nowPage + 1);
			intent.putExtra("clickFlag", 1);
			context.sendBroadcast(intent);
			break;
		case R.id.activity_fragment_tv_next:
			intent = new Intent("click_page");
			intent.putExtra("position", nowPage + 1);
			intent.putExtra("clickFlag", 2);
			context.sendBroadcast(intent);
			break;
		}
	}

}
