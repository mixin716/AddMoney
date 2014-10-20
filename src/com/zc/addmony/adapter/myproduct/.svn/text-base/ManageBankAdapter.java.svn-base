package com.zc.addmony.adapter.myproduct;

import java.util.List;
import com.zc.addmony.R;
import com.zc.addmony.bean.myproduct.ManageBankBean;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ManageBankAdapter extends BaseAdapter {

	private Context context;
	private List<ManageBankBean> banks;
	private ListViewPassValuetoActivityListener activityListener;

	public ManageBankAdapter(Context context, List<ManageBankBean> banks) {
		this.context = context;
		this.banks = banks;
	}

	public void setListViewPassValuetoActivityListener(
			ListViewPassValuetoActivityListener activityListener) {
		this.activityListener = activityListener;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return banks.size();
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
	public View getView(final int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = View.inflate(context, R.layout.adapter_manage_bank_layout,
					null);
			holder.llHave = (LinearLayout) view.findViewById(R.id.adapter_manage_bank_ll_have);
			holder.tvName = (TextView) view
					.findViewById(R.id.adapter_manage_bank_tv_name);
			holder.tvLimit = (TextView) view
					.findViewById(R.id.adapter_manage_bank_tv_limit);
			holder.tvCheck = (TextView) view
					.findViewById(R.id.adapter_manage_bank_tv_check);
			holder.tvUnbind = (TextView) view
					.findViewById(R.id.adapter_manage_bank_tv_unbind);
			holder.tvNull = (TextView) view.findViewById(R.id.adapter_manage_bank_tv_null);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if(banks.get(position).getFlag() == 0){
			holder.tvNull.setVisibility(View.VISIBLE);
			holder.llHave.setVisibility(View.INVISIBLE);
		}else{
			holder.tvNull.setVisibility(View.GONE);
			holder.llHave.setVisibility(View.VISIBLE);
		}
		
		holder.tvCheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				activityListener
						.doPassActionListener(null, 1, position, "打款验证");
			}
		});
		holder.tvUnbind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				activityListener
						.doPassActionListener(null, 2, position, "解除绑定");
			}
		});
		return view;
	}

	class ViewHolder {
		TextView tvName, tvLimit, tvCheck, tvUnbind,tvNull;
		ImageView imageBank;
		LinearLayout llHave;
	}

}
