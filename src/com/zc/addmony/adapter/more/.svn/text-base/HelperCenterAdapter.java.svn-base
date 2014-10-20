package com.zc.addmony.adapter.more;

import com.zc.addmony.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HelperCenterAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private String[] title = { "什么是七日年化收益率及万分收益", "关于银行卡验证", "基金的赎回流程", "安全保障",
			"货币基金本金与收益如何计算", "货币基金的申购流程", "登录与注册" };

	public HelperCenterAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return title.length;
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
			convertView = inflater.inflate(
					R.layout.adapter_helper_center_layout, null);
			holder.tvContent = (TextView) convertView
					.findViewById(R.id.adapter_helper_center_tv_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvContent.setText(title[position]);
		return convertView;
	}

	class ViewHolder {
		TextView tvContent;
	}

}
