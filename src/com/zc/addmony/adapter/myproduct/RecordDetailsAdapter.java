package com.zc.addmony.adapter.myproduct;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zc.addmony.R;
import com.zc.addmony.bean.myproduct.MoneyChangeBean;

public class RecordDetailsAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<MoneyChangeBean> list;

	public RecordDetailsAdapter(Context context, List<MoneyChangeBean> list) {
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
					R.layout.adapter_record_details_layout, null);
			holder.tvDate = (TextView) convertView
					.findViewById(R.id.adapter_record_detail_tv_date);
			holder.tvMoney = (TextView) convertView
					.findViewById(R.id.adapter_record_detail_tv_money);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.adapter_record_detail_tv_name);
			holder.tvPrompt = (TextView) convertView
					.findViewById(R.id.adapter_record_detail_tv_prompt);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (!TextUtils.isEmpty(list.get(position).getBusinflagStr())) {
			holder.tvName.setText(list.get(position).getBusinflagStr());
		}

		if ("024".equals(list.get(position).getCallingcode())) {
			holder.tvName.setText("取现");
			holder.tvMoney.setText(list.get(position).getApplyshare());
		} else {
			holder.tvName.setText("充值");
			holder.tvMoney.setText(list.get(position).getApplysum());

		}
		 holder.tvDate.setText(list.get(position).getApplydate());
		// holder.tvMoney.setText(list.get(position).getApplysum());
		return convertView;
	}

	class ViewHolder {
		TextView tvName, tvDate, tvMoney, tvPrompt;
	}

}
