package com.zc.addmony.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSInput;

import android.util.Log;

import com.zc.addmony.bean.activities.AddressBean;
import com.zc.addmony.bean.activities.ColorBean;
import com.zc.addmony.bean.activities.OrderDetailBean;
import com.zc.addmony.bean.activities.PhoneBean;
import com.zc.addmony.bean.activities.PhoneTCBean;
import com.zc.addmony.bean.activities.ShowPhoneBean;

/** 解析活动 */
public class LogicActivities {

	/** 解析参加活动的手机列表 */
	public static List<PhoneBean> parsePhoneList(String json) {
		List<PhoneBean> list = new ArrayList<PhoneBean>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				PhoneBean bean = new PhoneBean();
				bean.setPhoneName(obj.optString("name"));
				bean.setPhoneId(obj.optString("M_id"));

				JSONObject content = new JSONObject(obj.optString("content"));
				bean.setPhoneUrl(content.optString("bannerUrl"));
				
				// 颜色
				JSONArray colors = new JSONArray(content.optString("colors"));
				List<ColorBean> listColors = new ArrayList<ColorBean>();
				for (int j = 0; j < colors.length(); j++) {
					JSONObject colorObj = colors.getJSONObject(j);
					ColorBean colorBean = new ColorBean();
					colorBean.setC_id(colorObj.optString("C_id"));
					colorBean.setColor(colorObj.optString("color"));
					colorBean.setM_id(colorObj.optString("M_id"));
					listColors.add(colorBean);
				}
				bean.setPhoneColors(listColors);

				// 套餐
				JSONArray tcArray = new JSONArray(content.optString("tc"));
				List<PhoneTCBean> tcList = new ArrayList<PhoneTCBean>();
				for(int j = 0; j < tcArray.length(); j++){
					PhoneTCBean tcBean = new PhoneTCBean();
					JSONObject tcObj = tcArray.getJSONObject(j);
					tcBean.setContent(tcObj.optString("content"));
					tcBean.setM_id(tcObj.optString("M_id"));
					tcBean.setP_id(tcObj.optString("P_id"));
					tcBean.setP_name(tcObj.optString("p_name"));
					tcBean.setPrice(tcObj.optString("price"));
					tcBean.setShare(tcObj.optString("share"));
					tcList.add(tcBean);
				}
				bean.setPhoneTC(tcList);
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 解析号码归属地
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static List<AddressBean> parseGetAddress(String json) throws JSONException{
		List<AddressBean> list = new ArrayList<AddressBean>();
		JSONArray array = new JSONArray(json);
		int len = array.length();
		for (int i = 0; i < len; i++) {
			AddressBean bean = new AddressBean();
			JSONObject obj = array.getJSONObject(i);
			bean.setAid(obj.optString("aid"));
			bean.setArea(obj.optString("area"));
			bean.setFlag(false);
			list.add(bean);
		}
		return list;
	}
	/**
	 * 解析归属地号码
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static List<ShowPhoneBean> parsePhoneNum(String json) throws JSONException{
		List<ShowPhoneBean> list = new ArrayList<ShowPhoneBean>();
		JSONArray array = new JSONArray(json);
		int len = array.length();
		for (int i = 0; i < len; i++) {
			ShowPhoneBean bean = new ShowPhoneBean();
			JSONObject obj = array.getJSONObject(i);
			bean.setFlag(0);
			bean.setPhone(obj.optString("phone"));
			list.add(bean);
		}
		return list;
	}
	
	/** 解析订单详情*/
	public static OrderDetailBean parseOrderDetail(String json){
		OrderDetailBean bean = new OrderDetailBean();
		try {
			JSONObject obj = new JSONObject(json);
			bean.setO_order(obj.optString("o_order"));
			bean.setC_id(obj.optString("C_id"));
			bean.setN_id(obj.optString("N_id"));
			bean.setM_id(obj.optString("M_id"));
			bean.setO_name(obj.optString("o_name"));
			bean.setO_phone(obj.optString("o_phone"));
			bean.setO_card(obj.optString("o_card"));
			bean.setO_contact(obj.optString("o_contact"));
			bean.setO_remarks(obj.optString("o_remarks"));
			bean.setColor(obj.optString("color"));
			bean.setPhone(obj.optString("phone"));
			bean.setName(obj.optString("name"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
}
