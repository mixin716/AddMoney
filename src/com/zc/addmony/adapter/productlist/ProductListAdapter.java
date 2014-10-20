package com.zc.addmony.adapter.productlist;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import com.zc.addmony.R;
import com.zc.addmony.bean.productlist.ProductBean;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/** 产品列表adapter */
public class ProductListAdapter extends BaseAdapter {

	private Context context;
	private List<ProductBean> list;

	public ProductListAdapter(Context context, List<ProductBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = View.inflate(context, R.layout.adapter_product_list_layout,
					null);
			holder.tvName = (TextView) view
					.findViewById(R.id.adapter_product_list_tv_name);
			holder.tvMoney = (TextView) view
					.findViewById(R.id.adapter_product_list_tv_money);
			holder.tvPerson = (TextView) view
					.findViewById(R.id.adapter_product_list_tv_person);
			holder.tvRate = (TextView) view
					.findViewById(R.id.adapter_product_list_tv_rate);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		DecimalFormat df = new DecimalFormat(".00");
		Log.e("", list.get(position).getIncomeratio());
		if ("-".equals(list.get(position).getIncomeratio())) {
			holder.tvRate.setText("0.00%");
		} else {
			BigDecimal bg = new BigDecimal(list.get(position).getIncomeratio());
			double j = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			holder.tvRate.setText(j + "%");
		}

		holder.tvName.setText(list.get(position).getFundname());
		holder.tvPerson.setText(list.get(position).getBought() + "人已购");
		BigDecimal bg = new BigDecimal(list.get(position).getHf_incomeratio());
		double money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		holder.tvMoney.setText(money + "元");
		return view;
	}

	class ViewHolder {
		TextView tvName, tvMoney, tvPerson, tvRate;
	}

}
