package com.zc.addmony.ui.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.activities.ActivitiesPhoneBean;
import com.zc.addmony.bean.activities.PhoneBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.logic.LogicActivities;
import com.zc.addmony.utils.AnimUtil;

public class ShoppingActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private MApplication mApplication;
	private Button btnNext;
	private TextView tvTc;
	private RadioGroup rgPhone, rgColor;
	private List<PhoneBean> phones;
	private RadioButton rbIphone5, rbIphone5s, rbIphone6, rbColor1, rbColor2,
			rbColor3;
	private RadioButton rbs[];
	private ActivitiesPhoneBean apBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_shopping_layout);
		mApplication = (MApplication) this.getApplication();
		apBean = mApplication.getApBean();
		setViews();
		requestPhone();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		phones = new ArrayList<PhoneBean>();
	}

	@Override
	protected void setTitleViews() {
		titleText.setText("天天增财");

	}

	@Override
	protected void setViews() {
		tvTc = (TextView) findViewById(R.id.activity_shopping_tv_tc);
		rgPhone = (RadioGroup) findViewById(R.id.activity_shopping_rg_phone);
		rgColor = (RadioGroup) findViewById(R.id.activity_shopping_rg_color);

		rbIphone5 = (RadioButton) findViewById(R.id.activity_shopping_rb_iphone5);
		rbIphone5s = (RadioButton) findViewById(R.id.activity_shopping_rb_iphone5s);
		rbIphone6 = (RadioButton) findViewById(R.id.activity_shopping_rb_iphone6);
		rbColor1 = (RadioButton) findViewById(R.id.activity_shopping_rb_color1);
		rbColor2 = (RadioButton) findViewById(R.id.activity_shopping_rb_color2);
		rbColor3 = (RadioButton) findViewById(R.id.activity_shopping_rb_color3);

		btnNext = (Button) findViewById(R.id.activity_shopping_btn_next);
		btnNext.setOnClickListener(this);
		rgPhone.setOnCheckedChangeListener(this);
		rgColor.setOnCheckedChangeListener(this);

		rbIphone5.setVisibility(View.GONE);
		rbIphone5s.setVisibility(View.GONE);
		rbIphone6.setVisibility(View.GONE);
		rbColor1.setVisibility(View.INVISIBLE);
		rbColor2.setVisibility(View.INVISIBLE);
		rbColor3.setVisibility(View.INVISIBLE);
		rbs = new RadioButton[] { rbColor1, rbColor2, rbColor3 };
	}

	@Override
	protected void doClickAction(int viewId) {
		switch (viewId) {
		case R.id.title_iv_left:
			finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_shopping_btn_next:
			Intent intent = new Intent(this, ShareJudgmentActivity.class);
			startActivity(intent);
			AnimUtil.pushLeftInAndOut(this);
			break;

		default:
			break;
		}
	}

	/** 获取手机信息 */
	public void requestPhone() {
		showLoading();
		AjaxParams params = new AjaxParams();
		httpRequest.get(Urls.GET_PHONES, params, callBack, 0);
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		phones = LogicActivities.parsePhoneList(jsonString);
		for (int i = 0; i < phones.size(); i++) {
			if ("iphone6".equals(phones.get(i).getPhoneName())) {
				rbIphone6.setVisibility(View.VISIBLE);
				if (i == 0) {
					rbIphone6.setChecked(true);
					tvTc.setText("套餐月租:" + phones.get(i).getPhoneTc());
					apBean.setPhoneName("iphone6");
					apBean.setPhoneTc(phones.get(i).getPhoneTc());
				}
			}
			if ("iphone5".equals(phones.get(i).getPhoneName())) {
				rbIphone5.setVisibility(View.VISIBLE);
				if (i == 0) {
					rbIphone5.setChecked(true);
					tvTc.setText("套餐月租:" + phones.get(i).getPhoneTc());
					apBean.setPhoneName("iphone5");
					apBean.setPhoneTc(phones.get(i).getPhoneTc());
				}
			}
			if ("iphone5s".equals(phones.get(i).getPhoneName())) {
				rbIphone5s.setVisibility(View.VISIBLE);
				if (i == 0) {
					rbIphone5s.setChecked(true);
					tvTc.setText("套餐月租:" + phones.get(i).getPhoneTc());
					apBean.setPhoneName("iphone5s");
					apBean.setPhoneTc(phones.get(i).getPhoneTc());
				}
			}
			for (int j = 0; j < phones.get(0).getPhoneColors().size(); j++) {
				rbs[j].setVisibility(View.VISIBLE);
				rbs[j].setText(phones.get(0).getPhoneColors().get(j).getColor());
			}
			rbs[0].setChecked(true);
			apBean.setPhoneColor(phones.get(0).getPhoneColors().get(0)
					.getColor());
			apBean.setPhoneColorId(phones.get(0).getPhoneColors().get(0)
					.getC_id());
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (group.getId() == R.id.activity_shopping_rg_phone) {
			switch (checkedId) {
			case R.id.activity_shopping_rb_iphone5:
				rbIphone5.setChecked(true);
				rbIphone5s.setChecked(false);
				rbIphone6.setChecked(false);
				setSelectPhone("iphone5");
				apBean.setPhoneName("iphone5");
				break;
			case R.id.activity_shopping_rb_iphone5s:
				rbIphone5s.setChecked(true);
				rbIphone5.setChecked(false);
				rbIphone6.setChecked(false);
				setSelectPhone("iphone5s");
				apBean.setPhoneName("iphone5s");
				break;
			case R.id.activity_shopping_rb_iphone6:
				rbIphone5.setChecked(false);
				rbIphone5s.setChecked(false);
				rbIphone6.setChecked(true);
				setSelectPhone("iphone6");
				apBean.setPhoneName("iphone6");
				break;

			default:
				break;
			}
		} else if (group.getId() == R.id.activity_shopping_rg_color) {
			switch (checkedId) {
			case R.id.activity_shopping_rb_color1:
				rbColor1.setChecked(true);
				rbColor2.setChecked(false);
				rbColor3.setChecked(false);
				apBean.setPhoneColor(rbColor1.getText().toString().trim());
				setSelectPhoneColorID(0);
				break;
			case R.id.activity_shopping_rb_color2:
				rbColor1.setChecked(false);
				rbColor2.setChecked(true);
				rbColor3.setChecked(false);
				apBean.setPhoneColor(rbColor2.getText().toString().trim());
				setSelectPhoneColorID(0);
				break;
			case R.id.activity_shopping_rb_color3:
				rbColor1.setChecked(false);
				rbColor2.setChecked(false);
				rbColor3.setChecked(true);
				apBean.setPhoneColor(rbColor3.getText().toString().trim());
				setSelectPhoneColorID(0);
				break;

			default:
				break;
			}
		}
	}

	public void setSelectPhone(String phoneName) {
		rbColor1.setVisibility(View.INVISIBLE);
		rbColor2.setVisibility(View.INVISIBLE);
		rbColor3.setVisibility(View.INVISIBLE);
		for (int i = 0; i < phones.size(); i++) {
			if (phoneName.equals(phones.get(i).getPhoneName())) {
				tvTc.setText("套餐月租:" + phones.get(i).getPhoneTc());
				apBean.setPhoneTc(phones.get(i).getPhoneTc());
				for (int j = 0; j < phones.get(i).getPhoneColors().size(); j++) {
					rbs[j].setVisibility(View.VISIBLE);
					rbs[j].setText(phones.get(i).getPhoneColors().get(j)
							.getColor());
				}
				rbs[0].setChecked(true);
				apBean.setPhoneId(phones.get(i).getPhoneId());
			}
		}
	}

	/** 获取颜色ID */
	public void setSelectPhoneColorID(int posi) {
		String phoneName = null;
		if (rbIphone5.isChecked()) {
			phoneName = "iphone5";
		} else if (rbIphone5s.isChecked()) {
			phoneName = "iphone5s";
		} else if (rbIphone6.isChecked()) {
			phoneName = "iphone6";
		}
		for (int i = 0; i < phones.size(); i++) {
			if (phoneName.equals(phones.get(i).getPhoneName())) {
				apBean.setPhoneColorId(phones.get(0).getPhoneColors().get(posi)
						.getC_id());
			}
		}
	}

}
