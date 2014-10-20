package com.zc.addmony.adapter.buyproduct;

import java.util.List;

import com.zc.addmony.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SelectAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<String> list;

	public SelectAdapter(Context context, List<String> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_select_item_layout,
					null);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.activity_select_item_tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(list.get(position));
		return convertView;
	}

	class ViewHolder {
		TextView tvName;
	}

}
