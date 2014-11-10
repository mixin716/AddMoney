package com.zc.addmony.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zc.addmony.bean.buyproduct.BanksBean;
import com.zc.addmony.bean.buyproduct.BranchBean;
import com.zc.addmony.bean.myproduct.ManageBankBean;

public class LogicBuyProduct {

	/** 解析银行列表 */
	public static List<BanksBean> parseBanksList(String json) {
		List<BanksBean> list = new ArrayList<BanksBean>();
		try {
			JSONObject jsonObj = new JSONObject(json);
			JSONObject listOjb = new JSONObject(jsonObj.optString("results"));
			JSONArray array = new JSONArray(listOjb.optString("cardlist"));
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				BanksBean bean = new BanksBean();
				// bean.setBank_ext(obj.optString("bank_ext"));
				bean.setBank_name(obj.optString("bankname"));
				bean.setBank_num(obj.optString("bankserial"));
				// bean.setBlag(obj.optString("blag"));
				// bean.setId(obj.optString("id"));
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/** 解析支银行列表 */
	public static List<BranchBean> parseBranchList(String json) {
		List<BranchBean> list = new ArrayList<BranchBean>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				BranchBean bean = new BranchBean();
				bean.setVC_BANKNAME(obj.optString("VC_BANKNAME"));
				bean.setVC_BRANCHBANK(obj.optString("VC_BRANCHBANK"));
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	/** 解析已经绑定的银行卡*/
	public static List<ManageBankBean> parseMyBanks(String json){
		List<ManageBankBean> list = new ArrayList<ManageBankBean>();
		try {
			JSONObject jsonObj = new JSONObject(json);
			JSONObject resultObj = new JSONObject(jsonObj.optString("results"));
			JSONArray array = new JSONArray(resultObj.optString("tradeaccolist"));
			for(int i =0; i <array.length(); i++){
				JSONObject obj = array.getJSONObject(i);
				ManageBankBean bean = new ManageBankBean();
				bean.setFlag(1);
				bean.setBankName(obj.optString("bankname"));//银行名字
				bean.setBank_num(obj.optString("bankacco"));//银行卡号
				bean.setBankfullname(obj.optString("bankfullname"));//支行
				bean.setBankacconame(obj.optString("bankacconame"));//开户名字
				bean.setIdno(obj.optString("idno"));//身份证号
				bean.setBankserial(obj.optString("bankserial"));//银行编号
				bean.setTradeacco(obj.optString("tradeacco"));//充值用到标示
				list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

}
