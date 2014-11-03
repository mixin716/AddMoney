package com.zc.addmony.logic;

import org.json.JSONException;
import org.json.JSONObject;

import com.zc.addmony.bean.myproduct.LoginBean;

public class LogicPerson {

	/** 解析登录 */
	public static LoginBean parseLogin(String json) {
		LoginBean bean = new LoginBean();
		try {
			JSONObject objLogin = new JSONObject(json);
			JSONObject obj = new JSONObject(objLogin.optString("user_info"));
			bean.setToken(objLogin.optString("token"));
			bean.setId(Integer.valueOf(obj.optString("id")));
			bean.setFlag(obj.optString("flag"));
			bean.setOpenflag(Integer.valueOf(obj.optString("openflag")));
			bean.setPassword(obj.optString("password"));
			bean.setRegtime(obj.optString("regtime"));
			bean.setTrade_pwd(obj.optString("trade_pwd"));
			bean.setUser_id(Integer.valueOf(obj.optString("user_id")));
			bean.setUser_name(obj.optString("user_name"));
			bean.setRealname(obj.optString("realname"));
			bean.setIdcard(obj.optString("idcard"));
			bean.setPhone(obj.optString("phone"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bean;
	}
}
