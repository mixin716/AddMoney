package com.zc.addmony.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSharedData {

	static SharedPreferences share;
	static Editor edit;

	private static UserSharedData userShare;

	public static UserSharedData getInstance(Context context) {
		if (userShare == null) {
			synchronized (UserSharedData.class) {
				if (userShare == null) {
					userShare = new UserSharedData();
					share = context.getSharedPreferences("user_share_data",
							Context.MODE_WORLD_READABLE
									| Context.MODE_WORLD_WRITEABLE);
					edit = share.edit();
				}
			}
		}
		return userShare;
	}

	/** 保存登录状态 */
	public void SaveFlag(boolean flag) {
		edit.putBoolean("user_flag", flag);
		edit.commit();
	}

	/** 获取登录状态 */
	public boolean GetFlag() {
		return share.getBoolean("user_flag", false);
	}
	
	/** 是否参加过活动*/
	public void SaveActivity(boolean activitys){
		edit.putBoolean("user_activitys", activitys);
		edit.commit();
	}
	/** 是否参加过活动*/
	public boolean GetActivitys(){
		return share.getBoolean("user_activitys", false);
	}

	/** 保存登录session */
	public void SaveSession(String session) {
		edit.putString("login_session", session);
		edit.commit();
	}

	/** 获取登录session */
	public String GetSession() {
		return share.getString("login_session", null);
	}

	/** 保存token */
	public void SaveToken(String token) {
		edit.putString("user_token", token);
		edit.commit();
	}

	/** 获取token */
	public String GetToken() {
		return share.getString("user_token", null);
	}

	/** 保存id */
	public void SaveId(int id) {
		edit.putInt("user_id", id);
		edit.commit();
	}

	/** 获取id */
	public int GetId() {
		return share.getInt("user_id", 0);
	}

	/** 保存用户名--手机号 */
	public void SaveName(String name) {
		edit.putString("user_name", name);
		edit.commit();
	}

	/** 获取用户名--手机号 */
	public String GetName() {
		return share.getString("user_name", null);
	}

	/** 保存用户名--真实 */
	public void SaveRealname(String realname) {
		edit.putString("realname", realname);
		edit.commit();
	}

	/** 获取用户名--真实 */
	public String Getrealname() {
		return share.getString("realname", null);
	}

	/** 保存手机号 */
	public void SavePhone(String phone) {
		edit.putString("user_phone", phone);
		edit.commit();
	}

	/** 获取手机号 */
	public String GetPhone() {
		return share.getString("user_phone", null);
	}

	/** 保存身份证号 */
	public void SaveIdcard(String idcard) {
		edit.putString("idcard", idcard);
		edit.commit();
	}

	/** 获取身份证号 */
	public String GetIdcard() {
		return share.getString("idcard", null);
	}

	/** 保存密码 */
	public void SavePwd(String pwd) {
		edit.putString("user_pwd", pwd);
		edit.commit();
	}

	/** 获取密码 */
	public String GetPwd() {
		return share.getString("user_pwd", null);
	}

	/** 保存开户状态 */
	public void SaveOpenFlag(int openFlag) {
		edit.putInt("user_open", openFlag);
		edit.commit();
	}

	/** 获取开户状态 */
	public int GetOpenFlag() {
		return share.getInt("user_open", 0);
	}

	/** 保存支付密码 */
	public void SaveBuyPwd(String buyPwd) {
		edit.putString("user_buy", buyPwd);
		edit.commit();
	}

	/** 获取支付密码 */
	public String GetBuyPwd() {
		return share.getString("user_buy", null);
	}

	/** 保存银行卡数 */
	public void SaveBankSum(String banksum) {
		edit.putString("user_banksum", banksum);
		edit.commit();
	}

	/** 获取银行卡数 */
	public String GetBankSum() {
		return share.getString("user_banksum", null);
	}

	public void SaveTradeacco(String tradeacco) {
		edit.putString("tradeacco", tradeacco);
		edit.commit();
	}

	public String GetTradeacco() {
		return share.getString("tradeacco", null);
	}
	/** 保存登录账号*/
	public void SaveLoginAccount(String str){
		edit.putString("account", str);
		edit.commit();
	}
	/** 获取登录账号*/
	public String getAccount(){
		return share.getString("account", null);
	}

	/** 清除用户数据 */
	public void clearUserInfomation() {
		this.SaveBankSum(null);
		this.SaveBuyPwd(null);
		this.SaveFlag(false);
		this.SaveActivity(false);
		this.SaveIdcard(null);
		this.SaveName(null);
		this.SaveOpenFlag(0);
		this.SavePhone(null);
		this.SavePwd(null);
		this.SaveRealname(null);
		this.SaveSession(null);
		this.SaveToken(null);
		this.SaveTradeacco(null);
		this.SaveLoginAccount(null);
	}
}
