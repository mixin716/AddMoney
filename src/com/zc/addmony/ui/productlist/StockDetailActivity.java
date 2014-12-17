package com.zc.addmony.ui.productlist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.jky.struct2.http.core.AjaxParams;
import com.zc.addmony.BaseActivity;
import com.zc.addmony.MApplication;
import com.zc.addmony.R;
import com.zc.addmony.bean.productlist.ProductListBean;
import com.zc.addmony.common.Urls;
import com.zc.addmony.ui.buyproduct.BuyProductActivity;
import com.zc.addmony.utils.AnimUtil;

public class StockDetailActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private RadioGroup rgContent;
	private LinearLayout llSyInfo, llNormalInfo;
	private Button btnBuy;
	private TextView tvFundname, tvDm, tvJz, tvRq, tvRzdf, tvJyysyl, tvJyzsyl;
	private TextView tvDwjz, tvJzrq, tvLjjz, tvZsyl, tvJdsyl, tvBnsyl, tvYnsyl,
			tvSnsyl;
	private TextView tvJjdm, tvFelb, tvJjjl, tvFxdj, tvJjzt, tvSgzt, tvShzt,
			tvRgzt;
	private MApplication app;
	private ProductListBean bean;
	private String minPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewRes(R.layout.activity_stock_detail_layout);
		setViews();
	}

	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		app = (MApplication) this.getApplication();
		app.addAllActivity(this);
		bean = app.getPdBean();
	}

	@Override
	protected void setTitleViews() {
		// TODO Auto-generated method stub
		titleText.setText("増财基金");
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		rgContent = (RadioGroup) findViewById(R.id.activity_stock_detail_rg);
		llSyInfo = (LinearLayout) findViewById(R.id.activity_stock_detail_ll_sy);
		llNormalInfo = (LinearLayout) findViewById(R.id.activity_stock_detail_ll_normal);

		tvFundname = (TextView) findViewById(R.id.activity_product_detail_tv_fundname);
		tvDm = (TextView) findViewById(R.id.activity_stock_detail_tv_dm);
		tvJz = (TextView) findViewById(R.id.activity_stock_detail_tv_jz);
		tvJyysyl = (TextView) findViewById(R.id.activity_stock_detail_tv_jyysyl);
		tvRq = (TextView) findViewById(R.id.activity_stock_detail_tv_rq);
		tvRzdf = (TextView) findViewById(R.id.activity_stock_detail_tv_rzdf);
		tvJyzsyl = (TextView) findViewById(R.id.activity_stock_detail_tv_jyzsyl);

		tvDwjz = (TextView) findViewById(R.id.activity_stock_detail_tv_dwjz);
		tvJzrq = (TextView) findViewById(R.id.activity_stock_detail_tv_jzrq);
		tvLjjz = (TextView) findViewById(R.id.activity_stock_detail_tv_ljjz);
		tvZsyl = (TextView) findViewById(R.id.activity_stock_detail_tv_zsyl);
		tvJdsyl = (TextView) findViewById(R.id.activity_stock_detail_tv_jdsyl);
		tvBnsyl = (TextView) findViewById(R.id.activity_stock_detail_tv_bnsyl);
		tvYnsyl = (TextView) findViewById(R.id.activity_stock_detail_tv_ynsyl);
		tvSnsyl = (TextView) findViewById(R.id.activity_stock_detail_tv_snsyl);

		tvJjdm = (TextView) findViewById(R.id.activity_stock_detail_tv_jjdm);
		tvFelb = (TextView) findViewById(R.id.activity_stock_detail_tv_felb);
		tvJjjl = (TextView) findViewById(R.id.activity_stock_detail_tv_jjjl);
		tvFxdj = (TextView) findViewById(R.id.activity_stock_detail_tv_fxdj);
		tvJjzt = (TextView) findViewById(R.id.activity_stock_detail_tv_jjzt);
		tvSgzt = (TextView) findViewById(R.id.activity_stock_detail_tv_sgzt);
		tvShzt = (TextView) findViewById(R.id.activity_stock_detail_tv_shzt);
		tvRgzt = (TextView) findViewById(R.id.activity_stock_detail_tv_rgzt);
		btnBuy = (Button) findViewById(R.id.activity_stock_detail_btn_buy);
		btnBuy.setOnClickListener(this);
		rgContent.setOnCheckedChangeListener(this);
		llSyInfo.setVisibility(View.VISIBLE);
		llNormalInfo.setVisibility(View.GONE);
		setData();
		getNumberDetail();
	}

	/** 请求获取最低购买金额 */
	private void getNumberDetail() {
		AjaxParams params = new AjaxParams();
		params.put("fundcode", bean.getFundcode());
		httpRequest.get(Urls.GET_PRODUCT_NUMBER, params, callBack, 1);
	}

	public void setData() {
		tvFundname.setText(bean.getFundname());
		tvDm.setText("代码：" + bean.getFundcode());
		tvJz.setText("净值：" + bean.getUnitNV());
		tvRq.setText("日期：" + bean.getNavdate());
		tvRzdf.setText("日涨跌幅：" + bean.getDayinc());
		tvJyysyl.setText(bean.getRRInSingleMonth() + "%");
		tvJyzsyl.setText(bean.getRRInSingleWeek() + "%");

		tvDwjz.setText(bean.getUnitNV());
		tvJzrq.setText(bean.getNavdate());
		tvLjjz.setText(bean.getTotalnetvalue());
		tvZsyl.setText(bean.getRRSinceStart() + "%");
		tvJdsyl.setText(bean.getRRInThreeMonth() + "%");
		tvBnsyl.setText(bean.getRRInSixMonth() + "%");
		tvYnsyl.setText(bean.getRRInSingleYear() + "%");
		tvSnsyl.setText(bean.getRRInThreeYear() + "%");

		tvJjdm.setText(bean.getFundcode());
		if ("A".equals(bean.getSharetype())) {
			tvFelb.setText("前收费");
		} else {
			tvFelb.setText("后收费");
		}
		tvJjjl.setText(bean.getManager());
		String temp;
		if (!TextUtils.isEmpty(bean.getFundrisklevel())) {
			switch (Integer.valueOf(bean.getFundrisklevel())) {
			case 0:
				temp = "低风险";
				break;
			case 1:
				temp = "中风险";
				break;
			case 2:
				temp = "高风险";
				break;
			case 3:
				temp = "中低风险";
				break;
			default:
				temp = "中高风险";
				break;
			}
		} else {
			temp = "中高风险";
		}
		tvFxdj.setText(temp);
		if (TextUtils.isEmpty(bean.getFundstate())) {
			temp = "基金终止";
		} else {
			switch (Integer.valueOf(bean.getFundstate())) {
			case 0:
				temp = "正常";
				break;
			case 1:
				temp = "发行";
				break;
			case 2:
				temp = "发行成功";
				break;
			case 3:
				temp = "发行失败";
				break;
			case 4:
				temp = "停止交易";
				break;
			case 5:
				temp = "停止申购";
				break;
			case 6:
				temp = "停止赎回";
				break;
			case 7:
				temp = "权益登记";
				break;
			case 8:
				temp = "红利发放";
				break;
			case 9:
				temp = "基金封闭";
				break;
			default:
				temp = "基金终止";
				break;
			}
		}
		tvJjzt.setText(temp);
		if ("1".equals(bean.getDeclarestate())) {
			tvSgzt.setText("可申购");
		} else {
			tvSgzt.setText("不可申购");
		}
		if ("1".equals(bean.getWithdrawstate())) {
			tvShzt.setText("可赎回");
		} else {
			tvShzt.setText("不可赎回");
		}
		if ("1".equals(bean.getSubscribestate())) {
			tvRgzt.setText("可认购");
		} else {
			tvRgzt.setText("不可认购");
		}
	}

	@Override
	protected void doClickAction(int viewId) {
		// TODO Auto-generated method stub
		super.doClickAction(viewId);
		switch (viewId) {
		case R.id.title_iv_left:
			this.finish();
			AnimUtil.pushRightInAndOut(this);
			break;
		case R.id.activity_stock_detail_btn_buy:
			mApplication.fundBean.setFundcode(bean.getFundcode());
			Intent intent = new Intent(this, BuyProductActivity.class);
			intent.putExtra("FundTypeCode", bean.getFundTypeCode());
			intent.putExtra("minPrice", minPrice);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.activity_stock_detail_rb_sy:// 计算收益
			llSyInfo.setVisibility(View.VISIBLE);
			llNormalInfo.setVisibility(View.GONE);
			break;
		case R.id.activity_stock_detail_rb_normal:// 基本信息
			llSyInfo.setVisibility(View.GONE);
			llNormalInfo.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	@Override
	protected void handleJson(int reqeustCode, String jsonString, String message) {
		// TODO Auto-generated method stub
		super.handleJson(reqeustCode, jsonString, message);
		minPrice = jsonString;
	}

}
