package com.zc.addmony.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.zc.addmony.bean.myproduct.BuyProductsBean;
import com.zc.addmony.bean.myproduct.SaleBean;

/** 解析我的増财 */
public class LogicMyProduct {

	/** 解析购买的基金 */
	public static List<BuyProductsBean> parseBuyProducts(String json) {
		List<BuyProductsBean> list = new ArrayList<BuyProductsBean>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				BuyProductsBean bean = new BuyProductsBean();
				JSONObject obj = array.getJSONObject(i);
				if (!TextUtils.isEmpty(obj.optString("fundinfo"))
						&& !"null".equals(obj.optString("fundinfo"))) {
					JSONObject fundinfoObj = new JSONObject(
							obj.optString("fundinfo"));
					bean.setmWf(fundinfoObj.optString("DailyProfit"));// 万分收益
					bean.setUnitNV(fundinfoObj.optString(obj.optString("UnitNV")));
					bean.setmFundTypeCode(fundinfoObj.optString("FundTypeCode"));
					// float aaa, bbb, ccc = 0;
					// aaa =
					// Float.valueOf(fundinfoObj.optString("DailyProfit"));
					// bbb = Float.valueOf(obj.optString("currentremainshare"));
					// ccc = aaa * bbb;
					// DecimalFormat decimalFormat = new DecimalFormat("0.00");
					// bean.setmYestorday(decimalFormat.format(ccc));
				}
				bean.setMarketvalue(obj.optString("marketvalue"));
				bean.setmFundcode(obj.optString("fundcode"));
				bean.setmFundname(obj.optString("fundname"));
				bean.setmSharetype(obj.optString("sharetype"));
				bean.setmName(obj.optString("fundname"));// 基金名字
				bean.setmBank(obj.optString("bankacco"));// 银行卡
				bean.setmBankName(obj.optString("bankname"));// 银行名字
				bean.setmHave(obj.optString("usableremainshare"));// 持有金额
				bean.setmNot(obj.optString("unpaidincome"));// 未结算收益

				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	/** 解析赎回*/
	public static List<SaleBean> parseSale(String json){
		List<SaleBean> list = new ArrayList<SaleBean>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				SaleBean bean = new SaleBean();
				JSONObject obj = array.getJSONObject(i);
				bean.setBankacco( obj.optString("bankacco"));
				bean.setBankname(obj.optString("bankname"));
				bean.setFundname(obj.optString("fundname"));
				bean.setFundTypeCode("1101");
				bean.setUnpaidincome( obj.optString("unpaidincome"));
				bean.setUsableremainshare(obj.optString("usableremainshare"));
				bean.setSharetype(obj.optString("sharetype"));
				bean.setFundcode(obj.optString("fundcode"));
				bean.setMarketvalue(obj.optString("marketvalue"));
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
