package com.zc.addmony.adapter.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zc.addmony.R;

public class HelperCenterAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private String[] title = { "关于身份证位数", "关于银行验证", "关于银行预留手机号码", "关于资金安全保障",
			"基金净值", "累计净值", "七日年化收益率", "万份收益", "关于T日", "金额购买 份额赎回", "赎回款到账日期",
			"货币基金收益", "关于增财宝" };

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
