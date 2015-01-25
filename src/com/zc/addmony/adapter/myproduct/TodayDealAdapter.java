package com.zc.addmony.adapter.myproduct;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zc.addmony.R;
import com.zc.addmony.bean.productlist.ProductBean;

public class TodayDealAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ProductBean> list;
	private List<String> list1,list2;
	private Context context;

	public TodayDealAdapter(Context context, List<ProductBean> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		addList1();
		addList2();
	}
	
	public void addList1(){
		list1 = new ArrayList<String>();
		list1.add("090");list1.add("020");list1.add("021");list1.add("022");list1.add("022");
		list1.add("023");list1.add("039");
	}
	
	public void addList2(){
		list2 = new ArrayList<String>();
		list2.add("024");list2.add("025");list2.add("029");list2.add("033");list2.add("036");
		list2.add("098");
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
			holder.tvNumber = (TextView) convertView
					.findViewById(R.id.adapter_today_deal_tv_number);
			holder.tvYewu = (TextView) convertView
					.findViewById(R.id.adapter_today_deal_tv_yewu);
			holder.llState = (LinearLayout) convertView.findViewById(R.id.adapter_today_ll_state);
			holder.llMain = (LinearLayout) convertView.findViewById(R.id.adapter_today_ll_main);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String foundCode = list.get(position).getFundcode();
		holder.tvName.setText(list.get(position).getFundname());
		holder.tvCode.setText(foundCode);
		
		if(list1.contains(list.get(position).getCallingcode())){
			holder.llState.setVisibility(View.VISIBLE);
			holder.tvMoney.setText(list.get(position).getApplysum() + "");
			if (list.get(position).getKkstat().equals("0")) {// 未校验
				holder.tvState.setText("未校验");
			} else if (list.get(position).getKkstat().equals("1")) {
				holder.tvState.setText("无效");
			} else if (list.get(position).getKkstat().equals("2")) {
				holder.tvState.setText("有效");
			} else {
				holder.tvState.setText("已发送扣款指令");
			}
			holder.tvNumber.setText(list.get(position).getApplyserial());//申请编号
			//@"090": @"定投协议签订",@"020":@"认购",@"021":@"预约认购",
			//@"022":@"申购",@"023":@"预约申购",@"039":@"定期定额申购"
			if("090".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("定投协议签订");
			}else if("020".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("认购");
			}else if("021".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("预约认购");
			}else if("022".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("申购");
			}else if("023".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("预约申购");
			}else if("039".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("定期定额申购");
			}
			holder.tvYewu.setTextColor(context.getResources().getColor(R.color.black));
			holder.llMain.setBackgroundResource(R.drawable.ic_product_detail_top_view_background);
		}else{
			holder.tvMoney.setText(list.get(position).getApplyshare()+"");
			holder.tvNumber.setText(list.get(position).getApplyserial());//申请编号
			if("024".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("赎回");
			}else if("025".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("预约赎回");
			}else if("029".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("设置分红方式");
			}else if("033".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("非交易过户");
			}else if("036".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("基金转换");
			}else if("098".equals(list.get(position).getCallingcode())){
				holder.tvYewu.setText("快速过户");
			}
			holder.tvYewu.setTextColor(context.getResources().getColor(R.color.normal_red));
			holder.llState.setVisibility(View.GONE);
			holder.llMain.setBackgroundResource(R.drawable.ic_product_detail_top_view_red);
		}
		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
		holder.llMain.setPadding(padding, padding, padding, padding/2);
		return convertView;
	}

	class ViewHolder {
		TextView tvName, tvCode, tvMoney, tvState,tvNumber,tvYewu;
		LinearLayout llState,llMain;
	}

}
