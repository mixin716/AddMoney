package com.zc.addmony.adapter.activities;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zc.addmony.R;

public class EmailAddressAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<String> list;

	public EmailAddressAdapter(Context context, List<String> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(
					R.layout.adapter_select_address_item_layout, null);
			holder.tvAddress = (TextView) convertView.findViewById(R.id.adapter_select_address_tv_address);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvAddress.setText(list.get(position));
		return convertView;
	}

	class ViewHolder {
		TextView tvAddress;
	}

}
