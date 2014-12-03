package com.zc.addmony.logic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zc.addmony.bean.myproduct.BuyProductsBean;

/** 解析我的増财*/
public class LogicMyProduct {

	/** 解析购买的基金*/
	public static List<BuyProductsBean> parseBuyProducts(String json){
		List<BuyProductsBean> list = new ArrayList<BuyProductsBean>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				JSONObject fundinfoObj = new JSONObject(obj.optString("fundinfo"));
				BuyProductsBean bean = new BuyProductsBean();
				bean.setmFundcode(obj.optString("fundcode"));
				bean.setmFundname(obj.optString("fundname"));
				bean.setmSharetype(obj.optString("sharetype"));
				bean.setShuhui(obj.optString("shuhui"));//赎回标记
				bean.setmName(obj.optString("fundname"));//基金名字
				bean.setmBank(obj.optString("bankacco"));//银行卡
				bean.setmBankName(obj.optString("bankname"));//银行名字
				bean.setmWf(fundinfoObj.optString("hfincomeratio"));//万分收益
				bean.setmHave(obj.optString("currentremainshare"));//持有金额
				bean.setmNot(obj.optString("freezeremainshare"));//未结算收益
				float aaa,bbb,ccc = 0;
				aaa = Float.valueOf(fundinfoObj.optString("hfincomeratio"));
				bbb = Float.valueOf(obj.optString("currentremainshare"));
				ccc = aaa*bbb;
				DecimalFormat decimalFormat=new DecimalFormat("0.00");
				bean.setmYestorday(decimalFormat.format(ccc));
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
}
