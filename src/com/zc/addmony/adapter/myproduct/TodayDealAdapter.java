package com.zc.addmony.adapter.myproduct;

import java.util.List;

import com.zc.addmony.R;
import com.zc.addmony.bean.productlist.ProductBean;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TodayDealAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ProductBean> list;

	public TodayDealAdapter(Context context, List<ProductBean> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
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
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_today_deal_layout,
					null);
			holder.tvCode = (TextView) convertView
					.findViewById(R.id.adapter_today_deal_tv_code);
			holder.tvMoney = (TextView) convertView
					.findViewById(R.id.adapter_today_deal_tv_money);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.adapter_today_deal_tv_name);
			holder.tvState = (TextView) convertView
					.findViewById(R.id.adapter_today_deal_tv_state);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String foundCode = list.get(position).getFundcode();
		holder.tvName.setText(list.get(position).getFundname());
		holder.tvCode.setText(foundCode);
		//024 sum
		if("024".equals(list.get(position).getCallingcode())){
			holder.tvMoney.setText(list.get(position).getApplyshare()+"");
	 	}else{
	 		holder.tvMoney.setText(list.get(position).getApplysum() + "");
	 	}
		if (foundCode.equals("0")) {// 未校验
			holder.tvState.setText("未校验");
		} else if (foundCode.equals("1")) {
			holder.tvState.setText("无效");
		} else if (foundCode.equals("2")) {
			holder.tvState.setText("有效");
		} else {
			holder.tvState.setText("已发送扣款指令");
		}

		return convertView;
	}

	class ViewHolder {
		TextView tvName, tvCode, tvMoney, tvState;
	}

}
