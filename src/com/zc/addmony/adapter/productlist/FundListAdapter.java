package com.zc.addmony.adapter.productlist;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zc.addmony.R;
import com.zc.addmony.bean.productlist.ProductListBean;

public class FundListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ProductListBean> list;

	public FundListAdapter(Context context, List<ProductListBean> list) {
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
					R.layout.adapter_fund_item_layout, null);
			holder.llMain = (LinearLayout) convertView
					.findViewById(R.id.adapter_product_rate_item_ll);
			holder.tvDate = (TextView) convertView.findViewById(R.id.adapter_product_rate_item_tv_date);
			holder.tvQrnh = (TextView) convertView.findViewById(R.id.adapter_product_rate_item_tv_qrnh);
			holder.tvWfsy = (TextView) convertView.findViewById(R.id.adapter_product_rate_item_tv_wfsy);
			holder.tvMonth = (TextView) convertView.findViewById(R.id.adapter_product_rate_item_tv_month);
			holder.tvSixMonth = (TextView) convertView.findViewById(R.id.adapter_product_rate_item_tv_six_month);
			holder.tvThreeMonth = (TextView) convertView.findViewById(R.id.adapter_product_rate_item_tv_three_month);
			holder.tvUnit = (TextView) convertView.findViewById(R.id.adapter_product_rate_item_tv_unit);
			holder.tvWeek = (TextView) convertView.findViewById(R.id.adapter_product_rate_item_tv_week);
			holder.tvYear = (TextView) convertView.findViewById(R.id.adapter_product_rate_item_tv_year);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position % 2 == 0) {
			holder.llMain.setBackgroundColor(0xffd5d5d5);
		} else {
			holder.llMain.setBackgroundColor(0xffeeeeee);
		}
		holder.tvUnit.setText(list.get(position).getUnitNV());		
		holder.tvDate.setText(list.get(position).getNavdate());	
		if(TextUtils.isEmpty(list.get(position).getLatestWeeklyYield())){
			holder.tvQrnh.setText("0.00%");
		}else{
			BigDecimal bg = new BigDecimal(list.get(position).getLatestWeeklyYield());
			double money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			holder.tvQrnh.setText(money+"%");
		}
		if(TextUtils.isEmpty(list.get(position).getDailyProfit())){
			holder.tvWfsy.setText("0.00元");
		}else{
			BigDecimal bg = new BigDecimal(list.get(position).getDailyProfit());
			double money = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			holder.tvWfsy.setText(money+"元");
		}
		holder.tvWeek.setText(list.get(position).getRRInSingleWeek()+"%");		
		holder.tvMonth.setText(list.get(position).getRRInSingleMonth()+"%");		
		holder.tvThreeMonth.setText(list.get(position).getRRInThreeMonth()+"%");		
		holder.tvSixMonth.setText(list.get(position).getRRInSixMonth()+"%");		
		holder.tvYear.setText(list.get(position).getRRInSingleYear()+"%");		
		return convertView;
	}

	class ViewHolder {
		LinearLayout llMain;
		TextView tvUnit, tvDate, tvQrnh,tvWfsy,tvWeek, tvMonth, tvThreeMonth, tvSixMonth,
				tvYear;
	}


}
