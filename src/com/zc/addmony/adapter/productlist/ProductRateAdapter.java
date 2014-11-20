package com.zc.addmony.adapter.productlist;

import com.zc.addmony.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductRateAdapter extends BaseAdapter {
	private LayoutInflater inflater;

	public ProductRateAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 20;
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
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_product_rate_item_layout, null);
			holder.llMain = (LinearLayout) convertView.findViewById(R.id.adapter_product_rate_item_ll);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(position %2 == 0){
			holder.llMain.setBackgroundColor(0xffd5d5d5);
		}else{
			holder.llMain.setBackgroundColor(0xffeeeeee);
		}
		return convertView;
	}
	class ViewHolder{
		LinearLayout llMain;
	}

}
