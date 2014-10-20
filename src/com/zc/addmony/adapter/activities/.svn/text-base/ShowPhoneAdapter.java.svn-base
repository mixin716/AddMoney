package com.zc.addmony.adapter.activities;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zc.addmony.R;
import com.zc.addmony.bean.activities.ShowPhoneBean;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;

public class ShowPhoneAdapter extends BaseAdapter {

	private Context context;
	private List<ShowPhoneBean> list;
	private ListViewPassValuetoActivityListener activityListener;

	public ShowPhoneAdapter(Context context, List<ShowPhoneBean> list) {
		this.context = context;
		this.list = list;
	}

	public void setListViewPassValuetoActivityListener(
			ListViewPassValuetoActivityListener activityListener) {
		this.activityListener = activityListener;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size() / 2 + list.size() % 2;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = View.inflate(context, R.layout.adapter_show_phone_layout,
					null);
			holder.rg = (RadioGroup) view
					.findViewById(R.id.adapter_show_phone_rg);
			holder.rbPhone1 = (RadioButton) view
					.findViewById(R.id.adapter_show_phone_rb1);
			holder.rbPhone2 = (RadioButton) view
					.findViewById(R.id.adapter_show_phone_rb2);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.rbPhone1.setText(list.get(position * 2).getPhone());
		if (list.size() > 1) {
			if (list.size() % 2 != 0 && list.size()/2 == position) {
				holder.rbPhone2.setVisibility(View.INVISIBLE);
				if (list.get(position * 2).getFlag() == 1) {
					holder.rbPhone1.setChecked(true);
					holder.rbPhone1.setTextColor(context.getResources().getColor(R.color.white));
				}else{
					holder.rbPhone2.setChecked(false);
				}
			}else{
				holder.rbPhone2.setText(list.get(position * 2 + 1).getPhone());
				if (list.get(position * 2).getFlag() == 1) {
					holder.rbPhone1.setChecked(true);
					holder.rbPhone2.setChecked(false);
					holder.rbPhone1.setTextColor(context.getResources().getColor(R.color.white));
				} else if (list.get(position * 2 + 1).getFlag() == 1) {
					holder.rbPhone2.setChecked(true);
					holder.rbPhone1.setChecked(false);
					holder.rbPhone2.setTextColor(context.getResources().getColor(R.color.white));
				} else {
					holder.rbPhone1.setChecked(false);
					holder.rbPhone2.setChecked(false);
				}
			}
		} else {
			holder.rbPhone2.setVisibility(View.INVISIBLE);
			if (list.get(position).getFlag() == 1) {
				holder.rbPhone1.setChecked(true);
				holder.rbPhone1.setTextColor(context.getResources().getColor(R.color.white));
			} else {
				holder.rbPhone1.setChecked(false);
			}
		}

		holder.rbPhone1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (list.get(position * 2).getFlag() == 0) {
					// activityListener.doPassActionListener(null, 1,
					// position, "1");
					holder.rbPhone1.setTextColor(context.getResources().getColor(R.color.white));
					Intent intent = new Intent("refresh_phone");
					intent.putExtra("position", position * 2);
					context.sendBroadcast(intent);
				}
			}
		});
		holder.rbPhone2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (list.get(position * 2 + 1).getFlag() == 0) {
					// activityListener.doPassActionListener(null, 2, position,
					// "2");
					holder.rbPhone2.setTextColor(context.getResources().getColor(R.color.white));
					Intent intent = new Intent("refresh_phone");
					intent.putExtra("position", position * 2 + 1);
					context.sendBroadcast(intent);
				}
			}
		});

		return view;
	}

	class ViewHolder {
		RadioButton rbPhone1, rbPhone2;
		RadioGroup rg;
	}

}
