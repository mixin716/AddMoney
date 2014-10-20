package com.zc.addmony.bean.activities;

import java.util.List;

import com.zc.addmony.adapter.activities.ColorBean;

/** 活动手机bean */
public class PhoneBean {

	String phoneName;
	String phoneUrl;
	String phoneId;
	String phoneTc;// 手机套餐
	List<ColorBean> phoneColors;// 手机颜色

	public String getPhoneName() {
		return phoneName;
	}

	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}

	public String getPhoneUrl() {
		return phoneUrl;
	}

	public void setPhoneUrl(String phoneUrl) {
		this.phoneUrl = phoneUrl;
	}

	public String getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}

	public String getPhoneTc() {
		return phoneTc;
	}

	public void setPhoneTc(String phoneTc) {
		this.phoneTc = phoneTc;
	}

	public List<ColorBean> getPhoneColors() {
		return phoneColors;
	}

	public void setPhoneColors(List<ColorBean> phoneColors) {
		this.phoneColors = phoneColors;
	}

	

}
