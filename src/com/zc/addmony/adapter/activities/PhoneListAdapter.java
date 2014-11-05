package com.zc.addmony.adapter.activities;

import java.util.List;

import org.w3c.dom.Text;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zc.addmony.R;
import com.zc.addmony.bean.activities.PhoneBean;

/** 手机列表bean */
public class PhoneListAdapter extends BaseAdapter {

	private List<PhoneBean> phones;
	private Context context;

	public PhoneListAdapter(List<PhoneBean> phones, Context context) {
		this.phones = phones;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return phones.size();
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
		// TODO Auto-generated method stub
		HolderView holder;
		if (convertView == null) {
			holder = new HolderView();
			convertView = View.inflate(context,
					R.layout.adapter_phone_list_layout, null);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.adapter_phone_list_tv_name);
			holder.tvMoney = (TextView) convertView
					.findViewById(R.id.adapter_phone_list_tv_money);
			holder.tvDate = (TextView) convertView
					.findViewById(R.id.adapter_phone_list_tv_date);
			holder.tvTcMoney = (TextView) convertView
					.findViewById(R.id.adapter_phone_list_tv_tc_money);
		} else {
			holder = (HolderView) convertView.getTag();
		}
		holder.tvName.setText(phones.get(position).getPhoneName());
		holder.tvMoney.setText("冻结基金:"
				+ (Integer.valueOf(phones.get(position).getPhoneTC().get(0)
						.getShare())) + "元");
		holder.tvTcMoney.setText("套餐价格:"
				+ phones.get(position).getPhoneTC().get(0).getPrice() + "元");
		return convertView;
	}

	class HolderView {
		TextView tvName, tvMoney, tvTcMoney, tvDate;
	}
}
