package com.zc.addmony.bean;

import java.util.List;

public class HomeBean {
	private List<PaimingBean> paiming;
	private String beishu;
	private TuijianBean tuijian;
	private String imgUrl;// 基金图片
	private String activitysImg;// 活动图片
	private String activitysType;// 活动标记 0未开启 1开启
	private String activitysMessage;// 活动信息
	private String zcbCode;
	private String zcbShareType;
	private String zcbFundtypecode;

	public List<PaimingBean> getPaiming() {
		return paiming;
	}

	public void setPaiming(List<PaimingBean> paiming) {
		this.paiming = paiming;
	}

	public String getBeishu() {
		return beishu;
	}

	public void setBeishu(String beishu) {
		this.beishu = beishu;
	}

	public TuijianBean getTuijian() {
		return tuijian;
	}

	public void setTuijian(TuijianBean tuijian) {
		this.tuijian = tuijian;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getActivitysImg() {
		return activitysImg;
	}

	public void setActivitysImg(String activitysImg) {
		this.activitysImg = activitysImg;
	}

	public String getActivitysType() {
		return activitysType;
	}

	public void setActivitysType(String activitysType) {
		this.activitysType = activitysType;
	}

	public String getActivitysMessage() {
		return activitysMessage;
	}

	public void setActivitysMessage(String activitysMessage) {
		this.activitysMessage = activitysMessage;
	}

	public String getZcbCode() {
		return zcbCode;
	}

	public void setZcbCode(String zcbCode) {
		this.zcbCode = zcbCode;
	}

	public String getZcbShareType() {
		return zcbShareType;
	}

	public void setZcbShareType(String zcbShareType) {
		this.zcbShareType = zcbShareType;
	}

	public String getZcbFundtypecode() {
		return zcbFundtypecode;
	}

	public void setZcbFundtypecode(String zcbFundtypecode) {
		this.zcbFundtypecode = zcbFundtypecode;
	}

}
