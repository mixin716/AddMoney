package com.zc.addmony.adapter.activities;

import java.util.List;

import com.zc.addmony.R;
import com.zc.addmony.bean.activities.AddressBean;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CityAddressAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<AddressBean> list;
	private ListViewPassValuetoActivityListener activity;
	private int pos, rem;

	public CityAddressAdapter(Context context, List<AddressBean> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
		pos = list.size() / 3;
		rem = list.size() % 3;
	}

	public void setListViewPassValuetoActivityListener(
			ListViewPassValuetoActivityListener activity) {
		this.activity = activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (rem == 0) {
			return pos;
		} else {
			return pos + 1;
		}
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(
					R.layout.adapter_phone_address_layout, null);
			holder.cbAddress01 = (CheckBox) convertView
					.findViewById(R.id.adapter_phone_address_cb01);
			holder.cbAddress02 = (CheckBox) convertView
					.findViewById(R.id.adapter_phone_address_cb02);
			holder.cbAddress03 = (CheckBox) convertView
					.findViewById(R.id.adapter_phone_address_cb03);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (rem == 0) {
			holder.cbAddress01.setText(list.get(3 * position).getArea());
			holder.cbAddress02
					.setText(list.get(3 * position + 1).getArea());
			holder.cbAddress03
					.setText(list.get(3 * position + 2).getArea());
			holder.cbAddress01.setChecked(list.get(3 * position).isFlag());
			holder.cbAddress02.setChecked(list.get(3 * position + 1).isFlag());
			holder.cbAddress03.setChecked(list.get(3 * position + 2).isFlag());
			holder.cbAddress01.setVisibility(View.VISIBLE);
			holder.cbAddress02.setVisibility(View.VISIBLE);
			holder.cbAddress03.setVisibility(View.VISIBLE);
		} else if (rem == 1) {
			if (pos == 0) {
				holder.cbAddress01.setText(list.get(0).getArea());
				holder.cbAddress01.setChecked(list.get(0).isFlag());
				holder.cbAddress01.setVisibility(View.VISIBLE);
				holder.cbAddress02.setVisibility(View.INVISIBLE);
				holder.cbAddress03.setVisibility(View.INVISIBLE);
			} else {

				if (position < pos) {
					holder.cbAddress01.setText(list.get(3 * position)
							.getArea());
					holder.cbAddress02.setText(list.get(3 * position + 1)
							.getArea());
					holder.cbAddress03.setText(list.get(3 * position + 2)
							.getArea());
					holder.cbAddress01.setChecked(list.get(3 * position)
							.isFlag());
					holder.cbAddress02.setChecked(list.get(3 * position + 1)
							.isFlag());
					holder.cbAddress03.setChecked(list.get(3 * position + 2)
							.isFlag());
					holder.cbAddress01.setVisibility(View.VISIBLE);
					holder.cbAddress02.setVisibility(View.VISIBLE);
					holder.cbAddress03.setVisibility(View.VISIBLE);
				} else {
					holder.cbAddress01.setText(list.get(3 * position)
							.getArea());
					holder.cbAddress01.setChecked(list.get(3 * position)
							.isFlag());
					holder.cbAddress01.setVisibility(View.VISIBLE);
					holder.cbAddress02.setVisibility(View.INVISIBLE);
					holder.cbAddress03.setVisibility(View.INVISIBLE);
				}
			}
		} else if (rem == 2) {
			if (pos == 0) {
				holder.cbAddress01.setText(list.get(0).getArea());
				holder.cbAddress02.setText(list.get(1).getArea());
				holder.cbAddress01.setChecked(list.get(0).isFlag());
				holder.cbAddress02.setChecked(list.get(1).isFlag());
				holder.cbAddress01.setVisibility(View.VISIBLE);
				holder.cbAddress02.setVisibility(View.VISIBLE);
				holder.cbAddress03.setVisibility(View.INVISIBLE);

			} else {
				if (position < pos) {
					holder.cbAddress01.setText(list.get(3 * position)
							.getArea());
					holder.cbAddress02.setText(list.get(3 * position + 1)
							.getArea());
					holder.cbAddress03.setText(list.get(3 * position + 2)
							.getArea());
					holder.cbAddress01.setChecked(list.get(3 * position)
							.isFlag());
					holder.cbAddress02.setChecked(list.get(3 * position + 1)
							.isFlag());
					holder.cbAddress03.setChecked(list.get(3 * position + 2)
							.isFlag());
					holder.cbAddress01.setVisibility(View.VISIBLE);
					holder.cbAddress02.setVisibility(View.VISIBLE);
					holder.cbAddress03.setVisibility(View.VISIBLE);
				} else {
					holder.cbAddress01.setText(list.get(3 * position)
							.getArea());
					holder.cbAddress02.setText(list.get(3 * position + 1)
							.getArea());
					holder.cbAddress01.setChecked(list.get(3 * position)
							.isFlag());
					holder.cbAddress02.setChecked(list.get(3 * position + 2)
							.isFlag());
					holder.cbAddress01.setVisibility(View.VISIBLE);
					holder.cbAddress02.setVisibility(View.VISIBLE);
					holder.cbAddress03.setVisibility(View.INVISIBLE);
				}
			}
		}

		holder.cbAddress01
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						activity.doPassActionListener(isChecked, position, 0,
								"城市");

					}
				});
		holder.cbAddress02
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						activity.doPassActionListener(isChecked, position, 1,
								"城市");

					}
				});
		holder.cbAddress03
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						activity.doPassActionListener(isChecked, position, 2,
								"城市");

					}
				});

		return convertView;
	}

	class ViewHolder {
		CheckBox cbAddress01, cbAddress02, cbAddress03;
	}

}
