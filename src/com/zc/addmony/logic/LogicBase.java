package com.zc.addmony.logic;

import org.json.JSONException;
import org.json.JSONObject;

import com.zc.addmony.bean.BaseBean;


public class LogicBase {
	private static LogicBase instance;

	private LogicBase() {

	}

	public static LogicBase getInstance() {
		if (instance == null) {
			synchronized (LogicBase.class) {
				if (instance == null) {
					instance = new LogicBase();
				}
			}
		}
		return instance;
	}

	
	public BaseBean parseData(String jsonStr) {
		BaseBean baseBean = new BaseBean();
		try {
			JSONObject json = new JSONObject(jsonStr);
			baseBean.setFunction(json.optString("function",""));
			baseBean.setStatus(Integer.valueOf(json.optInt("status", -1)));
			baseBean.setContent(json.optString("content", ""));
			baseBean.setMessage(json.optString("message", ""));

		} catch (JSONException e) {
			e.printStackTrace();
			baseBean.setStatus(-1);
			baseBean.setMessage("");
			baseBean.setContent(jsonStr);
		}
		return baseBean;

	}
}
