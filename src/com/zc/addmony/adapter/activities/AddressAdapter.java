package com.zc.addmony.adapter.activities;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.zc.addmony.R;
import com.zc.addmony.bean.activities.AddressBean;
import com.zc.addmony.utils.ListViewPassValuetoActivityListener;

public class AddressAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<AddressBean> list;
	private ListViewPassValuetoActivityListener activity;
	private int pos, rem;

	public AddressAdapter(Context context, List<AddressBean> list) {
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
		final ViewHolder holder;
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
			holder.cbAddress02.setText(list.get(3 * position + 1).getArea());
			holder.cbAddress03.setText(list.get(3 * position + 2).getArea());
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
					holder.cbAddress01
							.setText(list.get(3 * position).getArea());
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
					holder.cbAddress01
							.setText(list.get(3 * position).getArea());
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
					holder.cbAddress01
							.setText(list.get(3 * position).getArea());
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
					holder.cbAddress01
							.setText(list.get(3 * position).getArea());
					holder.cbAddress02.setText(list.get(3 * position + 1)
							.getArea());
					holder.cbAddress01.setChecked(list.get(3 * position)
							.isFlag());
					holder.cbAddress02.setChecked(list.get(3 * position + 1)
							.isFlag());
					holder.cbAddress01.setVisibility(View.VISIBLE);
					holder.cbAddress02.setVisibility(View.VISIBLE);
					holder.cbAddress03.setVisibility(View.INVISIBLE);
				}
			}
		}

		// holder.cbAddress01
		// .setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// activity.doPassActionListener(isChecked, position, 0,
		// "省份");
		//
		// }
		// });
		holder.cbAddress01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!holder.cbAddress01.isChecked()) {
					holder.cbAddress01.setChecked(true);
				}
				activity.doPassActionListener(true, position, 0, "省份");
			}
		});
		// holder.cbAddress02
		// .setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		//
		// }
		// });
		holder.cbAddress02.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!holder.cbAddress02.isChecked()) {
					holder.cbAddress02.setChecked(true);
				}
				activity.doPassActionListener(true, position, 1, "省份");
			}
		});
		// holder.cbAddress03
		// .setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// activity.doPassActionListener(isChecked, position, 2,
		// "省份");
		//
		// }
		// });
		holder.cbAddress03.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!holder.cbAddress03.isChecked()) {
					holder.cbAddress03.setChecked(true);
				}
				activity.doPassActionListener(true, position, 2, "省份");
			}
		});
		return convertView;
	}

	class ViewHolder {
		CheckBox cbAddress01, cbAddress02, cbAddress03;
	}

}
