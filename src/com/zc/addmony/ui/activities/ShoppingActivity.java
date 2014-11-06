package com.zc.addmony.ui.activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.jky.struct2.bitmap.FinalBitmap;
import com.jky.struct2.bitmap.FinalBitmapManager;
import com.jky.struct2.http.FinalHttp;
import com.jky.struct2.http.core.AjaxCallBack;
import com.jky.struct2.http.core.AjaxParams;
import com.jky.struct2.http.entityhandle.HttpExceptionResult;
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
	private PhoneBean pBean;
	private Button btnNext;
	private ImageView imgTop,imgTc;
	private TextView tvTc;
	private RadioGroup  rgColor;
	private RadioButton rbColor1, rbColor2, rbColor3;
	private RadioButton rbs[];
	private ActivitiesPhoneBean apBean;
	private FinalBitmap finalBitmap;
	private FinalHttp finalHttp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_shopping_layout);
		mApplication = (MApplication) this.getApplication();
		apBean = mApplication.getApBean();
		pBean = mApplication.getpBean();
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		finalHttp = new FinalHttp(getApplicationContext());
		finalBitmap = FinalBitmapManager.getFinalBitmapManager(getApplicationContext())
		.getFinalBitmap(FinalBitmapManager.IMG_BIG);
	}

	@Override
	protected void setTitleViews() {
		titleText.setText("天天增财");

	}

	@Override
	protected void setViews() {
		imgTop = (ImageView) findViewById(R.id.activity_shopping_img_top);
		imgTc = (ImageView) findViewById(R.id.activity_shopping_img_tc);
		tvTc = (TextView) findViewById(R.id.activity_shopping_tv_tc);
		rgColor = (RadioGroup) findViewById(R.id.activity_shopping_rg_color);

		rbColor1 = (RadioButton) findViewById(R.id.activity_shopping_rb_color1);
		rbColor2 = (RadioButton) findViewById(R.id.activity_shopping_rb_color2);
		rbColor3 = (RadioButton) findViewById(R.id.activity_shopping_rb_color3);

		btnNext = (Button) findViewById(R.id.activity_shopping_btn_next);
		btnNext.setOnClickListener(this);
		rgColor.setOnCheckedChangeListener(this);

		rbColor1.setVisibility(View.INVISIBLE);
		rbColor2.setVisibility(View.INVISIBLE);
		rbColor3.setVisibility(View.INVISIBLE);
		rbs = new RadioButton[] { rbColor1, rbColor2, rbColor3 };
		for (int j = 0; j < pBean.getPhoneColors().size(); j++) {
			rbs[j].setVisibility(View.VISIBLE);
			rbs[j].setText(pBean.getPhoneColors().get(j).getColor());
		}
		rbs[0].setChecked(true);
		apBean.setPhoneColor(pBean.getPhoneColors().get(0).getColor());
		apBean.setPhoneColorId(pBean.getPhoneColors().get(0).getC_id());
		apBean.setPhoneTc(pBean.getPhoneTC().get(0).getP_name());
		
		tvTc.setText("套餐:"+pBean.getPhoneTC().get(0).getP_name());
		finalBitmap.display(imgTop, Urls.url+pBean.getPhoneUrl(), R.drawable.ic_shopping_top_icon);
		finalBitmap.display(imgTc, Urls.url+pBean.getPhoneTC().get(0).getContent(), R.drawable.ic_shopping_top_icon);
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

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (group.getId() == R.id.activity_shopping_rg_color) {
			switch (checkedId) {
			case R.id.activity_shopping_rb_color1:
				rbColor1.setChecked(true);
				rbColor2.setChecked(false);
				rbColor3.setChecked(false);
				apBean.setPhoneColor(rbColor1.getText().toString().trim());
				apBean.setPhoneColorId(pBean.getPhoneColors().get(0).getC_id());
				break;
			case R.id.activity_shopping_rb_color2:
				rbColor1.setChecked(false);
				rbColor2.setChecked(true);
				rbColor3.setChecked(false);
				apBean.setPhoneColor(rbColor2.getText().toString().trim());
				apBean.setPhoneColorId(pBean.getPhoneColors().get(1).getC_id());
				break;
			case R.id.activity_shopping_rb_color3:
				rbColor1.setChecked(false);
				rbColor2.setChecked(false);
				rbColor3.setChecked(true);
				apBean.setPhoneColor(rbColor3.getText().toString().trim());
				apBean.setPhoneColorId(pBean.getPhoneColors().get(2).getC_id());
				break;

			default:
				break;
			}
		}
	}

}
