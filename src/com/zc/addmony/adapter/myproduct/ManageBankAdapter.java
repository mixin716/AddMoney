package com.zc.addmony.adapter.myproduct;

import java.util.List;
import com.zc.addmony.R;
import com.zc.addmony.bean.myproduct.ManageBankBean;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
			holder.llHave = (LinearLayout) view
					.findViewById(R.id.adapter_manage_bank_ll_have);
			holder.tvName = (TextView) view
					.findViewById(R.id.adapter_manage_bank_tv_name);
			holder.tvLimit = (TextView) view
					.findViewById(R.id.adapter_manage_bank_tv_limit);
			holder.tvCheck = (TextView) view
					.findViewById(R.id.adapter_manage_bank_tv_check);
			holder.tvUnbind = (TextView) view
					.findViewById(R.id.adapter_manage_bank_tv_unbind);
			holder.tvNull = (TextView) view
					.findViewById(R.id.adapter_manage_bank_tv_null);
			holder.imageBank = (ImageView) view
					.findViewById(R.id.adapter_manage_bank_img_bank);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if (!TextUtils.isEmpty(banks.get(position).getBank_num())) {
			holder.tvName
					.setText(banks.get(position).getBankName()
							+ "（***"
							+ banks.get(position)
									.getBank_num()
									.substring(
											banks.get(position).getBank_num()
													.length() - 4,
											banks.get(position).getBank_num()
													.length()) + "）");
		} else {
			holder.tvName.setText(banks.get(position).getBankName());
		}

		if (banks.get(position).getFlag() == 0) {
			holder.tvNull.setVisibility(View.VISIBLE);
			holder.llHave.setVisibility(View.INVISIBLE);
		} else {
			holder.tvNull.setVisibility(View.GONE);
			holder.llHave.setVisibility(View.VISIBLE);
		}

		if (banks.get(position).getLimits() != null
				&& banks.get(position).getLimits().size() != 0) {
			// for (int i = 0; i < banks.get(position).getLimits().size(); i++)
			// {
			// banks.get(position).getLimits().get(i) = string;
			// }
			holder.tvLimit.setText("支付限额：单笔限额"
					+ banks.get(position).getLimits().get(0) + ",单日限额"
					+ banks.get(position).getLimits().get(1));
		}

		String bankName = banks.get(position).getBankName();
		if (bankName.indexOf("工商") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_gongshang);
		} else if (bankName.indexOf("光大") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_guangda);
		} else if (bankName.indexOf("建设") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_jianshe);
		} else if (bankName.indexOf("农业") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_nongye);
		} else if (bankName.indexOf("平安") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_pingan);
		} else if (bankName.indexOf("浦发") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_pufa);
		} else if (bankName.indexOf("温州") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_wenzhou);
		} else if (bankName.indexOf("兴业") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_xingye);
		} else if (bankName.indexOf("中信") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_zhongxin);
		}else if (bankName.indexOf("招商") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_home_zhaoshang_icon);
		}else if (bankName.indexOf("华夏") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_home_huaxia_icon);
		}else if (bankName.indexOf("民生") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_minsheng);
		}else if (bankName.indexOf("广发") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_guangfa);
		}else if (bankName.indexOf("上海") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_shanghai);
		} else if (bankName.indexOf("中国") != -1) {
			holder.imageBank.setImageResource(R.drawable.ic_bank_zhongguo);
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
				if (banks.size() > 2) {
					activityListener.doPassActionListener(null, 2, position,
							"解除绑定");
				} else {
					Toast.makeText(context, "您当前账户只有一张银行卡，暂不能解除绑定",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		return view;
	}

	class ViewHolder {
		TextView tvName, tvLimit, tvCheck, tvUnbind, tvNull;
		ImageView imageBank;
		LinearLayout llHave;
	}

}
