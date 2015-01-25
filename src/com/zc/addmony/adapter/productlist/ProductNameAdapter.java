package com.zc.addmony.adapter.productlist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zc.addmony.R;
import com.zc.addmony.bean.productlist.ProductListBean;

public class ProductNameAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ProductListBean> list;

	public ProductNameAdapter(Context context, List<ProductListBean> list) {
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
					R.layout.adapter_product_name_item_layout, null);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.adapter_product_name_item_name);
			holder.tvCode = (TextView) convertView
					.findViewById(R.id.adapter_product_name_item_code);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(list.get(position).getFundname());
		holder.tvCode.setText(list.get(position).getFundcode());
		return convertView;
	}

	class ViewHolder {
		TextView tvName, tvCode;
	}

}
