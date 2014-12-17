package com.zc.addmony.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zc.addmony.bean.HomeBean;
import com.zc.addmony.bean.PaimingBean;
import com.zc.addmony.bean.TuijianBean;

public class LogicHome {

	public static HomeBean pareseHome(String json) throws JSONException {
		HomeBean bean = new HomeBean();
		JSONObject obj = new JSONObject(json);
		JSONObject zcbObj = new JSONObject(obj.optString("zcb"));
		bean.setBeishu(zcbObj.optString("beishu"));
		bean.setZcbCode(zcbObj.optString("fundcode"));
		bean.setZcbShareType(zcbObj.optString("sharetype"));
		bean.setZcbFundtypecode(zcbObj.optString("fundtypecode"));
		// JSONArray array = obj.getJSONArray("paiming");
		// List<PaimingBean> paiming = new ArrayList<PaimingBean>();
		// int len = array.length();
		// for (int i = 0; i < len; i++) {
		// JSONObject paiObj = array.getJSONObject(i);
		// PaimingBean paiBean = new PaimingBean();
		// paiBean.setId(paiObj.optString("id"));
		// paiBean.setIncome(paiObj.optString("Income"));
		// paiBean.setUsername(paiObj.optString("username"));
		// paiming.add(paiBean);
		// }
		JSONObject tuiObj = obj.getJSONObject("tuijian");
		TuijianBean tuiBean = new TuijianBean();
		tuiBean.setBought(tuiObj.optString("bought"));
		tuiBean.setBuyavg(tuiObj.optString("buyavg"));
		tuiBean.setCurrentInterest(tuiObj.optString("currentInterest"));
		tuiBean.setFundcode(tuiObj.optString("fundcode"));
		tuiBean.setFundCompany(tuiObj.optString("fundCompany"));
		tuiBean.setFundname(tuiObj.optString("fundname"));
		tuiBean.setFundstate(tuiObj.optString("fundstate"));
		tuiBean.setFundtotalshare(tuiObj.optString("fundtotalshare"));
		tuiBean.setFundtype(tuiObj.optString("fundtype"));
		tuiBean.setHf_incomeratio(tuiObj.optString("hf_incomeratio"));
		tuiBean.setHx_f_incomeratio(tuiObj.optString("hx_f_incomeratio"));
		tuiBean.setHx_hf_incomeratio(tuiObj.optString("hx_hf_incomeratio"));
		tuiBean.setId(tuiObj.optString("id"));
		tuiBean.setIncome(tuiObj.optString("income"));
		tuiBean.setIncomeratio(tuiObj.optString("incomeratio"));
		tuiBean.setIs_index(tuiObj.optString("is_index"));
		tuiBean.setIsmoneyfund(tuiObj.optString("ismoneyfund"));
		tuiBean.setLogo(tuiObj.optString("logo"));
		tuiBean.setLyzf(tuiObj.optString("lyzf"));
		tuiBean.setMinprice(tuiObj.optString("minprice"));
		tuiBean.setNavdate(tuiObj.optString("navdate"));
		tuiBean.setPernetvalue(tuiObj.optString("pernetvalue"));
		tuiBean.setQrnh(tuiObj.optString("qrnh"));
		tuiBean.setRisklevel(tuiObj.optString("risklevel"));
		tuiBean.setSharetype(tuiObj.optString("sharetype"));
		tuiBean.setTotalnetvalue(tuiObj.optString("totalnetvalue"));
		tuiBean.setYnzf(tuiObj.optString("ynzf"));
		tuiBean.setBuyinfo(tuiObj.optString("buyinfo"));

		JSONObject imgObj = new JSONObject(obj.optString("imgInfo"));
		bean.setImgUrl(imgObj.optString("imgUrl"));

		JSONObject bannerObj = new JSONObject(obj.optString("banner"));
		bean.setActivitysImg(bannerObj.optString("url"));
		bean.setActivitysMessage(bannerObj.optString("message"));
		bean.setActivitysType(bannerObj.optString("type"));

		// bean.setPaiming(paiming);
		bean.setTuijian(tuiBean);
		return bean;

	}

}
