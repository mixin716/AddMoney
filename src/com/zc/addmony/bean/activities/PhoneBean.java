package com.zc.addmony.bean.activities;

import java.util.List;

/** 活动手机bean */
public class PhoneBean {

	String phoneName;
	String phoneUrl;
	String phoneId;
	List<PhoneTCBean> phoneTC;// 手机套餐
	String listUrl;// 列表手机图片
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

	public List<PhoneTCBean> getPhoneTC() {
		return phoneTC;
	}

	public void setPhoneTC(List<PhoneTCBean> phoneTC) {
		this.phoneTC = phoneTC;
	}

	public String getListUrl() {
		return listUrl;
	}

	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}

	public List<ColorBean> getPhoneColors() {
		return phoneColors;
	}

	public void setPhoneColors(List<ColorBean> phoneColors) {
		this.phoneColors = phoneColors;
	}

}
