package com.zc.addmony.bean.activities;

/** 用于展示手机号列表*/
public class ShowPhoneBean {

	String phone;// 手机号
	int flag;// 是否被选中标示 0否 1是

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
