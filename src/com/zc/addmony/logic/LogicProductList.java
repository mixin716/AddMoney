package com.zc.addmony.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.zc.addmony.bean.TuijianBean;
import com.zc.addmony.bean.productlist.ProductBean;
import com.zc.addmony.bean.productlist.ProductListBean;

public class LogicProductList {

	/** 解析产品列表 */
	public static List<ProductBean> parseProductList(String json) {
		List<ProductBean> list = new ArrayList<ProductBean>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				ProductBean bean = new ProductBean();
				bean.setBought(obj.optString("bought"));
				bean.setFundcode(obj.optString("fundcode"));
				bean.setFundCompany(obj.optString("fundCompany"));
				bean.setFundname(obj.optString("fundname"));
				bean.setHf_incomeratio(obj.optString("hf_incomeratio"));
				bean.setIncomeratio(obj.optString("incomeratio"));
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public static TuijianBean parseProductDetail(String json)
			throws JSONException {
		JSONObject tuiObj = new JSONObject(json);
		TuijianBean tuiBean = new TuijianBean();
		tuiBean.setBought(tuiObj.optString("bought"));
		tuiBean.setBuyavg(tuiObj.optString("buyavg"));
		tuiBean.setCurrentInterest(tuiObj.optString("currentInterest"));
		tuiBean.setFundcode(tuiObj.optString("fundcode"));
		tuiBean.setFundCompany(tuiObj.optString("fundCompany"));
		tuiBean.setFundname(tuiObj.optString("fundname"));
		tuiBean.setFundstate(tuiObj.optString("fundstate"));
		tuiBean.setFundtotalshare(tuiObj.optString("fundtotalshare"));
		tuiBean.setHf_incomeratio(tuiObj.optString("hf_incomeratio"));
		tuiBean.setHx_f_incomeratio(tuiObj.optString("hx_f_incomeratio"));
		tuiBean.setHx_hf_incomeratio(tuiObj.optString("hx_hf_incomeratio"));
		tuiBean.setId(tuiObj.optString("id"));
		tuiBean.setIncome(tuiObj.optString("income"));
		tuiBean.setIncomeratio(tuiObj.optString("incomeratio"));
		tuiBean.setIs_index(tuiObj.optString("is_index"));
		tuiBean.setLogo(tuiObj.optString("logo"));
		tuiBean.setLyzf(tuiObj.optString("lyzf"));
		tuiBean.setMinprice(tuiObj.optString("minprice"));
		tuiBean.setPernetvalue(tuiObj.optString("pernetvalue"));
		tuiBean.setQrnh(tuiObj.optString("qrnh"));
		tuiBean.setTotalnetvalue(tuiObj.optString("totalnetvalue"));
		tuiBean.setYnzf(tuiObj.optString("ynzf"));
		return tuiBean;

	}

	/** 解析当日交易申请列表 */
	public static List<ProductBean> parseTodayDealList(String json) {
		List<ProductBean> list = new ArrayList<ProductBean>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				ProductBean bean = new ProductBean();
				bean.setFundcode(obj.optString("fundcode"));
				bean.setFundname(obj.optString("fundname"));
				bean.setApplysum(obj.optString("applysum"));
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/** 解析产品列表2 */
	public static List<ProductListBean> parseProductListElse(String json) {
		List<ProductListBean> list = new ArrayList<ProductListBean>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				ProductListBean bean = new ProductListBean();
				bean.setFundcode(obj.optString("fundcode"));
				bean.setFundname(obj.optString("fundname"));
				bean.setNavdate(obj.optString("navdate"));
				bean.setRRInSingleMonth(getTwoNum(obj
						.optString("RRInSingleMonth")));
				bean.setRRInSingleWeek(getTwoNum(obj
						.optString("RRInSingleWeek")));
				bean.setRRInSingleYear(getTwoNum(obj
						.optString("RRInSingleYear")));
				bean.setRRInSixMonth(getTwoNum(obj.optString("RRInSixMonth")));
				bean.setRRInThreeMonth(getTwoNum(obj
						.optString("RRInThreeMonth")));
				bean.setUnitNV(getFourNum(obj.optString("UnitNV")));
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	private static String getFourNum(String num) {
		if (TextUtils.isEmpty(num)) {
			return "0.0000";
		} else if (num.substring(num.indexOf("."), num.length()).length() > 4) {
			return num.substring(0, num.indexOf(".") + 5);
		} else {
			return num;
		}

	}

	private static String getTwoNum(String num) {
		if (TextUtils.isEmpty(num)) {
			return "0.00";
		} else if (num.substring(num.indexOf("."), num.length()).length() > 2) {
			return num.substring(0, num.indexOf(".") + 3);
		} else {
			return num;
		}

	}
}
