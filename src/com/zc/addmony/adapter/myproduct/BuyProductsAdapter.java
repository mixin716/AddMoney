package com.zc.addmony.adapter.myproduct;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zc.addmony.R;
import com.zc.addmony.bean.myproduct.BuyProductsBean;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;

public class BuyProductsAdapter extends BaseAdapter {

	private Context context;
	private List<BuyProductsBean> list;
	private ListViewPassValuetoActivityListener activityListener;

	public BuyProductsAdapter(Context context, List<BuyProductsBean> list) {
		this.context = context;
		this.list = list;
	}

	public void setListViewPassValuetoActivityListener(
			ListViewPassValuetoActivityListener activityListener) {
		this.activityListener = activityListener;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.adapter_buy_products_layout, null);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.adapter_buy_products_tv_name);
			holder.tvWf = (TextView) convertView
					.findViewById(R.id.adapter_buy_products_tv_wf);
			holder.tvHave = (TextView) convertView
					.findViewById(R.id.adapter_buy_products_tv_have);
			holder.tvYestorday = (TextView) convertView
					.findViewById(R.id.adapter_buy_products_tv_yeatorday);
			holder.tvNot = (TextView) convertView
					.findViewById(R.id.adapter_buy_products_tv_not);
			holder.tvInRedeem = (TextView) convertView
					.findViewById(R.id.adapter_buy_products_tv_inredeem);
			holder.tvBank = (TextView) convertView
					.findViewById(R.id.adapter_buy_products_tv_bank);
			holder.tvBuy = (TextView) convertView
					.findViewById(R.id.adapter_buy_products_tv_buy);
			holder.tvRedeem = (TextView) convertView
					.findViewById(R.id.adapter_buy_products_tv_redeem);
			holder.rlInRedeem = (RelativeLayout) convertView
					.findViewById(R.id.adapter_buy_products_rl_inredeem);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(list.get(position).getmName());
		holder.tvWf.setText(list.get(position).getmWf());
		holder.tvHave.setText(list.get(position).getmHave());
		holder.tvYestorday.setText(list.get(position).getmYestorday());
		holder.tvNot.setText(list.get(position).getmNot());
		holder.tvInRedeem.setText(list.get(position).getmRedeem());
		holder.tvBank.setText(list.get(position).getmBank());

		holder.tvBuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activityListener.doPassActionListener(null, 1, position, "购买");
			}
		});
		holder.tvRedeem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activityListener.doPassActionListener(null, 2, position, "赎回");
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView tvName, tvWf, tvHave, tvYestorday, tvNot, tvInRedeem, tvBuy,
				tvRedeem, tvBank;
		RelativeLayout rlInRedeem;
	}

}
